package cn.edu.nju.zsyy.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import cn.edu.nju.zsyy.Activity.SearchActivity;
import cn.edu.nju.zsyy.bean.BaseData;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.bean.SearchData;

public class SearchDataParser 
{
	private static final String TAG = "SearchDataParser";
	private Handler handler=null;
	
	public SearchDataParser(Handler handler)
	{
		this.handler=handler;
	}
	
	public void getInfo(List<BaseData> datalist,int searchby ,String tosearch) throws Exception
	{
		//ËÑÖ¢×´£¬¼²²¡
		if(tosearch.replace(" ","").isEmpty())
			return;
		
		String searchBY="";
		if(searchby == Constants.NAME)
		{
			searchBY="Name";
		}
		else if(searchby == Constants.BODYPART)
		{
			searchBY="BodyPart";
		}
		
		String url="/storage/sdcard0/data/symptom.db";
		SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(new File(url), null);
		Cursor cursor=db.rawQuery("select * from symptomdata where "+searchBY+" like '%"+tosearch+"%' order by Popularity desc limit 100;",null);
		while(cursor.moveToNext())
		{
			SearchData curdata=new SearchData();
			curdata.setName(cursor.getString(0));
			if(cursor.getString(2).equals("1"))
			{
				curdata.setType(Constants.ZHENGZHUANG);
				curdata.setDetaildata(cursor.getString(9));
			}
			else
			{
				curdata.setType(Constants.JIBING);
				curdata.setDetaildata(cursor.getString(7));
			}
			
			curdata.setKeshi(cursor.getString(4));
			curdata.setDetailinfo(cursor.getString(6));
			datalist.add(curdata);
		}
		db.close();
		if(handler != null)
			handler.sendEmptyMessage(0);
	}

	public void getIndexInfo(SearchActivity context,ArrayList<String> wordlist, String tosearch,Handler handler) 
	{
		String url="/storage/sdcard0/data/symptom.db";
		SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(new File(url), null);
		Cursor cursor=db.rawQuery("select Name from symptomdata where Name like '%"+tosearch+"%' order by Popularity desc limit 20;",null);
		while(cursor.moveToNext() && !context.forcereturn)
		{
			wordlist.add(cursor.getString(0));
		}
		db.close();
		
		handler.sendEmptyMessage(0);
	}
}
