package com.lb.auto_fit_textview

import android.content.Context
import android.graphics.RectF
import android.graphics.Typeface
import android.os.Build
import android.text.Layout.Alignment
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView

/**
 * a textView that is able to self-adjust its font size depending on the min and max size of the font, and its own size.<br></br>
 * code is heavily based on this StackOverflow thread:
 * http://stackoverflow.com/questions/16017165/auto-fit-textview-for-android/21851239#21851239
 */
class AutoResizeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = android.R.attr.textViewStyle
) : AppCompatTextView(context, attrs, defStyle) {
    private val availableSpaceRect = RectF()
    private val sizeTester: SizeTester
    private var maxTextSize: Float = 0f
    private var spacingMult = 1.0f
    private var spacingAdd = 0.0f
    private var minTextSize: Float = 0f
    private var widthLimit: Int = 0
    private var maxLinesValue: Int = -1
    private var initialized = false
    private var textPaint: TextPaint

    private interface SizeTester {
        /**
         * @param suggestedSize  Size of text to be tested
         * @param availableSpace available space in which text must fit
         * @return an integer < 0 if after applying `suggestedSize` to
         * text, it takes less space than `availableSpace`, > 0
         * otherwise
         */
        fun onTestSize(suggestedSize: Int, availableSpace: RectF): Int
    }

    init {
        // using the minimal recommended font size
        minTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12f, resources.displayMetrics)
        maxTextSize = textSize
        textPaint = TextPaint(paint)
        maxLinesValue = maxLines
        
        // prepare size tester:
        sizeTester = object : SizeTester {
            val textRect = RectF()

            override fun onTestSize(suggestedSize: Int, availableSpace: RectF): Int {
                textPaint.textSize = suggestedSize.toFloat()
                val transformationMethod = transformationMethod
                val textToTest: String = transformationMethod?.getTransformation(text, this@AutoResizeTextView)
                    ?.toString()
                    ?: text.toString()
                
                val singleLine = maxLinesValue == 1
                if (singleLine) {
                    textRect.bottom = textPaint.fontSpacing
                    textRect.right = textPaint.measureText(textToTest)
                } else {
                    val layout: StaticLayout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        StaticLayout.Builder.obtain(textToTest, 0, textToTest.length, textPaint, widthLimit)
                            .setLineSpacing(spacingAdd, spacingMult)
                            .setAlignment(Alignment.ALIGN_NORMAL)
                            .setIncludePad(true)
                            .build()
                    } else {
                        @Suppress("DEPRECATION")
                        StaticLayout(textToTest, textPaint, widthLimit, Alignment.ALIGN_NORMAL, spacingMult, spacingAdd, true)
                    }
                    // return early if we have more lines
                    if (maxLinesValue != NO_LINE_LIMIT && layout.lineCount > maxLinesValue)
                        return 1
                    textRect.bottom = layout.height.toFloat()
                    var maxWidth = -1f
                    val lineCount = layout.lineCount
                    for (i in 0 until lineCount) {
                        val end = layout.getLineEnd(i)
                        if (i < lineCount - 1 && end > 0 && !isValidWordWrap(textToTest[end - 1]))
                            return 1
                        val lineWidth = layout.getLineRight(i) - layout.getLineLeft(i)
                        if (maxWidth < lineWidth)
                            maxWidth = lineWidth
                    }
                    textRect.right = maxWidth
                }
                textRect.offsetTo(0f, 0f)
                return if (availableSpace.contains(textRect)) -1 else 1
            }
        }
        initialized = true
    }

    private fun isValidWordWrap(c: Char): Boolean = c == ' ' || c == '-'

    override fun setAllCaps(allCaps: Boolean) {
        super.setAllCaps(allCaps)
        adjustTextSize()
    }

    override fun setTypeface(tf: Typeface?) {
        super.setTypeface(tf)
        adjustTextSize()
    }

    override fun setTextSize(size: Float) {
        maxTextSize = size
        adjustTextSize()
    }

    override fun setMaxLines(maxLines: Int) {
        super.setMaxLines(maxLines)
        this.maxLinesValue = maxLines
        adjustTextSize()
    }

    override fun getMaxLines(): Int = maxLinesValue

    @Deprecated("Deprecated in Java", ReplaceWith("maxLines = 1"))
    override fun setSingleLine() {
        @Suppress("DEPRECATION")
        super.setSingleLine()
        maxLinesValue = 1
        adjustTextSize()
    }

    @Deprecated("Deprecated in Java", ReplaceWith("maxLines = if (singleLine) 1 else -1"))
    override fun setSingleLine(singleLine: Boolean) {
        @Suppress("DEPRECATION")
        super.setSingleLine(singleLine)
        maxLinesValue = if (singleLine) 1 else NO_LINE_LIMIT
        adjustTextSize()
    }

    override fun setLines(lines: Int) {
        super.setLines(lines)
        maxLinesValue = lines
        adjustTextSize()
    }

    override fun setTextSize(unit: Int, size: Float) {
        maxTextSize = TypedValue.applyDimension(unit, size, context.resources.displayMetrics)
        adjustTextSize()
    }

    override fun setLineSpacing(add: Float, mult: Float) {
        super.setLineSpacing(add, mult)
        spacingMult = mult
        spacingAdd = add
    }

    /**
     * Set the lower text size limit and invalidate the view
     */
    fun setMinTextSize(minTextSize: Float) {
        this.minTextSize = minTextSize
        adjustTextSize()
    }

    private fun adjustTextSize() {
        if (!initialized) return
        val startSize = minTextSize.toInt()
        val heightLimit = measuredHeight - compoundPaddingBottom - compoundPaddingTop
        widthLimit = measuredWidth - compoundPaddingLeft - compoundPaddingRight
        if (widthLimit <= 0) return
        textPaint = TextPaint(paint)
        availableSpaceRect.right = widthLimit.toFloat()
        availableSpaceRect.bottom = heightLimit.toFloat()
        val calculatedSize = binarySearch(startSize, maxTextSize.toInt(), sizeTester, availableSpaceRect)
        super.setTextSize(TypedValue.COMPLEX_UNIT_PX, calculatedSize.toFloat())
    }

    private fun binarySearch(start: Int, end: Int, sizeTester: SizeTester, availableSpace: RectF): Int {
        var lastBest = start
        var lo = start
        var hi = end - 1
        var mid: Int
        while (lo <= hi) {
            mid = (lo + hi).ushr(1)
            val midValCmp = sizeTester.onTestSize(mid, availableSpace)
            if (midValCmp < 0) {
                lastBest = mid
                lo = mid + 1
            } else if (midValCmp > 0) {
                hi = mid - 1
            } else {
                return mid
            }
        }
        return lastBest
    }

    override fun onTextChanged(text: CharSequence, start: Int, before: Int, after: Int) {
        super.onTextChanged(text, start, before, after)
        adjustTextSize()
    }

    override fun onSizeChanged(width: Int, height: Int, oldwidth: Int, oldheight: Int) {
        super.onSizeChanged(width, height, oldwidth, oldheight)
        if (width != oldwidth || height != oldheight)
            adjustTextSize()
    }

    companion object {
        private const val NO_LINE_LIMIT = -1
    }
}
