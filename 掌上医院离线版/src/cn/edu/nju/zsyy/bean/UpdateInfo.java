package cn.edu.nju.zsyy.bean;


public class UpdateInfo 
{
	private String version=null;
	private String description=null;
	private String apkurl=null;
	
	public String getVersion() 
	{
		return version;
	}
	
	public void setVersion(String version) 
	{
		this.version = version;
	}
	
	public String getDescription() 
	{
		return description;
	}
	
	public void setDescription(String description) 
	{
		this.description = description;
	}
	
	public String getApkurl() 
	{
		return apkurl;
	}
	
	public void setApkurl(String apkurl) 
	{
		this.apkurl = apkurl;
	}
	
	public void setrrlroot(String urlroot)
	{
		Constants.urlroot=urlroot;
	}
	
	public void setyiyuanjianjie(String yiyuanjianjie)
	{
		Constants.yiyuanjianjiefile=yiyuanjianjie;
	}
	
	public void setzhongdianzhuankefile(String zhongdianzhuanke)
	{
		Constants.zhongdianzhuankefile=zhongdianzhuanke;
	}
	
	public void setmingyidaquan(String mingyidaquan)
	{
		Constants.mingyidaquanfile=mingyidaquan;
	}
}
