package cn.edu.nju.zsyy;

import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.bean.UpdateInfo;
import cn.edu.nju.zsyy.engine.DoctorParser;
import cn.edu.nju.zsyy.engine.DownloadFileTask;
import cn.edu.nju.zsyy.engine.KeshiInfoParser;
import cn.edu.nju.zsyy.engine.PositionParser;
import cn.edu.nju.zsyy.engine.UpdateInfoParser;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

public class SplashActivity extends Activity 
{
	private static final String TAG = "SplashActivity";
	private static UpdateInfo info=null;	
	
	@Override
	public void onCreate(Bundle bundle) 
	{
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		
		final Handler handler=new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				if(!Constants.initialed || DoctorParser.initialstate == 0 || KeshiInfoParser.initialstate == 0)
				{
					new Thread()
					{
						@Override
						public void run()
						{
							try 
							{
								UpdateInfoParser.getUpdateInfo(getResources().getString(R.string.updateurl));
								new KeshiInfoParser(new Handler()).getInfo();
								new DoctorParser(new Handler()).getInfo();
							} 
							catch (Exception e) 
							{
								e.printStackTrace();
								KeshiInfoParser.initialstate=0;
								DoctorParser.initialstate=0;
							}
						}
					}.start();
				}
			}
		};
		
		Animation splashAnimation = AnimationUtils.loadAnimation(this,R.anim.splash_animation);
		View img = findViewById(R.id.logo_building);
		
		if (splashAnimation != null) 
		{
			splashAnimation.setAnimationListener(new Animation.AnimationListener() 
			{
				@Override
				public void onAnimationEnd(Animation paramAnonymousAnimation) 
				{//刷屏页
					new Thread()
					{
						@Override
						public void run()
						{
							UpdateMyData();
						}
					}.start();
					Intent intent = new Intent(SplashActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
				}
	
				@Override
				public void onAnimationRepeat(Animation paramAnonymousAnimation) 
				{
				}
	
				@Override
				public void onAnimationStart(Animation paramAnonymousAnimation) 
				{
				}
			});
			img.startAnimation(splashAnimation);
			new Constants();
		}
	}
	
	private void showUpdateDialog() 
	{
		AlertDialog.Builder builder=new Builder(this);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("升级提醒");
		builder.setMessage(info.getDescription());
		builder.setPositiveButton("确定",new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				Log.i(TAG,"下载API文件"+info.getApkurl());
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				{
					
				}
				else
				{
					Toast.makeText(getApplicationContext(), "sd卡不可用", 1).show();
				}
			}
		});
		
		builder.setNegativeButton("取消",new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				Log.i(TAG,"用户取消升级");
			}
		});
	}
	
	private void UpdateMyData() 
	{	
		try 
		{
			Looper.prepare();
			info=UpdateInfoParser.getUpdateInfo(getResources().getString(R.string.updateurl));
			
			new KeshiInfoParser(new Handler()).getInfo();
			new DoctorParser(new Handler()).getInfo();
			new PositionParser(new Handler()).getInfo();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			Log.i(TAG,"获取更新信息异常，进入主界面");
			DoctorParser.initialstate=0;
			KeshiInfoParser.initialstate=0;
		}
	}	
}