package cn.edu.nju.zsyy.Activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import cn.edu.nju.zsyy.MainActivity;
import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.Fragments.PersonalInfoFragment;
import cn.edu.nju.zsyy.Fragments.SymptomDiseaseFragment;
import cn.edu.nju.zsyy.Fragments.WebFragment;
import cn.edu.nju.zsyy.bean.BaseData;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.bean.DoctorsInfo;
import cn.edu.nju.zsyy.bean.KeshiInfo;
import cn.edu.nju.zsyy.bean.SearchData;
import cn.edu.nju.zsyy.controls.SearchToolBar;
import cn.edu.nju.zsyy.engine.SearchDataParser;

public class SearchActivity extends ActionBarActivity
{
	private SearchToolBar searchwindow=null;
	private InputMethodManager imm=null;
	private ViewSwitcher mViewSwitcher=null;
	private View loginLoading=null;
	private ListView search_result=null;//只显示前100
	private View search_web=null;
	private AutoCompleteTextView edittext_search_bar=null;
	private List<BaseData> resultdata=new ArrayList<BaseData>();
	private AnimationDrawable loadingAnimation=null;
	private ProgressBar search_progress=null;//提示框用
	private ArrayList<String> wordlist=new ArrayList<String>();//提示列表字符串
	private MyAdapter adapter=null;
	private boolean waitreturn=false;
	public boolean forcereturn=false;
	
	private boolean Specified=false;
	private int SpecifyType=Constants.NAME;
	private String SpecifyString="";
	
	public int SearchType=Constants.SEARCHTYPE_ALL;//要搜索的类型
	public int SearchFilter=Constants.SEARCHFILTER_ALL;//搜索疾病症状数据库所用过滤器
	
	public void setSearchText(String tosearch)
	{
		if(edittext_search_bar != null)
		{
			edittext_search_bar.setText(tosearch);
			search_web.performClick();
		}
	}
	
	private final class MyListener implements OnItemClickListener 
	{
		@Override
		public void onItemClick(AdapterView<?> parent,View view,int position,long id)
		{
			FragmentTransaction tran=getSupportFragmentManager().beginTransaction();
			Fragment temp=null;

			switch(resultdata.get(position).getType())
			{
				case Constants.KESHI:
				{
					temp=new PersonalInfoFragment();
					break;
				}
				
				case Constants.YISHENG:
				{
					YiyuanData data=(YiyuanData)resultdata.get(position);
					temp=new WebFragment().SetParam(Constants.keshilist.get(data.index).getDetailurl(),"");
					break;
				}
					
				case Constants.WEIZHI:
				{
					break;
				}
				
				case Constants.JIBING:
				{
					SearchData data=(SearchData)resultdata.get(position);
					temp=new SymptomDiseaseFragment().SetParam(data);
					break;
				}
				
				case Constants.ZHENGZHUANG:
				{
					SearchData data=(SearchData)resultdata.get(position);
					temp=new SymptomDiseaseFragment().SetParam(data);
					break;
				}
			}
			
			tran.replace(R.id.container,temp).addToBackStack(null).commit();
		}
	}

	private final class MyAdapter extends BaseAdapter 
	{
		@Override
		public int getCount() 
		{
			return resultdata.size();
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
			TextView view=new TextView(SearchActivity.this);
			switch(resultdata.get(position).getType())
			{
				case Constants.KESHI:
				{
					YiyuanData data=(YiyuanData)resultdata.get(position);
					view.setText(Constants.keshilist.get(data.index).getname());
					break;
				}
				
				case Constants.YISHENG:
				{
					YiyuanData data=(YiyuanData)resultdata.get(position);
					view.setText(Constants.doctorlist.get(data.index).getDoctorname());
					break;
				}
					
				case Constants.WEIZHI:
				{
					break;
				}
				
				case Constants.JIBING:
				{
					SearchData data=(SearchData)resultdata.get(position);
					view.setText(data.getName());
					break;
				}
				
				case Constants.ZHENGZHUANG:
				{
					SearchData data=(SearchData)resultdata.get(position);
					view.setText(data.getName());
					break;
				}
			}

			view.setLayoutParams(lp);
			return view;
		}
	}

	public class YiyuanData extends BaseData
	{
		private int index;//在各自list中的位置
		
		public YiyuanData(int index,int type)
		{
			this.index=index;
			this.type=type;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		imm=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
		mViewSwitcher=(ViewSwitcher)findViewById(R.id.logindialog_view_switcher);
		loginLoading=(View)findViewById(R.id.login_loading);
		setTitle(R.drawable.search_title);
		getSupportActionBar().hide();
		
		Bundle extras=getIntent().getExtras();
		if(extras != null)
		{
			SpecifyType=extras.getInt("Type");
			SpecifyString=extras.getString("Data");
			Specified=true;
		}
		
		ImageView back=(ImageView)findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Intent intent=new Intent(SearchActivity.this,MainActivity.class);
				startActivity(intent);
			}	
		});
		
		search_result=(ListView) findViewById(R.id.search_result);
		
		LinearLayout search_bar_top=(LinearLayout)findViewById(R.id.search_bar_top);
		edittext_search_bar=(AutoCompleteTextView)search_bar_top.findViewById(R.id.edittext_search_bar);
		search_bar_top.setClickable(true);
		FrameLayout search_category_container=(FrameLayout)search_bar_top.findViewById(R.id.search_category_container);
		ImageView search_category=(ImageView)search_bar_top.findViewById(R.id.search_category);
		search_progress=(ProgressBar) search_bar_top.findViewById(R.id.search_progress);
		edittext_search_bar.setText(null);
		edittext_search_bar.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void afterTextChanged(Editable s) 
			{
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) 
			{
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,int count) 
			{
				//长度>1则从arraylist和数据库中分别寻找并把结果添加到提示列表框中
				StringBuilder sb=new StringBuilder();
				for(int i=0;i<s.length();i++)
				{
					if(s.charAt(i) != ' ')
						sb.append(s.charAt(i));
				}
				
				if(sb.length()>=2)
				{
					while(waitreturn)
					{
						forcereturn=true;
						try 
						{
							Thread.sleep(100);
						}
						catch (InterruptedException e) 
						{
							e.printStackTrace();
						}
					}
					
					forcereturn=false;
					wordlist.clear();
					UpdateAutoComplete(sb.toString());
				}
			}	
		});
		
		edittext_search_bar.setOnEditorActionListener(null);
		
		if(searchwindow == null)
			searchwindow=new SearchToolBar(this,search_bar_top,search_category);
		search_category_container.setEnabled(true);
		search_category_container.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				searchwindow.showAsDropDown(v);
			}
		});
		
		initAutoComplete();
		search_web=(View)findViewById(R.id.search_web);
		search_web.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				//加载延时动画
				loadingAnimation=(AnimationDrawable)loginLoading.getBackground();
				loadingAnimation.start();
				mViewSwitcher.showNext();
				
				final String tosearch=edittext_search_bar.getText().toString();
				if(tosearch.length() <= 0)
				{
					Toast.makeText(SearchActivity.this,"关键词过短",Toast.LENGTH_LONG).show();
					loadingAnimation.stop();
					mViewSwitcher.showPrevious();
					return;
				}

				resultdata.clear();
				
				if(Specified)
				{
					SearchDisease(tosearch);
				}
				else
				{
					if((SearchType & Constants.KESHI) != 0)
					{
						for(int i=0;i<Constants.keshilist.size();i++)
						{
							if(Constants.keshilist.get(i).getname().contains(tosearch))
							{
								resultdata.add(new YiyuanData(i,Constants.KESHI));
							}
						}	
					}

					if((SearchType & Constants.YISHENG) != 0)
					{
						for(int i=0;i<Constants.doctorlist.size();i++)
						{
							if(Constants.doctorlist.get(i).getDoctorname().contains(tosearch))
							{
								resultdata.add(new YiyuanData(i,Constants.YISHENG));
							}
						}
					}
					
					if((SearchType & Constants.WEIZHI) != 0)
					{
						
					}
					//地点。。。
					
					adapter=new MyAdapter();
					search_result.setAdapter(adapter);
					
					search_result.setOnItemClickListener(new MyListener());
					
					if((SearchType & Constants.JIBING) != 0 || (SearchType & Constants.ZHENGZHUANG) != 0)
					{
						SearchDisease(tosearch);
					}
					else
					{
						mViewSwitcher.showPrevious();
						loadingAnimation.stop();
					}
				}
			}
		});
		
		if(Specified)
		{
			setSearchText(SpecifyString);
		}
	}	
		
	public void SearchDisease(final String tosearch)
	{			
		final Handler handler=new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				//更新diseasedata 
				adapter.notifyDataSetChanged();
				//停止动画
				mViewSwitcher.showPrevious();
				loadingAnimation.stop();
			}
		};
		
		new Thread()
		{
			@Override
			public void run()
			{
				try 
				{
					new SearchDataParser(handler).getInfo(resultdata,SpecifyType,tosearch);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}.start();
	}

	/** 
	 * 初始化AutoCompleteTextView，最多显示5项提示，使 
	 * AutoCompleteTextView在一开始获得焦点时自动提示 
	 */  
	private void initAutoComplete() 
	{
		edittext_search_bar.setDropDownHeight(350); 
		edittext_search_bar.setVerticalScrollBarEnabled(true);
		edittext_search_bar.setThreshold(1);  
		edittext_search_bar.setOnFocusChangeListener(new OnFocusChangeListener() 
		{  
			@Override  
			public void onFocusChange(View v, boolean hasFocus)
			{  
				AutoCompleteTextView view = (AutoCompleteTextView) v;  
				if (hasFocus && edittext_search_bar.getText().toString().length() >= 1 && !Specified)
				{  
					view.showDropDown();  
				}  
			}  
		});  
	}

	private void UpdateAutoComplete(final String tosearch) 
	{
		if(!Specified)
		{
			final Handler handler=new Handler()
			{
				@Override
				public void handleMessage(Message msg) 
				{
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchActivity.this,android.R.layout.simple_dropdown_item_1line,wordlist);  
					edittext_search_bar.setAdapter(adapter); 
					edittext_search_bar.showDropDown();
					search_progress.setVisibility(View.GONE);
					waitreturn=false;
				}
			};
			
			waitreturn=true;
			search_progress.setVisibility(View.VISIBLE);
			
			new Thread()
			{
				@Override
				public void run()
				{
					for(DoctorsInfo info1:Constants.doctorlist)
					{
						if(forcereturn)
							break;
						if(info1.getDoctorname().contains(tosearch))
						{
							wordlist.add(info1.getDoctorname());
						}
					}
					
					for(KeshiInfo info2:Constants.keshilist)
					{
						if(forcereturn)
							break;
						if(info2.getname().contains(tosearch))
						{
							wordlist.add(info2.getname());
						}
					}
					
					if(forcereturn)
						return;
					
					new SearchDataParser(handler).getIndexInfo(SearchActivity.this,wordlist,tosearch,handler);
				}
			}.start();
		}  
	} 
}
