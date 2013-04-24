package com.carqi.ccbus.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKBusLineResult;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.MKPlanNode;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKSuggestionResult;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapController;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.TransitOverlay;

public class MapViewActivity extends MapActivity {
	private static final String TAG = "MapViewActivity";
	private Button back_btn;
	private Button home_btn;
	private String start;
	private String end;
	private ProgressDialog pd;
	// 添加百度地图相关控件
	private MapView mapView;
	// 在百度地图上添加一些控件，比如是放大或者缩小的控件
	//private MapController mapController;
	private MKSearch mkSearch; // 用于检索的类

	
	
	
	
	
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_view_demo);

		Log.i(TAG, "onCreate()XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		init();
		
		ChangChunBusApp app = (ChangChunBusApp)this.getApplication();
		if (app.bMapManager == null) {
			app.bMapManager = new BMapManager(getApplication());
			app.bMapManager.init(app.mStrKey, new ChangChunBusApp.MyGeneralListener());
		}
		app.bMapManager.start();
        // 如果使用地图SDK，请初始化地图Activity
        super.initMapActivity(app.bMapManager);
	

	mapView = (MapView) this.findViewById(R.id.bmapView);
	mapView.setBuiltInZoomControls(true);
    //设置在缩放动画过程中也显示overlay,默认为不绘制
	mapView.setDrawOverlayWhenZooming(true);
	/*
	 * mapController = mapView.getController(); //需要定义一个经纬度：北京天安门 GeoPoint
	 * geoPoint = new GeoPoint((int)(39.915*1E6), (int)(116.40*1E6));
	 * mapController.setCenter(geoPoint); //设置一个中心点
	 * mapController.setZoom(12); //设置缩放级别是12个级别
	 */

	/*pd = ProgressDialog.show(MapViewActivity.this, null, "正在查询请稍后...",
			true, true);*/

	
	 GeoPoint geoPoint = new GeoPoint((int)(43.908127*1E6),(int)(125.317547*1E6)); 
	 mapView.getController().setCenter(geoPoint); //设置一个中心点
	 mapView.getController().setZoom(13);
	 
	// 实例化地图的查询类
	//mapController = mapView.getController();
	
	
	
	mkSearch = new MKSearch();
	mkSearch.init(app.bMapManager, new MySearcherListener());

	Log.i(TAG, "起点：" + start + "	终点：" + end);
	MKPlanNode stNode = new MKPlanNode();
	stNode.name = start;
	// stNode.pt = new GeoPoint((int)(39.915*1E6), (int)(116.404*1E6));
	MKPlanNode enNode = new MKPlanNode();
	enNode.name = end;
	// enNode.pt = new GeoPoint(40057031, 116307852);
	mkSearch.setTransitPolicy(MKSearch.EBUS_TIME_FIRST);
	mkSearch.transitSearch("长春", stNode, enNode);
	}

	public class MySearcherListener implements MKSearchListener {

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
		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult res, int iError) {
			if (iError != 0 || res == null) {
				Toast.makeText(MapViewActivity.this, "抱歉，未找到结果",
						Toast.LENGTH_LONG).show();
				return;
			}
			Log.i(TAG, "iError is ~~~~~~~~~~" + iError);
			// Log.i(TAG, "res is ~~~~~~~~~~"+res.getAddrResult().);

			TransitOverlay transitOverlay = new TransitOverlay(
					MapViewActivity.this, mapView);
			transitOverlay.setData(res.getPlan(0));
			mapView.getOverlays().clear();
			mapView.getOverlays().add(transitOverlay);
			mapView.invalidate(); // 刷新地图的数据
			mapView.getController().animateTo(res.getStart().pt);
			/*mapController.animateTo(res.getStart().pt);
			mapController.setZoom(13); //设置缩放级别是12个级别
*/			//pd.dismiss();
		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub

		}

	}

	private void init() {
		start = getIntent().getExtras().getString("start");
		end = getIntent().getExtras().getString("end");
		
		back_btn = (Button) this.findViewById(R.id.title_back);
		home_btn = (Button) this.findViewById(R.id.title_home);
		back_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(1);
				finish();
			}
		});
		home_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
				MapViewActivity.this.finish();
			}
		});
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


}
