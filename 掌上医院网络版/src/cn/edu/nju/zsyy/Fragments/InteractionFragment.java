package cn.edu.nju.zsyy.Fragments;

import cn.edu.nju.zsyy.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class InteractionFragment extends Fragment
{//Ò½»¼»¥¶¯
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
		activity.setTitle(R.drawable.wodefuwu_title);
	}
	
//	@Override
//	public View onCreateView(LayoutInflater inflater,ViewGroup viewGroup, Bundle bundle) 
//	{
//		View v = inflater.inflate(R.layout.fragment_yuyueguahao,viewGroup, false);
//		return v;
//	}
}
