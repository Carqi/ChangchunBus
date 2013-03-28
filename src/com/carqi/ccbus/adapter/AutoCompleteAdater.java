package com.carqi.ccbus.adapter;

import com.carqi.ccbus.service.BusService;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;


public class AutoCompleteAdater extends SimpleCursorAdapter {

	private BusService busService = null;
	private Context context;
	// 查询字段
	private String queryField;

	public AutoCompleteAdater(Context context, int layout, Cursor c, String from, int to) {
		super(context, layout, c, new String[] { from }, new int[] { to });
		this.context = context;
		this.queryField = from;
	}

	/**
	 * 动态查询数据库
	 */
	@Override
	public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
		if (constraint != null) {
			return getBusService().query((String) constraint, queryField);
		} else {
			return null;
		}
	}

	/**
	 * 这里设置在弹出的提示列表中点击某一项后的返回值,返回值将被显示在文本框中
	 */
	@Override
	public CharSequence convertToString(Cursor cursor) {
		return cursor.getString(cursor.getColumnIndex(queryField));
	}

	public BusService getBusService() {
		if (busService == null) {
			busService = new BusService(this.context);
		}
		return busService;
	}

}
