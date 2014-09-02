package cn.edu.nju.zsyy.Fragments.Feature;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.Fragments.WebFragment;
import cn.edu.nju.zsyy.adapter.CascadeFatherAdapter_GetInfo;
import cn.edu.nju.zsyy.adapter.CascadeSubAdapter_GetInfo;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.bean.KeshiInfo;
import cn.edu.nju.zsyy.engine.KeshiInfoParser;

public class ZhongDianZhuanKe extends Fragment
{
	private static final String TAG = "ZhongDianZhuanKe";
	private LinearLayout lcontent=null;
	private int lcontentid=Constants.ZHONGDIANZHUANKE*0xFFFFFF;
	
	private ListView listview_keshi_left=null;
	private ListView listview_keshi_right=null;
	private CascadeFatherAdapter_GetInfo ladapter=null;
	private CascadeSubAdapter_GetInfo radapter=null;
	private int lastsel=-1;//选中大科室的第一个子科室位置
	private int selleft=0;
	private int selright=0;
	private List<KeshiInfo> info=null;//大科室数组
	private int[] imgs=null;
	
	
	/**
	 * 设置需要的参数
	 * @param info	科室数据
	 * @return
	 */
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
		
		listview_keshi_right=new ListView(getActivity());
		lcontent=new LinearLayout(getActivity());
		lcontent.setOrientation(LinearLayout.HORIZONTAL);
		lcontent.setId(lcontentid);
		
		radapter=new CascadeSubAdapter_GetInfo(getActivity(),info.get(selleft).getFirstindex(),info.get(selleft).getLastindex(),Constants.keshilist);
		listview_keshi_right.setAdapter(radapter);
		listview_keshi_right.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
			{					
				selright=position;
				String urlext=Constants.keshilist.get(lastsel+position).getDetailurl();
				gotoUrlPage(urlext,lcontentid);
			}
		});				
		
		listview_keshi_left=new ListView(getActivity());
		ladapter=new CascadeFatherAdapter_GetInfo(getActivity(),info,imgs);
		listview_keshi_left.setAdapter(ladapter);
		listview_keshi_left.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
			{		
				selleft=position;
				ladapter.setSelectedPosition(position);
				ladapter.notifyDataSetChanged();
				if(info.get(position).isHasChildren())
				{
					radapter.setData(info.get(position).getFirstindex(),info.get(position).getLastindex());
					lastsel=info.get(position).getFirstindex();
					radapter.notifyDataSetChanged();
				}
				else
				{
					gotoUrlPage(info.get(position).getDetailurl(),lcontentid);
				}
			}
		});
		
		
		LinearLayout.LayoutParams lp1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,0.618f);
		LinearLayout.LayoutParams lp2=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,0.382f);
		
		ladapter.setSelectedPosition(selleft);
		ladapter.notifyDataSetChanged();
		
		listview_keshi_left.setLayoutParams(lp1);
		listview_keshi_right.setLayoutParams(lp2);
		lcontent.removeAllViews();
		lcontent.addView(listview_keshi_left);
		lcontent.addView(listview_keshi_right);
		
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
