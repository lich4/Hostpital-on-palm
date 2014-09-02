package cn.edu.nju.zsyy.Activity;

import cn.edu.nju.zsyy.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ShareActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
		Button bt=(Button) findViewById(R.id.btsendshare);
		final EditText et=(EditText) findViewById(R.id.share_text);
		bt.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Intent intent=new Intent(Intent.ACTION_SEND);  
				intent.setType("text/plain");  
				intent.putExtra(Intent.EXTRA_SUBJECT, "∑÷œÌ’∆…œπ“∫≈»Ìº˛");  
				intent.putExtra(Intent.EXTRA_TEXT, et.getText().toString());  
				startActivity(Intent.createChooser(intent, getTitle()));  	
			}
		});
	}
}
