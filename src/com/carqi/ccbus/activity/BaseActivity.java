package com.carqi.ccbus.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;


public class BaseActivity extends Activity {
	// 写一个广播的内部类，当收到动作时，结束activity  
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {  
        @Override  
        public void onReceive(Context context, Intent intent) {  
            //unregisterReceiver(this); // 这句话必须要写要不会报错，不写虽然能关闭，会报一堆错  
            finish();  
        }  
    };  
  
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override  
    public void onResume() {  
        super.onResume();  
  
        // 在当前的activity中注册广播  
        IntentFilter filter = new IntentFilter();  
        filter.addAction("ExitApp");  
        getApplicationContext().registerReceiver(this.broadcastReceiver, filter); // 注册  
    }  
  
      
	@Override
	protected void onDestroy() {
	// 删除广播注册
	getApplicationContext().unregisterReceiver(this.broadcastReceiver);
	super.onDestroy();
	}
	
}
