package cn.edu.nju.zsyy.Fragments;

import java.util.List;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
import cn.edu.nju.zsyy.Fragments.Feature.MingYiDaQuan;
import cn.edu.nju.zsyy.Fragments.Feature.ZhongDianZhuanKe;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.bean.KeshiInfo;
import cn.edu.nju.zsyy.engine.KeshiInfoParser;

public class FeatureFragment extends Fragment implements OnClickListener
{//医院特色
	private static final String TAG = "FeatureFragment";
	private int type=0;
	private Button btjianjie=null;
	private Button btzhongdianzhuanke=null;
	private Button btmingyidaquan=null;
	private FragmentManager manager=null;

	private List<KeshiInfo> info=null;//大科室数组

	private ViewPager mPager=null;
	private FragmentStatePagerAdapter mAdapter=null;
	private int currIndex=0;
    private int pagenum=3;
    private int position_one;
    private int position_two;
    private int position_three;
    private ImageView ivBottomLine=null;
    private View featureview=null;
    
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
		activity.setTitle(R.drawable.yiyuantese_title);
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
	public View onCreateView(LayoutInflater inflater,ViewGroup viewGroup,Bundle bundle)
	{
		if(featureview != null)
			viewGroup.removeAllViews();
		featureview=inflater.inflate(R.layout.fragment_yiyuantese,null,false);
		info=KeshiInfoParser.getBigClass();
		InitWidth(featureview);
		
		mPager=(ViewPager)featureview.findViewById(R.id.vPager);
		btjianjie=(Button)featureview.findViewById(R.id.jianjie);
		btzhongdianzhuanke=(Button)featureview.findViewById(R.id.zhongdianzhuanke);
		btmingyidaquan=(Button)featureview.findViewById(R.id.mingyidaquan);
		
		btjianjie.setTextColor(getResources().getColor(R.color.themecolor));
		btzhongdianzhuanke.setTextColor(getResources().getColor(R.color.frame_button_text_light));
		btmingyidaquan.setTextColor(getResources().getColor(R.color.frame_button_text_light));
		
		btjianjie.setEnabled(true);
		btzhongdianzhuanke.setEnabled(true);
		btmingyidaquan.setEnabled(true);
		
		btjianjie.setOnClickListener(this);
		btzhongdianzhuanke.setOnClickListener(this);
		btmingyidaquan.setOnClickListener(this);
		
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
					return new WebFragment().SetParam("file://"+Constants.urlroot+Constants.yiyuanjianjiefile,"");
				else if(position == 1)
					return new ZhongDianZhuanKe().SetParam(info);
				else
					return new MingYiDaQuan().SetParam(info);
			}

			@Override
			public int getItemPosition(Object object)
			{
				return POSITION_NONE;
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
						btjianjie.setTextColor(getResources().getColor(R.color.themecolor));
						btzhongdianzhuanke.setTextColor(getResources().getColor(R.color.frame_button_text_light));
						btmingyidaquan.setTextColor(getResources().getColor(R.color.frame_button_text_light));
						break;
						
					case 1:
						if(currIndex == 2)
						{
							fromXDelta=position_two;
						}
						toXDelta=position_one;
						btjianjie.setTextColor(getResources().getColor(R.color.frame_button_text_light));
						btzhongdianzhuanke.setTextColor(getResources().getColor(R.color.themecolor));
						btmingyidaquan.setTextColor(getResources().getColor(R.color.frame_button_text_light));
						break;
						
					case 2:
						if(currIndex == 1)
						{
							fromXDelta=position_one;
						}
						toXDelta=position_two;
						btjianjie.setTextColor(getResources().getColor(R.color.frame_button_text_light));
						btzhongdianzhuanke.setTextColor(getResources().getColor(R.color.frame_button_text_light));
						btmingyidaquan.setTextColor(getResources().getColor(R.color.themecolor));
						break;
				}
				Animation anim=new TranslateAnimation(fromXDelta,toXDelta,fromYDelta,toYDelta);
				currIndex=index;
				anim.setFillAfter(true);
				anim.setDuration(100);
				ivBottomLine.startAnimation(anim);
			}
		});

		if(type == Constants.JIANJIE)
		{
			mPager.setCurrentItem(0);
		}
		else if(type == Constants.ZHONGDIANZHUANKE)
		{
			mPager.setCurrentItem(1);
		}
		else
		{
			mPager.setCurrentItem(2);
		}	
		
		return featureview;
	}
	
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
			case R.id.jianjie:
				mPager.setCurrentItem(0);
				SetType(Constants.JIANJIE);
				break;
				
			case R.id.zhongdianzhuanke:
				mPager.setCurrentItem(1);
				SetType(Constants.ZHONGDIANZHUANKE);
				break;
				
			case R.id.mingyidaquan:
				mPager.setCurrentItem(2);
				SetType(Constants.MINGYIDAQUAN);
				break;
		}
		mAdapter.notifyDataSetChanged();
	}
}
