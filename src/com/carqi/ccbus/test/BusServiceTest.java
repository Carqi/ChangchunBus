package com.carqi.ccbus.test;

import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;

import com.carqi.ccbus.service.BusService;

public class BusServiceTest extends AndroidTestCase {
	//private static final String TAG = "BusServiceTest";
	
	public void testCreateTable() throws Exception{
		BusService busService = new BusService(this.getContext());
		busService.createTable();
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
	
}
