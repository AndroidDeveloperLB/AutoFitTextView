package com.example.autofittextviewsample

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils.TruncateAt
import android.text.TextWatcher
import android.util.TypedValue
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.view.ViewTreeObserver.OnPreDrawListener
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.MenuProvider
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

        binding.plusLineCountButton.setOnClickListener {
            var maxLinesCount = binding.linesCountTextView.text.toString().toInt()
            binding.linesCountTextView.text = (++maxLinesCount).toString()
            recreateTextView()
        }

        binding.minusLineCountButton.setOnClickListener {
            var maxLinesCount = binding.linesCountTextView.text.toString().toInt()
            if (maxLinesCount == 1) return@setOnClickListener
            binding.linesCountTextView.text = (--maxLinesCount).toString()
            recreateTextView()
        }

        runJustBeforeBeingDrawn(binding.textViewContainer) { recreateTextView() }

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.addSubMenu("More info").let { subMenu ->
                    subMenu.setIcon(android.R.drawable.ic_menu_info_details)
                    subMenu.item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                    subMenu.add("show RecyclerView sample").setOnMenuItemClickListener {
                        startActivity(Intent(this@MainActivity, Main2Activity::class.java))
                        true
                    }
                    subMenu.add("Repository website").setOnMenuItemClickListener(
                        createUrlMenuItemClickListener("https://github.com/AndroidDeveloperLB/AutoFitTextView")
                    )
                    subMenu.add("All my repositories").setOnMenuItemClickListener(
                        createUrlMenuItemClickListener("https://github.com/AndroidDeveloperLB")
                    )
                    subMenu.add("All my apps").setOnMenuItemClickListener(
                        createUrlMenuItemClickListener("https://play.google.com/store/apps/developer?id=AndroidDeveloperLB")
                    )
                }
            }

            private fun createUrlMenuItemClickListener(url: String): MenuItem.OnMenuItemClickListener {
                return MenuItem.OnMenuItemClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                    startActivity(intent)
                    true
                }
            }

            override fun onMenuItemSelected(item: MenuItem): Boolean = true
        })
    }

    private fun recreateTextView() {
        binding.textViewContainer.removeAllViews()
        val maxWidth = binding.textViewContainer.width
        val maxHeight = binding.textViewContainer.height
        if (maxWidth <= 0 || maxHeight <= 0) return

        val textView = AutoResizeTextView(this@MainActivity)
        textView.gravity = Gravity.CENTER
        val width = binding.widthSeekBar.progress * maxWidth / binding.widthSeekBar.max
        val height = binding.heightSeekBar.progress * maxHeight / binding.heightSeekBar.max
        val maxLinesCount = binding.linesCountTextView.text.toString().toInt()
        textView.maxLines = maxLinesCount
        textView.textSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            maxHeight.toFloat(),
            resources.displayMetrics
        )
        textView.ellipsize = TruncateAt.END
        textView.layoutParams = LayoutParams(width, height)
        textView.setBackgroundColor(-0xff0100)
        textView.text = binding.contentEditText.text.toString()
        binding.textViewContainer.addView(textView)
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
