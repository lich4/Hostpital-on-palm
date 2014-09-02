package cn.edu.nju.zsyy;

import cn.edu.nju.zsyy.Fragments.MainFragment;
import cn.edu.nju.zsyy.Fragments.SearchFragment;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.drawer.NavigationDrawerFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends ActionBarActivity
{
	/**
	 * 主界面activity
	 * @version 1.0
	 * @created 2014-3-25
	 */
	private static final int TIME_GAP = 3000;
	private static MainActivity instance=null;
	private long backPressedTime=100;
	private NavigationDrawerFragment navigationDrawerFragment=null;
	private ImageView loginButton=null;
	private ImageView searchButton=null;
	private ImageView titleImage=null;
	private ImageView toggle=null;
	private boolean visible = false;

	public static MainActivity getInstance() 
	{
		return instance;
	}

	public NavigationDrawerFragment getDrawer() 
	{
		return navigationDrawerFragment;
	}

	public boolean isVisible() 
	{
		return visible;
	}

	@Override
	public void onBackPressed() 
	{
		if(!Constants.fstack.empty())
		{
			Constants.fstack.pop();
			if(!Constants.fstack.empty())
			{
		        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		        fragmentTransaction.replace(R.id.container,Constants.fstack.peek());
		        fragmentTransaction.addToBackStack(null);            
		        fragmentTransaction.commit();
			}
			else
			{
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
		}
		else
		{
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
	}

	@Override
	protected void onCreate(Bundle paramBundle) 
	{
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
				FragmentManager manager=getSupportFragmentManager();
				FragmentTransaction tran=manager.beginTransaction();
				FrameLayout fl=(FrameLayout) findViewById(R.id.container);
				Fragment newf=new SearchFragment();
				Constants.fstack.push(newf);
				tran.replace(R.id.container,newf);
				tran.commit();
			}
		});
		
		toggle.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View paramAnonymousView) 
			{
				navigationDrawerFragment.toggleDrawer();
			}
		});
		navigationDrawerFragment.setUp(R.id.navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onPause() 
	{
		super.onPause();
		visible = false;
	}

	@Override
	public void onPostCreate(Bundle paramBundle) 
	{
		super.onPostCreate(paramBundle);
	}

	@Override
	public void onResume() 
	{
		super.onResume();
		visible = true;
	}

	public void setFromMain(boolean goback) 
	{
		if (!goback) 
		{//不返回
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
			toggle.setImageResource(R.drawable.back);
			toggle.setOnClickListener(new View.OnClickListener() 
			{
				public void onClick(View v)
				{
					navigationDrawerFragment.goToMain();
					setFromMain(false);
				}
			});		
		}
	}

	public void setloginButton(int imgid,View.OnClickListener onClickListener) 
	{
		if (loginButton == null)
			return;
		if (imgid == -1)
		{
			loginButton.setImageDrawable(null);
			loginButton.setOnClickListener(null);
		}
		else
		{
			loginButton.setImageResource(imgid);
			loginButton.setOnClickListener(onClickListener);
		}
	}

	public void setTitle(int imgid)
	{
		if (titleImage != null)
			titleImage.setImageResource(imgid);
	}

	public void setTitle(String title)
	{
		this.getSupportActionBar().setTitle(title);
	}
}
