package cn.edu.nju.zsyy.engine;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.xmlpull.v1.XmlPullParser;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.util.Xml;
import cn.edu.nju.zsyy.bean.*;

public class KeshiInfoParser 
{
	private static final String TAG = "KeshiInfoParser";
	public static int initialstate=0;//0表示未初始化 1表示正在初始化 2表示初始化完成
	private Handler handler=null;
	
	public KeshiInfoParser(Handler handler)
	{
		this.handler=handler;
	}	

	public void getInfo() throws Exception
	{
		if(initialstate == 2 || !Constants.initialed)
			return;

		String url=Constants.urlroot+Constants.zhongdianzhuankefile;
		DefaultHttpClient httpClient = new DefaultHttpClient();;
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = httpClient.execute(httpGet);

		response=httpClient.execute(httpGet);
		StatusLine statu=response.getStatusLine();
		if (statu.getStatusCode() >= 300)
		{
			Log.i(TAG, "网络故障");
			initialstate=0;
			throw new HttpResponseException(statu.getStatusCode(),statu.getReasonPhrase());
		}
		HttpEntity entity = response.getEntity();
		if (entity != null)
		{
			InputStream instream = entity.getContent();
			KeshiInfo infokeshi=null;
			String lastname=null;
			KeshiInfo temp=null;
			
			XmlPullParser parser=Xml.newPullParser();
			parser.setInput(instream,"utf-8");
			int type=parser.getEventType();
			while(type != XmlPullParser.END_DOCUMENT)
			{
				if(type == XmlPullParser.START_TAG)
				{
					String name=parser.getName();
					if("keshi".equals(name))
					{
						infokeshi=new KeshiInfo();
						infokeshi.setFather(temp);
					}
					else if("name".equals(name))
					{
						infokeshi.setKeshiname(parser.nextText());
					}
					else if("id".equals(name))
					{
						String str=parser.nextText();
						if(str == "")
							infokeshi.setId(-1);
						else
							infokeshi.setId(Integer.parseInt(str));
					}
					else if("summary".equals(name))
					{
						infokeshi.setSummary(parser.nextText());
					}
					else if("detailurl".equals(name))
					{
						infokeshi.setDetailurl(parser.nextText());
					}
					else if("isroot".equals(name))
					{
						if(parser.nextText().equals("true"))
							infokeshi.setRootClass(true);
						else
							infokeshi.setRootClass(false);
					}
					else if("keshis".equals(name))
					{
						
					}
					else
					{
						temp=getKeshiByName(name);
						if(temp != null)
						{
							lastname=name;
							temp.setFirstindex(Constants.keshilist.size());
							temp.setHasChildren(true);
						}
					}
				}
				else if(type == XmlPullParser.END_TAG)
				{
					String name=parser.getName();
					if("keshi".equals(name))
					{
						Constants.keshilist.add(infokeshi);
						if(handler != null)
							handler.sendEmptyMessage(0);
					}
					else if(lastname.equals(name) && temp != null)
					{
						temp.setLastindex(Constants.keshilist.size()-1);
					}
				}
				type=parser.next();
			}
			initialstate=2;
			instream.close();	
		}
		else
		{
			Log.i(TAG, "空数据");
			initialstate=0;
			throw new ClientProtocolException("Response contains no content");
		}
	}
	
	public static List<KeshiInfo> getBigClass()//得到大科室列表
	{
		List<KeshiInfo> info=new ArrayList<KeshiInfo>();
		for(KeshiInfo ele:Constants.keshilist)
		{
			if(ele.isRootClass())
				info.add(ele);
			else//大科室连写
				break;
		}
		return info;
	}
	
	public static List<KeshiInfo> getSmallClass(KeshiInfo obj)//得到大科室下子科室
	{
		List<KeshiInfo> info=new ArrayList<KeshiInfo>();
		if(initialstate == 2)
		{//xml中保证相同科室在一起
			if(obj.getFirstindex() != -1)
				info=Constants.keshilist.subList(obj.getFirstindex(),obj.getLastindex()+1);
		}
		return info;
	}
	
	public static KeshiInfo getKeshiByName(String name)
	{
		for(KeshiInfo ele:Constants.keshilist)
		{
			if(ele.getKeshiname().equals(name))
				return ele;
		}	
		return null;
	}
	
	public static KeshiInfo getKeshiNochildByName(String name)
	{
		for(KeshiInfo ele:Constants.keshilist)
		{
			if(ele.getKeshiname().equals(name) && !ele.isHasChildren())
				return ele;
		}	
		return null;
	}
	
	public static KeshiInfo getKeshiById(int id)
	{
		for(KeshiInfo ele:Constants.keshilist)
		{
			if(ele.getId() == id)
				return ele;
		}	
		return null;
	}
}
