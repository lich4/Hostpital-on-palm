package cn.edu.nju.zsyy.adapter;

import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.engine.KeshiInfoParser;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewKeshiAdapter extends BaseAdapter 
{
	private Context context=null;
	private LayoutInflater inflater=null;
	private Handler handler=null;
	private TextView listitem_title=null;
	private TextView listitem_summary=null;
	
	public ListViewKeshiAdapter(Context context)
	{
		this.context=context;
		inflater=LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() 
	{
		return Constants.keshilist.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return getItem(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return Constants.keshilist.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View keshiview=convertView;
		if(keshiview == null)
			keshiview=inflater.inflate(R.layout.listitem_keshi_right, null);
		listitem_title=(TextView)keshiview.findViewById(R.id.listitem_title);
		listitem_summary=(TextView)keshiview.findViewById(R.id.listitem_summary);
		listitem_title.setText(Constants.keshilist.get(position).getKeshiname());
		listitem_summary.setText(Constants.keshilist.get(position).getSummary());
		return keshiview;
	}

}
