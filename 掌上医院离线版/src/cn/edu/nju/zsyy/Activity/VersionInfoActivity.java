package cn.edu.nju.zsyy.Activity;

import cn.edu.nju.zsyy.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class VersionInfoActivity extends Activity
{
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_versioninfo);
		Button bt=(Button)findViewById(R.id.detectnewversion);
		bt.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				//联网检测
				if(Math.random() > 0.5)
				{
					AlertDialog.Builder builder=new AlertDialog.Builder(VersionInfoActivity.this);
					builder.setTitle("新版本说明");
					builder.setMessage("[新增]新增全局搜索功能\n[优化]优化部分界面\n[修复]修复了部分手机无法分享的问题");
					builder.setPositiveButton("立即更新",new AlertDialog.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which) 
						{
							//升级结束退出
							Toast.makeText(VersionInfoActivity.this, "升级成功",Toast.LENGTH_LONG).show();
							finish();
						}
						
					});
					builder.setNegativeButton("取消",new AlertDialog.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which) 
						{
						}
					});
					
					builder.show();
				}
				else
				{
					Toast.makeText(VersionInfoActivity.this, "已经是最新版本",Toast.LENGTH_LONG).show();
				}
			}
		});
		
		ImageView back=(ImageView)findViewById(R.id.main_head_logo);
		back.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				finish();
			}
		});
	}
}
