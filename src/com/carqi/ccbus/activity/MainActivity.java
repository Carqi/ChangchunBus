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
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

	private TabHost m_tabHost;
    /**
     * TabWidget控件
     */
    private TabWidget mTabWidget;
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
		mTabWidget = m_tabHost.getTabWidget();
		
		/* 当点击Tab选项卡的时候，更改当前Tab标签的背景 */
        m_tabHost.setOnTabChangedListener(new OnTabChangeListener()
        {
            @Override
            public void onTabChanged(String tabId)
            {
                for (int i = 0; i < mTabWidget.getChildCount(); i++)
                {
                    View view = mTabWidget.getChildAt(i);
                    if (m_tabHost.getCurrentTab() == i)
                    {

                        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
                    	imageView.setImageResource(mImageViewArrayPressed[i]);
                    }
                    else
                    {
                        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
                    	imageView.setImageResource(mImageViewArray[i]);
                    }
                }
            }
        });
	}
	
	private Intent getTabItemIntent(int i) {
		Intent intent = new Intent(this, mTabClassArray[i]);
		return intent;
	}

	private View getTabItemView(int i) {
		View view = mLayoutInflater.inflate(R.layout.tab_item_view, null);

		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);

		if (imageView != null && i != 0) {
			imageView.setImageResource(mImageViewArray[i]);
		}else{
			imageView.setImageResource(mImageViewArrayPressed[i]);
		}

		TextView textView = (TextView) view.findViewById(R.id.textview);

		textView.setText(mTextviewArray[i]);
		return view;
	}
}
