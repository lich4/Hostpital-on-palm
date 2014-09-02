package cn.edu.nju.zsyy.Fragments.Navigation;

import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.Activity.SearchActivity;
import cn.edu.nju.zsyy.adapter.CascadeFatherAdapter_GetInfo;
import cn.edu.nju.zsyy.adapter.CascadeSubAdapter_GetInfo;
import cn.edu.nju.zsyy.bean.Constants;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemClickListener;


public class GuaHaoYinDao extends Fragment
{
	private static final String TAG="GuaHaoYinDao";
	private LinearLayout lcontent=null;
	private int lcontentid=Constants.GUAHAOYINDAO*0xFFFFFF;
	
	private CascadeSubAdapter_GetInfo radapter=null;
	private CascadeFatherAdapter_GetInfo ladapter=null;
	
	private int type=0;
	private boolean ismale=true;
	private boolean isfront=true;
	private ListView llv=null;
	private ListView rlv=null;
	private int lindex=0;
	private LinearLayout llinear=null;
	private View bodyview=null;
	
	private String[] firstlayer={"头部","颈部","腹部","臀部","腰部","四肢","背部","胸部","其他"};
	private String[][] secondlayer=
	{
		{"鼻","耳","口","头","眼","眉","面","脑"},
		{"颈部"},
		{"腹部"},
		{"臀部"},
		{"腰部"},
		{"四肢","上肢","下肢"},
		{"背部"},
		{"胸部"},
		{"生殖系统","运动系统","消化系统","循环系统","呼吸系统","神经系统","泌尿系统","内分泌系统","其他"},
	};
	
	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		lcontent=new LinearLayout(getActivity());
		lcontent.setOrientation(LinearLayout.HORIZONTAL);
		lcontent.setId(lcontentid);
		bodyview=inflater.inflate(R.layout.man_face, null);
		
		final ViewPager mPager=new ViewPager(getActivity());
		PagerAdapter myPagerAdapter=new PagerAdapter()
		{
			@Override
			public int getItemPosition(Object object)
			{
				return POSITION_NONE;
			}
			
			@Override
			public void destroyItem(ViewGroup container, int position,Object object) 
			{
				// TODO Auto-generated method stub
				container.removeView((View)object);
				object=null;
			}
			
			@Override
			public Object instantiateItem(ViewGroup container,int position)
			{
				llinear=new LinearLayout(getActivity());
				LinearLayout.LayoutParams lp1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1);
				LinearLayout.LayoutParams lp2=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,8);			
				LinearLayout bottomview=(LinearLayout) inflater.inflate(R.layout.change, null);

				
				if(position == 0)
				{//人体图
					ToggleButton tb=(ToggleButton)bodyview.findViewById(R.id.gender);
					if(tb != null)
					{
						if(!ismale)
						{
							tb.setBackgroundDrawable(getResources().getDrawable(R.drawable.sex_woman));
						}
						else
						{
							tb.setBackgroundDrawable(getResources().getDrawable(R.drawable.sex_man));
						}
					}

					LinearLayout.LayoutParams lpi=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1);				
					bodyview.setLayoutParams(lpi);
					llinear.addView(bodyview);
					setListener();
				}
				else
				{//器官列表
					llv=new ListView(getActivity());
					rlv=new ListView(getActivity());
					llinear.removeAllViews();
					llinear.setOrientation(LinearLayout.HORIZONTAL);
					LinearLayout.LayoutParams lpi=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1);
					llv.setLayoutParams(lpi);
					rlv.setLayoutParams(lpi);
					
					llv.setAdapter(new BaseAdapter()
					{
						@Override
						public int getCount() 
						{
							return firstlayer.length;
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
						public View getView(int position,View convertView, ViewGroup parent) 
						{
							ListView.LayoutParams lp=new ListView.LayoutParams(ListView.LayoutParams.FILL_PARENT,ListView.LayoutParams.FILL_PARENT);
							TextView view=new TextView(getActivity());
							view.setText(firstlayer[position]);
							view.setLayoutParams(lp);
							return view;
						}
						
					});
					
					llv.setOnItemClickListener(new OnItemClickListener()
					{
						@Override
						public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
						{
							lindex=position;
							rlv.setAdapter(new BaseAdapter()
							{
								@Override
								public int getCount() 
								{
									return secondlayer[lindex].length;
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
								public View getView(int position,View convertView, ViewGroup parent) 
								{
									ListView.LayoutParams lp=new ListView.LayoutParams(ListView.LayoutParams.FILL_PARENT,ListView.LayoutParams.FILL_PARENT);
									TextView view=new TextView(getActivity());
									view.setText(secondlayer[lindex][position]);
									view.setLayoutParams(lp);
									return view;
								}
								
							});
						}
					});
					
					rlv.setAdapter(new BaseAdapter()
					{
						@Override
						public int getCount() 
						{
							return secondlayer[lindex].length;
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
						public View getView(int position,View convertView, ViewGroup parent) 
						{
							ListView.LayoutParams lp=new ListView.LayoutParams(ListView.LayoutParams.FILL_PARENT,ListView.LayoutParams.FILL_PARENT);
							TextView view=new TextView(getActivity());
							view.setText(secondlayer[lindex][position]);
							view.setLayoutParams(lp);
							return view;
						}
					});
					
					rlv.setOnItemClickListener(new OnItemClickListener()
					{
						@Override
						public void onItemClick(AdapterView<?> parent, View view,int position, long id)
						{
							onSearch(secondlayer[lindex][position]);	
						}
					});
					
					llinear.addView(llv);
					llinear.addView(rlv);
				}
				
				LinearLayout ll=new LinearLayout(getActivity());
				ViewGroup.LayoutParams lp3=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
				ll.setOrientation(LinearLayout.VERTICAL);
				ll.setLayoutParams(lp3);
				llinear.setLayoutParams(lp1);
				bottomview.setLayoutParams(lp2);
				
				ll.addView(llinear);
				ll.addView(bottomview);

				container.addView(ll);
				return ll;
			}

			private void setListener() 
			{
				View v;
				v=bodyview.findViewById(R.id.gender);
				if(v != null)
				{
					final ToggleButton tb=(ToggleButton)v;
					tb.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View view) 
						{
							if(ismale)
							{
								Toast.makeText(getActivity(),"选择女性", Toast.LENGTH_SHORT).show();
								ismale=false;
							}
							else
							{
								Toast.makeText(getActivity(),"选择男性", Toast.LENGTH_SHORT).show();
								ismale=true;
							}
							
							bodyview=null;
							if(ismale)
							{
								if(isfront)
								{
									bodyview=inflater.inflate(R.layout.man_face, null);
								}
								else
								{
									bodyview=inflater.inflate(R.layout.man_back, null);
								}
							}
							else
							{
								if(isfront)
								{
									bodyview=inflater.inflate(R.layout.woman_face, null);
								}
								else
								{
									bodyview=inflater.inflate(R.layout.woman_back, null);
								}
							}
							
							notifyDataSetChanged();
							
//							setListener();		
						}
					});
				}
				
				v=bodyview.findViewById(R.id.shengzhixitong);
				if(v != null)
				{
					v.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View view) 
						{
							onSearch("生殖系统");	
						}
					});
				}
				
				v=bodyview.findViewById(R.id.xiazhi);
				if(v != null)
				{
					v.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v) 
						{
							onSearch("下肢");
						}
					});
				}
				
				v=bodyview.findViewById(R.id.zuotunbu);
				if(v != null)
				{
					v.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							onSearch("臀部");
						}
					});
				}
				
				v=bodyview.findViewById(R.id.youtunbu);
				if(v != null)
				{
					v.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v) 
						{
							onSearch("臀部");
						}
					});
				}
				
				v=bodyview.findViewById(R.id.xiongbu);
				if(v != null)
				{
					v.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v) 
						{
							onSearch("胸部");
						}
					});
				}
				
				v=bodyview.findViewById(R.id.jingbu);
				if(v != null)
				{
					v.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v) 
						{
							onSearch("颈部");
						}
					});
				}
				
				v=bodyview.findViewById(R.id.toubu);
				if(v != null)
				{
					v.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v) 
						{
							AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
							builder.setTitle("选择部位").setItems(secondlayer[0],new DialogInterface.OnClickListener()
							{
								@Override
								public void onClick(DialogInterface dialog,int which) 
								{
									onSearch(secondlayer[0][which]);
								}
							});
							builder.show();
						}
					});
				}
				
				v=bodyview.findViewById(R.id.zuoshangzhi);
				if(v != null)
				{
					v.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v) 
						{
							onSearch("上肢");
						}
					});
				}
				
				v=bodyview.findViewById(R.id.youshangzhi);
				if(v != null)
				{
					v.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v) 
						{
							onSearch("上肢");
						}
					});
				}
				
				v=bodyview.findViewById(R.id.turnaroundleft);
				if(v != null)
				{
					v.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v) 
						{
							Toast.makeText(getActivity(),"翻转", Toast.LENGTH_SHORT).show();
							isfront=!isfront;
							bodyview=null;
							
							if(ismale)
							{
								if(isfront)
								{
									bodyview=inflater.inflate(R.layout.man_face, null);
								}
								else
								{
									bodyview=inflater.inflate(R.layout.man_back, null);
								}
							}
							else
							{
								if(isfront)
								{
									bodyview=inflater.inflate(R.layout.woman_face, null);
								}
								else
								{
									bodyview=inflater.inflate(R.layout.woman_back, null);
								}
							}
							
							notifyDataSetChanged();
						}
					});
				}
				
				v=bodyview.findViewById(R.id.turnaroundright);
				if(v != null)
				{
					v.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v) 
						{
							Toast.makeText(getActivity(),"翻转", Toast.LENGTH_SHORT).show();
							isfront=!isfront;
							bodyview=null;
							
							if(ismale)
							{
								if(isfront)
								{
									bodyview=inflater.inflate(R.layout.man_face, null);
								}
								else
								{
									bodyview=inflater.inflate(R.layout.man_back, null);
								}
							}
							else
							{
								if(isfront)
								{
									bodyview=inflater.inflate(R.layout.woman_face, null);
								}
								else
								{
									bodyview=inflater.inflate(R.layout.woman_back, null);
								}
							}
							
							notifyDataSetChanged();
							
						}
					});
				}
				
				v=bodyview.findViewById(R.id.beibu);
				if(v != null)
				{
					v.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v) 
						{
							onSearch("背部");
						}
					});
				}
				
				v=bodyview.findViewById(R.id.yaobu);
				if(v != null)
				{
					v.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v) 
						{
							onSearch("腰部");
						}
					});
				}
			}

			@Override
			public int getCount() 
			{
				return 2;
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1)
			{
				return arg0 == arg1;
			}
		};
		
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1);
		mPager.setLayoutParams(lp);
		mPager.setAdapter(myPagerAdapter);		
		lcontent.removeAllViews();
		lcontent.addView(mPager);
		
		return lcontent;
	}

	public void onSearch(String bodypart)
	{
		Toast.makeText(getActivity(), bodypart, Toast.LENGTH_LONG).show();
		Intent intent=new Intent(getActivity(),SearchActivity.class);
		intent.putExtra("Type", Constants.BODYPART);
		intent.putExtra("Data", bodypart);
		startActivity(intent);
	}
}
