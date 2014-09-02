package cn.edu.nju.zsyy.bean;

public class BaseHospitalInfo 
{
	private int id;
	private String name = "";
	private String summary = "";
	private boolean isRootClass = false;
	private boolean hasChildren = false;
	private int firstindex = -1;
	private int lastindex = -1;

	public String getname() 
	{
		return name;
	}

	public void setname(String keshiname)
	{
		this.name = keshiname;
	}

	public String getSummary() 
	{
		return summary;
	}

	public void setSummary(String summary) 
	{
		this.summary = summary;
	}

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
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