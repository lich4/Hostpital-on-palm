package cn.edu.nju.zsyy.drawer;

import android.view.*;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

public class ExpandCollapseAnimation extends Animation
{
  public static final int COLLAPSE = 1;
  public static final int EXPAND = 0;
  private View mAnimatedView;
  private int mEndHeight;
  private LinearLayout.LayoutParams mLayoutParams;
  private int mType;

  public ExpandCollapseAnimation(View paramView, int paramInt)
  {
    this.mAnimatedView = paramView;
    this.mEndHeight = this.mAnimatedView.getMeasuredHeight();
    this.mLayoutParams = ((LinearLayout.LayoutParams)paramView.getLayoutParams());
    this.mType = paramInt;
    if (this.mType == 0);
    for (this.mLayoutParams.bottomMargin = (-this.mEndHeight); ; this.mLayoutParams.bottomMargin = 0)
    {
      paramView.setVisibility(0);
      return;
    }
  }

  protected void applyTransformation(float paramFloat, Transformation paramTransformation)
  {
    super.applyTransformation(paramFloat, paramTransformation);
    if (paramFloat < 1.0F)
    {
      if (this.mType == 0);
      for (this.mLayoutParams.bottomMargin = (-this.mEndHeight + (int)(paramFloat * this.mEndHeight)); ; this.mLayoutParams.bottomMargin = (-(int)(paramFloat * this.mEndHeight)))
      {
        this.mAnimatedView.requestLayout();
        return;
      }
    }
    if (this.mType == 0)
    {
      this.mLayoutParams.bottomMargin = 0;
      this.mAnimatedView.requestLayout();
      return;
    }
    this.mLayoutParams.bottomMargin = (-this.mEndHeight);
    this.mAnimatedView.setVisibility(8);
    this.mAnimatedView.requestLayout();
  }
}