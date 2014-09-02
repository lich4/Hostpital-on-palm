package cn.edu.nju.zsyy.engine;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
import android.util.Xml;

import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.bean.UpdateInfo;

public class UpdateInfoParser 
{
	private static UpdateInfo info=null;
	
	public static UpdateInfo getUpdateInfo(String path) throws Exception
	{
		if(Constants.initialed)
			return info;
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(path);
		HttpResponse response = httpClient.execute(httpGet);

		response=httpClient.execute(httpGet);
		StatusLine statu=response.getStatusLine();
		if (statu.getStatusCode() >= 300)
		{
			throw new HttpResponseException(statu.getStatusCode(),statu.getReasonPhrase());
		}
		HttpEntity entity = response.getEntity();
		if (entity != null)
		{
			InputStream instream = entity.getContent();
			XmlPullParser parser=Xml.newPullParser();
			info=new UpdateInfo();
			parser.setInput(instream,"utf-8");
			int type=parser.getEventType();
			while(type != XmlPullParser.END_DOCUMENT)
			{
				if(type == XmlPullParser.START_TAG)
				{
					if("version".equals(parser.getName()))
					{
						info.setVersion(parser.nextText());	
					}
					else if("description".equals(parser.getName()))
					{
						info.setDescription(parser.nextText());
					}
					else if("apkurl".equals(parser.getName()))
					{
						info.setApkurl(parser.nextText());
					}
					else if("urlroot".equals(parser.getName()))
					{
						info.setrrlroot(parser.nextText());
					}
					else if("yiyuanjianjie".equals(parser.getName()))
					{
						info.setyiyuanjianjie(parser.nextText());
					}
					else if("zhongdianzhuanke".equals(parser.getName()))
					{
						info.setzhongdianzhuankefile(parser.nextText());
					}
					else if("mingyidaquan".equals(parser.getName()))
					{
						info.setmingyidaquan(parser.nextText());
					}
				}
				type=parser.next();
			}
			Constants.initialed=true;
			instream.close();	
		}
		else
		{
			throw new ClientProtocolException("Response contains no content");
		}
		
		return info;
	}
}
