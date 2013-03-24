package com.carqi.ccbus.utils;

import com.carqi.ccbus.activity.LineActivity;
import com.carqi.ccbus.activity.R;
import com.carqi.ccbus.activity.StationQueryActivity;


public class Commons {
	public static String mTextviewArray[] = {"线路查询", "站点查询", "换乘查询", "网络查询", "更多"};
	public static Class mTabClassArray[] = {LineActivity.class,StationQueryActivity.class,LineActivity.class,LineActivity.class,LineActivity.class};
	public static int mImageViewArray[] = { R.drawable.bus_item_1,
			R.drawable.bus_item_2, R.drawable.bus_item_3,
		R.drawable.bus_item_4, R.drawable.bus_item_5 };
}
