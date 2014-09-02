package cn.edu.nju.zsyy.safety;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ViewSwitcher;
import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.utils.UIHelper;
import cn.edu.nju.zsyy.utils.ValidUtil;

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
				
				if(AccountInfo.signIn(LoginActivity.this))
				{
					UIHelper.ToastMessage(v.getContext(),"登陆成功");
					final Handler handler=new Handler()
					{
						@Override
						public void handleMessage(Message msg)
						{
							loadingAnimation.stop();
							finish();	
						}
					};
					new Thread(new Runnable()
					{   
						@Override
					    public void run()
						{   
					        try 
					        {
								Thread.sleep(2000);
							} 
					        catch (InterruptedException e) 
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}   
					        handler.sendEmptyMessage(0); //告诉主线程执行任务   
					    }   
					}).start();		
				}
				else
				{
					UIHelper.ToastMessage(v.getContext(), "登录失败");
					mViewSwitcher.showPrevious();
					loadingAnimation.stop();
					btn_close.setVisibility(View.VISIBLE);
				}
			}
		});
	}
}