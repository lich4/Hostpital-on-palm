package cn.edu.nju.zsyy.Fragments;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.edu.nju.zsyy.MainActivity;
import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.zsyyApplication;
import cn.edu.nju.zsyy.adapter.BannerAdapter;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.drawer.NavigationDrawerFragment;
import cn.edu.nju.zsyy.engine.DoctorParser;
import cn.edu.nju.zsyy.engine.KeshiInfoParser;
import cn.edu.nju.zsyy.engine.UpdateInfoParser;
import cn.edu.nju.zsyy.safety.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainFragment extends Fragment 
{
	private static final int LOGIN_REQUEST = 1;
	private static final int PAGE_COUNT = 5;
	private static final int SUCCESS = 1;
	private BannerAdapter adapter;
	private NavigationDrawerFragment drawer;
	private ImageView[] indicators = new ImageView[PAGE_COUNT];
	private int previousPosition = 0;
	private ViewPager viewPager=null;
	private ScheduledExecutorService scheduledExecutorService=null;
	private static int currentItem=0;
	private int type=0;

	public Fragment SetType(int type)
	{
		this.type=type;
		return this;
	}
	
	public MainFragment() 
	{
		AccountInfo.setOnSignInStatusChangedListener(new AccountInfo.OnSignInStatusChangedListener() 
		{
			public void onSignInStatusChanged(boolean state) 
			{
				MainActivity activity = (MainActivity) getActivity();
				if (activity == null) 
					return;
				
				if (AccountInfo.SignedIn)
				{//ÒÑµÇÂ½
					activity.setloginButton(R.drawable.frame_icon_account,new OnClickListener() 
					{
						@Override
						public void onClick(View v) 
						{
							Intent intent = new Intent(getActivity(),PersonalCenterActivity.class);
							startActivity(intent);
						}
					});
				}
				else
				{//Î´µÇÂ½
					activity.setloginButton(R.drawable.frame_icon_account_error,new OnClickListener() 
					{
						public void onClick(View v) 
						{
							Intent intent = new Intent(getActivity(),LoginActivity.class);
							startActivityForResult(intent, 1);
						}
					});
				}
			}
		});
	}

	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
		activity.setTitle(R.drawable.main_title);
		drawer = ((MainActivity) activity).getDrawer();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup viewGroup, Bundle bundle) 
	{
		View v = inflater.inflate(R.layout.fragment_main,viewGroup, false);
		viewPager = ((ViewPager) v.findViewById(R.id.banner));
		indicators[0] = ((ImageView) v.findViewById(R.id.image_1));
		indicators[1] = ((ImageView) v.findViewById(R.id.image_2));
		indicators[2] = ((ImageView) v.findViewById(R.id.image_3));
		indicators[3] = ((ImageView) v.findViewById(R.id.image_4));
		indicators[4] = ((ImageView) v.findViewById(R.id.image_5));
		indicators[previousPosition].setImageResource(R.drawable.news_image_selected);
		ImageView yiyuantese = (ImageView) v.findViewById(R.id.mainfunc_yiyuantese);
		ImageView jiuyidaohang = (ImageView) v.findViewById(R.id.mainfunc_jiuyidaohang);
		ImageView yuyueguahao = (ImageView) v.findViewById(R.id.mainfunc_yuyueguahao);
		ImageView wodefuwu = (ImageView) v.findViewById(R.id.mainfunc_wodefuwu);
		ImageView caozuozhinan = (ImageView) v.findViewById(R.id.mainfunc_caozuozhinan);
		ImageView gengduo = (ImageView) v.findViewById(R.id.mainfunc_gengduo);
		
		yiyuantese.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				drawer.gotoYIYUANTESE();
			}
		});
		jiuyidaohang.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				drawer.gotoJIUYIDAOHANG();
			}
		});
		yuyueguahao.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				drawer.gotoYUYUEGUAHAO();
			}
		});
		wodefuwu.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v)
			{
				drawer.gotoWODEFUWU();
			}
		});
		caozuozhinan.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				drawer.gotoCAOZUOZHINAN();
			}
		});
		gengduo.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				drawer.gotoGENGDUO();
			}
		});
		if (adapter == null)
			adapter = new BannerAdapter(getActivity(),this);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() 
		{
			@Override
			public void onPageScrollStateChanged(int state) 
			{
				if (state == ViewPager.SCROLL_STATE_IDLE) 
				{
					indicators[previousPosition].setImageResource(R.drawable.news_image_unselected);
					int i = viewPager.getCurrentItem();
					indicators[i].setImageResource(R.drawable.news_image_selected);
					previousPosition=i;
				}
			}

			@Override
			public void onPageScrolled(int position,float poisitionOffset, int positionOffsetPixels) 
			{
			}

			@Override
			public void onPageSelected(int position) 
			{
			}
		});

		return v;
	}

	@Override
	public void onResume() 
	{
		super.onResume();
		getActivity().setTitle(R.drawable.main_title);
		((MainActivity) getActivity()).setFromMain(false);
		if (AccountInfo.SignedIn) 
		{
			((MainActivity) getActivity()).setloginButton(R.drawable.frame_icon_account,new OnClickListener() 
			{
				public void onClick(View v) 
				{
					Intent intent = new Intent(getActivity(),PersonalCenterActivity.class);
					startActivity(intent);
				}
			});
		}
		else
		{
			((MainActivity) getActivity()).setloginButton(R.drawable.frame_icon_account_error,new OnClickListener() 
			{
				public void onClick(View v) 
				{
					Intent intent = new Intent(getActivity(), LoginActivity.class);
					startActivityForResult(intent, 1);
				}
			});
		}
	}
	
	@Override
	public void onStart()
	{
		scheduledExecutorService=Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new ViewPagerTask(), 1, 5, TimeUnit.SECONDS);
		super.onStart();
	}
	
	@Override
	public void onStop()
	{
		scheduledExecutorService.shutdown();
		super.onStop();
	}
	
	private class ViewPagerTask implements Runnable
	{
		@Override
		public void run() 
		{
			currentItem=(currentItem+1)%PAGE_COUNT;
			handler.obtainMessage().sendToTarget();
		}
		
	}
	
	private Handler handler=new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			viewPager.setCurrentItem(currentItem);
		}
	};
}