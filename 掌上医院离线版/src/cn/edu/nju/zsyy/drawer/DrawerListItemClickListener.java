package cn.edu.nju.zsyy.drawer;

import cn.edu.nju.zsyy.MainActivity;
import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.adapter.SlideExpandableListAdapter;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.bean.Groups;

import android.content.Context;
import android.support.v4.app.*;
import android.support.v4.widget.*;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class DrawerListItemClickListener implements OnItemClickListener 
{
	private SlideExpandableListAdapter adapter;
	private Context context;
	private DrawerLayout drawerLayout;
	private ListView drawerListView;
	private FragmentManager fragmentManager;
	private Groups groups;

	public DrawerListItemClickListener(Context context,ListView drawerListView,SlideExpandableListAdapter adapter,
			Groups groups, FragmentManager fragmentManager) 
	{
		this.context = context;
		this.drawerListView = drawerListView;
		this.adapter = adapter;
		this.groups = groups;
		this.fragmentManager = fragmentManager;
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView,final View view, final int position, final long id) 
	{
		if (groups.get(position).getChildren().size() > 0) 
		{//有子节点
			LinearLayout expandView = (LinearLayout)(view.findViewById(R.id.expandable));
			Animation anim = expandView.getAnimation();
			if ((anim != null) && (anim.hasStarted()) && (!anim.hasEnded())) 
			{
				anim.setAnimationListener(new Animation.AnimationListener() 
				{
					public void onAnimationEnd(Animation anim) 
					{
						drawerListView.performItemClick(view, position,id);
					}

					public void onAnimationRepeat(Animation anim) 
					{
					}

					public void onAnimationStart(Animation anim) 
					{
					}
				});
			}
			else
			{
				expandView.setAnimation(null);
				ImageView arrow = (ImageView) view.findViewById(R.id.indicator);

				if(expandView.getVisibility() == View.VISIBLE)
				{
					ExpandCollapseAnimation eanim = new ExpandCollapseAnimation(expandView,ExpandCollapseAnimation.COLLAPSE);
					eanim.setDuration(200);
					expandView.startAnimation(eanim);
					arrow.startAnimation(AnimationUtils.loadAnimation(context, R.anim.collapse_indicator));
				}
				else
				{
					ExpandCollapseAnimation eanim = new ExpandCollapseAnimation(expandView,ExpandCollapseAnimation.EXPAND);
					eanim.setDuration(200);
					expandView.startAnimation(eanim);
					arrow.startAnimation(AnimationUtils.loadAnimation(context, R.anim.expand_indicator));
				}		
			}
		}
		else
		{//无子节点
			groups.onItemSelected(groups.getGroupView(position),R.id.group_name);
			Fragment fragment = groups.get(position).getGroupFragment();
			if (fragment != null) 
			{
				((MainActivity) context).setToggleIcon(false);
				FragmentTransaction trans=fragmentManager.beginTransaction().replace(R.id.container, fragment);
				trans.addToBackStack(null).commitAllowingStateLoss();
			}
			drawerLayout.closeDrawers();
		}
	}

	public void setDrawerLayout(DrawerLayout drawerLayout) 
	{
		this.drawerLayout = drawerLayout;
	}
}