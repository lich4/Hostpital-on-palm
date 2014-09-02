package cn.edu.nju.zsyy.adapter;

import cn.edu.nju.zsyy.R;
import android.view.View;
import android.widget.ListAdapter;

public class SlideExpandableListAdapter extends AbstractSlideExpandableListAdapter
{
  private int expandable_view_id;

  public SlideExpandableListAdapter(ListAdapter paramListAdapter)
  {
    this(paramListAdapter, R.id.expandable);
  }

  public SlideExpandableListAdapter(ListAdapter paramListAdapter, int paramInt)
  {
    super(paramListAdapter);
    this.expandable_view_id = paramInt;
  }

  public View getExpandableView(View paramView)
  {
    return paramView.findViewById(this.expandable_view_id);
  }
}