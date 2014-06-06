package com.example.autofittextviewsample;
import java.util.Random;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import com.example.autofittextviewtest.AutoResizeTextView;

public class MainActivity extends Activity
  {
  private final Random        _random            =new Random();
  private static final String ALLOWED_CHARACTERS ="qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

  @Override
  protected void onCreate(final Bundle savedInstanceState)
    {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    final ViewGroup container=(ViewGroup)findViewById(R.id.container);
    final TextView descriptionTextView=(TextView)findViewById(R.id.descTextView);
    findViewById(R.id.randomizButton).setOnClickListener(new OnClickListener()
      {
        @Override
        public void onClick(final View v)
          {
          container.removeAllViews();
          final int maxWidth=container.getWidth();
          final int maxHeight=container.getHeight();
          final AutoResizeTextView textView=new AutoResizeTextView(MainActivity.this);
          textView.setGravity(Gravity.CENTER);
          final int width=_random.nextInt(maxWidth)+1;
          final int height=_random.nextInt(maxHeight)+1;
          final int maxLines=_random.nextInt(4)+1;
          textView.setMaxLines(maxLines);
          textView.setTextSize(500);
          textView.setEllipsize(TruncateAt.END);
          // since we use it only once per each click, we don't need to cache the results, ever
          textView.setEnableSizeCache(false);
          textView.setEnableSizeCache(false);
          textView.setLayoutParams(new LayoutParams(width,height));
          textView.setBackgroundColor(0xff00ff00);
          final String text=getRandomText();
          descriptionTextView.setText("length:"+text.length()+" maxLines:"+maxLines+" width:"+width+" height:"+height);
          textView.setText(text);
          container.addView(textView);
          }
      });
    }

  private String getRandomText()
    {
    final int textLength=_random.nextInt(20)+1;
    final StringBuilder builder=new StringBuilder();
    for(int i=0;i<textLength;++i)
      builder.append(ALLOWED_CHARACTERS.charAt(_random.nextInt(ALLOWED_CHARACTERS.length())));
    return builder.toString();
    }

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
        url="https://play.google.com/store/apps/developer?id=Liran+Barsisa";
        break;
      case R.id.menuItem_all_my_repositories:
        url="https://github.com/AndroidDeveloperLB";
        break;
      case R.id.menuItem_current_repository_website:
        url="https://github.com/AndroidDeveloperLB/AndroidJniBitmapOperations";
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
  }
