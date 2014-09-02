package cn.edu.nju.zsyy.Fragments;

import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import cn.edu.nju.zsyy.MainActivity;
import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.adapter.CascadeFatherAdapter_GetInfo;
import cn.edu.nju.zsyy.adapter.CascadeSubAdapter_GetInfo;
import cn.edu.nju.zsyy.adapter.ListViewKeshiAdapter;
import cn.edu.nju.zsyy.adapter.ListViewYishengAdapter;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.bean.DoctorsInfo;
import cn.edu.nju.zsyy.bean.KeshiInfo;
import cn.edu.nju.zsyy.controls.PullToRefreshListView;
import cn.edu.nju.zsyy.engine.DoctorParser;
import cn.edu.nju.zsyy.engine.KeshiInfoParser;
import cn.edu.nju.zsyy.utils.UIHelper;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.*;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class FeatureFragment extends Fragment implements OnClickListener
{//医院特色
	private int type=0;
	private LayoutInflater inflater=null;
	private LinearLayout lcontent=null;
	private Button jianjie=null;
	private Button zhongdianzhuanke=null;
	private Button mingyidaquan=null;
	private FragmentManager manager=null;
	private PullToRefreshListView listview_keshi_left=null;
	private PullToRefreshListView listview_keshi_right=null;
	private PullToRefreshListView listview_yisheng_left=null;
	private PullToRefreshListView listview_yisheng_right=null;
	private List<KeshiInfo> info=null;//大科室数组
	private int lastsel=-1;//选中大科室的第一个子科室位置
	private int[] imgs=null;
	private CascadeFatherAdapter_GetInfo ladapter=null;
	private CascadeSubAdapter_GetInfo radapter=null;
	private List<DoctorsInfo> curlist=null;
	
	public Fragment SetType(int type)
	{
		this.type=type;
		return this;
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		activity.setTitle(R.drawable.yiyuantese_title);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup viewGroup,Bundle bundle)
	{
		manager=getFragmentManager();
		this.inflater=inflater;
		View featureview=inflater.inflate(R.layout.fragment_yiyuantese,null,false);
		lcontent=(LinearLayout)featureview.findViewById(R.id.lcontainer);
		jianjie=(Button)featureview.findViewById(R.id.jianjie);
		zhongdianzhuanke=(Button)featureview.findViewById(R.id.zhongdianzhuanke);
		mingyidaquan=(Button)featureview.findViewById(R.id.mingyidaquan);
		jianjie.setEnabled(true);
		zhongdianzhuanke.setEnabled(true);
		mingyidaquan.setEnabled(true);
		
		jianjie.setOnClickListener(this);
		zhongdianzhuanke.setOnClickListener(this);
		mingyidaquan.setOnClickListener(this);
		
		if(info == null)
		{
			info=KeshiInfoParser.getBigClass();
			imgs=new int[info.size()];
			for(int i=0;i<imgs.length;i++)
			{
				imgs[i]=R.drawable.icon1;
			}
			if(info.size() != 0)
			{
				lastsel=info.get(0).getFirstindex();
			}
		}
		lcontent.removeAllViews();
		
		switch(type)
		{
			case Constants.JIANJIE:
			{
				jianjie.setBackgroundDrawable(getResources().getDrawable(R.drawable.frame_button_p));
				WebView mWebView=new WebView(getActivity());
				LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1);
				WebSettings webSettings = mWebView.getSettings();
				webSettings.setLoadWithOverviewMode(true);
				mWebView.setBackgroundColor(Color.TRANSPARENT);
				mWebView.setVerticalScrollBarEnabled(false);
				mWebView.setHorizontalScrollBarEnabled(false);
				mWebView.setWebChromeClient(new WebChromeClient());
				mWebView.setWebViewClient(new WebViewClient());
				mWebView.loadUrl(Constants.urlroot+Constants.yiyuanjianjiefile);
				mWebView.setLayoutParams(lp);
				mWebView.setVisibility(View.VISIBLE);
				mWebView.setBackgroundColor(Color.WHITE);
				lcontent.addView(mWebView);
				break;
			}
				
			case Constants.ZHONGDIANZHUANKE:
			{
				zhongdianzhuanke.setBackgroundDrawable(getResources().getDrawable(R.drawable.frame_button_p));
				lcontent.setOrientation(LinearLayout.HORIZONTAL);
				
				LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1);	
				
				listview_keshi_right=new PullToRefreshListView(getActivity());
				
				if(lastsel > 0)
				{
					radapter=new CascadeSubAdapter_GetInfo(getActivity(),info.get(0).getFirstindex(),info.get(0).getLastindex());
					listview_keshi_right.setAdapter(radapter);
					listview_keshi_right.setOnItemClickListener(new OnItemClickListener()
					{
						@Override
						public void onItemClick(AdapterView<?> parent, View view,int position, long id)
						{					
							position--;//特殊的PullToRefreshListView;
							String urlext=Constants.keshilist.get(lastsel+position).getDetailurl();
							gotoUrlPage(urlext);
						}
					});				
				}
				
				listview_keshi_left=new PullToRefreshListView(getActivity());
				ladapter=new CascadeFatherAdapter_GetInfo(getActivity(),info,imgs);
				listview_keshi_left.setAdapter(ladapter);
				listview_keshi_left.setOnItemClickListener(new OnItemClickListener()
				{
					@Override
					public void onItemClick(AdapterView<?> parent, View view,int position, long id)
					{		
						position--;//特殊的PullToRefreshListView;
						ladapter.setSelectedPosition(position);
						ladapter.notifyDataSetChanged();
						if(info.get(position).isHasChildren())
						{
							radapter.setData(info.get(position).getFirstindex(),info.get(position).getLastindex());
							radapter.notifyDataSetChanged();
							lastsel=info.get(position).getFirstindex();
						}
						else
						{
							gotoUrlPage(info.get(position).getDetailurl());
						}
					}
				});
				
				listview_keshi_left.setLayoutParams(lp);
				listview_keshi_right.setLayoutParams(lp);
				lcontent.addView(listview_keshi_left);
				lcontent.addView(listview_keshi_right);
				break;
			}
				
			case Constants.MINGYIDAQUAN:
			{
				mingyidaquan.setBackgroundDrawable(getResources().getDrawable(R.drawable.frame_button_p));
				lcontent.setOrientation(LinearLayout.HORIZONTAL);
				
				LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1);	
				
				listview_yisheng_right=new PullToRefreshListView(getActivity());
				
				if(lastsel > 0)
				{
					radapter=new CascadeSubAdapter_GetInfo(getActivity(),info.get(0).getFirstindex(),info.get(0).getLastindex());
					listview_yisheng_right.setAdapter(radapter);
					listview_yisheng_right.setOnItemClickListener(new OnItemClickListener()
					{
						@Override
						public void onItemClick(AdapterView<?> parent, View view,int position, long id)
						{
							position--;//特殊的PullToRefreshListView;
							curlist=DoctorParser.getKeshiDoctors(Constants.keshilist.get(lastsel+position));
							listview_yisheng_right.setAdapter(new ListViewYishengAdapter(getActivity(),curlist));
							listview_yisheng_right.setOnItemClickListener(new OnItemClickListener()
							{
								@Override
								public void onItemClick(AdapterView<?> parent,View view, int position, long id) 
								{
									position--;//特殊的PullToRefreshListView;
									FragmentTransaction tran=getFragmentManager().beginTransaction();
									String urlext=curlist.get(position).getDetailurl();
									Fragment temp=new WebFragment().SetParam(Constants.urlroot+urlext,"");
									tran.replace(R.id.container,temp).addToBackStack(null).commit();
									Constants.fstack.push(temp);
								}
							});
						}
					});				
				}
				
				listview_yisheng_left=new PullToRefreshListView(getActivity());
				ladapter=new CascadeFatherAdapter_GetInfo(getActivity(),info,imgs);
				listview_yisheng_left.setAdapter(ladapter);
				listview_yisheng_left.setOnItemClickListener(new OnItemClickListener()
				{
					@Override
					public void onItemClick(AdapterView<?> parent, View view,int position, long id)
					{		
						position--;//特殊的PullToRefreshListView;
						ladapter.setSelectedPosition(position);
						ladapter.notifyDataSetChanged();
						if(info.get(position).isHasChildren())
						{
							lastsel=info.get(position).getFirstindex();
							radapter=new CascadeSubAdapter_GetInfo(getActivity(),info.get(position).getFirstindex(),info.get(position).getLastindex());
							listview_yisheng_right.setAdapter(radapter);
							listview_yisheng_right.setOnItemClickListener(new OnItemClickListener()
							{
								@Override
								public void onItemClick(AdapterView<?> parent, View view,int position, long id)
								{
									position--;//特殊的PullToRefreshListView;
									curlist=DoctorParser.getKeshiDoctors(Constants.keshilist.get(lastsel+position));
									listview_yisheng_right.setAdapter(new ListViewYishengAdapter(getActivity(),curlist));
									listview_yisheng_right.setOnItemClickListener(new OnItemClickListener()
									{
										@Override
										public void onItemClick(AdapterView<?> parent,View view, int position, long id) 
										{
											position--;//特殊的PullToRefreshListView;
											FragmentTransaction tran=getFragmentManager().beginTransaction();
											String urlext=curlist.get(position).getDetailurl();
											Fragment temp=new WebFragment().SetParam(Constants.urlroot+urlext,"");
											tran.replace(R.id.container,temp).addToBackStack(null).commit();
											Constants.fstack.push(temp);
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
									position--;//特殊的PullToRefreshListView;
									FragmentTransaction tran=getFragmentManager().beginTransaction();
									String urlext=curlist.get(position).getDetailurl();
									Fragment temp=new WebFragment().SetParam(Constants.urlroot+urlext,"");
									tran.replace(R.id.container,temp).addToBackStack(null).commit();
									Constants.fstack.push(temp);
								}
							});
						}
					}
				});
				
				listview_yisheng_left.setLayoutParams(lp);
				listview_yisheng_right.setLayoutParams(lp);
				lcontent.addView(listview_yisheng_left);
				lcontent.addView(listview_yisheng_right);
				break;
			}
		}
		
		return featureview;
	}
	
	public void gotoUrlPage(String url)
	{
		FragmentTransaction tran=manager.beginTransaction();
		Fragment temp=new WebFragment().SetParam(Constants.urlroot+url,"");
		tran.replace(R.id.lcontainer,temp).addToBackStack(null).commit();
		Constants.fstack.push(temp);
	}
	
	@Override
	public void onClick(View v) 
	{
		manager=getFragmentManager();
		
		switch(v.getId())
		{
			case R.id.jianjie:
				Constants.set(Constants.JIANJIE, new FeatureFragment().SetType(Constants.JIANJIE));
				manager.beginTransaction().replace(R.id.container,Constants.get(Constants.JIANJIE)).commit();
				Constants.fstack.push(Constants.get(Constants.JIANJIE));
				break;
				
			case R.id.zhongdianzhuanke:
				Constants.set(Constants.ZHONGDIANZHUANKE, new FeatureFragment().SetType(Constants.ZHONGDIANZHUANKE));
				manager.beginTransaction().replace(R.id.container,Constants.get(Constants.ZHONGDIANZHUANKE)).commit();
				Constants.fstack.push(Constants.get(Constants.ZHONGDIANZHUANKE));
				break;
				
			case R.id.mingyidaquan:
				Constants.set(Constants.MINGYIDAQUAN, new FeatureFragment().SetType(Constants.MINGYIDAQUAN));
				manager.beginTransaction().replace(R.id.container,Constants.get(Constants.MINGYIDAQUAN)).commit();
				Constants.fstack.push(Constants.get(Constants.MINGYIDAQUAN));
				break;
		}
	}
}
