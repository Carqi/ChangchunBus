package com.carqi.ccbus.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.carqi.ccbus.data.Bus;

public class ExchangeBusDetailActivity extends BaseActivity {
	private static final String TAG = "ExchangeBusDetailActivity";
	public static int RESULT = 1;
	public static int REQUEST = 0;
	private List<Bus> list;
	private TextView startStationText;
	private TextView endStationText;
	private TextView titleText;
	private TextView detailText;
	private TextView totaldetailText;
	private Button back_btn;
	private Button home_btn;
	private String startStation;	//换乘查询起点
	private String endStation;	//换乘查询终点
	private String detailStations; //显示换乘路线所经过的所有站点
	private String sta_result; //显示换乘路线所经过的所有站点 经过换行处理后
	private int stanum;	//途径站总数
	
	private LinearLayout detailLayout;
	
	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exchange_detail);
		init();
		
		startStation = getIntent().getExtras().getString("startStation");
		endStation = getIntent().getExtras().getString("endStation");
		detailStations = getIntent().getExtras().getString("detailStations");
		stanum = getIntent().getExtras().getInt("stanum");
		String[] stations = detailStations.split("-");
		StringBuilder station1 = new StringBuilder();
		if(stanum<0){
			for(int i=stations.length-1 ; i>=0 ; i--){
				if(stations[i] != null && !stations[i].equals("")){
					Log.i(TAG, "站点"+i+":"+stations[i]);
					station1.append(stations[i]+"\n");
				}
			}
		}else{
			for(int i=0 ; i<stations.length ; i++){
				if(stations[i] != null && !stations[i].equals("")){
					Log.i(TAG, "站点"+i+":"+stations[i]);
					station1.append(stations[i]+"\n");
				}
			}
		}
		
		totaldetailText.setText("  途经"+stations.length+"站");
		sta_result = station1.toString();
		
		
		String plan_no = getIntent().getExtras().getString("plan_no");


		Log.i(TAG, "起始站："+startStation);
		startStationText.setText(startStation);
		endStationText.setText(endStation);
		titleText.setText(plan_no);

		
		
	}
	private void init() {
		startStationText = (TextView) this.findViewById(R.id.startStation);
		endStationText = (TextView) this.findViewById(R.id.endStation);
		titleText = (TextView) this.findViewById(R.id.title_text);
		detailText = (TextView) this.findViewById(R.id.detail);
		totaldetailText = (TextView) this.findViewById(R.id.totaldetailText);
		detailLayout = (LinearLayout) this.findViewById(R.id.detailLayout);
		
		
		totaldetailText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				
				if(detailText.getText().toString().equals("")){
						detailText.setText(sta_result);
						totaldetailText.setBackgroundResource(R.drawable.select_back1);
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						detailLayout.setLayoutParams(params);
				}else{
					detailText.setText("");
					totaldetailText.setBackgroundResource(R.drawable.select_back2);
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 0);
					detailLayout.setLayoutParams(params);
			
				}
				
			}
		});
		
		
		
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
