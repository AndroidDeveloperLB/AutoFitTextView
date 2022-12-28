package com.example.autofittextviewsample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils.TruncateAt
import android.text.TextWatcher
import android.util.TypedValue
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup.LayoutParams
import android.view.ViewTreeObserver.OnPreDrawListener
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.example.autofittextviewsample.databinding.ActivityMainBinding
import com.lb.auto_fit_textview.AutoResizeTextView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.contentEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {
                recreateTextView()
            }
        })
        val seekBarChangeListener = object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                recreateTextView()
            }
        }
        binding.heightSeekBar.setOnSeekBarChangeListener(seekBarChangeListener)
        binding.widthSeekBar.setOnSeekBarChangeListener(seekBarChangeListener)
        findViewById<View>(R.id.plusLineCountButton).setOnClickListener {
            var maxLinesCount = Integer.parseInt(binding.linesCountTextView.text.toString())
            binding.linesCountTextView.text = (++maxLinesCount).toString()
            recreateTextView()
        }
        findViewById<View>(R.id.minusLineCountButton).setOnClickListener(OnClickListener {
            var maxLinesCount = Integer.parseInt(binding.linesCountTextView.text.toString())
            if (maxLinesCount == 1)
                return@OnClickListener
            binding.linesCountTextView.text = (--maxLinesCount).toString()
            recreateTextView()
        })
        runJustBeforeBeingDrawn(binding.textViewContainer) { recreateTextView() }


    }

    private fun recreateTextView() {
        binding.textViewContainer.removeAllViews()
        val maxWidth = binding.textViewContainer.width
        val maxHeight = binding.textViewContainer.height
        val textView = AutoResizeTextView(this@MainActivity)
        textView.gravity = Gravity.CENTER
        val width = binding.widthSeekBar.progress * maxWidth / binding.widthSeekBar.max
        val height = binding.heightSeekBar.progress * maxHeight / binding.heightSeekBar.max
        val maxLinesCount = Integer.parseInt(binding.linesCountTextView.text.toString())
        textView.maxLines = maxLinesCount
        textView.textSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            maxHeight.toFloat(),
            resources.displayMetrics
        )
        textView.ellipsize = TruncateAt.END
        // since we use it only once per each click, we don't need to cache the results, ever
        textView.layoutParams = LayoutParams(width, height)
        textView.setBackgroundColor(-0xff0100)
        val text = binding.contentEditText.text.toString()
        textView.text = text
        binding.textViewContainer.addView(textView)
    }

    // private String getRandomText()
    // {
    // final int textLength=_random.nextInt(20)+1;
    // final StringBuilder builder=new StringBuilder();
    // for(int i=0;i<textLength;++i)
    // builder.append(ALLOWED_CHARACTERS.charAt(_random.nextInt(ALLOWED_CHARACTERS.length())));
    // return builder.toString();
    // }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var url: String? = null
        when (item.itemId) {
            R.id.menuItem_all_my_apps -> url =
                "https://play.google.com/store/apps/developer?id=AndroidDeveloperLB"

            R.id.menuItem_all_my_repositories -> url = "https://github.com/AndroidDeveloperLB"
            R.id.menuItem_current_repository_website -> url =
                "https://github.com/AndroidDeveloperLB/AutoFitTextView"

            R.id.menuItem_show_recyclerViewSample -> {
                startActivity(Intent(this, Main2Activity::class.java))
                return true
            }
        }
        if (url == null)
            return true
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        startActivity(intent)
        return true
    }

    companion object {

        fun runJustBeforeBeingDrawn(view: View, runnable: Runnable) {
            val preDrawListener = object : OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    runnable.run()
                    view.viewTreeObserver.removeOnPreDrawListener(this)
                    return true
                }
            }
            view.viewTreeObserver.addOnPreDrawListener(preDrawListener)
        }
    }
}
