package cn.edu.nju.zsyy.drawer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.nju.zsyy.MainActivity;
import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.Fragments.*;
import cn.edu.nju.zsyy.adapter.DrawerGroupAdapter;
import cn.edu.nju.zsyy.adapter.SlideExpandableListAdapter;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.bean.Group;
import cn.edu.nju.zsyy.bean.Groups;
import cn.edu.nju.zsyy.safety.AccountInfo;

public class NavigationDrawerFragment extends Fragment 
{
	private DrawerGroupAdapter drawerGroupAdapter=null;
	private DrawerListItemClickListener drawerListItemClickListener=null;
	private Groups groups=null;
	private NavigationDrawerCallbacks mCallbacks=null;
	private int mCurrentSelectedPosition = 0;
	private DrawerLayout mDrawerLayout=null;
	private ListView mDrawerListView=null;
	private static ActionBarDrawerToggle mDrawerToggle=null;
	private View mFragmentContainerView=null;
	private TextView name=null;

	public NavigationDrawerFragment() 
	{

	}

	private void configureSigninState() 
	{
		if (AccountInfo.SignedIn) 
		{
			name.setText(AccountInfo.RealName);
			name.setVisibility(View.VISIBLE);
		}
		else
		{
			name.setVisibility(View.INVISIBLE);
		}
	}

	private ActionBar getActionBar() 
	{
		return ((ActionBarActivity) getActivity()).getSupportActionBar();
	}

	private String[] getGroupNames(Groups paramGroups) 
	{
		String[] arrayOfString = new String[paramGroups.size()];
		for (int i = 0; i < arrayOfString.length; i++)
			arrayOfString[i] = paramGroups.get(i).getGroupName();
		return arrayOfString;
	}

	private List<Group> makeDrawerList() 
	{
		ArrayList<Group> groupList = new ArrayList<Group>();
		ArrayList<String> namelist=new ArrayList<String>();
		ArrayList<Fragment> fragmentlist=new ArrayList<Fragment>();
		
		groupList.add(new Group("首页",new MainFragment()));//0
		namelist.clear();
		fragmentlist.clear();
		//name数量要和framgent数量匹配
		namelist.add("医院简介");
		namelist.add("科室介绍");
		namelist.add("专家介绍");
		fragmentlist.add(new FeatureFragment().SetType(Constants.JIANJIE));
		fragmentlist.add(new FeatureFragment().SetType(Constants.ZHONGDIANZHUANKE));
		fragmentlist.add(new FeatureFragment().SetType(Constants.MINGYIDAQUAN));
		groupList.add(new Group("医院特色",namelist,fragmentlist));//1
		
		namelist.clear();
		fragmentlist.clear();
		namelist.add("就医流程");
		namelist.add("挂号引导");
		namelist.add("医院地图");
		fragmentlist.add(new NavigationFragment().SetType(Constants.JIUYILIUCHENG));
		fragmentlist.add(new NavigationFragment().SetType(Constants.GUAHAOYINDAO));
		fragmentlist.add(new NavigationFragment().SetType(Constants.YIYUANDITU));
		groupList.add(new Group("就医导航",namelist,fragmentlist));//2
		
		namelist.clear();
		fragmentlist.clear();
		namelist.add("普通挂号");
		namelist.add("专家挂号");
		namelist.add("高级专家挂号");
		namelist.add("专病专科挂号");
		fragmentlist.add(new AppointmentFragment().SetType(Constants.PUTONGGUAHAO));
		fragmentlist.add(new AppointmentFragment().SetType(Constants.ZHUANJIAGUAHAO));
		fragmentlist.add(new AppointmentFragment().SetType(Constants.GAOJIZHUANJIAGUAHAO));
		fragmentlist.add(new AppointmentFragment().SetType(Constants.ZHUANBINGZHUANKEGUAHAO));
		groupList.add(new Group("预约挂号",namelist,fragmentlist));//3

//		namelist.clear();
//		fragmentlist.clear();
//		namelist.add("候诊室");
//		namelist.add("电子医嘱");
//		namelist.add("电子病历");
//		namelist.add("电子检验报告");
//		namelist.add("手机健康园地");
//		fragmentlist.add(Constants.get(Constants.HOUZHENSHI));
//		fragmentlist.add(Constants.get(Constants.DIANZIYIZHU));
//		fragmentlist.add(Constants.get(Constants.DIANZIBINGLI));
//		fragmentlist.add(Constants.get(Constants.DIANZIJIANYANBAOGAO));	
//		fragmentlist.add(Constants.get(Constants.SHOUJIJIANKANGYUANDI));	
//		groupList.add(new Group("我的服务",namelist,fragmentlist));//4
//		
//		namelist.clear();
//		namelist.add("软件操作指南");
//		namelist.add("手机挂号流程");
//		namelist.add("使用帮助");
//		fragmentlist.add(Constants.get(Constants.RUANJIANCAOZUOZHINAN));
//		fragmentlist.add(Constants.get(Constants.SHOUJIGUAHAOLIUCHENG));
//		fragmentlist.add(Constants.get(Constants.SHIYONGBANGZHU));	
//		groupList.add(new Group("使用助手",namelist,fragmentlist));	//5	
//		
//		namelist.clear();
//		namelist.add("我的账号");
//		namelist.add("应用分享");
//		namelist.add("版本信息及更新");
//		namelist.add("软件意见反馈");
//		groupList.add(new Group("更多",namelist,fragmentlist));
//		
//		groupList.add(new Group("软件设置",Constants.get(Constants.RUANJIANSHEZHI)));//7

		return groupList;
	}

	private void selectItem(int index) 
	{
		mCurrentSelectedPosition = index;
		if (mDrawerListView != null)
			mDrawerListView.setItemChecked(index, true);
		if (mDrawerLayout != null)
			mDrawerLayout.closeDrawer(mFragmentContainerView);
		if (mCallbacks != null)
			mCallbacks.onNavigationDrawerItemSelected(index);
	}

	private void setDrawerListener(SlideExpandableListAdapter adapter,Groups groups) 
	{
		drawerListItemClickListener = new DrawerListItemClickListener(getActivity(), mDrawerListView,adapter, groups,getFragmentManager());
		mDrawerListView.setOnItemClickListener(drawerListItemClickListener);
	}

	private void showGlobalContextActionBar()
	{
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle("掌上医院");
	}

	public void closeDrawer() 
	{
		if (mDrawerLayout != null)
			mDrawerLayout.closeDrawer(mFragmentContainerView);
	}

	public DrawerLayout getDrawer() 
	{
		return mDrawerLayout;
	}

	public void gotoYIYUANTESE()
	{//主菜单医院特色
		((MainActivity) getActivity()).setToggleIcon(false);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Fragment f=new FeatureFragment().SetType(Constants.JIANJIE);
        fragmentTransaction.replace(R.id.container,f);
        fragmentTransaction.addToBackStack(null);            
        fragmentTransaction.commit();
        Constants.state=Constants.JIANJIE;
	}
	
	public void gotoJIUYIDAOHANG()
	{//主菜单就医导航
		((MainActivity) getActivity()).setToggleIcon(false);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Fragment f=new NavigationFragment().SetType(Constants.JIUYILIUCHENG);
        fragmentTransaction.replace(R.id.container,f);
        fragmentTransaction.addToBackStack(null);            
        fragmentTransaction.commit();
        Constants.state=Constants.JIUYILIUCHENG;
	}
	
	public void gotoYUYUEGUAHAO()
	{//主菜单预约挂号
		((MainActivity) getActivity()).setToggleIcon(false);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Fragment f=new AppointmentFragment().SetType(Constants.PUTONGGUAHAO);
        fragmentTransaction.replace(R.id.container,f);
        fragmentTransaction.addToBackStack(null);            
        fragmentTransaction.commit();
        Constants.state=Constants.PUTONGGUAHAO;
	}
	
//	public void gotoWODEFUWU()
//	{//主菜单我的服务
//		((MainActivity) getActivity()).setFromMain(false);
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.container,Constants.get(Constants.WODEFUWU));
//        fragmentTransaction.addToBackStack(null);            
//        fragmentTransaction.commit();
//        Constants.state=Constants.WODEFUWU;
//        Constants.fstack.push(Constants.get(Constants.WODEFUWU));
//	}
	
	public void gotoCAOZUOZHINAN()
	{//主菜单操作指南

	}
	
//	public void gotoGENGDUO()
//	{//主菜单更多
//		((MainActivity) getActivity()).setFromMain(false);
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.container,Constants.get(Constants.GENGDUO));
//        fragmentTransaction.addToBackStack(null);            
//        fragmentTransaction.commit();
//        Constants.state=Constants.GENGDUO;
//        Constants.fstack.push(Constants.get(Constants.GENGDUO));
//	}
	
	public boolean isDrawerOpen() 
	{
		return (mDrawerLayout != null) && (mDrawerLayout.isDrawerOpen(mFragmentContainerView));
	}

	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
	}

	public void onConfigurationChanged(Configuration configuration) 
	{
		super.onConfigurationChanged(configuration);
		mDrawerToggle.onConfigurationChanged(configuration);
	}

	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
	}

	public void onCreateOptionsMenu(Menu menu,MenuInflater menuInflater) 
	{
		if ((mDrawerLayout != null) && (isDrawerOpen()))
			showGlobalContextActionBar();
		super.onCreateOptionsMenu(menu, menuInflater);
	}

	public View onCreateView(LayoutInflater inflater,ViewGroup viewGroup, Bundle bundle) 
	{
		View localView = inflater.inflate(R.layout.fragment_navigation_drawer, viewGroup, false);
		mDrawerListView = (ListView) localView.findViewById(R.id.drawer_list);
		if (groups == null) 
		{
			groups = new Groups(makeDrawerList());
			drawerGroupAdapter = new DrawerGroupAdapter(getActivity(),getGroupNames(groups), groups, getFragmentManager());
		}
		name = ((TextView) localView.findViewById(R.id.realname));
		SlideExpandableListAdapter slideAdapter = new SlideExpandableListAdapter(drawerGroupAdapter, R.id.expandable);
		mDrawerListView.setAdapter(slideAdapter);
		setDrawerListener(slideAdapter, groups);
		mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
		return localView;
	}

	public void onDetach() 
	{
		super.onDetach();
		mCallbacks = null;
	}

	public boolean onOptionsItemSelected(MenuItem item) 
	{
		if (mDrawerToggle.onOptionsItemSelected(item))
			return true;
		else
			return super.onOptionsItemSelected(item);
	}

	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt("selected_navigation_drawer_position",mCurrentSelectedPosition);
	}

	public void openDrawer() 
	{
		if (mDrawerLayout != null) 
		{
			mDrawerLayout.openDrawer(mFragmentContainerView);
			groups.onResume();
		}
	}

	public void setUp(int drawerId, DrawerLayout drawerlayout) 
	{
		mFragmentContainerView = getActivity().findViewById(drawerId);
		mDrawerLayout = drawerlayout;
		drawerListItemClickListener.setDrawerLayout(drawerlayout);
		drawerGroupAdapter.setDrawerLayout(drawerlayout);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout,
				R.drawable.drawer_toggle, R.string.navigation_drawer_open,R.string.navigation_drawer_close) 
		{
			public void onDrawerClosed(View v) 
			{
				super.onDrawerClosed(v);
				if (!isAdded())
					return;
				getActivity().supportInvalidateOptionsMenu();
			}

			public void onDrawerOpened(View v) 
			{
				super.onDrawerOpened(v);
				configureSigninState();
				if (!isAdded())
					return;

				getActivity().supportInvalidateOptionsMenu();
			}
		};

		mDrawerLayout.post(new Runnable() 
		{
			public void run() 
			{
				NavigationDrawerFragment.mDrawerToggle.syncState();
			}
		});
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		goToMain();
	}
	
	public void goToMain() 
	{
		mDrawerListView.performItemClick(mFragmentContainerView, 0, 0L);
	}

	public void toggleDrawer() 
	{
		if (isDrawerOpen()) 
		{
			closeDrawer();
		}
		else
		{
			openDrawer();
		}
	}

	public static abstract interface NavigationDrawerCallbacks 
	{
		public abstract void onNavigationDrawerItemSelected(int index);
	}
}