package cn.edu.nju.zsyy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import cn.edu.nju.zsyy.Activity.SearchActivity;
import cn.edu.nju.zsyy.drawer.NavigationDrawerFragment;
import cn.edu.nju.zsyy.safety.LoginActivity;

public class MainActivity extends ActionBarActivity
{
	private static final String TAG = "MainActivity";
	/**
	 * 主界面activity 所有Framgment共有
	 * @version 1.0
	 * @created 2014-3-25
	 */
	
	private static MainActivity instance=null;//全局Activity的this指针
	private NavigationDrawerFragment navigationDrawerFragment=null;//侧滑控件的Fragment
	private ImageView loginButton=null;//登录按钮
	private ImageView searchButton=null;//搜索按钮
	private ImageView titleImage=null;//标题图片
	private ImageView toggle=null;//侧滑按钮
	private static OnSelfBack onSelfBackListener=null;//返回键监听器
	
	@Override
	public void onResume()
	{
		super.onResume();
		Bundle bundle=getIntent().getExtras();
		if(bundle != null)
		{
			if(bundle.getString("intent").equals("AppointmentFragment"))
			{
				navigationDrawerFragment.gotoYUYUEGUAHAO();
			}
		}
	}
	
	//防止onResume获取不到参数
	@Override
	protected void onNewIntent(Intent intent)
	{
		super.onNewIntent(intent);
		setIntent(intent);
	}
	
	@Override
	public void onRestart()
	{
		super.onRestart();
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
	}

	/*
	 * 获取侧滑菜单的Fragment
	 */
	public NavigationDrawerFragment getDrawer() 
	{
		return navigationDrawerFragment;
	}

	/*
	 * 按下硬件返回键
	 */
	@Override
	public void onBackPressed() 
	{
		Log.i(TAG,"onBackPressed");
		onSelfBack();
	}
	
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			Log.i(TAG,"onBackPressed");
			onSelfBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onCreate(Bundle paramBundle) 
	{
		Log.i(TAG,"onCreate");
		super.onCreate(paramBundle);

		setContentView(R.layout.activity_main);
		instance = this;
		navigationDrawerFragment = ((NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer));
		getSupportActionBar().hide();
		toggle = (ImageView)findViewById(R.id.main_head_logo);
		titleImage = (ImageView)findViewById(R.id.main_head_title);
		loginButton = (ImageView)findViewById(R.id.main_head_loginbutton);
		searchButton = (ImageView)findViewById(R.id.main_head_searchbutton);
		
		searchButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Log.i(TAG,"searchButton.onClick");
				Intent intent = new Intent(MainActivity.this,SearchActivity.class);
				startActivity(intent);
			}
		});
		
		toggle.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View paramAnonymousView) 
			{
				Log.i(TAG,"toggle.onClick");
				navigationDrawerFragment.toggleDrawer();
			}
		});
		navigationDrawerFragment.setUp(R.id.navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	/**
	 * @param goback true:设置为返回 false:设置为侧滑 
	 * 设置侧滑按钮功能为返回上个Fragment或打开策划菜单
	 */
	public void setToggleIcon(boolean goback) 
	{
		if (!goback) 
		{//不返回
			Log.i(TAG,"ToggleIcon set to toggle");
			toggle.setImageResource(R.drawable.drawer_toggle);
			toggle.setOnClickListener(new View.OnClickListener() 
			{
				public void onClick(View v) 
				{//打开菜单
					navigationDrawerFragment.toggleDrawer();
				}
			});
		}
		else
		{//返回
			Log.i(TAG,"ToggleIcon set to back");
			toggle.setImageResource(R.drawable.back);
			toggle.setOnClickListener(new View.OnClickListener() 
			{
				public void onClick(View v)
				{
					navigationDrawerFragment.goToMain();
					setToggleIcon(false);
				}
			});		
		}
	}

	/**
	 * 设置登录按钮状态
	 * @param imgid 设置图片id
	 * @param onClickListener 设置图片监听器
	 */
	public void setloginButton(int imgid,View.OnClickListener onClickListener) 
	{
		Log.i(TAG,"loginbutton set!");
		loginButton.setImageResource(imgid);
		loginButton.setOnClickListener(onClickListener);
	}

	/**
	 * 设置标题图片
	 * @param imgid 标题图片id
	 */
	public void setTitle(int imgid)
	{
		Log.i(TAG,"title set!");
		titleImage.setImageResource(imgid);
	}
	
	public static abstract interface OnSelfBack
	{
		public void onSelfBack();
	}
	
	/**
	 * 设置返回按钮功能
	 * 如果找到功能列表就认为当前处于首页
	 * 处于首页时按下返回键将弹出确认退出对话框，否则返回到上个Fragment
	 */
	public void onSelfBack()
	{
		if(onSelfBackListener == null)
		{
			if(null != findViewById(R.id.functable))
			{
				Log.i(TAG,"quit from MainActivity");
				AlertDialog.Builder dialog=new AlertDialog.Builder(this);
				dialog.setTitle("离开程序");
				dialog.setTitle("您确定要退出吗?");
				dialog.setIcon(R.drawable.ic_launcher);
				dialog.setPositiveButton("确定",new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						finish();
						System.exit(0);
					}
				});
				dialog.setNegativeButton("取消",new DialogInterface.OnClickListener() 
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						return;
					}
				});
				dialog.show();
			}
			else
			{
				Log.i(TAG,"go back from other fragment");
				super.onBackPressed();
			}
		}
		else
		{
			Log.i(TAG,"go back from other fragment use other listener");
			onSelfBackListener.onSelfBack();
			onSelfBackListener=null;//只保持一次
		}
	}
	
	/**
	 * 设置返回按钮外部监听器
	 * @param listener 目标监听器
	 */
	public void setOnSelfBackListener(OnSelfBack listener)
	{
		this.onSelfBackListener=listener;
	}
}
