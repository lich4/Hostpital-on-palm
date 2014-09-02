package cn.edu.nju.zsyy.adapter;

import java.util.List;

import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.bean.KeshiInfo;
import cn.edu.nju.zsyy.engine.DoctorParser;
import cn.edu.nju.zsyy.engine.KeshiInfoParser;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CascadeSubAdapter_GetInfo extends BaseAdapter
{
	private Context context=null;
	private LayoutInflater inflater=null;
	private Handler handler=null;
	private int begin=0;
	private int end=0;
	private TextView listitem_title=null;
	private TextView listitem_summary=null;

	public CascadeSubAdapter_GetInfo(Context context,int begin,int end) 
	{
		this.context=context;
		inflater=LayoutInflater.from(context);
		if(begin == -1)//begin=end=-1
		{
			begin=0;//getCount() == end-begin+1 == 0
		}
		this.begin=begin;
		this.end=end;
	}

	@Override
	public int getCount() 
	{
		return end-begin+1;
	}

	@Override
	public Object getItem(int position)
	{
		return getItem(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return Constants.keshilist.get(position+begin).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View keshiview=convertView;
		if(keshiview == null)
			keshiview=inflater.inflate(R.layout.listitem_keshi_right, null);
		listitem_title=(TextView)keshiview.findViewById(R.id.listitem_title);
		listitem_summary=(TextView)keshiview.findViewById(R.id.listitem_summary);
		listitem_title.setText(Constants.keshilist.get(position+begin).getKeshiname());
		listitem_summary.setText(Constants.keshilist.get(position+begin).getSummary());	
		return keshiview;
	}
	
	public void setData(int begin,int end)
	{
		this.begin=begin;
		this.end=end;
	}
}
