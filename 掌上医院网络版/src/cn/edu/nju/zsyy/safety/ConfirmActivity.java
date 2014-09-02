package cn.edu.nju.zsyy.safety;

import cn.edu.nju.zsyy.R;
import cn.edu.nju.zsyy.utils.UIHelper;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class ConfirmActivity extends Activity
{
	private String docname=null;
	private String keshi=null;
	private String datetime=null;
	private String wubie=null;
	private String segment=null;
	private String type=null;
	private InputMethodManager imm=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirmappointment);
		imm=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
		ImageButton btn_close=(ImageButton)findViewById(R.id.login_close_button);
		btn_close.setOnClickListener(UIHelper.finish(this));
		
		Button appointment=(Button) findViewById(R.id.btn_appointment);
		Button cancel=(Button) findViewById(R.id.btn_cancel);
		cancel.setOnClickListener(UIHelper.finish(this));
		appointment.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Toast.makeText(ConfirmActivity.this, "Ô¤Ô¼³É¹¦",1000).show();	
				finish();
			}
		});
	}
}
