package com.carqi.ccbus.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class AboutOurActivity extends BaseActivity {
	private RelativeLayout shared;
	private RelativeLayout send_email;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_about);
	}
	
}
