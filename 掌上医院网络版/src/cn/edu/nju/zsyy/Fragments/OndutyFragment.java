package cn.edu.nju.zsyy.Fragments;

import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.safety.ConfirmActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class OndutyFragment  extends Fragment
{
	private String name=null;
	private String keshi=null;
	private TextView doctorname=null;
	private TextView doctorkeshi=null;
	private TextView datetime=null;
	private TextView xingqi=null;
	private TextView wubie=null;
	private Button yuyue=null;

	public Fragment SetParams(String name,String keshi)
	{
		this.name=name;
		this.keshi=keshi;
		return this;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup viewGroup, Bundle bundle) 
	{
		ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
		ListView listview=new ListView(getActivity());
		listview.setAdapter(new BaseAdapter()
		{
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
		return listview;
	}
}
