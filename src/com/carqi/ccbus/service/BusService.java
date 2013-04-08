package com.carqi.ccbus.service;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.carqi.ccbus.data.Bus;
import com.carqi.ccbus.data.BusExchange;
import com.carqi.ccbus.data.BusStation;

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
	public List<Bus> findBus(String line){
		List<Bus> buses = new ArrayList<Bus>();
		dbm.openDatabase();
		db = dbm.getDatabase();
		String sql = "select * from bus_info1 where line like ?";
		Cursor cursor = db.rawQuery(sql, new String[]{"%"+line+"%"});
		while(cursor.moveToNext()){
			String line1 = cursor.getString(cursor.getColumnIndex("line"));
			String intro = cursor.getString(cursor.getColumnIndex("intro"));
			String station = cursor.getString(cursor.getColumnIndex("station"));
			Log.i(TAG, station);
			buses.add(new Bus(line1, intro, station));
		} 
		cursor.close();
		dbm.closeDatabase();
		db.close();
		return buses;
	}
	/**
	 * 根据站点名查询出包含该站点的线路信息
	 * @param station
	 * @return
	 */
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
		dbm.closeDatabase();
		db.close();
		return buses;
	}
	
	public List<BusStation> showStations(String station){
		List<BusStation> stalist = new ArrayList<BusStation>();
		dbm.openDatabase();
		db = dbm.getDatabase();
		String sql = "select station from all_station where station like ?";
		Cursor cursor = db.rawQuery(sql, new String[]{"%"+station+"%"});
		while(cursor.moveToNext()){
			String station1 = cursor.getString(cursor.getColumnIndex("station"));
			stalist.add(new BusStation(station1));
		}
		cursor.close();
		dbm.closeDatabase();
		db.close();
		return stalist;
	}
	/**
	 * 根据输入内容模糊查询  自动提示
	 * @param name
	 * @param from
	 * @return
	 */
	public Cursor query(String line, String from) {
		Log.i(TAG, "自动提示67："+line);
		dbm.openDatabase();
		db = dbm.getDatabase();
		String sql = null;
		if(from.equals("line")){
			sql = "select id as _id, line from bus_info1 where line like ? ";
			return db.rawQuery(sql, new String[]{"%"+line+"%"});
		}else{
			sql = "select id as _id, station from all_station where station like ? ";
			return db.rawQuery(sql, new String[]{"%"+line+"%"});
		}
	}
	
	/**
	 * 从数据库获取车站数据
	 * 
	 * @return
	 */
	public List<BusStation> getAllStations() {
		dbm.openDatabase();
		db = dbm.getDatabase();
		List<BusStation> list = new ArrayList<BusStation>();
		String sql = "SELECT * FROM all_station ORDER BY fristLetter";
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext()){
			BusStation station = new BusStation();
			station.setFristLetter(cursor.getString(cursor.getColumnIndex("fristLetter")));
			station.setStation(cursor.getString(cursor.getColumnIndex("station")));
			station.setAllLetter(cursor.getString(cursor.getColumnIndex("allLetter")));
			list.add(station);
		}
		cursor.close();
		dbm.closeDatabase();
		db.close();
		return list;
	}
	/**
	 * 直达查询
	 * @param startStation
	 * @param endStation
	 * @return
	 */
	public List<BusExchange> exchangeQuery(String startStation, String endStation){
		List<BusExchange> busExcahnge = new ArrayList<BusExchange>();
		dbm.openDatabase();
		db = dbm.getDatabase();
		String sql = "select * from (select * from bus_info1 where station like '%-"+startStation+"-%') where station like '%-"+endStation+"-%'";
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext()){
			String line = cursor.getString(cursor.getColumnIndex("line"));
			//String intro = cursor.getString(cursor.getColumnIndex("intro"));
			String station1 = cursor.getString(cursor.getColumnIndex("station"));
			busExcahnge.add(new BusExchange(station1, line));
		}
		if(busExcahnge.size() != 0){
			cursor.close();
			dbm.closeDatabase();
			db.close();
		}else{
			busExcahnge = once_exchange(startStation, endStation);
		}
		return busExcahnge;
		
	}
	/**
	 * 一次换乘查询
	 * @param startStation
	 * @param endStation
	 * @return
	 *@date 2013-4-7
	 */
	public List<BusExchange> once_exchange(String startStation, String endStation) {
		List<BusExchange> busExcahnge = new ArrayList<BusExchange>();
		//dbm.openDatabase();
		//db = dbm.getDatabase();
		createTempView(db);
		Log.i(TAG, "视图建立成功！");
		String sql = "select bv1.StartStop as StartStation, bv1.station_line as line1," +
				"bv1.EndStop as ExchangeStation,bv2.station_line as line2,bv2.EndStop as EndStation," +
				"bv1.StopCount+bv2.StopCount as Total " +
				"from bus_view bv1, bus_view bv2 " +
				"where bv1.StartStop='"+startStation+"'" +
				"and bv1.EndStop=bv2.StartStop " +
				"and bv2.EndStop='"+endStation+"' order by bv1.EndStop";

		Log.i(TAG, "168:"+sql);
		Cursor cursor = db.rawQuery(sql, null);

		Log.i(TAG, "It's here ~~~~~~~~~~~~~~~~~~171");
		while(cursor.moveToNext()){
			String startSta = cursor.getString(cursor.getColumnIndex("StartStation"));
			String line1 = cursor.getString(cursor.getColumnIndex("line1"));
			String exchange = cursor.getString(cursor.getColumnIndex("ExchangeStation"));
			String line2 = cursor.getString(cursor.getColumnIndex("line2"));
			String endSta = cursor.getString(cursor.getColumnIndex("EndStation"));
			String total = cursor.getString(cursor.getColumnIndex("Total"));
			busExcahnge.add(new BusExchange(startSta, line1, exchange, line2, endSta, total));
		}

		cursor.close();
		dbm.closeDatabase();
		db.close();
		return busExcahnge;
	}
	/**
	 * 创建数据库临时视图
	 * 
	 *@date 2013-4-7
	 */
	public void createTempView(SQLiteDatabase db){
		String db_view = "create temp view bus_view as " +
				"select st1.station_name as StartStop,st2.[station_name] as EndStop, " +
				"st1.station_line_id as station_line, " +
				"st2.station_order-st1.station_order as StopCount " +
				"from stations st1,stations st2 " +
				"where st1.station_line_id=st2.station_line_id " +
				"and st1.station_order<st2.station_order";
		db.execSQL(db_view);
	}
	
	public boolean checkStation(String station){
		dbm.openDatabase();
		db = dbm.getDatabase();
		String sql = "select * from all_station where station = '"+station+"'";
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor.moveToFirst()){
			cursor.close();
			dbm.closeDatabase();
			db.close();
			return true;
		}else{
			cursor.close();
			dbm.closeDatabase();
			db.close();
			return false;
		}
	}
	
	
	
	
	
	/****************************some method for test**************************************/
	public void insertStation(){
		dbm.openDatabase();
		db = dbm.getDatabase();
		String sql = "select * from allstation";
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext()){
			String station = cursor.getString(cursor.getColumnIndex("station"));
			if(station != null){
				db.execSQL("insert into all_station(station) values ('"+station+"')");
				
			}
			/*
			String[] temp = station.split("-");
			Log.i(TAG, "站总数" + String.valueOf(temp.length));
			for(int i=0 ; i<temp.length ; i++){
				if(temp[i] != null && !temp[i].equals("")){
					db.execSQL("insert into allstation(station) values ('"+temp[i]+"')");
				}
			}*/
		}
		cursor.close();
		dbm.closeDatabase();
		db.close();
		
	}
	/**
	 * 将所有的站点录入到站点表中
	 * 
	 *@date 2013-4-5
	 */
	public void insert_to_Stations(){
		dbm.openDatabase();
		db = dbm.getDatabase();
		String sql = "select * from bus_info1";
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext()){
			String line_no = cursor.getString(cursor.getColumnIndex("line"));
			String station = cursor.getString(cursor.getColumnIndex("station"));
			/*if(station != null){
				db.execSQL("insert into all_station(station) values ('"+station+"')");
				
			}*/
			
			String[] temp = station.split("-");
			Log.i(TAG, "站总数" + String.valueOf(temp.length));
			for(int i=0 ; i<temp.length ; i++){
				if(temp[i] != null && !temp[i].equals("")){
					db.execSQL("insert into stations(station_line_id, station_order, station_name) values ('"+line_no+"','"+i+"','"+temp[i]+"')");
				}
			}
		}
		cursor.close();
		dbm.closeDatabase();
		db.close();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public void createTable(){
		dbm.openDatabase();
		db = dbm.getDatabase();
		String sql = "CREATE TABLE all_station (id integer primary key autoincrement, station VARCHAR(20))";
		db.execSQL(sql);
		dbm.closeDatabase();
		db.close();
	}
	public List<String> showAllStations_temp(){
		List<String> list = new ArrayList<String>();
		dbm.openDatabase();
		db = dbm.getDatabase();
		String sql = "select station from all_station";
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext()){
			String station = cursor.getString(cursor.getColumnIndex("station"));
			list.add(station);
		}
		cursor.close();
		dbm.closeDatabase();
		db.close();
		return list;
	}
	public void MyexecSQL(){
		dbm.openDatabase();
		db = dbm.getDatabase();
		//String sql = "insert into all_station(station) values ('"+station+"')";
		//String sql = "ALTER TABLE all_station ADD allLetter varchar(20)";
		String sql = "CREATE TABLE stations (station_id integer primary key autoincrement, station_line_id VARCHAR(20), " +
					 "station_order integer, station_name VARCHAR(20))";
		db.execSQL(sql);
		dbm.closeDatabase();
		db.close();
	}
	
	public void update(String station, String fristLetter, String allLetter){
		dbm.openDatabase();
		db = dbm.getDatabase();
		String sql = "update all_station set fristLetter = '"+fristLetter
						+"', allLetter = '"+allLetter+"' where station = '"+station+"'";
		db.execSQL(sql);
		dbm.closeDatabase();
		db.close();
	}
}
