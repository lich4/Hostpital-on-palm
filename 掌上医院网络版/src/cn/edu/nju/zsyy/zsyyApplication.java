package cn.edu.nju.zsyy;

import java.util.LinkedList;
import java.util.List;

import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.controls.FontyEditText;
import cn.edu.nju.zsyy.controls.FontyTextView;
import cn.edu.nju.zsyy.engine.DoctorParser;
import cn.edu.nju.zsyy.engine.KeshiInfoParser;
import cn.edu.nju.zsyy.engine.UpdateInfoParser;
import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class zsyyApplication extends Application 
{
//	private static String URL_PREFIX = "http://s1.smartjiangsu.com:8080/nju/login.action?query=login&";
//	private JSONObject jsonObject;
	private Typeface lanting=null;
	
	private String contactServer(String paramString1, String paramString2)
	{
//		try 
//		{
//			String str1 = "http://s1.smartjiangsu.com:8080/nju/login.action?query=login&username="
//					+ paramString1 + "&pwd=" + paramString2 + "";
//			JSONObject localJSONObject = new JSONObject(HTTPHelp.getInstance()
//					.httpGet(str1));
//			if (localJSONObject.getString("result").equals("1")) 
//			{
//				this.jsonObject = localJSONObject.getJSONObject("data");
//				return null;
//			}
//			if (localJSONObject.getString("msg").equals("user_exist")) 
//			{
//				String str2 = getString(2131427541);
//				return str2;
//			}
//		} 
//		catch (IOException localIOException) 
//		{
//			localIOException.printStackTrace();
//			return getString(2131427370);
//		} 
//		catch (JSONException localJSONException) 
//		{
//			localJSONException.printStackTrace();
//		}
//		return getString(2131427404);
		return "ok";
	}

	private void signin()
	{
		new AsyncTask() 
		{
			private boolean loginDataFound = false;
			private String password = null;
			private String username = null;

			protected String doInBackground(Void[] paramAnonymousArrayOfVoid) 
			{
//				if ((this.username != null) && (this.password != null)) {
//					this.loginDataFound = true;
//					return ZSDXApplication.this.contactServer(this.username,
//							this.password);
//				}
				return null;
			}

			protected void onPostExecute(String paramAnonymousString)
//					throws JSONException 
			{
//				if (!this.loginDataFound)
//					return;
//				if (paramAnonymousString == null) 
//				{
//					AccountInfo.signIn(ZSDXApplication.this,
//							ZSDXApplication.this.jsonObject, true);
//					return;
//				}
//				AccountInfo.signOut(ZSDXApplication.this);
			}

			public void onPreExecute()
			{
//				SharedPreferences localSharedPreferences = ZSDXApplication.this
//						.getSharedPreferences("pocket_nju", 0);
//				this.username = localSharedPreferences.getString("username",
//						null);
//				this.password = localSharedPreferences.getString("password",
//						null);
			}

			@Override
			protected Object doInBackground(Object... arg0) 
			{
				return null;
			}
		}.execute(new Void[0]);
	}

	public Typeface getGlobalTypeface() 
	{
		return this.lanting;
	}

	@Override
	public void onCreate() 
	{
		super.onCreate();
		this.lanting = Typeface.createFromAsset(getAssets(),
				"fonts/lanting.ttf");
		FontyEditText.setLanting(this.lanting);
		FontyTextView.setLanting(this.lanting);
		signin();
	}
}
