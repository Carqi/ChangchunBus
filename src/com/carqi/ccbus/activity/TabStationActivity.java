package com.carqi.ccbus.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

import com.carqi.ccbus.data.Bus;

public class TabStationActivity extends TabActivity {
	public static int RESULT = 1;
	public static int REQUEST = 0;
    /**
     * TabHost控件
     */
	private TabHost mTabHost;
    /**
     * TabWidget控件
     */
	private TabWidget mTabWidget;
	private Bus bus = null;
	private Button back;
	private Button home_btn;
	private TextView line_noText;
	private TextView line_introText;
	private List<String> list = new ArrayList<String>();
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_station);
		
		init();
		
		mTabHost = this.getTabHost();
		/* 去除标签下方的白线 */
		mTabHost.setPadding(mTabHost.getPaddingLeft(),
				mTabHost.getPaddingTop(), mTabHost.getPaddingRight(),
				mTabHost.getPaddingBottom() - 5);
				//Resources rs = getResources();
		
		
		bus = (Bus) getIntent().getExtras().getSerializable("bus");
		
		
		
		String line_no = bus.getLine();
		String line_intro = bus.getIntro();
		line_noText.setText(line_no);
		line_introText.setText(line_intro);
		String station = bus.getStation();
		String[] temp = station.split("-");
		for(int i=0 ; i<temp.length ; i++){
			if(temp[i] != null && !temp[i].equals("")){
				list.add(temp[i]);
			}
		}
		String forward = list.get(0);
		String revert = list.get(list.size()-1);
		
		
		

		Intent layout1intent = new Intent();

		Bundle bundle = new Bundle();
		bundle.putSerializable("bus", bus);
		layout1intent.putExtras(bundle);

		layout1intent.setClass(this, StationActivity.class);
		TabHost.TabSpec layout1spec = mTabHost.newTabSpec("layout1");
		layout1spec.setIndicator("去往"+revert+"方向");
		layout1spec.setContent(layout1intent);
		mTabHost.addTab(layout1spec);

		Intent layout2intent = new Intent();
		layout2intent.putExtras(bundle);

		layout2intent.setClass(this, StationRevertActivity.class);
		TabHost.TabSpec layout2spec = mTabHost.newTabSpec("layout2");
		layout2spec.setIndicator("去往"+forward+"方向");
		layout2spec.setContent(layout2intent);
		mTabHost.addTab(layout2spec);

		/* 对Tab标签的定制 */
		mTabWidget = mTabHost.getTabWidget();
		for (int i = 0; i < mTabWidget.getChildCount(); i++) {
			/* 得到每个标签的视图 */
			View view = mTabWidget.getChildAt(i);
			/* 设置每个标签的背景 */
			if (mTabHost.getCurrentTab() == i) {
				view.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.number_bg_pressed));
			} else {
				view.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.number_bg));
			}
            /* 设置Tab间分割竖线的颜色 */
            // tabWidget.setBackgroundColor(Color.WHITE);
            /* 设置Tab间分割竖线的背景图片 */
            // tabWidget.setBackgroundResource(R.drawable.icon);
            /* 设置tab的高度 */
			mTabWidget.getChildAt(i).getLayoutParams().height = 55;
			TextView tv = (TextView) mTabWidget.getChildAt(i).findViewById(
					android.R.id.title);
			/* 设置tab内字体的颜色 */
			tv.setTextColor(Color.rgb(255, 255, 255));
			tv.setGravity(Gravity.CENTER_VERTICAL);
		}

		/* 当点击Tab选项卡的时候，更改当前Tab标签的背景 */
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				for (int i = 0; i < mTabWidget.getChildCount(); i++) {
					View view = mTabWidget.getChildAt(i);
					if (mTabHost.getCurrentTab() == i) {
						view.setBackgroundDrawable(getResources().getDrawable(
								R.drawable.number_bg_pressed));
					} else {
						view.setBackgroundDrawable(getResources().getDrawable(
								R.drawable.number_bg));
					}
				}
			}
		});
	}

	private void init() {
		line_noText = (TextView) this.findViewById(R.id.line_no);
		line_introText = (TextView) this.findViewById(R.id.line_intro);
		back = (Button) this.findViewById(R.id.title_back);
		home_btn = (Button) this.findViewById(R.id.title_home);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(RESULT);
				finish();
			}
		});
		home_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			}
		});
	}
	
	
	
}
