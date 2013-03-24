package com.carqi.ccbus.activity;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.carqi.ccbus.adapter.StationAdapter;
import com.carqi.ccbus.data.Bus;
import com.carqi.ccbus.data.BusStation;
/**
 * 获得反向的车站列表
 * @author gaoxy
 *
 */
public class StationRevertActivity extends Activity {
	private static final String TAG = "StationActivity";
	public static int RESULT = 1;
	public static int REQUEST = 0;
	private ListView listView;
	private List<BusStation> stalist;
	private Bus bus = null;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stationlist);
		listView = (ListView) this.findViewById(R.id.busstationlist);
		
		bus = (Bus) getIntent().getExtras().getSerializable("bus");
		String station = bus.getStation();
		String[] temp = station.split("-");
		stalist = new ArrayList<BusStation>();
		for(int i=temp.length - 1 ; i>= 0 ; i--){
			if(temp[i] != null && !temp[i].equals("")){
				BusStation busSta = new BusStation();
				Log.i(TAG, "站名revert"+String.valueOf(i)+"：" + temp[i]);
				busSta.setStation(temp[i]);
				stalist.add(busSta);
			}
		}
		StationAdapter stationAdapter = new StationAdapter(this, stalist);
		listView.setAdapter(stationAdapter);
	}
}
