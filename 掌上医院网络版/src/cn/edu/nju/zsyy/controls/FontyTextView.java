package cn.edu.nju.zsyy.controls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontyTextView extends TextView
{
  private static Typeface lanting;

  public FontyTextView(Context context)
  {
    super(context);
    setTypeface(lanting);
  }

  public FontyTextView(Context context, AttributeSet attrs)
  {
    super(context, attrs);
    setTypeface(lanting);
  }

  public FontyTextView(Context context, AttributeSet attrs, int style)
  {
    super(context, attrs, style);
    setTypeface(lanting);
  }

  public static void setLanting(Typeface typeface)
  {
    lanting = typeface;
  }
}