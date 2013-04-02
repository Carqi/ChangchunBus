package com.carqi.ccbus.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.carqi.ccbus.adapter.ExchangeBusAdapter;
import com.carqi.ccbus.data.Bus;
import com.carqi.ccbus.data.BusStation;
import com.carqi.ccbus.service.BusService;

public class ExchangeBusListActivity extends BaseActivity {
	private static final String TAG = "ExchangeBusListActivity";
	public static int RESULT = 1;
	public static int REQUEST = 0;
	private ListView listView;
	private List<Bus> list;
	private TextView startStationText;
	private TextView endStationText;
	private Button back_btn;
	private Button home_btn;
	private TextView titleText;
	private String startStation;	//换乘查询起点
	private String endStation;	//换乘查询终点
	private List<BusStation> detailListStations; //显示换乘路线所经过的所有站点
	private int[] stanum;	//途径站总数
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exchange_list);
		init();
		
		startStation = getIntent().getExtras().getString("startStation");
		endStation = getIntent().getExtras().getString("endStation");


		Log.i(TAG, "起始站："+startStation);
		startStationText.setText(startStation);
		endStationText.setText(endStation);
		titleText.setText("方案列表");
		
		
		BusService busService = new BusService(getApplicationContext());
		list = busService.throughBus(startStation, endStation);
		int startNum = 0;
		int endNum = 0;
		stanum = new int[list.size()];
		detailListStations = new ArrayList<BusStation>();
		for(int i=0 ; i<list.size() ; i++){
			String station = list.get(i).getStation();
			//Log.i(TAG, "station = " +station);
			String[] stations = station.split("-");
			for(int j=0 ; j< stations.length ; j++){
				if(stations[j] != null && !stations[j].equals("")){
					//Log.i(TAG, "stations = " +stations[i]);
					if(startStation.equals(stations[j])){
						//Log.i(TAG, "startNum = " +i);
						startNum = j;
					}else if(endStation.equals(stations[j])){
						//Log.i(TAG, "endNum = " +i);
						endNum = j;
					}
				}
			}
			stanum[i] = endNum - startNum;
			//如果stanum[i]等于负数说明终点站存储在数据库的前面、
			if(stanum[i]<0){
				int a = station.indexOf(stations[endNum+1]);
				int b = station.indexOf(stations[startNum]);
				String tempStr = station.substring(a, b);
				detailListStations.add(new BusStation(tempStr));
				Log.i(TAG, "tempStr1:"+tempStr);
			}else{
				int a = station.indexOf(stations[startNum+1]);
				int b = station.indexOf(stations[endNum]);
				String tempStr = station.substring(a, b);
				detailListStations.add(new BusStation(tempStr));
				Log.i(TAG, "tempStr2:"+tempStr);
			}
		}
		
		for(int i=0; i<stanum.length ; i++){
			Log.i(TAG, stanum[i]+"");
		}
		
		
		/*for(int i=0 ; i<detailListStations.size() ; i++){
			Log.i(TAG, "测试："+detailListStations.get(i).getStation()+"");
			
		}*/
		
		
		
		//stationText.setText(station);
		//totalText.setText(String.valueOf(list.size()));
		
		ExchangeBusAdapter busAdapter = new ExchangeBusAdapter(this, list, stanum);
		listView.setAdapter(busAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent nextIntent = new Intent(getApplicationContext(), ExchangeBusDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("startStation", startStation);
				bundle.putString("endStation", endStation);
				bundle.putString("plan_no", "方案"+(position+1));
				bundle.putString("detailStations", detailListStations.get(position).getStation());
				bundle.putInt("stanum", stanum[position]);
				nextIntent.putExtras(bundle);
				startActivity(nextIntent);
				
				
			}
		});
		
		
	}
	private void init() {
		listView = (ListView) this.findViewById(R.id.buslist);
		startStationText = (TextView) this.findViewById(R.id.startStation);
		endStationText = (TextView) this.findViewById(R.id.endStation);
		titleText = (TextView) this.findViewById(R.id.title_text);
		
		back_btn = (Button) this.findViewById(R.id.title_back);
		home_btn = (Button) this.findViewById(R.id.title_home);
		back_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(RESULT);
				finish();
			}
		});
		home_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			}
		});
	}
	
	
}
