package cn.edu.nju.zsyy.controls;

import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.Activity.SearchActivity;
import cn.edu.nju.zsyy.bean.Constants;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class SearchToolBar extends PopupWindow implements OnClickListener
{
	
	/**
	 * @param context
	 * @param fatherView	Õû¸öËÑË÷¿ò¿Ø¼þ
	 * @param fromView		ËÑË÷°´Å¥
	 */
	private LinearLayout searchfather=null;
	private RelativeLayout content_mutiwindow=null;
	private SearchActivity mainpointer=null;
	
	private ImageView search_category_all=null;
	private ImageView search_category_keshi=null;
	private ImageView search_category_yisheng=null;
	private ImageView search_category_weizhi=null;
	private ImageView search_category_jibing=null;
	private ImageView search_category_zhengzhuang=null;
	
	private ImageView searchbutton=null;
	
	private ImageView gender_any=null;
	private ImageView gender_male=null;
	private ImageView gender_female=null;
	private ImageView age_any=null;
	private ImageView age_old=null;
	private ImageView age_child=null;
	
	public void SetSearchType(int type)
	{
		mainpointer.SearchType=type;
	}
	
	public void SetSearchFilter(int type)
	{
		mainpointer.SearchFilter=type;
	}
	
	public SearchToolBar(SearchActivity activity,LinearLayout searchfather,ImageView searchbutton)
	{
		super(activity);
		mainpointer=activity;
		this.searchfather=searchfather;
		this.searchbutton=searchbutton;
		setWindowLayoutMode(0, 0);
		int[] pos=new int[2];
		searchfather.getLocationInWindow(pos);
		DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
		setWidth(screenW-80);
		setAnimationStyle(R.style.Animation_longclick_menu);
		
		View searchview=LayoutInflater.from(activity).inflate(R.layout.search_category,null);
		content_mutiwindow=(RelativeLayout) searchview.findViewById(R.id.content_mutiwindow);
		content_mutiwindow.measure(0, 0);
		setHeight(content_mutiwindow.getMeasuredHeight());
		setContentView(searchview);
		setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.blank));
		
		((RelativeLayout.LayoutParams)(content_mutiwindow.getLayoutParams())).addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.TRUE);
		setOutsideTouchable(true); 
		setFocusable(true);
		
		searchview.findViewById(R.id.search_category_all_container).setOnClickListener(this);
		searchview.findViewById(R.id.search_category_keshi_container).setOnClickListener(this);
		searchview.findViewById(R.id.search_category_yisheng_container).setOnClickListener(this);
		searchview.findViewById(R.id.search_category_weizhi_container).setOnClickListener(this);
		searchview.findViewById(R.id.search_category_jibing_container).setOnClickListener(this);
		searchview.findViewById(R.id.search_category_zhengzhuang_container).setOnClickListener(this);
	
		search_category_all=(ImageView) searchview.findViewById(R.id.search_category_all);
		search_category_keshi=(ImageView) searchview.findViewById(R.id.search_category_keshi);
		search_category_yisheng=(ImageView) searchview.findViewById(R.id.search_category_yisheng);
		search_category_weizhi=(ImageView) searchview.findViewById(R.id.search_category_weizhi);
		search_category_jibing=(ImageView) searchview.findViewById(R.id.search_category_jibing);
		search_category_zhengzhuang=(ImageView) searchview.findViewById(R.id.search_category_zhengzhuang);
	
		gender_any=(ImageView) searchview.findViewById(R.id.gender_any);
		gender_male=(ImageView) searchview.findViewById(R.id.gender_male);
		gender_female=(ImageView) searchview.findViewById(R.id.gender_female);
		age_any=(ImageView) searchview.findViewById(R.id.age_any);
		age_old=(ImageView) searchview.findViewById(R.id.age_old);
		age_child=(ImageView) searchview.findViewById(R.id.age_child);
		gender_any.setOnClickListener(this);
		gender_male.setOnClickListener(this);
		gender_female.setOnClickListener(this);
		age_any.setOnClickListener(this);
		age_old.setOnClickListener(this);
		age_child.setOnClickListener(this);
		
		Resources res=mainpointer.getResources();
		gender_any.setImageDrawable(res.getDrawable(R.drawable.check_checked));
		age_any.setImageDrawable(res.getDrawable(R.drawable.check_checked));
		search_category_all.setImageDrawable(res.getDrawable(R.drawable.search_category_all_click));
		
		Button btapply=(Button) searchview.findViewById(R.id.apply);
		btapply.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				dismiss();
			}
		});
	}

	@Override
	public void onClick(View v) 
	{
		Resources res=mainpointer.getResources();
		switch(v.getId())
		{
			case R.id.search_category_all_container:
				searchbutton.setImageDrawable(res.getDrawable(R.drawable.search_all));
				search_category_all.setImageDrawable(res.getDrawable(R.drawable.search_category_all_click));
				search_category_keshi.setImageDrawable(res.getDrawable(R.drawable.search_category_keshi));
				search_category_yisheng.setImageDrawable(res.getDrawable(R.drawable.search_category_yisheng));
				search_category_weizhi.setImageDrawable(res.getDrawable(R.drawable.search_category_weizhi));
				search_category_jibing.setImageDrawable(res.getDrawable(R.drawable.search_category_jibing));
				search_category_zhengzhuang.setImageDrawable(res.getDrawable(R.drawable.search_category_zhengzhuang));
				SetSearchType(Constants.SEARCHTYPE_ALL);
				break;
				
			case R.id.search_category_keshi_container:
				searchbutton.setImageDrawable(res.getDrawable(R.drawable.search_keshi));
				search_category_all.setImageDrawable(res.getDrawable(R.drawable.search_category_all));
				search_category_keshi.setImageDrawable(res.getDrawable(R.drawable.search_category_keshi_click));
				search_category_yisheng.setImageDrawable(res.getDrawable(R.drawable.search_category_yisheng));
				search_category_weizhi.setImageDrawable(res.getDrawable(R.drawable.search_category_weizhi));
				search_category_jibing.setImageDrawable(res.getDrawable(R.drawable.search_category_jibing));
				search_category_zhengzhuang.setImageDrawable(res.getDrawable(R.drawable.search_category_zhengzhuang));
				SetSearchType(Constants.KESHI);
				break;
				
			case R.id.search_category_yisheng_container:
				searchbutton.setImageDrawable(res.getDrawable(R.drawable.search_yisheng));
				search_category_all.setImageDrawable(res.getDrawable(R.drawable.search_category_all));
				search_category_keshi.setImageDrawable(res.getDrawable(R.drawable.search_category_keshi));
				search_category_yisheng.setImageDrawable(res.getDrawable(R.drawable.search_category_yisheng_click));
				search_category_weizhi.setImageDrawable(res.getDrawable(R.drawable.search_category_weizhi));
				search_category_jibing.setImageDrawable(res.getDrawable(R.drawable.search_category_jibing));
				search_category_zhengzhuang.setImageDrawable(res.getDrawable(R.drawable.search_category_zhengzhuang));
				SetSearchType(Constants.YISHENG);
				break;
				
			case R.id.search_category_weizhi_container:
				searchbutton.setImageDrawable(res.getDrawable(R.drawable.search_weizhi));
				search_category_all.setImageDrawable(res.getDrawable(R.drawable.search_category_all));
				search_category_keshi.setImageDrawable(res.getDrawable(R.drawable.search_category_keshi));
				search_category_yisheng.setImageDrawable(res.getDrawable(R.drawable.search_category_yisheng));
				search_category_weizhi.setImageDrawable(res.getDrawable(R.drawable.search_category_weizhi_click));
				search_category_jibing.setImageDrawable(res.getDrawable(R.drawable.search_category_jibing));
				search_category_zhengzhuang.setImageDrawable(res.getDrawable(R.drawable.search_category_zhengzhuang));
				SetSearchType(Constants.WEIZHI);
				break;
				
			case R.id.search_category_jibing_container:
				searchbutton.setImageDrawable(res.getDrawable(R.drawable.search_jibing));
				search_category_all.setImageDrawable(res.getDrawable(R.drawable.search_category_all));
				search_category_keshi.setImageDrawable(res.getDrawable(R.drawable.search_category_keshi));
				search_category_yisheng.setImageDrawable(res.getDrawable(R.drawable.search_category_yisheng));
				search_category_weizhi.setImageDrawable(res.getDrawable(R.drawable.search_category_weizhi));
				search_category_jibing.setImageDrawable(res.getDrawable(R.drawable.search_category_jibing_click));
				search_category_zhengzhuang.setImageDrawable(res.getDrawable(R.drawable.search_category_zhengzhuang));
				SetSearchType(Constants.JIBING);
				break;
				
			case R.id.search_category_zhengzhuang_container:
				searchbutton.setImageDrawable(res.getDrawable(R.drawable.search_zhengzhuang));
				search_category_all.setImageDrawable(res.getDrawable(R.drawable.search_category_all));
				search_category_keshi.setImageDrawable(res.getDrawable(R.drawable.search_category_keshi));
				search_category_yisheng.setImageDrawable(res.getDrawable(R.drawable.search_category_yisheng));
				search_category_weizhi.setImageDrawable(res.getDrawable(R.drawable.search_category_weizhi));
				search_category_jibing.setImageDrawable(res.getDrawable(R.drawable.search_category_jibing));
				search_category_zhengzhuang.setImageDrawable(res.getDrawable(R.drawable.search_category_zhengzhuang_click));
				SetSearchType(Constants.ZHENGZHUANG);
				break;
				
			case R.id.gender_any:
				gender_any.setImageDrawable(res.getDrawable(R.drawable.check_checked));
				gender_male.setImageDrawable(res.getDrawable(R.drawable.check_normal));
				gender_female.setImageDrawable(res.getDrawable(R.drawable.check_normal));
				break;
				
			case R.id.gender_male:
				gender_any.setImageDrawable(res.getDrawable(R.drawable.check_normal));
				gender_male.setImageDrawable(res.getDrawable(R.drawable.check_checked));
				gender_female.setImageDrawable(res.getDrawable(R.drawable.check_normal));
				break;
				
			case R.id.gender_female:
				gender_any.setImageDrawable(res.getDrawable(R.drawable.check_normal));
				gender_male.setImageDrawable(res.getDrawable(R.drawable.check_normal));
				gender_female.setImageDrawable(res.getDrawable(R.drawable.check_checked));
				break;
				
			case R.id.age_any:
				age_any.setImageDrawable(res.getDrawable(R.drawable.check_checked));
				age_old.setImageDrawable(res.getDrawable(R.drawable.check_normal));
				age_child.setImageDrawable(res.getDrawable(R.drawable.check_normal));
				break;
				
			case R.id.age_old:
				age_any.setImageDrawable(res.getDrawable(R.drawable.check_normal));
				age_old.setImageDrawable(res.getDrawable(R.drawable.check_checked));
				age_child.setImageDrawable(res.getDrawable(R.drawable.check_normal));
				break;
				
			case R.id.age_child:
				age_any.setImageDrawable(res.getDrawable(R.drawable.check_normal));
				age_old.setImageDrawable(res.getDrawable(R.drawable.check_normal));
				age_child.setImageDrawable(res.getDrawable(R.drawable.check_checked));
				break;
		}
	}	
}
