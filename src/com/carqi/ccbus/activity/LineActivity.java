package com.carqi.ccbus.activity;


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
import com.carqi.ccbus.data.BusStation;
import com.carqi.ccbus.service.BusService;

public class LineActivity extends Activity {
	private static final String TAG = "LineActivity";
	private Button queryButton;
	private TextView lineText;
	private Bus bus = new Bus();
	private List<BusStation> stalist = new ArrayList<BusStation>();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.line);
		init();
	}
	

	private void init() {
		queryButton = (Button) this.findViewById(R.id.query_button);
		lineText = (EditText) this.findViewById(R.id.lineText);
		queryButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String line = lineText.getText().toString();
				if(line != null && !line.equals("")){
					Log.i(TAG, "站点：" + line);
					BusService busService = new BusService(getApplicationContext());
					bus = busService.findBus(line);
					if(bus != null){
						BusStation busSta = new BusStation();
						String station = bus.getStation();
						String[] temp = station.split("-");
						Log.i(TAG, "站总数" + String.valueOf(temp.length));
						for(int i=0 ; i<temp.length ; i++){
							if(temp[i] != null && !temp[i].equals("")){
								busSta.setStation(temp[i]);
								stalist.add(busSta);
							}
						}
						Intent nextIntent = new Intent(getApplicationContext(), TabStationActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("bus", bus);
						nextIntent.putExtras(bundle);
						startActivity(nextIntent);
						
						
					}else{
						Toast.makeText(getApplicationContext(), R.string.notargetline, Toast.LENGTH_SHORT).show();
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
			new AlertDialog.Builder(LineActivity.this)
					.setIcon(R.drawable.ic_launcher)
					.setTitle("长春离线公交")
					.setMessage("你确定退出了哦?")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface arg0,
										int arg1) {
									android.os.Process.killProcess(android.os.Process.myPid());
									LineActivity.this.finish();
								}

							}).setNegativeButton("取消", null).show();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
	
}
