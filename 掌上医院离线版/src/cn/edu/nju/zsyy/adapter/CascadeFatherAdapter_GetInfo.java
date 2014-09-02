package cn.edu.nju.zsyy.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.bean.*;
import cn.edu.nju.zsyy.engine.KeshiInfoParser;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.widget.*;

public class CascadeFatherAdapter_GetInfo extends BaseAdapter 
{
	private Context context=null;
	private LayoutInflater inflater=null;
	private List<BaseHospitalInfo> items=null;
	private int[] images=null;//int
	private TextView listitem_title=null;
	private ImageView listitem_image=null;
	private LinearLayout listitem_layout=null;
	private int lastsel=0;
	private Handler handler=null;
 
	public CascadeFatherAdapter_GetInfo(Context context,List<?> bigclass,int[] images)
	{
		this.context = context;
		this.items = new ArrayList<BaseHospitalInfo>();
		this.items.addAll((List<BaseHospitalInfo>) bigclass);
		this.images = images;
		if(this.images.length < this.items.size())
		{
			this.images=new int[this.items.size()];
			for(int i=0;i<this.items.size();i++)
			{
				this.images[i]=images[0];
			}
		}
		inflater=LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() 
	{
		return items.size();
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
//		listitem_image=(ImageView)keshiview.findViewById(R.id.listitem_image);
		listitem_layout=(LinearLayout)keshiview.findViewById(R.id.listitem_layout);
		listitem_title.setText(items.get(position).getname());
//		listitem_image.setImageResource(images[position]);	
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
}
