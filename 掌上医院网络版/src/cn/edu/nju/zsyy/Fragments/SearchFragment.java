package cn.edu.nju.zsyy.Fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.bean.SearchData;
import cn.edu.nju.zsyy.controls.FontyTextView;
import cn.edu.nju.zsyy.controls.PullToRefreshListView;
import cn.edu.nju.zsyy.engine.SearchDataParser;

public class SearchFragment extends Fragment 
{
	private PullToRefreshListView search_result=null;//只显示前100
	private ImageButton search_searchbutton=null;
	private EditText search_searchstring=null;
	private List<SearchData> diseasedata=new ArrayList<SearchData>();
	private List<YiyuanData> yiyuandata=new ArrayList<YiyuanData>();
	
	public class YiyuanData
	{
		private int index;//在各自list中的位置
		private int type;//类型
		
		public YiyuanData(int index,int type)
		{
			this.index=index;
			this.type=type;
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.fragment_search,container, false);
		search_result=(PullToRefreshListView) v.findViewById(R.id.search_result);
		search_searchstring=(EditText)v.findViewById(R.id.search_searchstring);
		search_searchbutton=(ImageButton)v.findViewById(R.id.search_searchbutton);
		search_searchbutton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				final String tosearch=search_searchstring.getText().toString();
				if(tosearch.length() < 2)
				{
					Toast.makeText(getActivity(),"关键词过短",0).show();
					return;
				}
				
				diseasedata.clear();
				yiyuandata.clear();
				
				//先在科室和医生列表和地点列表中搜索
				for(int i=0;i<Constants.keshilist.size();i++)
				{
					if(Constants.keshilist.get(i).getKeshiname().contains(tosearch))
					{
						yiyuandata.add(new YiyuanData(i,Constants.KESHI));
					}
				}
				
				for(int i=0;i<Constants.doctorlist.size();i++)
				{
					if(Constants.doctorlist.get(i).getDoctorname().contains(tosearch))
					{
						yiyuandata.add(new YiyuanData(i,Constants.YISHENG));
					}
				}
				
				//地点。。。
				
				if(!yiyuandata.isEmpty())//说明搜索的是科室或者医生或者地点，已经匹配，不需要找病症
				{
					search_result.setAdapter(new BaseAdapter()
					{
						@Override
						public int getCount() 
						{
							return yiyuandata.size();
						}

						@Override
						public Object getItem(int position) 
						{
							return getItem(position);
						}

						@Override
						public long getItemId(int position) 
						{
							return position;
						}

						@Override
						public View getView(int position, View convertView,ViewGroup parent) 
						{
							ListView.LayoutParams lp=new ListView.LayoutParams(ListView.LayoutParams.FILL_PARENT,ListView.LayoutParams.FILL_PARENT);
							TextView view=new FontyTextView(getActivity());
							YiyuanData data=yiyuandata.get(position);
							if(data.type == Constants.KESHI)
								view.setText(Constants.keshilist.get(data.index).getKeshiname());
							else
								view.setText(Constants.doctorlist.get(data.index).getDoctorname());
							view.setLayoutParams(lp);
							return view;
						}
					});
					
					search_result.setOnItemClickListener(new OnItemClickListener()
					{
						@Override
						public void onItemClick(AdapterView<?> parent,View view,int position,long id)
						{
							position--;
							YiyuanData data=yiyuandata.get(position);
							FragmentTransaction tran=getFragmentManager().beginTransaction();
							Fragment temp=null;
							if(data.type == Constants.KESHI)
							{
								temp=new PersonalInfoFragment();
							}
							else
							{
								temp=new WebFragment().SetParam(Constants.keshilist.get(data.index).getDetailurl(),"");
							}
							tran.replace(R.id.container,temp).addToBackStack(null).commit();
							Constants.fstack.push(temp);
						}
					});
					
					return;
				}
				else
				{
					final BaseAdapter objadapter=new BaseAdapter()
					{
						@Override
						public int getCount() 
						{
							return diseasedata.size();
						}
	
						@Override
						public Object getItem(int position) 
						{
							return getItem(position);
						}
	
						@Override
						public long getItemId(int position) 
						{
							return position;
						}
	
						@Override
						public View getView(int position, View convertView,ViewGroup parent) 
						{
							ListView.LayoutParams lp=new ListView.LayoutParams(ListView.LayoutParams.FILL_PARENT,ListView.LayoutParams.FILL_PARENT);
							TextView view=new FontyTextView(getActivity());
							SearchData data=diseasedata.get(position);
							view.setText(data.getName());
							view.setLayoutParams(lp);
							return view;
						}
					};
					
					search_result.setAdapter(objadapter);
					
					search_result.setOnItemClickListener(new OnItemClickListener()
					{
						@Override
						public void onItemClick(AdapterView<?> parent,View view,int position,long id)
						{
							position--;
							SearchData data=diseasedata.get(position);
							FragmentTransaction tran=getFragmentManager().beginTransaction();
							Fragment temp=null;
							temp=new SymptomDiseaseFragment().SetParam(data);
							tran.replace(R.id.container,temp).addToBackStack(null).commit();
							Constants.fstack.push(temp);
						}
					});
					
					final Handler handler=new Handler()
					{
						@Override
						public void handleMessage(Message msg)
						{
							//更新diseasedata 
							objadapter.notifyDataSetChanged();
						}
					};
					
					new Thread()
					{
						@Override
						public void run()
						{
							try 
							{
								new SearchDataParser(handler).getInfo(diseasedata,tosearch);
							} 
							catch (Exception e) 
							{
								e.printStackTrace();
							}
						}
					}.start();
				}
			}
		});
		
		return v;
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		activity.setTitle(R.drawable.search_title);
	}
}
