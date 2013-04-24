package com.carqi.ccbus.activity;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKEvent;
import com.baidu.mapapi.MKGeneralListener;

public class ChangChunBusApp extends Application {
	
	static ChangChunBusApp mDemoApp;
	
	//百度MapAPI的管理类
	public BMapManager bMapManager = null;
	
	public String mStrKey = "D82747F5591A038A70D9FA4920D0593CEC2B3EB9";
	boolean m_bKeyRight = true;	// 授权Key正确，验证通过
	
	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	public static class MyGeneralListener implements MKGeneralListener {
		@Override
		public void onGetNetworkState(int iError) {
			Log.d("MyGeneralListener", "onGetNetworkState error is "+ iError);
			Toast.makeText(ChangChunBusApp.mDemoApp.getApplicationContext(), "您的网络出错啦！",
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onGetPermissionState(int iError) {
			Log.d("MyGeneralListener", "onGetPermissionState error is "+ iError);
			if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				Toast.makeText(ChangChunBusApp.mDemoApp.getApplicationContext(), "输入的Key有错误，请核实。", Toast.LENGTH_SHORT).show();
				ChangChunBusApp.mDemoApp.m_bKeyRight = false;
			}
		}
	}

	@Override
    public void onCreate() {
		Log.v("BMapApiDemoApp", "onCreate");
		mDemoApp = this;
		bMapManager = new BMapManager(this);
		boolean isSuccess = bMapManager.init(this.mStrKey, new MyGeneralListener());
		// 初始化地图sdk成功，设置定位监听时间
		if (isSuccess) {
			bMapManager.getLocationManager().setNotifyInternal(10, 5);
		}
		else {
		    // 地图sdk初始化失败，不能使用sdk
		}
		super.onCreate();
	}

	@Override
	//建议在您app的退出之前调用mapadpi的destroy()函数，避免重复初始化带来的时间消耗
	public void onTerminate() {
		// TODO Auto-generated method stub
		if (bMapManager != null) {
			bMapManager.destroy();
			bMapManager = null;
		}
		super.onTerminate();
	}

}
