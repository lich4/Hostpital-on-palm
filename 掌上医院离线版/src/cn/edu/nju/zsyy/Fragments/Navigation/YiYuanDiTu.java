package cn.edu.nju.zsyy.Fragments.Navigation;

import java.util.List;

import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.adapter.CascadeFatherAdapter_GetInfo;
import cn.edu.nju.zsyy.adapter.CascadeSubAdapter_GetInfo;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.bean.PositionInfo;
import cn.edu.nju.zsyy.engine.PositionParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class YiYuanDiTu extends Fragment
{
	private static final String TAG="YiYuanDiTu";
	private LinearLayout lcontent=null;
	private int lcontentid=Constants.YIYUANDITU*0xFFFFFF;
	private ListView llv=null;
	private ListView rlv=null;
	private CascadeSubAdapter_GetInfo radapter=null;
	private CascadeFatherAdapter_GetInfo ladapter=null;
	
	private int lastsel=-1;
	private int selleft=0;
	private int selright=0;
	
	private List<PositionInfo> info=null;
	private int[] imgs=null;
	
	public Fragment SetParam(List<PositionInfo> info)
	{
		this.info=info;
		return this;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		if(info != null)
		{
			imgs=new int[info.size()];
			for(int i=0;i<imgs.length;i++)
			{
				imgs[i]=R.drawable.listitem_icon;
			}
			if(info.size() != 0)
			{
				lastsel=info.get(0).getFirstindex();
			}
		}
		
		lcontent=new LinearLayout(getActivity());
		lcontent.setOrientation(LinearLayout.HORIZONTAL);
		lcontent.setId(lcontentid);	
		
		rlv=new ListView(getActivity());
		radapter=new CascadeSubAdapter_GetInfo(getActivity(),info.get(0).getFirstindex(),info.get(0).getLastindex(),Constants.positionlist);
		rlv.setAdapter(radapter);			
		
		llv=new ListView(getActivity());
		ladapter=new CascadeFatherAdapter_GetInfo(getActivity(),info,imgs);
		llv.setAdapter(ladapter);
		llv.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
			{		
				selleft=0;
				ladapter.setSelectedPosition(position);
				ladapter.notifyDataSetChanged();
				if(info.get(position).isHasChildren())
				{
					radapter.setData(info.get(position).getFirstindex(), info.get(position).getLastindex());
					lastsel=info.get(position).getFirstindex();
					radapter.notifyDataSetChanged();
				}
			}
		});
		
		LinearLayout.LayoutParams lp1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,0.618f);
		LinearLayout.LayoutParams lp2=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,0.382f);
		llv.setLayoutParams(lp1);
		rlv.setLayoutParams(lp2);
		lcontent.addView(llv);
		lcontent.addView(rlv);
		return lcontent;
	}
}
