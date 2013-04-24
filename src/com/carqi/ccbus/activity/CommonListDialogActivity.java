package com.carqi.ccbus.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.carqi.ccbus.adapter.CommonAdapter;
import com.carqi.ccbus.adapter.CommonStationAdapter;
import com.carqi.ccbus.data.Bus;
import com.carqi.ccbus.data.BusStation;

public class CommonListDialogActivity extends Activity {
	private static final String TAG = "BusListDialogActivity";
	public static int RESULT = 1;
	public static int REQUEST = 0;
	private ListView listView;
	private TextView commonText;
	private List<Bus> buslist;
	private List<BusStation> stalist;
	private String guide; 
	
	private Boolean isStart; //网络查询时，是否是起点标志
	
	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_dialog_list);
		init();
		
		guide = getIntent().getStringExtra("guide");
		if(guide.equals("lineDialog")){
			buslist = (List<Bus>) getIntent().getExtras().getSerializable("buslist");

			String line_temp = buslist.get(0).getLine();
			Log.i(TAG, "line_temp = "+line_temp);
			
			commonText.setText("为您找到以下相关线路：");
			CommonAdapter comAdapter = new CommonAdapter(this, buslist);
			listView.setAdapter(comAdapter);
		}else if(guide.equals("netDialog")){
			isStart = getIntent().getExtras().getBoolean("isStart");
			buslist = (List<Bus>) getIntent().getExtras().getSerializable("buslist");
			if(isStart){
				commonText.setText("您要找的起点是：");
				CommonAdapter comAdapter = new CommonAdapter(this, buslist);
				listView.setAdapter(comAdapter);
			}else{
				commonText.setText("您要找的终点是：");
				CommonAdapter comAdapter = new CommonAdapter(this, buslist);
				listView.setAdapter(comAdapter);
			}
			
		}else{
			Log.i(TAG, "here ~~~~~~~~~~~~~~~~~~~~~~~~~~");
			stalist = (List<BusStation>) getIntent().getExtras().getSerializable("stalist");

			
			commonText.setText("为您找到以下相关站点：");
			CommonStationAdapter comAdapter = new CommonStationAdapter(this, stalist);
			listView.setAdapter(comAdapter);
			
		}
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				if(guide.equals("lineDialog")){
					Intent nextIntent = new Intent(getApplicationContext(), TabStationActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("bus", buslist.get(position));
					nextIntent.putExtras(bundle);
					startActivity(nextIntent);
				}else if(guide.equals("netDialog")){
					Intent intent = new Intent();
					if(isStart){
						intent.putExtra("start", buslist.get(position).getLine());
						setResult(30, intent);
					}else{
						intent.putExtra("end", buslist.get(position).getLine());
						setResult(40, intent);
					}
				}else{
					Intent nextIntent = new Intent(getApplicationContext(), BusListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("station", stalist.get(position).getStation());
					nextIntent.putExtras(bundle);
					startActivity(nextIntent);
				}
				CommonListDialogActivity.this.finish();
				
			}
		});
		
		
	}
	private void init() {
		listView = (ListView) this.findViewById(R.id.commonlist);
		commonText = (TextView) this.findViewById(R.id.common_title);
	}
	
	
}
