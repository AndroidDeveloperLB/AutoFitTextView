package com.example.autofittextviewsample;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils.TruncateAt;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.lb.auto_fit_textview.AutoResizeTextView;

public class MainActivity extends Activity
  {
  // private final Random _random =new Random();
  // private static final String ALLOWED_CHARACTERS ="qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
  private EditText  _contentEditText;
  private ViewGroup _textViewcontainer;
  private SeekBar   _widthSeekBar;
  private SeekBar   _heightSeekBar;
  private TextView  _linesCountTextView;

  @Override
  protected void onCreate(final Bundle savedInstanceState)
    {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    _textViewcontainer=(ViewGroup)findViewById(R.id.container);
    _contentEditText=(EditText)findViewById(R.id.contentEditText);
    _contentEditText.addTextChangedListener(new TextWatcher()
      {
        @Override
        public void onTextChanged(final CharSequence s,final int start,final int before,final int count)
          {}

        @Override
        public void beforeTextChanged(final CharSequence s,final int start,final int count,final int after)
          {}

        @Override
        public void afterTextChanged(final Editable s)
          {
          recreateTextView();
          }
      });
    _widthSeekBar=(SeekBar)findViewById(R.id.widthSeekBar);
    _heightSeekBar=(SeekBar)findViewById(R.id.heightSeekBar);
    final OnSeekBarChangeListener seekBarChangeListener=new OnSeekBarChangeListener()
      {
        @Override
        public void onStopTrackingTouch(final SeekBar seekBar)
          {}

        @Override
        public void onStartTrackingTouch(final SeekBar seekBar)
          {}

        @Override
        public void onProgressChanged(final SeekBar seekBar,final int progress,final boolean fromUser)
          {
          recreateTextView();
          }
      };
    _heightSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    _widthSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    _linesCountTextView=(TextView)findViewById(R.id.linesCountTextView);
    findViewById(R.id.plusLineCountButton).setOnClickListener(new OnClickListener()
      {
        @Override
        public void onClick(final View v)
          {
          int maxLinesCount=Integer.parseInt(_linesCountTextView.getText().toString());
          _linesCountTextView.setText(Integer.toString(++maxLinesCount));
          recreateTextView();
          }
      });
    findViewById(R.id.minusLineCountButton).setOnClickListener(new OnClickListener()
      {
        @Override
        public void onClick(final View v)
          {
          int maxLinesCount=Integer.parseInt(_linesCountTextView.getText().toString());
          if(maxLinesCount==1)
            return;
          _linesCountTextView.setText(Integer.toString(--maxLinesCount));
          recreateTextView();
          }
      });
    runJustBeforeBeingDrawn(_textViewcontainer,new Runnable()
      {
        @Override
        public void run()
          {
          recreateTextView();
          }
      });
    }

  protected void recreateTextView()
    {
    _textViewcontainer.removeAllViews();
    final int maxWidth=_textViewcontainer.getWidth();
    final int maxHeight=_textViewcontainer.getHeight();
    final AutoResizeTextView textView=new AutoResizeTextView(MainActivity.this);
    textView.setGravity(Gravity.CENTER);
    final int width=_widthSeekBar.getProgress()*maxWidth/_widthSeekBar.getMax();
    final int height=_heightSeekBar.getProgress()*maxHeight/_heightSeekBar.getMax();
    final int maxLinesCount=Integer.parseInt(_linesCountTextView.getText().toString());
    textView.setMaxLines(maxLinesCount);
    textView.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,maxHeight,getResources().getDisplayMetrics()));
    textView.setEllipsize(TruncateAt.END);
    // since we use it only once per each click, we don't need to cache the results, ever
    textView.setLayoutParams(new LayoutParams(width,height));
    textView.setBackgroundColor(0xff00ff00);
    final String text=_contentEditText.getText().toString();
    textView.setText(text);
    _textViewcontainer.addView(textView);
    }

  // private String getRandomText()
  // {
  // final int textLength=_random.nextInt(20)+1;
  // final StringBuilder builder=new StringBuilder();
  // for(int i=0;i<textLength;++i)
  // builder.append(ALLOWED_CHARACTERS.charAt(_random.nextInt(ALLOWED_CHARACTERS.length())));
  // return builder.toString();
  // }
  @Override
  public boolean onCreateOptionsMenu(final Menu menu)
    {
    getMenuInflater().inflate(R.menu.main,menu);
    return true;
    }

  @Override
  public boolean onOptionsItemSelected(final MenuItem item)
    {
    String url=null;
    switch(item.getItemId())
      {
      case R.id.menuItem_all_my_apps:
        url="https://play.google.com/store/apps/developer?id=AndroidDeveloperLB";
        break;
      case R.id.menuItem_all_my_repositories:
        url="https://github.com/AndroidDeveloperLB";
        break;
      case R.id.menuItem_current_repository_website:
        url="https://github.com/AndroidDeveloperLB/AutoFitTextView";
        break;
      }
    if(url==null)
      return true;
    final Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(url));
    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
    startActivity(intent);
    return true;
    }

  public static void runJustBeforeBeingDrawn(final View view,final Runnable runnable)
    {
    final OnPreDrawListener preDrawListener=new OnPreDrawListener()
      {
        @Override
        public boolean onPreDraw()
          {
          runnable.run();
          view.getViewTreeObserver().removeOnPreDrawListener(this);
          return true;
          }
      };
    view.getViewTreeObserver().addOnPreDrawListener(preDrawListener);
    }
  }
