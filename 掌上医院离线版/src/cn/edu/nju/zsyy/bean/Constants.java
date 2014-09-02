package cn.edu.nju.zsyy.bean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import android.app.Activity;
import android.support.v4.app.Fragment;
import cn.edu.nju.zsyy.Fragments.*;
import cn.edu.nju.zsyy.Fragments.Feature.MingYiDaQuan;
import cn.edu.nju.zsyy.Fragments.Feature.ZhongDianZhuanKe;
import cn.edu.nju.zsyy.bean.*;

public class Constants 
{	
	//功能序号
	public static final int SHOUYE=00;//首页
	
	
	public static final int JIANJIE=10;//医院简介
	public static final int ZHONGDIANZHUANKE=11;//重点专科
	public static final int MINGYIDAQUAN=12;//名医大全
	
	
	public static final int JIUYILIUCHENG=20;//就医流程
	public static final int GUAHAOYINDAO=21;//挂号引导
	public static final int YIYUANDITU=22;//医院地图
	
	
	public static final int PUTONGGUAHAO=30;//普通挂号
	public static final int ZHUANJIAGUAHAO=31;//专家挂号
	public static final int GAOJIZHUANJIAGUAHAO=32;//高级专家挂号
	public static final int ZHUANBINGZHUANKEGUAHAO=33;//专病专科挂号
	
	public static final int YISHENGJIANJIE=34;//医生简介
	public static final int CHUZHENSHIJIAN=35;//出诊时间
	
	public static final int HOUZHENSHI=40;//候诊室
	public static final int DIANZIYIZHU=41;//电子医嘱
	public static final int DIANZIBINGLI=42;//电子病历
	public static final int DIANZIJIANYANBAOGAO=43;//电子检验报告
	public static final int SHOUJIJIANKANGYUANDI=45;//手机健康园地
	
	
	public static final int RUANJIANCAOZUOZHINAN=50;//软件操作指南
	public static final int SHOUJIGUAHAOLIUCHENG=51;//手机挂号流程
	public static final int SHIYONGBANGZHU=52;//使用帮助

	public static final int WODEZHANGHAO=60;//我的账号
	public static final int YINGYONGFENXIANG=61;//应用分享
	public static final int BANBENXINXIJIGENGXIN=62;//版本信息及更新
	public static final int RUANJIANYIJIANFANKUI=63;//软件意见反馈
	
	
	public static final int RUANJIANSHEZHI=80;
	
	
	public static final int MAXNUM=100;
	
	//状态序号
	public static final int YISHENG=0x01;//医生
	public static final int KESHI=0x02;//科室
	public static final int WEIZHI=0x04;//位置
	public static final int JIBING=0x08;//疾病
	public static final int ZHENGZHUANG=0x10;//症状
	
	public static final int SEARCHTYPE_ALL=YISHENG | KESHI | WEIZHI | JIBING | ZHENGZHUANG;
	
	//搜索疾病及症状的限制条件
	public static final int CHILD=0x100;//儿童
	public static final int OLD=0x200;//老人
	public static final int AGE_ALL=CHILD | OLD;
	
	public static final int MALE=0x1000;//男
	public static final int FEMALE=0x2000;//女
	public static final int GENDER_ALL=MALE | FEMALE;
	
	public static final int SEARCHFILTER_ALL=AGE_ALL | GENDER_ALL;
	
	//搜索过滤
	public static final int NAME=0x100;
	public static final int BODYPART=0x200;
	
	public static int state=SHOUYE;//当前状态
	public static boolean initialed=false;
	
	public static String urlroot="";
	public static String yiyuanjianjiefile="";
	public static String zhongdianzhuankefile="";
	public static String mingyidaquanfile="";
	public static List<DoctorsInfo> doctorlist=new ArrayList<DoctorsInfo>();
	public static List<KeshiInfo> keshilist=new ArrayList<KeshiInfo>();
	public static List<PositionInfo> positionlist=new ArrayList<PositionInfo>();
}
