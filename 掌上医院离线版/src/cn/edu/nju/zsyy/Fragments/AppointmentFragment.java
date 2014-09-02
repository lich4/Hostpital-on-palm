package cn.edu.nju.zsyy.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.adapter.CascadeAdapter_Appointment;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.safety.ConfirmActivity;
import cn.edu.nju.zsyy.safety.LoginActivity;

public class AppointmentFragment extends Fragment implements OnClickListener
{//预约挂号
	private static final String TAG="AppointmentFragment";
	private int type=0;
	private Button btputongguahao=null;
	private Button btzhuanjiaguahao=null;
	private Button btgaojizhuanjia=null;
	private Button btzhuanbingzhuanke=null;
	
	private int currIndex=0;
    private int pagenum=4;
    private int position_one;
    private int position_two;
    private int position_three;
	
	private LinearLayout lcontent=null;
	private FragmentManager manager=null;
	CascadeAdapter_Appointment ladapter=null;
	CascadeAdapter_Appointment radapter=null;
	private ImageView ivBottomLine=null;
	private View appointmentview=null;
	
	private int bottomLineWidth;
	
	private String[] firstlayer={"内科","外科","妇产科","儿科","老年医学科","	其它","妇幼分院","生殖中心(医大)","二院院区"};
	private String[][] secondlayer=
	{
		{"呼吸内科","消化内科","心血管内科","血液内科","肾内科","内分泌科","风湿免疫科","神经内科","肿瘤科","感染科","皮肤科"},
		{"普外科","脑外科","骨科","泌尿外科","胸心外科","整形烧伤科","眼科","鼻咽喉科","口腔科","康复医学科","血管外科"},
		{"妇科"},
		{"儿科"},
		{"老年消化内科","老年肾内科","老年神经内科","老年内分泌科","老年呼吸内科","老年心血管内科"},
		{"中医科","针灸科","临床心理科","血管病与肿瘤介入","核医学科","超声诊断科","营养科","PET/CT门诊","高压氧门诊","放疗科"},
		{"妇科","产科","妇保","儿保","生殖中心","儿科","儿童先心外科","普外科","妇科内分泌"},
		{"生殖中心医大门诊"},
		{"呼吸科","心血管科","肾内科","血液科","普外科","骨科","泌尿外科","整形烧伤科","皮肤科"},
	};
	
	private int[] btids={R.id.monday_morning,R.id.monday_afternoon,R.id.tuesday_morning,R.id.tuesday_afternoon,
			R.id.wednesday_morning,R.id.wednesday_afternoon,R.id.thursday_morning,R.id.thursday_afternoon,
			R.id.friday_morning,R.id.friday_afternoon,R.id.saturday_morning,R.id.saturday_afternoon};
	private Spinner big=null;
	private Spinner small=null;
	private int selfirst=0;
	private int selsecond=0;
	private Button bt[]=new Button[12];//对应6天上下午
//	private String[][] ids;//科室id  医生id  WebService可能用到
	
	public Fragment SetType(int type)
	{
		this.type=type;
		return this;
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		activity.setTitle(R.drawable.yuyueguahao_title);
	}
	
	private void InitWidth(View v) 
	{
		manager=getFragmentManager();
        ivBottomLine = (ImageView)v.findViewById(R.id.iv_bottom_line);
        bottomLineWidth = ivBottomLine.getLayoutParams().width;
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        ivBottomLine.setLayoutParams(new RelativeLayout.LayoutParams(screenW/4,10));
        position_one = (int) (screenW / 4.0);
        position_two = position_one * 2;
        position_three = position_one * 3;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup viewGroup, Bundle bundle) 
	{
		manager=getFragmentManager();
		if(appointmentview != null)
			viewGroup.removeAllViews();
		
		appointmentview=inflater.inflate(R.layout.fragment_yuyueguahao,null,false);
		
		InitWidth(appointmentview);
		
		lcontent=(LinearLayout)appointmentview.findViewById(R.id.lcontainer);
		btputongguahao=(Button)appointmentview.findViewById(R.id.putongguahao);
		btzhuanjiaguahao=(Button)appointmentview.findViewById(R.id.zhuanjiaguahao);
		btgaojizhuanjia=(Button)appointmentview.findViewById(R.id.gaojizhuanjiaguahao);
		btzhuanbingzhuanke=(Button)appointmentview.findViewById(R.id.zhuanbingzhuankeguahao);
		btputongguahao.setEnabled(true);
		btzhuanjiaguahao.setEnabled(true);
		btgaojizhuanjia.setEnabled(true);
		btzhuanbingzhuanke.setEnabled(true);
		
		btputongguahao.setOnClickListener(this);
		btzhuanjiaguahao.setOnClickListener(this);
		btgaojizhuanjia.setOnClickListener(this);
		btzhuanbingzhuanke.setOnClickListener(this);

		big=(Spinner)appointmentview.findViewById(R.id.bigclass);
		small=(Spinner)appointmentview.findViewById(R.id.smallclass);

		for(int i=0;i<12;i++)
		{
			bt[i]=(Button)appointmentview.findViewById(btids[i]);
		}
		
		setCurrentItem(0); 
		ShowPage(Constants.PUTONGGUAHAO);
		
		return appointmentview;
	}

	private void ShowPage(int type)
	{
		switch(type)
		{
			case Constants.PUTONGGUAHAO:
			{
				big.setEnabled(true);
		        small.setEnabled(true);
				big.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,firstlayer));
		        small.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,secondlayer[selfirst]));
		        big.setOnItemSelectedListener(new OnItemSelectedListener()
				{
					@Override
					public void onItemSelected(AdapterView<?> parent,View view,int position,long id) 
					{
						selfirst=position;
						small.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,secondlayer[selfirst]));
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) 
					{
					}
				});
				
				small.setOnItemSelectedListener(new OnItemSelectedListener()
				{
					@Override
					public void onItemSelected(AdapterView<?> parent,View view,int position,long id) 
					{
						selsecond=position;
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) 
					{
					}
				});
		        for(int i=0;i<12;i++)
				{
					bt[i].setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							Intent intent = new Intent(getActivity(),ConfirmActivity.class);
							startActivity(intent);
						}	
					});
				}
				
				break;
			}
			
			case Constants.ZHUANJIAGUAHAO:
			{
		        big.setEnabled(true);
		        small.setEnabled(true);
				big.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,firstlayer));
		        small.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,secondlayer[selfirst]));
				big.setOnItemSelectedListener(new OnItemSelectedListener()
				{
					@Override
					public void onItemSelected(AdapterView<?> parent,View view,int position,long id) 
					{
						selfirst=position;
						small.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,secondlayer[selfirst]));
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) 
					{
					}
				});
				
				small.setOnItemSelectedListener(new OnItemSelectedListener()
				{
					@Override
					public void onItemSelected(AdapterView<?> parent,View view,int position,long id) 
					{
						selsecond=position;
						
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) 
					{
					}
				});
				
				for(int i=0;i<12;i++)
				{
					bt[i].setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							LinearLayout info=(LinearLayout)appointmentview.findViewById(R.id.content);
							LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1);	
							ListView doctorlist=new ListView(getActivity());
							doctorlist.setAdapter(new BaseAdapter()
							{
								private ImageView doctorimg=null;
								private TextView doctorname=null;
								private TextView zhicheng=null;
								private TextView gonghao=null;
								private TextView summary=null;
								
								@Override
								public int getCount()
								{
									return 10;
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
								public View getView(int position, View convertView, ViewGroup parent) 
								{
									View doctorview=convertView;
									if(doctorview == null)
										doctorview=LayoutInflater.from(getActivity()).inflate(R.layout.listitem_appointment, null);
									doctorimg=(ImageView) doctorview.findViewById(R.id.listitem_doctorimg);
									doctorname=(TextView) doctorview.findViewById(R.id.listitem_doctorname);
									zhicheng=(TextView) doctorview.findViewById(R.id.listitem_zhicheng);
									gonghao=(TextView) doctorview.findViewById(R.id.listitem_gonghao);
									summary=(TextView) doctorview.findViewById(R.id.listitem_summary);
									return doctorview;
								}
							});
							
							doctorlist.setOnItemClickListener(new OnItemClickListener()
							{
								@Override
								public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
								{
									FragmentTransaction tran=getFragmentManager().beginTransaction();
									Fragment temp=new OndutyFragment();
									tran.replace(R.id.container,temp).addToBackStack(null).commit();
								}
							});
							doctorlist.setLayoutParams(lp);
							info.removeAllViews();
							info.addView(doctorlist);
						}	
					});
				}

				break;
			}
			
			case Constants.GAOJIZHUANJIAGUAHAO:
			{
				big.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,new String[]{}));
		        small.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,new String[]{}));
		        big.setEnabled(false);
		        small.setEnabled(false);
		        for(int i=0;i<12;i++)
				{
					bt[i].setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							LinearLayout info=(LinearLayout)appointmentview.findViewById(R.id.content);
							LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1);	
							ListView doctorlist=new ListView(getActivity());
							doctorlist.setAdapter(new BaseAdapter()
							{
								private ImageView doctorimg=null;
								private TextView doctorname=null;
								private TextView zhicheng=null;
								private TextView gonghao=null;
								private TextView summary=null;
								
								@Override
								public int getCount()
								{
									return 10;
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
								public View getView(int position, View convertView, ViewGroup parent) 
								{
									View doctorview=convertView;
									if(doctorview == null)
										doctorview=LayoutInflater.from(getActivity()).inflate(R.layout.listitem_appointment, null);
									doctorimg=(ImageView) doctorview.findViewById(R.id.listitem_doctorimg);
									doctorname=(TextView) doctorview.findViewById(R.id.listitem_doctorname);
									zhicheng=(TextView) doctorview.findViewById(R.id.listitem_zhicheng);
									gonghao=(TextView) doctorview.findViewById(R.id.listitem_gonghao);
									summary=(TextView) doctorview.findViewById(R.id.listitem_summary);
									return doctorview;
								}
							});
							
							doctorlist.setOnItemClickListener(new OnItemClickListener()
							{
								@Override
								public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
								{
									FragmentTransaction tran=getFragmentManager().beginTransaction();
									Fragment temp=new OndutyFragment();
									tran.replace(R.id.container,temp).addToBackStack(null).commit();
								}
							});
							doctorlist.setLayoutParams(lp);
							info.removeAllViews();
							info.addView(doctorlist);
						}	
					});
				}
				break;
			}
			
			case Constants.ZHUANBINGZHUANKEGUAHAO:
			{
				break;
			}
		}
	}

	@Override
	public void onClick(View v) 
	{
		manager=getFragmentManager();
		
		switch(v.getId())
		{
			case R.id.putongguahao:
				setCurrentItem(0);
				SetType(Constants.PUTONGGUAHAO);
				ShowPage(Constants.PUTONGGUAHAO);
				break;
				
			case R.id.zhuanjiaguahao:
				setCurrentItem(1);
				SetType(Constants.ZHUANJIAGUAHAO);
				ShowPage(Constants.ZHUANJIAGUAHAO);
				break;
				
			case R.id.gaojizhuanjiaguahao:
				setCurrentItem(2);
				SetType(Constants.GAOJIZHUANJIAGUAHAO);
				ShowPage(Constants.GAOJIZHUANJIAGUAHAO);
				break;
				
			case R.id.zhuanbingzhuankeguahao:
				setCurrentItem(3);
				SetType(Constants.ZHUANBINGZHUANKEGUAHAO);
				ShowPage(Constants.ZHUANBINGZHUANKEGUAHAO);
				break;
		}
	}

	private void setCurrentItem(int index) 
	{
		float fromXDelta=0,toXDelta=0,fromYDelta=0,toYDelta=0;
		switch(index)
		{
			case 0:
				if(currIndex == 1)
				{
					fromXDelta=position_one;
				}
				else if(currIndex == 2)
				{
					fromXDelta=position_two;							
				}
				else if(currIndex == 3)
				{
					fromXDelta=position_three;							
				}
				btputongguahao.setTextColor(getResources().getColor(R.color.themecolor));
				btzhuanjiaguahao.setTextColor(getResources().getColor(R.color.frame_button_text_light));
				btgaojizhuanjia.setTextColor(getResources().getColor(R.color.frame_button_text_light));
				btzhuanbingzhuanke.setTextColor(getResources().getColor(R.color.frame_button_text_light));
				break;
				
			case 1:
				if(currIndex == 2)
				{
					fromXDelta=position_two;
				}
				else if(currIndex == 3)
				{
					fromXDelta=position_three;
				}
				toXDelta=position_one;
				btputongguahao.setTextColor(getResources().getColor(R.color.frame_button_text_light));
				btzhuanjiaguahao.setTextColor(getResources().getColor(R.color.themecolor));
				btgaojizhuanjia.setTextColor(getResources().getColor(R.color.frame_button_text_light));
				btzhuanbingzhuanke.setTextColor(getResources().getColor(R.color.frame_button_text_light));
				break;
				
			case 2:
				if(currIndex == 1)
				{
					fromXDelta=position_one;
				}
				else if(currIndex == 3)
				{
					fromXDelta=position_three;
				}
				toXDelta=position_two;
				btputongguahao.setTextColor(getResources().getColor(R.color.frame_button_text_light));
				btzhuanjiaguahao.setTextColor(getResources().getColor(R.color.frame_button_text_light));
				btgaojizhuanjia.setTextColor(getResources().getColor(R.color.themecolor));
				btzhuanbingzhuanke.setTextColor(getResources().getColor(R.color.frame_button_text_light));
				break;
				
			case 3:
				if(currIndex == 1)
				{
					fromXDelta=position_one;
				}
				else if(currIndex == 2)
				{
					fromXDelta=position_two;
				}
				toXDelta=position_three;
				btputongguahao.setTextColor(getResources().getColor(R.color.frame_button_text_light));
				btzhuanjiaguahao.setTextColor(getResources().getColor(R.color.frame_button_text_light));
				btgaojizhuanjia.setTextColor(getResources().getColor(R.color.frame_button_text_light));
				btzhuanbingzhuanke.setTextColor(getResources().getColor(R.color.themecolor));
				break;
		}
		Animation anim=new TranslateAnimation(fromXDelta,toXDelta,fromYDelta,toYDelta);
		currIndex=index;
		anim.setFillAfter(true);
		anim.setDuration(100);
		ivBottomLine.startAnimation(anim);
	}
}
