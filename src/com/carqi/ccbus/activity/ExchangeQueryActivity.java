package com.carqi.ccbus.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.carqi.ccbus.adapter.AutoCompleteAdater;
import com.carqi.ccbus.data.BusStation;
import com.carqi.ccbus.service.BusService;

public class ExchangeQueryActivity extends BaseActivity {
	private static final String TAG = "ExchangeQueryActivity";
	private Button queryButton;
	private AutoCompleteTextView startStationText;
	private AutoCompleteTextView endStationText;
	private Drawable mIconSearchClear; // 搜索文本框清除文本内容图标
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exchangequery);
		init();
	}
	
	
	
	
	private void init() {
		final Resources res = getResources();
		mIconSearchClear = res.getDrawable(R.drawable.txt_search_clear);
		
		queryButton = (Button) this.findViewById(R.id.exchangequery_button);
		startStationText = (AutoCompleteTextView) this.findViewById(R.id.startStationText);
		endStationText = (AutoCompleteTextView) this.findViewById(R.id.endStationText);
		AutoCompleteAdater cursorAdapter = new AutoCompleteAdater(this, R.layout.simple_dropdown_item_1line, null, "station", android.R.id.text1);
		
		startStationText.setThreshold(2);
		startStationText.setAdapter(cursorAdapter);

		endStationText.setThreshold(2);
		endStationText.setAdapter(cursorAdapter);
		
		
		queryButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String startStation = startStationText.getText().toString();
				String endStation = endStationText.getText().toString();
				Log.i(TAG, "startStation:"+startStation);
				Log.i(TAG, "endStation:"+endStation);
				if(startStation != null && !startStation.equals("") && endStation != null && !endStation.equals("")){
					
				}else if(startStation.equals("") && endStation.equals("")){

					Log.i(TAG, "75----------------------:");
					Toast.makeText(getApplicationContext(), R.string.pleaseinputstartstation, Toast.LENGTH_SHORT).show();
				}else if(!startStation.equals("") && endStation.equals("")){

					Log.i(TAG, "79----------------------:");
					Toast.makeText(getApplicationContext(), R.string.pleaseinputendstation, Toast.LENGTH_SHORT).show();
				}else{

					Log.i(TAG, "84:----------------------");
					Toast.makeText(getApplicationContext(), R.string.pleaseinputstartstation, Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
		startStationText.addTextChangedListener(new TextWatcher() {
			//缓存上一次文本框内是否为空
	        private boolean isnull = true;
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if (TextUtils.isEmpty(s)) {
	                if (!isnull) {
	                	startStationText.setCompoundDrawablesWithIntrinsicBounds(null,
	                            null, null, null);
	                    isnull = true;
	                }
	            } else {
	                if (isnull) {
	                	startStationText.setCompoundDrawablesWithIntrinsicBounds(null,
	                            null, mIconSearchClear, null);
	                    isnull = false;
	                }
	            }
			}
		});
		startStationText.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
	            switch (event.getAction()) {
	            case MotionEvent.ACTION_UP:
	                int curX = (int) event.getX();
	                if (curX > v.getWidth() - 38
	                        && !TextUtils.isEmpty(startStationText.getText())) {
	                	startStationText.setText("");
	                    int cacheInputType = startStationText.getInputType();// backup  the input type
	                    startStationText.setInputType(InputType.TYPE_NULL);// disable soft input
	                    startStationText.onTouchEvent(event);// call native handler
	                    startStationText.setInputType(cacheInputType);// restore input  type
	                    return true;// consume touch even
	                }
	                break;
	            }
	            return false;
			}
		});
	}
	



	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(ExchangeQueryActivity.this)
					.setIcon(R.drawable.ic_launcher)
					.setTitle("长春离线公交")
					.setMessage("你确定退出了哦?")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface arg0,
										int arg1) {
									android.os.Process.killProcess(android.os.Process.myPid());
									ExchangeQueryActivity.this.finish();
								}

							}).setNegativeButton("取消", null).show();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
	
}
