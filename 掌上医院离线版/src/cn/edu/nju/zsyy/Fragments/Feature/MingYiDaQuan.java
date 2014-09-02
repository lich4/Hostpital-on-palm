package cn.edu.nju.zsyy.Fragments.Feature;

import java.util.List;

import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.Fragments.WebFragment;
import cn.edu.nju.zsyy.adapter.CascadeFatherAdapter_GetInfo;
import cn.edu.nju.zsyy.adapter.CascadeSubAdapter_GetInfo;
import cn.edu.nju.zsyy.adapter.ListViewYishengAdapter;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.bean.DoctorsInfo;
import cn.edu.nju.zsyy.bean.KeshiInfo;
import cn.edu.nju.zsyy.engine.DoctorParser;
import cn.edu.nju.zsyy.engine.KeshiInfoParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MingYiDaQuan extends Fragment
{
	private static final String TAG = "MingYiDaQuan";
	private LinearLayout lcontent=null;
	private int lcontentid=Constants.MINGYIDAQUAN*0xFFFFFF;
	
	private ListView listview_yisheng_left=null;
	private ListView listview_yisheng_right=null;
	private int lastsel=-1;//选中大科室的第一个子科室位置
	private int selleft=0;
	private int selright=0;
	
	private List<KeshiInfo> info=null;//大科室数组
	private int[] imgs=null;
	private CascadeFatherAdapter_GetInfo ladapter=null;
	private CascadeSubAdapter_GetInfo radapter=null;
	private List<DoctorsInfo> curlist=null;
	
	public Fragment SetParam(List<KeshiInfo> info)
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
		lcontent.setId(lcontentid);
		lcontent.setOrientation(LinearLayout.HORIZONTAL);	
		
		listview_yisheng_right=new ListView(getActivity());
		
		radapter=new CascadeSubAdapter_GetInfo(getActivity(),info.get(0).getFirstindex(),info.get(0).getLastindex(),Constants.keshilist);
		listview_yisheng_right.setAdapter(radapter);
		listview_yisheng_right.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
			{
				selright=position;
				curlist=DoctorParser.getKeshiDoctors(Constants.keshilist.get(lastsel+position));
				listview_yisheng_right.setAdapter(new ListViewYishengAdapter(getActivity(),curlist));
				listview_yisheng_right.setOnItemClickListener(new OnItemClickListener()
				{
					@Override
					public void onItemClick(AdapterView<?> parent,View view, int position, long id) 
					{
						selright=position;
						gotoUrlPage(curlist.get(position).getDetailurl(),lcontentid);
					}
				});
			}
		});				
		
		listview_yisheng_left=new ListView(getActivity());
		ladapter=new CascadeFatherAdapter_GetInfo(getActivity(),info,imgs);
		listview_yisheng_left.setAdapter(ladapter);
		listview_yisheng_left.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
			{		
				selleft=position;
				ladapter.setSelectedPosition(position);
				ladapter.notifyDataSetChanged();
				if(info.get(position).isHasChildren())
				{
					lastsel=info.get(position).getFirstindex();
					radapter=new CascadeSubAdapter_GetInfo(getActivity(),info.get(position).getFirstindex(),info.get(position).getLastindex(),Constants.keshilist);
					listview_yisheng_right.setAdapter(radapter);
					listview_yisheng_right.setOnItemClickListener(new OnItemClickListener()
					{
						@Override
						public void onItemClick(AdapterView<?> parent, View view,int position, long id)
						{
							selleft=position;
							curlist=DoctorParser.getKeshiDoctors(Constants.keshilist.get(lastsel+position));
							listview_yisheng_right.setAdapter(new ListViewYishengAdapter(getActivity(),curlist));
							listview_yisheng_right.setOnItemClickListener(new OnItemClickListener()
							{
								@Override
								public void onItemClick(AdapterView<?> parent,View view, int position, long id) 
								{
									selright=position;
									gotoUrlPage(Constants.urlroot+curlist.get(position).getDetailurl(),lcontentid);
								}
							});
						}
					});	
					radapter.notifyDataSetChanged();
				}
				else
				{					
					curlist=DoctorParser.getKeshiDoctors(info.get(position));
					listview_yisheng_right.setAdapter(new ListViewYishengAdapter(getActivity(),curlist));
					listview_yisheng_right.setOnItemClickListener(new OnItemClickListener()
					{
						@Override
						public void onItemClick(AdapterView<?> parent,View view, int position, long id) 
						{
							selright=position;
							gotoUrlPage(Constants.urlroot+curlist.get(position).getDetailurl(),lcontentid);
						}
					});
				}
			}
		});
		
		LinearLayout.LayoutParams lp1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,0.618f);
		LinearLayout.LayoutParams lp2=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,0.382f);
		
		listview_yisheng_left.setLayoutParams(lp1);
		listview_yisheng_right.setLayoutParams(lp2);
		lcontent.removeAllViews();
		lcontent.addView(listview_yisheng_left);
		lcontent.addView(listview_yisheng_right);
		
		return lcontent;
	}
	
	/**
	 * 加载Web页面-可设置网址
	 * @param url	目标网址
	 * @param id	要替换的控件id
	 */
	public void gotoUrlPage(String url, int id)
	{
		FragmentTransaction tran=getFragmentManager().beginTransaction();
		((LinearLayout)getActivity().findViewById(id)).removeAllViews();
		Fragment temp=new WebFragment().SetParam(Constants.urlroot+url,"");
		tran.replace(id,temp).addToBackStack(null).commit();
	}
}
