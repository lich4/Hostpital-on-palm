package cn.edu.nju.zsyy.Fragments;

import cn.edu.nju.zsyy.MainActivity;
import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.bean.SearchData;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class SymptomDiseaseFragment extends Fragment implements OnClickListener
{
	private static final String TAG="SymptomDiseaseFragment";
	private SearchData data=null;
	private Button btjianjie=null;
	private Button btdescription=null;
	private LinearLayout lcontent=null;
	private LayoutInflater inflater=null;
	
	private int currIndex=0;
    private int pagenum=2;
    private int position_one;
    private int position_two;
    private int position_three;
    private ImageView ivBottomLine=null;
    private View diseaseview=null;
    private int bottomLineWidth;
    
	public SymptomDiseaseFragment SetParam(SearchData data)
	{
		this.data=data;
		return this;
	}
	
	private void InitWidth(View v) 
	{
        ivBottomLine = (ImageView)v.findViewById(R.id.iv_bottom_line);
        bottomLineWidth = ivBottomLine.getLayoutParams().width;
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        ivBottomLine.setLayoutParams(new RelativeLayout.LayoutParams(screenW/2,10));
        position_one = (int) (screenW / 2.0);
        position_two = position_one * 2;
        position_three = position_one * 3;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		this.inflater=inflater;
		if(diseaseview != null)
			container.removeAllViews();
		diseaseview = inflater.inflate(R.layout.fragment_symptomdisease,container, false);
		InitWidth(diseaseview);
		
		lcontent=(LinearLayout)diseaseview.findViewById(R.id.lcontainer);
		btjianjie=(Button) diseaseview.findViewById(R.id.jianjie);
		btdescription=(Button)diseaseview.findViewById(R.id.description);
		btjianjie.setEnabled(true);
		btdescription.setEnabled(true);
		btjianjie.setOnClickListener(this);
		btdescription.setOnClickListener(this);
		
		if(data.getType() == Constants.ZHENGZHUANG)
		{
			btdescription.setText("¿ÉÄÜ¼²²¡");
		}
		else
		{
			btdescription.setText("°éËæÖ¢×´");
		}
		onjianjie();
		
		return diseaseview;
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		activity.setTitle(R.drawable.search_title);
	}

	public void onjianjie()
	{
		TextView tv=new TextView(getActivity());
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1);
		tv.setText(data.getDetailinfo());
		lcontent.addView(tv);
	}
	
	public void ondescription()
	{
		if(data.getType() == Constants.ZHENGZHUANG)
		{
			LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1);	
			ListView diseaselist=new ListView(getActivity());
			diseaselist.setAdapter(new BaseAdapter()
			{
				private TextView listitem_name=null;
				private TextView listitem_keshi=null;
				private Button listitem_guahao=null;
				private TextView listitem_description=null;
				private String[] items=data.getDetaildata().split(",");
				
				@Override
				public int getCount()
				{
					return items.length;
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
					View view=convertView;
					if(view == null)
						view=LayoutInflater.from(getActivity()).inflate(R.layout.listitem_symptom, null);
					listitem_name=(TextView)view.findViewById(R.id.listitem_name);
					listitem_keshi=(TextView)view.findViewById(R.id.listitem_keshi);
					listitem_guahao=(Button)view.findViewById(R.id.listitem_guahao);
					listitem_description=(TextView)view.findViewById(R.id.listitem_description);
					String item[]=items[position].split(":");
					listitem_name.setText(item[0]);
					listitem_keshi.setText(item[1]);
					listitem_description.setText(item[2]);
					
					listitem_guahao.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v) 
						{
							Intent intent=new Intent(getActivity(),MainActivity.class);
							intent.putExtra("intent","AppointmentFragment");
							startActivity(intent);
						}
					});

					return view;
				}
			});
			
			diseaselist.setOnItemClickListener(new OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
				{
//					Intent intent=new Intent(getActivity(),MainActivity.class);
//					intent.putExtra("intent","OndutyFragment");
//					startActivity(intent);
//					FragmentTransaction tran=getFragmentManager().beginTransaction();
//					Fragment temp=new OndutyFragment();
//					tran.replace(R.id.lcontainer,temp).addToBackStack(null).commit();
				}
			});
			diseaselist.setLayoutParams(lp);
			lcontent.addView(diseaselist);
		}
		else
		{
			View v=inflater.inflate(R.layout.fragment_disease,null);
			TextView listitem_name=(TextView)v.findViewById(R.id.listitem_name);
			TextView listitem_keshi=(TextView)v.findViewById(R.id.listitem_keshi);
			Button listitem_guahao=(Button)v.findViewById(R.id.listitem_guahao);
			TextView listitem_description=(TextView)v.findViewById(R.id.listitem_description);
			listitem_name.setText(data.getName());
			listitem_keshi.setText(data.getKeshi());
			listitem_description.setText(data.getDetaildata());
			listitem_guahao.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					Intent intent=new Intent(getActivity(),MainActivity.class);
					intent.putExtra("intent","AppointmentFragment");
					startActivity(intent);
				}
			});
			lcontent.addView(v);
		}	
	}
	
	public void onPageSelected(int index) 
	{
		float fromXDelta=0,toXDelta=0,fromYDelta=0,toYDelta=0;
		switch(index)
		{
			case 0:
				if(currIndex == 1)
				{
					fromXDelta=position_one;
				}
				btjianjie.setTextColor(getResources().getColor(R.color.themecolor));
				btdescription.setTextColor(getResources().getColor(R.color.frame_button_text_light));
				break;
				
			case 1:
				toXDelta=position_one;
				btjianjie.setTextColor(getResources().getColor(R.color.frame_button_text_light));
				btdescription.setTextColor(getResources().getColor(R.color.themecolor));
				break;
		}
		Animation anim=new TranslateAnimation(fromXDelta,toXDelta,fromYDelta,toYDelta);
		currIndex=index;
		anim.setFillAfter(true);
		anim.setDuration(100);
		ivBottomLine.startAnimation(anim);
	}
	
	@Override
	public void onClick(View v) 
	{
		lcontent.removeAllViews();
		
		switch(v.getId())
		{
			case R.id.jianjie:
				onjianjie();
				onPageSelected(0);
				break;
				
			case R.id.description:
				ondescription();
				onPageSelected(1);
				break;
		}
	}
}
