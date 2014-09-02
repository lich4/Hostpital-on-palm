package cn.edu.nju.zsyy.adapter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.bean.DoctorsInfo;
import cn.edu.nju.zsyy.engine.DoctorParser;
import cn.edu.nju.zsyy.engine.KeshiInfoParser;
import cn.edu.nju.zsyy.utils.BitmapManager;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewYishengAdapter extends BaseAdapter 
{
	private Context context=null;
	private LayoutInflater inflater=null;
	private BitmapManager bmpManager=null;
	private Handler handler=null;
	private List<DoctorsInfo> doctorlist=null;
	
	public ListViewYishengAdapter(Context context,List<DoctorsInfo> doctorlist)
	{
		this.context=context;
		this.doctorlist=new ArrayList<DoctorsInfo>();
		this.doctorlist.addAll(doctorlist);
		inflater=LayoutInflater.from(context);
		this.bmpManager = new BitmapManager(BitmapFactory.decodeResource(context.getResources(), R.drawable.widget_dface_loading));
	}
	
	@Override
	public int getCount() 
	{
		return doctorlist.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return getItem(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return doctorlist.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{		
		View yishengview=convertView;
		if(yishengview == null)
			yishengview=inflater.inflate(R.layout.listitem_yisheng, null);
		TextView listitem_title=(TextView)yishengview.findViewById(R.id.listitem_title);
		TextView listitem_summary=(TextView)yishengview.findViewById(R.id.listitem_summary);
		ImageView listitem_image=(ImageView)yishengview.findViewById(R.id.listitem_image);
		listitem_title.setText(doctorlist.get(position).getDoctorname());
		listitem_summary.setText(doctorlist.get(position).getSummary());
		String url=Constants.urlroot+doctorlist.get(position).getSmallpicurl();
		
		try
		{
			bmpManager.loadBitmap(url, listitem_image);
			listitem_image.setVisibility(ImageView.VISIBLE);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return yishengview;
	}

}
