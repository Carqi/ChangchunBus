package com.carqi.ccbus.test;

import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;
import android.util.Log;

import com.carqi.ccbus.service.BusService;

public class BusServiceTest extends AndroidTestCase {
	private static final String TAG = "BusServiceTest";
	
	public void testCreateTable() throws Exception{
		BusService busService = new BusService(this.getContext());
		busService.insert_to_Stations();
	}
	public void testInsertStation() throws Exception{
		BusService busService = new BusService(this.getContext());
		busService.MyexecSQL();
	}
	
/*	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void testChinaLocale() {

		BusService busService = new BusService(this.getContext());
		List<String> list = new ArrayList<String>();
		list = busService.showAllStations_temp();
		String[] arr = new String[list.size()];
		list.toArray(arr);
		Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);
		//String[] arr = { "张三", "李四", "王五", "刘六", "1路", "64路停车场", "E景佳园" };
		Arrays.sort(arr, cmp);
		for (int i = 0; i < arr.length; i++){
			Log.i(TAG, "站点名称"+i+"："+arr[i]);
			busService.insert(arr[i]);
		}
	}*/
	public void testChinaLocale() {

		BusService busService = new BusService(this.getContext());
		PinyinTest pinyin = new PinyinTest();
		List<String> list = new ArrayList<String>();
		list = busService.showAllStations_temp();
		for(int i=0 ; i<list.size() ; i++){
			String station = list.get(i);
			String fristLetter = pinyin.converterToFirstSpell(station);
			String allLetter = pinyin.converterToSpell(station);
			busService.update(station, fristLetter, allLetter);
		}
	}
	
	public void end_pos() throws Exception {
		String line = "62路";
		String line_con = "306路/362路/62路/88路/";
		int start = line_con.indexOf("/"+line) + 1;
		int end = start + line.length();
		String result = line_con.substring(start, end);
		Log.i(TAG, "起始位置："+start);
		Log.i(TAG, "终止位置："+end);
		Log.i(TAG, "结果："+result);
	}
	
}
