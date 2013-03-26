package com.carqi.ccbus.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.carqi.ccbus.data.Bus;
import com.carqi.ccbus.service.BusService;

public class StationQueryActivity extends Activity {
	private static final String TAG = "StationQueryActivity";
	private Button queryButton;
	private TextView stationText;
	private Bus bus = new Bus();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stationquery);
		init();
	}
	
	
	
	
	private void init() {
		queryButton = (Button) this.findViewById(R.id.querystation_button);
		stationText = (EditText) this.findViewById(R.id.stationText);
		queryButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				List<Bus> buses = new ArrayList<Bus>();
				String station = stationText.getText().toString();
				if(station != null && !station.equals("")){
					Log.i(TAG, "站点名称：" + station);
					BusService busService = new BusService(getApplicationContext());
					buses = busService.findStation(station);
					if(buses != null && buses.size()>0){
						Intent nextIntent = new Intent(getApplicationContext(), BusListActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("station", station);
						//bundle.putSerializable("buses", (Serializable) buses);
						nextIntent.putExtras(bundle);
						startActivity(nextIntent);
						
						
					}else{
						Toast.makeText(getApplicationContext(), R.string.notargetstation, Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(getApplicationContext(), R.string.nocontent, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	



	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(StationQueryActivity.this)
					.setIcon(R.drawable.ic_launcher)
					.setTitle("长春离线公交")
					.setMessage("你确定退出了哦?")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface arg0,
										int arg1) {
									android.os.Process.killProcess(android.os.Process.myPid());
									StationQueryActivity.this.finish();
								}

							}).setNegativeButton("取消", null).show();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
	
}
