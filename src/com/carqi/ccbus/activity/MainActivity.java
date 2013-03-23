package com.carqi.ccbus.activity;

import static com.carqi.ccbus.utils.Commons.*;

import android.annotation.TargetApi;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity{

	private TabHost m_tabHost;
	private LayoutInflater mLayoutInflater;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
	}
	
	@TargetApi(Build.VERSION_CODES.DONUT)
	private void init() {
		m_tabHost = getTabHost();
		mLayoutInflater = LayoutInflater.from(this);
		int count = mTabClassArray.length;
		for (int i = 0; i < count; i++) {
			TabSpec tabSpec = m_tabHost.newTabSpec(mTextviewArray[i])
					.setIndicator(getTabItemView(i))
					.setContent(getTabItemIntent(i));
			m_tabHost.addTab(tabSpec);
			m_tabHost.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.drawable.selector_tab_background);
		}
	}

	private Intent getTabItemIntent(int i) {
		Intent intent = new Intent(this, mTabClassArray[i]);
		return intent;
	}

	private View getTabItemView(int i) {
		View view = mLayoutInflater.inflate(R.layout.tab_item_view, null);

		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);

		if (imageView != null) {
			imageView.setImageResource(mImageViewArray[i]);
		}

		TextView textView = (TextView) view.findViewById(R.id.textview);

		textView.setText(mTextviewArray[i]);
		return view;
	}
}
