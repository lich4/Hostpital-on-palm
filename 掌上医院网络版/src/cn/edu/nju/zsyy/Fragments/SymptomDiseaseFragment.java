package cn.edu.nju.zsyy.Fragments;

import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.bean.SearchData;
import cn.edu.nju.zsyy.controls.PullToRefreshListView;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class SymptomDiseaseFragment extends Fragment implements OnClickListener
{
	private SearchData data=null;
	private Button jianjie=null;
	private Button description=null;
	private LinearLayout lcontent=null;
	private LayoutInflater inflater=null;
	
	public SymptomDiseaseFragment SetParam(SearchData data)
	{
		this.data=data;
		return this;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		this.inflater=inflater;
		View v = inflater.inflate(R.layout.fragment_symptomdisease,container, false);
		lcontent=(LinearLayout)v.findViewById(R.id.lcontainer);
		jianjie=(Button) v.findViewById(R.id.jianjie);
		description=(Button)v.findViewById(R.id.description);
		jianjie.setEnabled(true);
		description.setEnabled(true);
		jianjie.setOnClickListener(this);
		description.setOnClickListener(this);
		if(data.type == Constants.ZHENGZHUANG)
		{
			description.setText("¿ÉÄÜ¼²²¡");
		}
		else
		{
			description.setText("°éËæÖ¢×´");
		}
		onjianjie();
		
		return v;
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		activity.setTitle(R.drawable.search_title);
	}

	public void onjianjie()
	{
		jianjie.setBackgroundDrawable(getResources().getDrawable(R.drawable.frame_button_p));
		TextView tv=new TextView(getActivity());
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1);
		tv.setText(data.getDetailinfo());
		lcontent.addView(tv);
	}
	
	public void ondescription()
	{
		description.setBackgroundDrawable(getResources().getDrawable(R.drawable.frame_button_p));
		if(data.type == Constants.ZHENGZHUANG)
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
							FragmentTransaction tran=getFragmentManager().beginTransaction();
							Fragment temp=Constants.get(Constants.PUTONGGUAHAO);
							tran.replace(R.id.container,temp).addToBackStack(null).commit();
							Constants.fstack.push(temp);
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
					FragmentTransaction tran=getFragmentManager().beginTransaction();
					Fragment temp=new OndutyFragment();
					tran.replace(R.id.container,temp).addToBackStack(null).commit();
					Constants.fstack.push(temp);
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
					FragmentTransaction tran=getFragmentManager().beginTransaction();
					Fragment temp=Constants.get(Constants.PUTONGGUAHAO);
					tran.replace(R.id.container,temp).addToBackStack(null).commit();
					Constants.fstack.push(temp);
				}
			});
			lcontent.addView(v);
		}	
	}
	
	@Override
	public void onClick(View v) 
	{
		lcontent.removeAllViews();
		
		switch(v.getId())
		{
			case R.id.jianjie:
				onjianjie();
				break;
				
			case R.id.description:
				ondescription();
				break;
		}
	}
}
