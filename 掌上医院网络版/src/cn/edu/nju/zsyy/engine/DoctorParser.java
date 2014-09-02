package cn.edu.nju.zsyy.engine;

import java.io.InputStream;
import java.util.ArrayList;
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

public class DoctorParser 
{
	private static final String TAG = "DoctorParser";
	public static int initialstate=0;//0表示未初始化 1表示正在初始化 2表示初始化完成
	private Handler handler=null;
	
	public DoctorParser(Handler handler)
	{
		this.handler=handler;
	}
	
	public void getInfo() throws Exception
	{
		if(initialstate == 2 || !Constants.initialed)
			return;
		
		initialstate=1;
		
		String url=Constants.urlroot+Constants.mingyidaquanfile;
		DefaultHttpClient httpClient = new DefaultHttpClient();
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
			DoctorsInfo infodoctors=null;
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
					if("doctor".equals(name))
					{
						infodoctors=new DoctorsInfo();
						infodoctors.setFather(lastname);
					}
					else if("name".equals(name))
					{
						infodoctors.setDoctorname(parser.nextText());
					}
					else if("id".equals(name))
					{
						String str=parser.nextText();
						if(str == "")
							infodoctors.setId(0);
						else
							infodoctors.setId(Integer.parseInt(str));
					}
					else if("summary".equals(name))
					{
						infodoctors.setSummary(parser.nextText());
					}
					else if("smallpicurl".equals(name))
					{
						infodoctors.setSmallpicurl(parser.nextText());
					}
					else if("detailurl".equals(name))
					{
						infodoctors.setDetailurl(parser.nextText());
					}
					else if("usefulinfourl".equals(name))
					{
						infodoctors.setUsefulinfo(parser.nextText());
					}
					else if("doctors".equals(name))
					{
						
					}
					else
					{
						temp=KeshiInfoParser.getKeshiNochildByName(name);
						if(temp != null)
						{
							lastname=name;
							temp.setFirstindex(Constants.doctorlist.size());
						}
					}
				}
				else if(type == XmlPullParser.END_TAG)
				{
					String name=parser.getName();
					if("doctor".equals(name))
					{
						Constants.doctorlist.add(infodoctors);
						if(handler != null)
							handler.sendEmptyMessage(0);
					}
					else if(lastname.equals(name) && temp != null)
					{
						temp.setLastindex(Constants.doctorlist.size()-1);
					}
				}	
				type=parser.next();
			}
			initialstate=2;
			instream.close();
		}
		else
		{
			Log.i(TAG, "数据为空");
			initialstate=0;
			throw new ClientProtocolException("Response contains no content");
		}
	}
	
	public static List<DoctorsInfo> getKeshiDoctors(KeshiInfo info)
	{
		List<DoctorsInfo> out=new ArrayList<DoctorsInfo>();
		if(!info.isHasChildren())
		{
			if(info.getFirstindex() != -1)
				out=Constants.doctorlist.subList(info.getFirstindex(),info.getLastindex()+1);
		}
		return out;
	}
	
	public static DoctorsInfo getDoctorByName(String name)
	{
		for(DoctorsInfo ele:Constants.doctorlist)
		{
			if(ele.getDoctorname().equals(name))
				return ele;
		}	
		return null;
	}
	
	public static DoctorsInfo getDoctorById(int id)
	{
		for(DoctorsInfo ele:Constants.doctorlist)
		{
			if(ele.getId() == id)
				return ele;
		}	
		return null;
	}
}
