package cn.edu.nju.zsyy.Fragments;

import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.engine.DoctorParser;
import cn.edu.nju.zsyy.safety.ConfirmActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class PersonalInfoFragment extends Fragment implements OnClickListener
{
	private int docid;
	private LinearLayout lcontent=null;
	private Button yishengjianjie=null;
	private Button chuzhenshijian=null;
	private FragmentManager manager=null;
	
	public PersonalInfoFragment SetParam(int docid)
	{
		this.docid=docid;
		return this;
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		activity.setTitle(R.drawable.yishengxinxi_title);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		manager=getFragmentManager();
		View v = inflater.inflate(R.layout.fragment_personalinfo,container, false);
		lcontent=(LinearLayout)v.findViewById(R.id.lcontainer);
		yishengjianjie=(Button)v.findViewById(R.id.yishengjianjie);
		chuzhenshijian=(Button)v.findViewById(R.id.chuzhenshijian);
		yishengjianjie.setEnabled(true);
		chuzhenshijian.setEnabled(true);
		
		yishengjianjie.setOnClickListener(this);
		chuzhenshijian.setOnClickListener(this);
		
		onyishengjianjie();
		
		return v;
	}

	public void onyishengjianjie()
	{
		yishengjianjie.setBackgroundDrawable(getResources().getDrawable(R.drawable.frame_button_p));
		WebView mWebView=new WebView(getActivity());
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1);
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setLoadWithOverviewMode(true);
		mWebView.setBackgroundColor(Color.TRANSPARENT);
		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setHorizontalScrollBarEnabled(false);
		mWebView.setWebChromeClient(new WebChromeClient());
		mWebView.setWebViewClient(new WebViewClient());
		//DoctorParser.getDoctorById(id);
		mWebView.loadUrl(Constants.urlroot+Constants.doctorlist.get(0).getDetailurl());
		mWebView.setLayoutParams(lp);
		mWebView.setVisibility(View.VISIBLE);
		mWebView.setBackgroundColor(Color.WHITE);
		lcontent.addView(mWebView);
	}
	
	public void onchuzhenshijian()
	{
		chuzhenshijian.setBackgroundDrawable(getResources().getDrawable(R.drawable.frame_button_p));
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
		ListView listview=new ListView(getActivity());
		listview.setAdapter(new BaseAdapter()
		{
			private String name=null;
			private String keshi=null;
			private TextView doctorname=null;
			private TextView doctorkeshi=null;
			private TextView datetime=null;
			private TextView xingqi=null;
			private TextView wubie=null;
			private Button yuyue=null;
			
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
				View details=convertView;
				if(details == null)
					details=LayoutInflater.from(getActivity()).inflate(R.layout.listitem_personalduty, null);
				doctorname=(TextView)details.findViewById(R.id.listitem_doctorname);
				doctorkeshi=(TextView)details.findViewById(R.id.listitem_keshi);
				datetime=(TextView)details.findViewById(R.id.listitem_datetime);
				xingqi=(TextView)details.findViewById(R.id.listitem_week);
				wubie=(TextView)details.findViewById(R.id.listitem_noon);
				yuyue=(Button)details.findViewById(R.id.appointment);
				yuyue.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v) 
					{
						Intent intent = new Intent(getActivity(),ConfirmActivity.class);
						startActivity(intent);
					}
				});
				return details;
			}
		});
		listview.setLayoutParams(lp);
		lcontent.addView(listview);
	}
	
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		manager=getFragmentManager();
		lcontent.removeAllViews();
		
		switch(v.getId())
		{
			case R.id.yishengjianjie:
				onyishengjianjie();
				break;
				
			case R.id.chuzhenshijian:
				onchuzhenshijian();
				break;
		}
	}
}
