package com.carqi.ccbus.activity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKBusLineResult;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKSuggestionResult;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.carqi.ccbus.data.Bus;

public class NetActivity extends MapActivity{
	private static final String TAG = "MainActivity";
	private EditText startText;
	private EditText endText;
	private String start;
	private String end;
	private Button queryButton;
	
	private List<Bus> buslist;
	private ProgressDialog pd;

	//添加百度地图相关控件
	private MapView mapView;
	private MKSearch mkSearch; //用于检索的类
	
	public static String mStrSuggestions[] = {};
	
	private boolean isStart; //当isStart=true，代表给起点赋值，false代表给终点赋值
	private boolean back = false; //
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net_query);
		initUI();
		//buslist = new ArrayList<Bus>();
		ChangChunBusApp app = (ChangChunBusApp)this.getApplication();
		if (app.bMapManager == null) {
			app.bMapManager = new BMapManager(getApplication());
			app.bMapManager.init(app.mStrKey, new ChangChunBusApp.MyGeneralListener());
		}
		app.bMapManager.start();
        // 如果使用地图SDK，请初始化地图Activity
        super.initMapActivity(app.bMapManager);
		

		//实例化地图的查询类
		mkSearch = new MKSearch();
		mkSearch.init(app.bMapManager, new MySearcherListener());
	}

	
	
	
	public class MySearcherListener implements MKSearchListener{

		@Override
		public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetRGCShareUrlResult(String arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult res, int iError) {
			if (iError != 0 || res == null) {
				Log.i(TAG, "iError is ~~~~~~~~~~~~~~："+iError);
				Toast.makeText(NetActivity.this, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
				return;
			}
			
			int nSize = res.getSuggestionNum();
			buslist = new ArrayList<Bus>();
			for (int i = 0; i < nSize; i++) {
				if(res.getSuggestion(i).city.equals("长春市")){
					Log.i(TAG, "city"+res.getSuggestion(i).city);
					Log.i(TAG, "key"+res.getSuggestion(i).key);
					Bus bus = new Bus();
					bus.setLine(res.getSuggestion(i).key);
					buslist.add(bus);
				}

				
			}
			
			Intent intent = new Intent(getApplicationContext(), CommonListDialogActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("guide", "netDialog");
			if(isStart){
				bundle.putBoolean("isStart", true);
			}else{
				bundle.putBoolean("isStart", false);
			}
			bundle.putSerializable("buslist", (Serializable) buslist);
			//bundle.putStringArray("stopArray", mStrSuggestions);
			intent.putExtras(bundle);
			startActivityForResult(intent, 200);

			
		}

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	private void initUI() {
		mapView = (MapView) this.findViewById(R.id.bmapView);
		mapView.setVisibility(View.GONE);

		startText = (EditText) this.findViewById(R.id.start);
		endText = (EditText) this.findViewById(R.id.end);
		queryButton = (Button) this.findViewById(R.id.net_button);
		queryButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				start = startText.getText().toString();
				end = endText.getText().toString();
				if(start != null && !start.equals("") && end != null && !end.equals("")){
					if(back){
						Intent intent = new Intent(getApplicationContext(), MapViewActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("start", start);
						bundle.putString("end", end);
						intent.putExtras(bundle);
						startActivity(intent);
					}else{
						Log.i(TAG, "195起点："+start+"终点："+end);
						isStart = true;
						mkSearch.suggestionSearch(start);
						
					}
					
					
				}else if(start.equals("") && !end.equals("")){
					Toast.makeText(getApplicationContext(), "请输入起点", Toast.LENGTH_SHORT).show();
				}else if(start.equals("") && end.equals("")){
					Toast.makeText(getApplicationContext(), "请输入起点", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getApplicationContext(), "请输入终点", Toast.LENGTH_SHORT).show();
				}
				
				
				
				
				
				
				
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 30) {
			start = data.getStringExtra("start");
			startText.setText(start);
			isStart = false;
			mkSearch.suggestionSearch(end);
		}else{
			back = true;
			end = data.getStringExtra("end");
			endText.setText(end);
			Intent intent = new Intent(getApplicationContext(), MapViewActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("start", start);
			bundle.putString("end", end);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}

	@Override
	protected void onPause() {
		ChangChunBusApp app = (ChangChunBusApp)this.getApplication();
		app.bMapManager.stop();
		super.onPause();
	}
	@Override
	protected void onResume() {
		ChangChunBusApp app = (ChangChunBusApp)this.getApplication();
		app.bMapManager.start();
		super.onResume();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
	
	
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(NetActivity.this)
					.setIcon(R.drawable.ic_launcher)
					.setTitle("长春离线公交")
					.setMessage("你确定退出了哦?")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface arg0,
										int arg1) {
									close();
								}

							}).setNegativeButton("取消", null).show();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
	
	public void close(){
		Intent intent = new Intent();  
        intent.setAction("ExitApp"); // 说明动作  
        getApplicationContext().sendBroadcast(intent);// 该函数用于发送广播  
        finish();  
    } 
	
	
}
