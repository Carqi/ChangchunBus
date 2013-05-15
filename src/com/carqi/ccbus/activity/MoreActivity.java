package com.carqi.ccbus.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MoreActivity extends BaseActivity {
	private RelativeLayout shared;
	private RelativeLayout send_email;
	private RelativeLayout about_our;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_settings);
		init();
		shared.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setAction(Intent.ACTION_SEND);
				i.putExtra(Intent.EXTRA_TEXT, "大家好，我正在手机使用一款很好用的长春公交查询软件，"
						+ "可离线查询公交信息。免费的哦，你也试试吧~~");
				i.setType("text/plain");
				startActivity(i);
			}
		});
		
		send_email.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("mailto:Carqi@126.com"); 
				Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
				intent.putExtra(Intent.EXTRA_SUBJECT, "长春公交意见反馈"); // 主题
				intent.putExtra(Intent.EXTRA_TEXT, "您遇到的问题或改进建议："); // 正文
				startActivity(Intent.createChooser(intent, "请选择邮件类应用"));
			}
		});
		about_our.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), AboutOurActivity.class);
				startActivity(intent);
			}
		});
	}

	private void init() {
		shared = (RelativeLayout) this.findViewById(R.id.shared);
		send_email = (RelativeLayout) this.findViewById(R.id.send_email);
		about_our = (RelativeLayout) this.findViewById(R.id.about_our);
	}
	
}
