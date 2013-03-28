package com.carqi.ccbus.test;

import android.test.AndroidTestCase;

import com.carqi.ccbus.service.BusService;

public class BusServiceTest extends AndroidTestCase {
	
	public void testCreateTable() throws Exception{
		BusService busService = new BusService(this.getContext());
		busService.createTable();
	}
	public void testInsertStation() throws Exception{
		BusService busService = new BusService(this.getContext());
		busService.insertStation();
	}
}
