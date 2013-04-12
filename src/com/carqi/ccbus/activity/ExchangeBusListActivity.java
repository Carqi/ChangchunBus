package com.carqi.ccbus.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.carqi.ccbus.adapter.ExchangeBusAdapter;
import com.carqi.ccbus.adapter.ThroughBusAdapter;
import com.carqi.ccbus.data.BusExchange;
import com.carqi.ccbus.data.BusStation;
import com.carqi.ccbus.service.BusService;

@SuppressLint("HandlerLeak")
public class ExchangeBusListActivity extends BaseActivity {
	private static final String TAG = "ExchangeBusListActivity";
	public static int RESULT = 1;
	public static int REQUEST = 0;
	private ListView listView;
	private List<BusExchange> exchange_list;
	private TextView startStationText;
	private TextView endStationText;
	private Button back_btn;
	private Button home_btn;
	private TextView titleText;
	private TextView sorry_title_Text;
	private TextView sorry_content_Text;
	private String startStation;	//换乘查询起点
	private String endStation;	//换乘查询终点
	private List<BusStation> detailListStations; //显示换乘路线所经过的所有站点
	private int[] stanum;	//途径站总数
	
	private ProgressDialog progressDialog; 
	private Handler handler;
	
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
		
		
		/** 
	     * 用Handler来更新UI 
	     */  
	    handler = new Handler(){  
	  
	        @Override  
	        public void handleMessage(Message msg) {  
	              
	            //关闭ProgressDialog  
	            progressDialog.dismiss();  
	              
	            //更新UI  
	            if(exchange_list.size() != 0){
	            	if(exchange_list.get(0).getLine2() == null){
	            		zhida(exchange_list);
	            	}else{
						once_exchange(exchange_list);
	            	}
				}else{
					sorry_title_Text.setVisibility(View.VISIBLE);
					sorry_content_Text.setVisibility(View.VISIBLE);
				}
	            
	        }}; 
		
		
		
		//显示ProgressDialog  
        progressDialog = ProgressDialog.show(ExchangeBusListActivity.this, null, "正在查询换乘方案...", true, false);  
          
        
        
        
        //新建线程  
        new Thread(){  

            @Override  
            public void run() {  
                //需要花时间计算的方法  
                //Calculation.calculate(4); 
            	
            	exchange_list = exchange_query_db(startStation, endStation);
                //向handler发消息  
            	handler.sendEmptyMessage(0);  
            }}.start();  
	}
		

		
	private List<BusExchange> exchange_query_db(String startStation, String endStation) {
		BusService busService = new BusService(getApplicationContext());
		
		exchange_list = busService.exchangeQuery(startStation, endStation);
		return exchange_list;
	}
	
	
	
	
	
	
	
		
	
	/**
	 * 处理换乘1次时的返回数据
	 * @param exchange_list2
	 *@date 2013-4-8
	 */
	private void once_exchange( List<BusExchange> exchange_list1) {
		ExchangeBusAdapter busAdapter = new ExchangeBusAdapter(this, exchange_list1);
		Log.i(TAG, "145:~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		listView.setAdapter(busAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {

				Intent nextIntent = new Intent(getApplicationContext(),
						Exchange_OnceDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("startStation", startStation);
				bundle.putString("exchangeStation", exchange_list.get(position).getExchangeStation1());
				bundle.putString("endStation", endStation);
				bundle.putString("plan_no", "方案" + (position + 1));
				nextIntent.putExtras(bundle);
				startActivity(nextIntent);
			}
		});
		
		
	}







	/**
	 * 处理直达线路数据
	 * @param list 
	 *@date 2013-4-5
	 */
	private void zhida(List<BusExchange> list) {
		int startNum = 0;
		int endNum = 0;
		stanum = new int[list.size()];
		detailListStations = new ArrayList<BusStation>();
		for(int i=0 ; i<list.size() ; i++){
			String station = list.get(i).getStartStation();
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
		/*for(int i=0; i<stanum.length ; i++){
			Log.i(TAG, stanum[i]+"");
		}*/
		ThroughBusAdapter busAdapter = new ThroughBusAdapter(this, list,
				stanum);
		listView.setAdapter(busAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent nextIntent = new Intent(getApplicationContext(),
						ExchangeBusDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("startStation", startStation);
				bundle.putString("endStation", endStation);
				bundle.putString("line", exchange_list.get(position).getLine1());
				bundle.putString("plan_no", "方案" + (position + 1));
				bundle.putString("detailStations",
						detailListStations.get(position).getStation());
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
		sorry_title_Text = (TextView) this.findViewById(R.id.sorry_title);
		sorry_content_Text = (TextView) this.findViewById(R.id.sorry_content);
		

		sorry_title_Text.setVisibility(View.GONE);
		sorry_content_Text.setVisibility(View.GONE);
		
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
