package com.carqi.ccbus.activity;

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

import com.carqi.ccbus.adapter.BusAdapter;
import com.carqi.ccbus.data.Bus;
import com.carqi.ccbus.service.BusService;

public class BusListActivity extends BaseActivity {
	private static final String TAG = "BusListActivity";
	public static int RESULT = 1;
	public static int REQUEST = 0;
	private ListView listView;
	private List<Bus> list;
	private TextView stationText;
	private Button back_btn;
	private Button home_btn;
	private TextView totalText;
	/*根据站点查询传递过来的站点名称*/
	private String station;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buslist);
		init();
		
		station = getIntent().getExtras().getString("station");

		Log.i(TAG, "station is :" + station);
		BusService busService = new BusService(getApplicationContext());
		list = busService.findStation(station);
		
		stationText.setText(station);
		totalText.setText(String.valueOf(list.size()));
		
		BusAdapter busAdapter = new BusAdapter(this, list);
		listView.setAdapter(busAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent nextIntent = new Intent(getApplicationContext(), TabStationActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("bus", list.get(position));
				nextIntent.putExtras(bundle);
				startActivity(nextIntent);
				
				
			}
		});
		
		
	}
	private void init() {
		listView = (ListView) this.findViewById(R.id.buslist);
		stationText = (TextView) this.findViewById(R.id.station);
		totalText = (TextView) this.findViewById(R.id.total_result);
		
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
