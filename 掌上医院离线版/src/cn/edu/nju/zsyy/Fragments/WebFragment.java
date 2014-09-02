package cn.edu.nju.zsyy.Fragments;

import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.bean.Constants;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

public class WebFragment extends Fragment
{
	private String murl=null;
	private String mtitle=null;
	private FragmentManager manager=null;
	private WebView mwebview=null;
	
	public Fragment SetParam(String murl,String mtitle)
	{
		this.murl="file://"+murl;
		this.mtitle=mtitle;
		return this;
	}

	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		mwebview=new WebView(getActivity());
		ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
		WebSettings webSettings = mwebview.getSettings();
		webSettings.setLoadWithOverviewMode(true);
		mwebview.setBackgroundColor(Color.TRANSPARENT);
		mwebview.setVerticalScrollBarEnabled(false);
		mwebview.setHorizontalScrollBarEnabled(false);
		mwebview.setWebChromeClient(new WebChromeClient());
		mwebview.setWebViewClient(new WebViewClient());
		mwebview.loadUrl(murl);
		mwebview.setLayoutParams(lp);
		mwebview.setVisibility(View.VISIBLE);
		mwebview.setBackgroundColor(Color.WHITE);
		return mwebview;
	}
	
}
