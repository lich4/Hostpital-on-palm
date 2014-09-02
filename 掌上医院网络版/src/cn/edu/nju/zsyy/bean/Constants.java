package cn.edu.nju.zsyy.bean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import android.support.v4.app.Fragment;
import cn.edu.nju.zsyy.Fragments.*;
import cn.edu.nju.zsyy.bean.*;

public class Constants 
{	
	//功能序号
	public static final int SHOUYE=00;//首页
	public static final int JIANJIE=10;//医院简介
	public static final int ZHONGDIANZHUANKE=11;//重点专科
	public static final int MINGYIDAQUAN=12;//名医大全
//	public static final int YIYUANSHESHI=13;//医院设施
	public static final int JIUYILIUCHENG=20;//就医流程
	public static final int GUAHAOYINDAO=21;//挂号引导
	public static final int YIYUANDITU=22;//医院地图
	public static final int PUTONGGUAHAO=30;//普通挂号
	public static final int ZHUANJIAGUAHAO=31;//专家挂号
	public static final int GAOJIZHUANJIAGUAHAO=32;//高级专家挂号
	public static final int ZHUANBINGZHUANKEGUAHAO=33;//专病专科挂号
	public static final int YISHENGJIANJIE=34;//医生简介
	public static final int CHUZHENSHIJIAN=35;//出诊时间
//	public static final int PINGJIA=36;//评价
	public static final int HOUZHENSHI=40;//候诊室
	public static final int DIANZIYIZHU=41;//电子医嘱
	public static final int CHAKANDIANZIBINGLI=42;//查看电子病历
	public static final int DIANZIJIANYANBAOGAO=43;//电子检验报告
	public static final int YIHUANHUDONG=44;//医患互动
	public static final int SHOUJIJIANKANGGUWEN=45;//手机健康顾问
	public static final int WODEFUWU=46;//我的服务
	public static final int GUAHAOLIUCHENGTU=50;//挂号流程
	public static final int GUAHAOGUIZE=51;
	public static final int SHIYONGBANGZHU=52;
	public static final int GENGDUO=60;
	public static final int LIANXIWOMEN=70;
	public static final int RUANJIANSHEZHI=80;
	
//	public static final int WEB=90;
	public static final int MAXNUM=100;
	
	//状态序号
	public static final int YISHENG=00;//搜医生
	public static final int KESHI=01;//搜科室
	public static final int WEIZHI=02;//搜位置
	public static final int JIBING=10;//搜疾病
	public static final int ZHENGZHUANG=11;//搜症状
	
	public static int state=SHOUYE;//当前状态
	public static boolean initialed=false;
	
	public static String urlroot="";
	public static String yiyuanjianjiefile="";
	public static String zhongdianzhuankefile="";
	public static String mingyidaquanfile="";
	public static List<DoctorsInfo> doctorlist=new ArrayList<DoctorsInfo>();
	public static List<KeshiInfo> keshilist=new ArrayList<KeshiInfo>();
	
	public static Stack<Fragment> fstack=new Stack<Fragment>();//记录Fragment跳转操作
	
	private static Fragment globalFragment[]=null;
	
	public Constants()
	{
		globalFragment=new Fragment[MAXNUM];
		for(int i=0;i<MAXNUM;i++)
		{
			globalFragment[i]=null;
		}
		
		globalFragment[SHOUYE]=new MainFragment().SetType(SHOUYE);
		globalFragment[JIANJIE]=new FeatureFragment().SetType(JIANJIE);
		globalFragment[ZHONGDIANZHUANKE]=new FeatureFragment().SetType(ZHONGDIANZHUANKE);
		globalFragment[MINGYIDAQUAN]=new FeatureFragment().SetType(MINGYIDAQUAN);
//		globalFragment[YIYUANSHESHI]=new FeatureFragment().SetType(YIYUANSHESHI);
		globalFragment[JIUYILIUCHENG]=new NavigationFragment().SetType(JIUYILIUCHENG);
		globalFragment[GUAHAOYINDAO]=new NavigationFragment().SetType(GUAHAOYINDAO);
		globalFragment[YIYUANDITU]=new NavigationFragment().SetType(YIYUANDITU);
		globalFragment[PUTONGGUAHAO]=new AppointmentFragment().SetType(PUTONGGUAHAO);
		globalFragment[ZHUANJIAGUAHAO]=new AppointmentFragment().SetType(ZHUANJIAGUAHAO);
		globalFragment[GAOJIZHUANJIAGUAHAO]=new AppointmentFragment().SetType(GAOJIZHUANJIAGUAHAO);
		globalFragment[ZHUANBINGZHUANKEGUAHAO]=new AppointmentFragment().SetType(ZHUANBINGZHUANKEGUAHAO);
		globalFragment[HOUZHENSHI]=new WaitingRoomFragment().SetType(HOUZHENSHI);
		globalFragment[DIANZIYIZHU]=new MedicalOrderFragment().SetType(DIANZIYIZHU);
		globalFragment[CHAKANDIANZIBINGLI]=new MedicalRecordFragment().SetType(CHAKANDIANZIBINGLI);
		globalFragment[DIANZIJIANYANBAOGAO]=new InspectionReportFragment().SetType(DIANZIJIANYANBAOGAO);
		globalFragment[YIHUANHUDONG]=new InteractionFragment().SetType(YIHUANHUDONG);
		globalFragment[SHOUJIJIANKANGGUWEN]=new HealthConsultantFragment().SetType(SHOUJIJIANKANGGUWEN);
		globalFragment[WODEFUWU]=new ServiceFragment();
		globalFragment[GUAHAOLIUCHENGTU]=new HelpFragment().SetType(GUAHAOLIUCHENGTU);
		globalFragment[GUAHAOGUIZE]=new HelpFragment().SetType(GUAHAOGUIZE);
		globalFragment[SHIYONGBANGZHU]=new HelpFragment().SetType(SHIYONGBANGZHU);
		globalFragment[GENGDUO]=new MoreFragment().SetType(GENGDUO);
		globalFragment[LIANXIWOMEN]=new ContactusFragment().SetType(LIANXIWOMEN);
		globalFragment[RUANJIANSHEZHI]=new SettingsFragment();
	}
	
	public static Fragment get(int index)
	{
		return globalFragment[index];
	}
	
	public static void set(int index,Fragment fragment)
	{
		globalFragment[index]=fragment;
	}
}
