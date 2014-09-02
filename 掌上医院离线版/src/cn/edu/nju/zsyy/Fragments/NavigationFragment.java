package cn.edu.nju.zsyy.Fragments;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.Fragments.Navigation.GuaHaoYinDao;
import cn.edu.nju.zsyy.Fragments.Navigation.JiuYiLiuCheng;
import cn.edu.nju.zsyy.Fragments.Navigation.YiYuanDiTu;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.bean.PositionInfo;
import cn.edu.nju.zsyy.engine.PositionParser;

public class NavigationFragment extends Fragment implements OnClickListener
{//¾ÍÒ½µ¼º½
	private static final String TAG = "NavigationFragment";
	private int type=0;
	private Button btjiuyiliucheng=null;
	private Button btguahaoyindao=null;
	private Button btyiyuanditu=null;
	private FragmentManager manager=null;

	private List<PositionInfo> info=null;
	
	private ViewPager mPager=null;
	private FragmentStatePagerAdapter mAdapter=null;
	
	private int currIndex=0;
    private int pagenum=3;
    private int position_one;
    private int position_two;
    private int position_three;
    private ImageView ivBottomLine;
    private View navigationview=null;
    
	private int bottomLineWidth;
	
    public Fragment SetType(int type)
	{
		this.type=type;
		return this;
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		activity.setTitle(R.drawable.jiuyidaohang_title);
	}
	
	private void InitWidth(View v) 
	{
		manager=getFragmentManager();
        ivBottomLine = (ImageView)v.findViewById(R.id.iv_bottom_line);
        bottomLineWidth = ivBottomLine.getLayoutParams().width;
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        ivBottomLine.setLayoutParams(new RelativeLayout.LayoutParams(screenW/3,10));
        position_one = (int) (screenW / 3.0);
        position_two = position_one * 2;
        position_three = position_one * 3;
    }
	
	@Override
	public View onCreateView(final LayoutInflater inflater,ViewGroup viewGroup, Bundle bundle) 
	{
		if(navigationview != null)
			viewGroup.removeAllViews();
		navigationview=inflater.inflate(R.layout.fragment_jiuyidaohang,null,false);
		info=PositionParser.getBigClass();
		InitWidth(navigationview);
		
		mPager=(ViewPager)navigationview.findViewById(R.id.vPager);
		btjiuyiliucheng=(Button)navigationview.findViewById(R.id.jiuyiliucheng);
		btguahaoyindao=(Button)navigationview.findViewById(R.id.guahaoyindao);
		btyiyuanditu=(Button)navigationview.findViewById(R.id.yiyuanditu);
		
		btjiuyiliucheng.setTextColor(getResources().getColor(R.color.themecolor));
		btguahaoyindao.setTextColor(getResources().getColor(R.color.frame_button_text_light));
		btyiyuanditu.setTextColor(getResources().getColor(R.color.frame_button_text_light));
		
		btjiuyiliucheng.setEnabled(true);
		btguahaoyindao.setEnabled(true);
		btyiyuanditu.setEnabled(true);
		
		btjiuyiliucheng.setOnClickListener(this);
		btguahaoyindao.setOnClickListener(this);
		btyiyuanditu.setOnClickListener(this);
		
		
		mAdapter=new FragmentStatePagerAdapter(manager)
		{
			@Override
			public int getCount() 
			{
				return pagenum;
			}
			
			@Override
			public Fragment getItem(int position)
			{
				if(position == 0)
					return new JiuYiLiuCheng();
				else if(position == 1)
					return new GuaHaoYinDao();
				else
					return new YiYuanDiTu().SetParam(info);
			}
		};
		
		mPager.setAdapter(mAdapter);
		
		mPager.setOnPageChangeListener(new OnPageChangeListener()
		{
			@Override
			public void onPageScrollStateChanged(int arg0)
			{
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) 
			{	
			}

			@Override
			public void onPageSelected(int index) 
			{
				float fromXDelta=0,toXDelta=0,fromYDelta=0,toYDelta=0;
				switch(index)
				{
					case 0:
						if(currIndex == 1)
						{
							fromXDelta=position_one;
						}
						else if(currIndex == 2)
						{
							fromXDelta=position_two;							
						}
						btjiuyiliucheng.setTextColor(getResources().getColor(R.color.themecolor));
						btguahaoyindao.setTextColor(getResources().getColor(R.color.frame_button_text_light));
						btyiyuanditu.setTextColor(getResources().getColor(R.color.frame_button_text_light));
						break;
						
					case 1:
						if(currIndex == 2)
						{
							fromXDelta=position_two;
						}
						toXDelta=position_one;
						btjiuyiliucheng.setTextColor(getResources().getColor(R.color.frame_button_text_light));
						btguahaoyindao.setTextColor(getResources().getColor(R.color.themecolor));
						btyiyuanditu.setTextColor(getResources().getColor(R.color.frame_button_text_light));
						break;
						
					case 2:
						if(currIndex == 1)
						{
							fromXDelta=position_one;
						}
						toXDelta=position_two;
						btjiuyiliucheng.setTextColor(getResources().getColor(R.color.frame_button_text_light));
						btguahaoyindao.setTextColor(getResources().getColor(R.color.frame_button_text_light));
						btyiyuanditu.setTextColor(getResources().getColor(R.color.themecolor));
						break;
				}
				Animation anim=new TranslateAnimation(fromXDelta,toXDelta,fromYDelta,toYDelta);
				currIndex=index;
				anim.setFillAfter(true);
				anim.setDuration(100);
				ivBottomLine.startAnimation(anim);
			}
		});
		
		if(type == Constants.JIUYILIUCHENG)
		{
			mPager.setCurrentItem(0);
		}
		else if(type == Constants.GUAHAOYINDAO)
		{
			mPager.setCurrentItem(1);
		}
		else
		{
			mPager.setCurrentItem(2);
		}	
		
		return navigationview;
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
			case R.id.jiuyiliucheng:
				mPager.setCurrentItem(0);
				SetType(Constants.JIUYILIUCHENG);
				break;
				
			case R.id.guahaoyindao:
				mPager.setCurrentItem(1);
				SetType(Constants.GUAHAOYINDAO);
				break;
				
			case R.id.yiyuanditu:
				mPager.setCurrentItem(2);
				SetType(Constants.YIYUANDITU);
				break;
		}
	}
}
