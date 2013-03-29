package com.carqi.ccbus.utils;

import com.carqi.ccbus.activity.ExchangeQueryActivity;
import com.carqi.ccbus.activity.LineActivity;
import com.carqi.ccbus.activity.R;
import com.carqi.ccbus.activity.StationQueryActivity;


public class Commons {
	public static String mTextviewArray[] = {"线路查询", "站点查询", "换乘查询", "网络查询", "更多"};
	@SuppressWarnings("rawtypes")
	public static Class mTabClassArray[] = {LineActivity.class,StationQueryActivity.class,ExchangeQueryActivity.class,LineActivity.class,LineActivity.class};
	public static int mImageViewArray[] = { R.drawable.bus_item_1,
			R.drawable.bus_item_2, R.drawable.bus_item_3,
		R.drawable.bus_item_4, R.drawable.bus_item_5 };
	public static int mImageViewArrayPressed[] = { R.drawable.bus_item_pressed1,
		R.drawable.bus_item_pressed2, R.drawable.bus_item_pressed3,
	R.drawable.bus_item_pressed4, R.drawable.bus_item_pressed5 };
}
