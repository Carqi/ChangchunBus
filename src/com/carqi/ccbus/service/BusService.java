package com.carqi.ccbus.service;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.carqi.ccbus.data.Bus;

public class BusService {
	private DBManager dbm;
	private SQLiteDatabase db;
	private static final String TAG = "BusService";
	public BusService(Context context) {
		this.dbm = new DBManager(context);
	}
	/**
	 * 根据线路名称查询公交线路
	 * @param line 线路名称
	 * @return
	 */
	public Bus findBus(String line){
		dbm.openDatabase();
		db = dbm.getDatabase();
		String sql = "select * from bus_info1 where line like ?";
		Cursor cursor = db.rawQuery(sql, new String[]{line+"%"});
		if(cursor.moveToFirst()){
			String line1 = cursor.getString(cursor.getColumnIndex("line"));
			String intro = cursor.getString(cursor.getColumnIndex("intro"));
			String station = cursor.getString(cursor.getColumnIndex("station"));
			Log.i(TAG, station);
			return new Bus(line1, intro, station);
		}
		cursor.close();
		return null;
	}
	
	public List<Bus> findStation(String station){
		List<Bus> buses = new ArrayList<Bus>();
		dbm.openDatabase();
		db = dbm.getDatabase();
		String sql = "select id,line,intro,station from bus_info1 where station like ?";
		Cursor cursor = db.rawQuery(sql, new String[]{"%"+station+"%"});
		while(cursor.moveToNext()){
			String line = cursor.getString(cursor.getColumnIndex("line"));
			String intro = cursor.getString(cursor.getColumnIndex("intro"));
			String station1 = cursor.getString(cursor.getColumnIndex("station"));
			buses.add(new Bus(line, intro, station1));
		}
		cursor.close();
		return buses;
	}
	/**
	 * 根据输入内容模糊查询
	 * @param name
	 * @return
	 */
	public Cursor query(String line) {
		dbm.openDatabase();
		db = dbm.getDatabase();
		String sql = "select id as _id, line from bus_info1 where line like ? limit 10";
		return db.rawQuery(sql, new String[]{line+"%"});
	}

}
