package cn.edu.nju.zsyy.adapter;

import java.util.BitSet;

import cn.edu.nju.zsyy.drawer.ExpandCollapseAnimation;
import cn.edu.nju.zsyy.drawer.WrapperListAdapterImpl;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseIntArray;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

public abstract class AbstractSlideExpandableListAdapter extends WrapperListAdapterImpl
{
  private int animationDuration = 330;
  private View lastOpen = null;
  private int lastOpenPosition = -1;
  private BitSet openItems = new BitSet();
  private final SparseIntArray viewHeights = new SparseIntArray(10);

  public AbstractSlideExpandableListAdapter(ListAdapter paramListAdapter)
  {
    super(paramListAdapter);
  }

  private void animateView(View paramView, int paramInt)
  {
    ExpandCollapseAnimation localExpandCollapseAnimation = new ExpandCollapseAnimation(paramView, paramInt);
    localExpandCollapseAnimation.setDuration(getAnimationDuration());
    paramView.startAnimation(localExpandCollapseAnimation);
  }

  private void enableFor(int paramInt, View paramView)
  {
    if ((paramView == this.lastOpen) && (paramInt != this.lastOpenPosition))
      this.lastOpen = null;
    if (paramInt == this.lastOpenPosition)
      this.lastOpen = paramView;
    if (this.viewHeights.get(paramInt, -1) == -1)
    {
      this.viewHeights.put(paramInt, paramView.getMeasuredHeight());
      updateExpandable(paramView, paramInt);
      return;
    }
    updateExpandable(paramView, paramInt);
  }

  private static BitSet readBitSet(Parcel paramParcel)
  {
    int i = paramParcel.readInt();
    BitSet localBitSet = new BitSet();
    for (int j = 0; j < i; j++)
      localBitSet.set(paramParcel.readInt());
    return localBitSet;
  }

  private void updateExpandable(View paramView, int paramInt)
  {
    LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams)paramView.getLayoutParams();
    if (this.openItems.get(paramInt))
    {
      paramView.setVisibility(0);
      localLayoutParams.bottomMargin = 0;
      return;
    }
    paramView.setVisibility(8);
    localLayoutParams.bottomMargin = (0 - this.viewHeights.get(paramInt));
  }

  private static void writeBitSet(Parcel paramParcel, BitSet paramBitSet)
  {
    int i = -1;
    paramParcel.writeInt(paramBitSet.cardinality());
    while (true)
    {
      i = paramBitSet.nextSetBit(i + 1);
      if (i == -1)
        break;
      paramParcel.writeInt(i);
    }
  }

  public boolean collapseLastOpen()
  {
    if (isAnyItemExpanded())
    {
      if (this.lastOpen != null)
        animateView(this.lastOpen, 1);
      this.openItems.set(this.lastOpenPosition, false);
      this.lastOpenPosition = -1;
      return true;
    }
    return false;
  }

  public void enableFor(View paramView, int paramInt)
  {
    View localView = getExpandableView(paramView);
    localView.measure(paramView.getWidth(), paramView.getHeight());
    enableFor(paramInt, localView);
  }

  public int getAnimationDuration()
  {
    return this.animationDuration;
  }

  public abstract View getExpandableView(View paramView);

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView = this.wrapped.getView(paramInt, paramView, paramViewGroup);
    enableFor(localView, paramInt);
    return localView;
  }

  public boolean isAnyItemExpanded()
  {
    return this.lastOpenPosition != -1;
  }

  public void onRestoreInstanceState(SavedState paramSavedState)
  {
    this.lastOpenPosition = paramSavedState.lastOpenPosition;
    this.openItems = paramSavedState.openItems;
  }

  public Parcelable onSaveInstanceState(Parcelable paramParcelable)
  {
    SavedState localSavedState = new SavedState(paramParcelable);
    localSavedState.lastOpenPosition = this.lastOpenPosition;
    localSavedState.openItems = this.openItems;
    return localSavedState;
  }

  public void rememberExpand(int paramInt, boolean paramBoolean)
  {
    this.openItems.set(paramInt, paramBoolean);
  }

  public void setAnimationDuration(int paramInt)
  {
    if (paramInt < 0)
      throw new IllegalArgumentException("Duration is less than zero");
    this.animationDuration = paramInt;
  }

  static class SavedState extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new SavedState(paramAnonymousParcel);
      }

      public AbstractSlideExpandableListAdapter.SavedState[] newArray(int paramAnonymousInt)
      {
        return new AbstractSlideExpandableListAdapter.SavedState[paramAnonymousInt];
      }
    };
    public int lastOpenPosition = -1;
    public BitSet openItems = null;

    private SavedState(Parcel paramParcel)
    {
      super(paramParcel);
      this.lastOpenPosition = paramParcel.readInt();
      this.openItems = AbstractSlideExpandableListAdapter.readBitSet(paramParcel);
    }

    SavedState(Parcelable paramParcelable)
    {
      super(paramParcelable);
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.lastOpenPosition);
      AbstractSlideExpandableListAdapter.writeBitSet(paramParcel, this.openItems);
    }
  }
}