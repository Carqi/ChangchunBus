package com.carqi.ccbus.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

import com.carqi.ccbus.data.Bus;
import com.carqi.ccbus.data.BusStation;

public class TabStationActivity extends TabActivity {
	public static int RESULT = 1;
	public static int REQUEST = 0;
	/**
	 * TabHost�ؼ�
	 */
	private TabHost mTabHost;
	/**
	 * TabWidget�ؼ�
	 */
	private TabWidget mTabWidget;
	private Bus bus = null;
	private Button back;
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
		/* ȥ����ǩ�·��İ��� */
		mTabHost.setPadding(mTabHost.getPaddingLeft(),
				mTabHost.getPaddingTop(), mTabHost.getPaddingRight(),
				mTabHost.getPaddingBottom() - 5);
		Resources rs = getResources();

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
		layout1spec.setIndicator("ȥ��"+revert+"����");
		layout1spec.setContent(layout1intent);
		mTabHost.addTab(layout1spec);

		Intent layout2intent = new Intent();
		layout2intent.putExtras(bundle);

		layout2intent.setClass(this, StationRevertActivity.class);
		TabHost.TabSpec layout2spec = mTabHost.newTabSpec("layout2");
		layout2spec.setIndicator("ȥ��"+forward+"����");
		layout2spec.setContent(layout2intent);
		mTabHost.addTab(layout2spec);

		/* ��Tab��ǩ�Ķ��� */
		mTabWidget = mTabHost.getTabWidget();
		for (int i = 0; i < mTabWidget.getChildCount(); i++) {
			/* �õ�ÿ����ǩ����ͼ */
			View view = mTabWidget.getChildAt(i);
			/* ����ÿ����ǩ�ı��� */
			if (mTabHost.getCurrentTab() == i) {
				view.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.number_bg_pressed));
			} else {
				view.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.number_bg));
			}
			/* ����Tab��ָ����ߵ���ɫ */
			// tabWidget.setBackgroundColor(Color.WHITE);
			/* ����Tab��ָ����ߵı���ͼƬ */
			// tabWidget.setBackgroundResource(R.drawable.icon);
			/* ����tab�ĸ߶� */
			mTabWidget.getChildAt(i).getLayoutParams().height = 55;
			TextView tv = (TextView) mTabWidget.getChildAt(i).findViewById(
					android.R.id.title);
			/* ����tab���������ɫ */
			tv.setTextColor(Color.rgb(255, 255, 255));
			tv.setGravity(Gravity.CENTER_VERTICAL);
		}

		/* �����Tabѡ���ʱ�򣬸��ĵ�ǰTab��ǩ�ı��� */
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
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(RESULT);
				finish();
			}
		});
		
	}
	
	
	
}
