package cn.edu.nju.zsyy.bean;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.*;

public class Group
{
  private List<String> children;
  private List<Fragment> childrenFragments;
  private Fragment groupFragment;
  private String groupName;

  public Group(String name, Fragment fragment)
  {
    groupName = name;
    groupFragment = fragment;
    children = new ArrayList<String>();
    childrenFragments = new ArrayList<Fragment>();
  }

  public Group(String groupName, List<String> nameList, List<Fragment> fragmentList)
  {
    this.groupName = groupName;
    children = new ArrayList<String>();
    children.addAll(nameList);
    childrenFragments = new ArrayList<Fragment>();
    childrenFragments.addAll(fragmentList);
  }

  public List<String> getChildren()
  {
    return children;
  }

  public List<Fragment> getChildrenFragments()
  {
    return childrenFragments;
  }

  public Fragment getGroupFragment()
  {
    return groupFragment;
  }

  public String getGroupName()
  {
    return groupName;
  }

  public void setChildren(List<String> childList)
  {
    children.clear();
    children.addAll(childList);
  }

  public void setChildrenFragments(List<Fragment> fragmentList)
  {
    childrenFragments.clear();
    childrenFragments.addAll(fragmentList);
  }

  public void setGroupFragment(Fragment fragment)
  {
    groupFragment = fragment;
  }

  public void setGroupName(String name)
  {
    groupName = name;
  }
}