package cn.edu.nju.zsyy.safety;

import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.bean.Constants;
import cn.edu.nju.zsyy.utils.UIHelper;
import cn.edu.nju.zsyy.utils.ValidUtil;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ViewSwitcher;

public class LoginActivity extends Activity
{
	private InputMethodManager imm=null;
	private ViewSwitcher mViewSwitcher=null;
	private View loginLoading=null;
	private EditText mAccount=null;
	private EditText mPwd=null;
	private CheckBox chb_rememberMe=null;
	private CheckBox chb_autologin=null;
	private ImageButton btn_close=null;
	private Button btn_login=null;
	private AnimationDrawable loadingAnimation=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		imm=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
		mViewSwitcher=(ViewSwitcher)findViewById(R.id.logindialog_view_switcher);
		loginLoading=(View)findViewById(R.id.login_loading);
		mAccount=(EditText)findViewById(R.id.login_idnumber);
		mPwd=(EditText)findViewById(R.id.login_password);
		chb_rememberMe=(CheckBox)findViewById(R.id.login_checkbox_rememberMe);
		chb_autologin=(CheckBox)findViewById(R.id.login_checkbox_autologin);
		btn_close=(ImageButton)findViewById(R.id.login_close_button);
		btn_close.setOnClickListener(UIHelper.finish(this));
		
		btn_login=(Button)findViewById(R.id.login_btn_login);
		btn_login.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				String account=mAccount.getText().toString();
				String pwd=mPwd.getText().toString();
				boolean isRememberMe=chb_rememberMe.isChecked();
				boolean isAutoLogin=chb_autologin.isChecked();
				//判断输入
				if(!ValidUtil.isValidIDCard(account))
				{
					UIHelper.ToastMessage(v.getContext(),"证件号不合法");
				}
				if(!ValidUtil.isValidPsw(pwd))
				{
					UIHelper.ToastMessage(v.getContext(),"密码有误");
				}
				
				btn_close.setVisibility(View.GONE);
				loadingAnimation=(AnimationDrawable)loginLoading.getBackground();
				loadingAnimation.start();
				mViewSwitcher.showNext();
				
				if(!login(account,pwd))
				{
					mViewSwitcher.showPrevious();
					loadingAnimation.stop();
					btn_close.setVisibility(View.VISIBLE);
					AccountInfo.RealName=account;
					AccountInfo.Password=pwd;
					AccountInfo.Gender=0;
					AccountInfo.IDNumber="0000";
					AccountInfo.PhoneNumber="13770640982";
					AccountInfo.Email="lichao.890427@163.com";
					if(!AccountInfo.signIn(LoginActivity.this))
					{
						btn_close.setVisibility(View.VISIBLE);
						loadingAnimation.stop();
						mViewSwitcher.showPrevious();
						UIHelper.ToastMessage(v.getContext(),"登陆失败");
					}
					else
					{
						UIHelper.ToastMessage(v.getContext(),"登陆成功");
						Constants.fstack.clear();
						finish();
					}
				}
			}
		});
	}
	
	private boolean login(String account,String pwd)
	{
		//设置AccountInfo，从服务器获取信息
		
		return true;
	}
}