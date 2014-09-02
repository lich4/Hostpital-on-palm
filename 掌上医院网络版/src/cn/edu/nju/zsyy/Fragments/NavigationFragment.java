package cn.edu.nju.zsyy.Fragments;

import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.controls.PullToRefreshListView;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
public class NavigationFragment extends Fragment
{//¾ÍÒ½µ¼º½
	private int type=0;
	
	public Fragment SetType(int type)
	{
		this.type=type;
		return this;
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		activity.setTitle(R.drawable.jiuyidaohang_title);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup viewGroup, Bundle bundle) 
	{
		View v = inflater.inflate(R.layout.fragment_jiuyidaohang,viewGroup, false);
		return v;
	}
}
