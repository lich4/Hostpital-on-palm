package cn.edu.nju.zsyy.bean;

import java.util.ArrayList;
import java.util.List;

import android.view.*;
import android.widget.*;

public class Groups
{
  private List<Group> groups;
  private List<View> groupsViews;
  private TextView previousSelectedTextView;
  private View previousSelectedView;

  public Groups(List<Group> groupList)
  {
    groups = groupList;
    groupsViews = new ArrayList<View>();
    for(int i=0;i<groups.size();i++)
    {
    	groupsViews.add(null);
    }
  }

  public void clearSelection()
  {
    previousSelectedView = null;
  }

  public Group get(int index)
  {
    return (Group)groups.get(index);
  }

  public View getGroupView(int index)
  {
    return (View)groupsViews.get(index);
  }

  public List<Group> getGroups()
  {
    return groups;
  }

  public void onItemSelected(View v, int index)
  {
  }

  public void onResume()
  {
  }

  public void setGroupView(int index, View v)
  {
    groupsViews.set(index, v);
  }

  public void setGroups(List<Group> groupList)
  {
    groups = groupList;
  }

  public int size()
  {
    return groups.size();
  }
}