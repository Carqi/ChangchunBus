package com.carqi.ccbus.activity;


import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.carqi.ccbus.adapter.StationAdapter;
import com.carqi.ccbus.data.Bus;
import com.carqi.ccbus.data.BusStation;

public class StationActivity extends BaseActivity {
	private static final String TAG = "StationActivity";
	public static int RESULT = 1;
	public static int REQUEST = 0;
	private ListView listView;
	private List<BusStation> stalist;
	private Bus bus = null;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stationlist);
		init();
		bus = (Bus) getIntent().getExtras().getSerializable("bus");
		String station = bus.getStation();
		String[] temp = station.split("-");
		stalist = new ArrayList<BusStation>();
		for(int i=0 ; i<temp.length ; i++){
			if(temp[i] != null && !temp[i].equals("")){
				BusStation busSta = new BusStation();
				busSta.setStation(temp[i]);
				stalist.add(busSta);
			}
		}
		StationAdapter stationAdapter = new StationAdapter(this, stalist);
		listView.setAdapter(stationAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				Intent nextIntent = new Intent(getApplicationContext(), BusListActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("station", stalist.get(position).getStation());
				Log.i(TAG, "station is :" + stalist.get(position).getStation());
				nextIntent.putExtras(bundle);
				startActivity(nextIntent);
				
			}
		});
	}
	private void init() {
		listView = (ListView) this.findViewById(R.id.busstationlist);
		/*line_noText = (TextView) this.findViewById(R.id.line_no);
		line_introText = (TextView) this.findViewById(R.id.line_intro);
		back_btn = (Button) this.findViewById(R.id.title_back);
		back_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(RESULT);
				finish();
			}
		});*/
	}
	
	
}
