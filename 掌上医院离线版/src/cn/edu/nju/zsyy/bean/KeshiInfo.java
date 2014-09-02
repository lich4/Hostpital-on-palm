package cn.edu.nju.zsyy.bean;

public class KeshiInfo extends BaseHospitalInfo 
{
	private KeshiInfo father=null;//ËùÊô´ó¿ÆÊÒ
	private String detailurl="";
	
	public String getDetailurl() 
	{
		return detailurl;
	}
	
	public void setDetailurl(String detailurl) 
	{
		this.detailurl = detailurl;
	}

	public KeshiInfo getFather() 
	{
		return father;
	}

	public void setFather(KeshiInfo father) 
	{
		this.father = father;
	}
}
