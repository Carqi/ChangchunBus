package com.carqi.ccbus.service;

import com.carqi.ccbus.data.Bus;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BusService {
	private DBManager dbm;
	private SQLiteDatabase db;
	private static final String TAG = "BusService";
	public BusService(Context context) {
		this.dbm = new DBManager(context);
	}
	/**
	 * 
	 * @param line 
	 * @return
	 */
	public Bus findBus(String line){
		dbm.openDatabase();
		db = dbm.getDatabase();
		Cursor cursor = db.rawQuery("select * from bus_info1 where line=?", new String[]{line});
		if(cursor.moveToFirst()){
			String line1 = cursor.getString(cursor.getColumnIndex("line"));
			String intro = cursor.getString(cursor.getColumnIndex("intro"));
			String station = cursor.getString(cursor.getColumnIndex("station"));
			Log.i(TAG, station);
			return new Bus(line1, intro, station);
		}
		cursor.close();
		dbm.closeDatabase();
		return null;
	}

}
