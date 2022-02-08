package com.example.autofittextviewsample

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnClickListener
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import kotlinx.android.synthetic.main.activity_resize.*

class DynamicResizeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resize)
        contentEditText!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {
                contentTextView!!.text = contentEditText!!.text.toString()
            }
        })
        startSeekBar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                topStartMargin!!.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    // textViewContainer!!.width / 2 so each margin only goes to the center
                    width = 1.coerceAtLeast(startSeekBar!!.progress * textViewContainer!!.width / 2 / startSeekBar!!.max)
                }
            }
        })
        topSeekBar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                topStartMargin!!.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    // textViewContainer!!.height / 2 so each margin only goes to the center
                    height = 1.coerceAtLeast(topSeekBar!!.progress * textViewContainer!!.height / 2 / topSeekBar!!.max)
                }
            }
        })
        endSeekBar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                bottomEndMargin!!.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    // textViewContainer!!.width / 2 so each margin only goes to the center
                    width = 1.coerceAtLeast(endSeekBar!!.progress * textViewContainer!!.width / 2 / endSeekBar!!.max)
                }
            }
        })
        bottomSeekBar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                bottomEndMargin!!.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    // textViewContainer!!.height / 2 so each margin only goes to the center
                    height = 1.coerceAtLeast(bottomSeekBar!!.progress * textViewContainer!!.height / 2 / bottomSeekBar!!.max)
                }
            }
        })
        findViewById<View>(R.id.plusLineCountButton).setOnClickListener {
            var maxLinesCount = Integer.parseInt(linesCountTextView!!.text.toString())
            linesCountTextView!!.text = Integer.toString(++maxLinesCount)
            contentTextView!!.maxLines = maxLinesCount
        }
        findViewById<View>(R.id.minusLineCountButton).setOnClickListener(OnClickListener {
            var maxLinesCount = Integer.parseInt(linesCountTextView!!.text.toString())
            if (maxLinesCount == 1)
                return@OnClickListener
            linesCountTextView!!.text = Integer.toString(--maxLinesCount)
            contentTextView!!.maxLines = maxLinesCount
        })
        // synchronize contentTextView to the controls
        contentTextView!!.text = contentEditText!!.text.toString()
        contentTextView!!.maxLines = Integer.parseInt(linesCountTextView!!.text.toString())
    }
}
