package cn.edu.nju.zsyy.bean;

public class KeshiInfo 
{
	private int id;//全局唯一标记
	private String keshiname="";
	private String summary="";
	private String detailurl="";
	private KeshiInfo father=null;//所属大科室
	private boolean isRootClass=false;//是否为大类别
	private boolean hasChildren=false;//是否有子节点
	private int firstindex=-1;//该科室对应医生列表的第一个医生/子科室	hasChildren=false/true
	private int lastindex=-1;//该科室对应医生列表的最后一个医生/子科室	hasChildren=false/true
	
	public String getKeshiname() 
	{
		return keshiname;
	}
	
	public void setKeshiname(String keshiname) 
	{
		this.keshiname = keshiname;
	}
	
	public String getSummary()
	{
		return summary;
	}
	
	public void setSummary(String summary) 
	{
		this.summary = summary;
	}
	
	public String getDetailurl() 
	{
		return detailurl;
	}
	
	public void setDetailurl(String detailurl) 
	{
		this.detailurl = detailurl;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public KeshiInfo getFather() 
	{
		return father;
	}

	public void setFather(KeshiInfo father) 
	{
		this.father = father;
	}

	public boolean isRootClass() 
	{
		return isRootClass;
	}

	public void setRootClass(boolean isRootClass) 
	{
		this.isRootClass = isRootClass;
	}

	public int getFirstindex() 
	{
		return firstindex;
	}

	public void setFirstindex(int firstindex) 
	{
		this.firstindex = firstindex;
	}

	public int getLastindex()
	{
		return lastindex;
	}

	public void setLastindex(int lastindex) 
	{
		this.lastindex = lastindex;
	}

	public boolean isHasChildren() 
	{
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) 
	{
		this.hasChildren = hasChildren;
	}
}
