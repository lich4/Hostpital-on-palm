package cn.edu.nju.zsyy.controls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class FontyEditText extends EditText
{
  private static Typeface lanting;

  public FontyEditText(Context paramContext)
  {
    super(paramContext);
    setTypeface(lanting);
  }

  public FontyEditText(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setTypeface(lanting);
  }

  public FontyEditText(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setTypeface(lanting);
  }

  public static void setLanting(Typeface paramTypeface)
  {
    lanting = paramTypeface;
  }
}