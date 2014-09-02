package cn.edu.nju.zsyy.engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

import cn.edu.nju.zsyy.bean.Constants;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DownloadFileTask 
{
	public static File getFile(String path,String filepath) throws Exception
	{
		int time = 0;
    	final int RETRY_TIME=3;
    	
    	do
    	{
    		try 
			{
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
					File file=new File(filepath);
					FileOutputStream fos=new FileOutputStream(file);
					byte[] buffer=new byte[1024];
					int len=0;
					while((len=instream.read(buffer)) != -1)
					{
						fos.write(buffer,0,len);
					}
					fos.flush();
					fos.close();
					instream.close();
					return file;
				}
				else
				{
					throw new ClientProtocolException("Response contains no content");
				}
			} 
    		catch (IOException e) 
    		{
				time++;
				if(time < RETRY_TIME) 
				{
					try 
					{
						Thread.sleep(1000);
					} 
					catch (Exception e1) 
					{
						
					} 
					continue;
				}
				// ·¢ÉúÍøÂçÒì³£
				e.printStackTrace();
			} 
    	}
    	while(time < RETRY_TIME);
		return null;
	}
}
