package cn.edu.nju.zsyy.engine;

import java.util.ArrayList;
import java.util.List;

import cn.edu.nju.zsyy.bean.BaseHospitalInfo;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.bean.PositionInfo;
import android.os.Handler;

public class PositionParser 
{
	private static final String TAG = "DoctorParser";
	public static int initialstate=0;//0表示未初始化 1表示正在初始化 2表示初始化完成
	private Handler handler=null;
	
	public PositionParser(Handler handler)
	{
		this.handler=handler;
	}
	
	public void getInfo()
	{
		for(BaseHospitalInfo info:Constants.keshilist)
		{
			PositionInfo pos=new PositionInfo();
			pos.setFirstindex(info.getFirstindex());
			pos.setHasChildren(info.isHasChildren());
			pos.setId(info.getId());
			pos.setLastindex(info.getLastindex());
			pos.setSummary("门诊大楼5楼西侧\n(电梯口出门右转)");
			pos.setname(info.getname());
			pos.setType(Constants.KESHI);
			pos.setRootClass(info.isRootClass());
			Constants.positionlist.add(pos);
		}
		
		PositionInfo jiaofeipos=new PositionInfo();
		jiaofeipos.setHasChildren(true);
		jiaofeipos.setFirstindex(Constants.positionlist.size());
		jiaofeipos.setLastindex(Constants.positionlist.size());
		jiaofeipos.setId(-1);
		jiaofeipos.setname("缴费处");
		jiaofeipos.setType(Constants.WEIZHI);
		jiaofeipos.setRootClass(true);
		Constants.positionlist.add(jiaofeipos);
		
		PositionInfo jiaofeiposc=new PositionInfo();
		jiaofeiposc.setHasChildren(false);
		jiaofeiposc.setId(-1);
		jiaofeiposc.setSummary("门诊大楼5楼西侧\n(电梯口出门右转)");
		jiaofeiposc.setname("缴费处");
		jiaofeiposc.setType(Constants.WEIZHI);
		Constants.positionlist.add(jiaofeiposc);
		
		PositionInfo yaofangpos=new PositionInfo();
		yaofangpos.setHasChildren(true);
		yaofangpos.setFirstindex(Constants.positionlist.size());
		yaofangpos.setLastindex(Constants.positionlist.size());
		yaofangpos.setId(-1);
		yaofangpos.setname("药房");
		yaofangpos.setType(Constants.WEIZHI);
		yaofangpos.setRootClass(true);
		Constants.positionlist.add(yaofangpos);
		
		PositionInfo yaofangposc=new PositionInfo();
		yaofangposc.setHasChildren(false);
		yaofangposc.setId(-1);
		yaofangposc.setSummary("门诊大楼5楼西侧\n(电梯口出门右转)");
		yaofangposc.setname("药房");
		yaofangposc.setType(Constants.WEIZHI);
		Constants.positionlist.add(yaofangposc);
		
		PositionInfo jianchapos=new PositionInfo();
		jianchapos.setHasChildren(true);
		jianchapos.setFirstindex(Constants.positionlist.size());
		jianchapos.setLastindex(Constants.positionlist.size());
		jianchapos.setId(-1);
		jianchapos.setname("检查处");
		jianchapos.setType(Constants.WEIZHI);
		yaofangpos.setRootClass(true);
		Constants.positionlist.add(jianchapos);
		
		PositionInfo jianchaposc=new PositionInfo();
		jianchaposc.setHasChildren(false);
		jianchaposc.setId(-1);
		jianchaposc.setSummary("门诊大楼5楼西侧\n(电梯口出门右转)");
		jianchaposc.setname("检查处");
		jianchaposc.setType(Constants.WEIZHI);
		Constants.positionlist.add(jianchaposc);
		
		PositionInfo yiloupos=new PositionInfo();
		PositionInfo erloupos=new PositionInfo();
		PositionInfo sanloupos=new PositionInfo();
		PositionInfo siloupos=new PositionInfo();
		PositionInfo wuloupos=new PositionInfo();
		PositionInfo loucengtu=new PositionInfo();
		loucengtu.setname("楼层图");
		loucengtu.setHasChildren(true);
		loucengtu.setId(-1);
		loucengtu.setType(Constants.WEIZHI);
		
		yiloupos.setname("一楼");
		yiloupos.setHasChildren(false);
		yiloupos.setId(-1);
		yiloupos.setSummary("门诊大楼5楼西侧\n(电梯口出门右转)");
		yiloupos.setType(Constants.WEIZHI);
		loucengtu.setFirstindex(Constants.positionlist.size());
		Constants.positionlist.add(yiloupos);
		
		erloupos.setname("二楼");
		erloupos.setHasChildren(false);
		erloupos.setId(-1);
		erloupos.setSummary("门诊大楼5楼西侧\n(电梯口出门右转)");
		erloupos.setType(Constants.WEIZHI);
		Constants.positionlist.add(erloupos);
		
		sanloupos.setname("三楼");
		sanloupos.setHasChildren(false);
		sanloupos.setId(-1);
		sanloupos.setSummary("门诊大楼5楼西侧\n(电梯口出门右转)");
		sanloupos.setType(Constants.WEIZHI);
		Constants.positionlist.add(sanloupos);
		
		siloupos.setname("四楼");
		siloupos.setHasChildren(false);
		siloupos.setId(-1);
		siloupos.setSummary("门诊大楼5楼西侧\n(电梯口出门右转)");
		siloupos.setType(Constants.WEIZHI);
		Constants.positionlist.add(siloupos);
		
		wuloupos.setname("五楼");
		wuloupos.setHasChildren(false);
		wuloupos.setId(-1);
		wuloupos.setSummary("门诊大楼5楼西侧\n(电梯口出门右转)");
		wuloupos.setType(Constants.WEIZHI);
		loucengtu.setLastindex(Constants.positionlist.size());
		Constants.positionlist.add(wuloupos);
	}
	
	public static List<PositionInfo> getBigClass()//得到大科室列表
	{
		List<PositionInfo> info=new ArrayList<PositionInfo>();
		for(PositionInfo ele:Constants.positionlist)
		{
			if(ele.isRootClass())
				info.add(ele);
		}
		return info;
	}
	
	public static List<PositionInfo> getSmallClass(PositionInfo obj)//得到大科室下子科室
	{
		List<PositionInfo> info=new ArrayList<PositionInfo>();
		if(initialstate == 2)
		{
			if(obj.getFirstindex() != -1)
				info=Constants.positionlist.subList(obj.getFirstindex(),obj.getLastindex()+1);
		}
		return info;
	}
	
	public static PositionInfo getPositionByName(String name)
	{
		for(PositionInfo ele:Constants.positionlist)
		{
			if(ele.getname().equals(name))
				return ele;
		}	
		return null;
	}
	
	public static PositionInfo getPositionNochildByName(String name)
	{
		for(PositionInfo ele:Constants.positionlist)
		{
			if(ele.getname().equals(name) && !ele.isHasChildren())
				return ele;
		}	
		return null;
	}
	
	public static PositionInfo getPositionById(int id)
	{
		for(PositionInfo ele:Constants.positionlist)
		{
			if(ele.getId() == id)
				return ele;
		}	
		return null;
	}
}
