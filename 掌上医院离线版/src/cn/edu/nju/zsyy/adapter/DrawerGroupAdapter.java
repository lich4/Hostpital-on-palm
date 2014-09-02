package cn.edu.nju.zsyy.adapter;

import java.util.List;

import cn.edu.nju.zsyy.MainActivity;
import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.bean.Group;
import cn.edu.nju.zsyy.bean.Groups;
import android.content.Context;
import android.support.v4.app.*;
import android.support.v4.widget.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;

public class DrawerGroupAdapter extends ArrayAdapter<String> 
{
	private static final String TAG = "DrawerGroupAdapter";
	private Context context;
	private DrawerLayout drawerLayout;
	private FragmentManager fragmentManager;
	private Groups groups;

	public DrawerGroupAdapter(Context context,String[] groupNames, Groups bindedGroups,FragmentManager bindedManager) 
	{
		super(context,R.layout.item_drawer_group, groupNames);
		this.context = context;
		groups = bindedGroups;
		fragmentManager = bindedManager;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) 
	{
		Log.i(TAG, position+"");
		LayoutInflater inflater = (LayoutInflater)context.getSystemService("layout_inflater");
		Group curgroup = groups.get(position);
		View groupPage = inflater.inflate(R.layout.item_drawer_group, parent, false);
		groups.setGroupView(position, groupPage);
		((TextView) groupPage.findViewById(R.id.group_name)).setText(curgroup.getGroupName());
		ImageView arrowImg = (ImageView) groupPage.findViewById(R.id.indicator);
		
		if (!curgroup.getChildren().isEmpty()) 
		{
			arrowImg.setVisibility(View.VISIBLE);
			LinearLayout layout = (LinearLayout) groupPage.findViewById(R.id.expandable);
			layout.removeAllViews();
			String[] childnames = new String[curgroup.getChildren().size()];
			curgroup.getChildren().toArray(childnames);

			for (int i = 0; i < childnames.length; i++) 
			{
				String str = childnames[i];
				final View childPage = inflater.inflate(R.layout.item_drawer_child, null);
				((TextView) childPage.findViewById(R.id.child_name)).setText(str);
				childPage.setOnTouchListener(new MyOnTouchListener(position*10+i,childPage,curgroup.getChildrenFragments().get(i)));
				layout.addView(childPage,LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
			}
		}
		else
		{
			arrowImg.setVisibility(View.INVISIBLE);
		}
		return groupPage;
	}

	public class MyOnTouchListener implements OnTouchListener
	{
		private int type=Constants.SHOUYE;
		private View childPage=null;
		private Fragment childFragment=null;
		
		public MyOnTouchListener(int type,View childPage,Fragment childFragment)
		{
			this.childPage=childPage;
			this.type=type;
			this.childFragment=childFragment;
		}
		
		@Override
		public boolean onTouch(View v,MotionEvent event) 
		{
			if (event.getAction() == MotionEvent.ACTION_UP) 
			{
				groups.onItemSelected(childPage, R.id.child_name);
				if (childFragment != null) 
				{
					Constants.state=type;
					((MainActivity) context).setToggleIcon(false);
					FragmentTransaction ft=fragmentManager.beginTransaction().replace(R.id.container, childFragment);
					ft.addToBackStack(null);
					ft.commit();
					drawerLayout.closeDrawers();
				}
			}
			return true;
		}		
	}
	
	public void setDrawerLayout(DrawerLayout drawerLayout) 
	{
		this.drawerLayout = drawerLayout;
	}
}