package cn.edu.nju.zsyy.engine;

import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;

import android.os.Handler;
import android.util.Log;
import android.util.Xml;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.bean.DoctorsInfo;
import cn.edu.nju.zsyy.bean.KeshiInfo;
import cn.edu.nju.zsyy.bean.SearchData;

public class SearchDataParser 
{
	private static final String TAG = "SearchDataParser";
	private Handler handler=null;
	
	public SearchDataParser(Handler handler)
	{
		this.handler=handler;
	}
	
	public void getInfo(List<SearchData> datalist, String tosearch) throws Exception
	{
		String url=Constants.urlroot.replace("data/","query.jsp?querytype=3&tosearch=")+tosearch+"&searchtype=0";
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = httpClient.execute(httpGet);

		response=httpClient.execute(httpGet);
		StatusLine statu=response.getStatusLine();
		if (statu.getStatusCode() >= 300)
		{
			Log.i(TAG, "ÍøÂç¹ÊÕÏ");
			throw new HttpResponseException(statu.getStatusCode(),statu.getReasonPhrase());
		}
		HttpEntity entity = response.getEntity();
		if (entity != null)
		{
			InputStream instream = entity.getContent();
			SearchData curdata=null;
			
			XmlPullParser parser=Xml.newPullParser();
			parser.setInput(instream,"utf-8");
			int type=parser.getEventType();
			while(type != XmlPullParser.END_DOCUMENT)
			{
				if(type == XmlPullParser.START_TAG)
				{
					String name=parser.getName();
					if("item".equals(name))
					{
						curdata=new SearchData();
					}
					else if("name".equals(name))
					{
						curdata.setName(parser.nextText());
					}
					else if("issymptom".equals(name))
					{
						if(parser.nextText().equals("1"))
							curdata.setType(Constants.ZHENGZHUANG);
						else
							curdata.setType(Constants.JIBING);
					}
					else if("keshi".equals(name))
					{
						curdata.setKeshi(parser.nextText());
					}
					else if("ddescription".equals(name))
					{
						curdata.setDetailinfo(parser.nextText());
					}
					else if("data".equals(name))
					{
						curdata.setDetaildata(parser.nextText());
					}
				}
				else if(type == XmlPullParser.END_TAG)
				{
					String name=parser.getName();
					if("item".equals(name))
					{
						datalist.add(curdata);
//						if(handler != null)
//							handler.sendEmptyMessage(0);
					}
				}	
				type=parser.next();
			}
			instream.close();
			if(handler != null)
			handler.sendEmptyMessage(0);
		}
		else
		{
			Log.i(TAG, "Êý¾ÝÎª¿Õ");
			throw new ClientProtocolException("Response contains no content");
		}
	}
}
