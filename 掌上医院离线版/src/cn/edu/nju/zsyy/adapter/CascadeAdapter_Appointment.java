package cn.edu.nju.zsyy.adapter;

import java.util.ArrayList;
import java.util.List;

import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.bean.KeshiInfo;
import cn.edu.nju.zsyy.engine.KeshiInfoParser;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CascadeAdapter_Appointment extends BaseAdapter 
{
	private Context context=null;
	private LayoutInflater inflater=null;
	private String[] items=null;
	private int[] images=null;//int
	private TextView listitem_title=null;
	private ImageView listitem_image=null;
	private LinearLayout listitem_layout=null;
	private int lastsel=0;
	private Handler handler=null;
 
	public CascadeAdapter_Appointment(Context context,String[] items,int[] images)
	{
		this.context = context;
		this.items = items;
		this.images = images;
		inflater=LayoutInflater.from(context);
	}
	
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
		View keshiview=convertView;
		if(keshiview == null)
			keshiview=inflater.inflate(R.layout.listitem_keshi_left, null);
		listitem_title=(TextView)keshiview.findViewById(R.id.listitem_title);
		listitem_image=(ImageView)keshiview.findViewById(R.id.listitem_image);
		listitem_layout=(LinearLayout)keshiview.findViewById(R.id.listitem_layout);
		listitem_title.setText(items[position]);
		listitem_image.setImageResource(images[position]);	
		if(lastsel == position)
		{
			listitem_title.setTextColor(Color.BLUE);
			listitem_layout.setBackgroundColor(Color.LTGRAY);
		}
		else
		{
			listitem_title.setTextColor(Color.BLACK);
			listitem_layout.setBackgroundColor(Color.TRANSPARENT);
		}
		return keshiview;
	}
	
	public void setSelectedPosition(int position) 
	{   
	   lastsel = position;   
	}   
	
	public void setData(String[] items)
	{
		this.items=items;
	}
}
