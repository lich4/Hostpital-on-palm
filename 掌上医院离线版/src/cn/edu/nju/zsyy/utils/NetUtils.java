package cn.edu.nju.zsyy.utils;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class NetUtils 
{
	public String getVersion(Activity context)
	{
		try
		{
			PackageManager manager=context.getPackageManager();
			PackageInfo info=manager.getPackageInfo(context.getPackageName(), 0);
			return info.versionName;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "°æ±¾ºÅÎ´Öª";
		}
	}
}
