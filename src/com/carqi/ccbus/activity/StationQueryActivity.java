package com.carqi.ccbus.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
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

public class StationQueryActivity extends Activity {
	private static final String TAG = "StationQueryActivity";
	private Button queryButton;
	private AutoCompleteTextView stationText;
	private Drawable mIconSearchClear; // 搜索文本框清除文本内容图标
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stationquery);
		init();
	}
	
	
	
	
	private void init() {
		final Resources res = getResources();
		mIconSearchClear = res.getDrawable(R.drawable.txt_search_clear);
		
		queryButton = (Button) this.findViewById(R.id.querystation_button);
		stationText = (AutoCompleteTextView) this.findViewById(R.id.stationText);
		AutoCompleteAdater cursorAdapter = new AutoCompleteAdater(this, R.layout.simple_dropdown_item_1line, null, "station", android.R.id.text1);
		
		stationText.setThreshold(2);
		stationText.setAdapter(cursorAdapter);
		
		
		
		queryButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				List<BusStation> stalist = new ArrayList<BusStation>();
				String station = stationText.getText().toString();
				if(station != null && !station.equals("")){
					Log.i(TAG, "站点名称：" + station);
					BusService busService = new BusService(getApplicationContext());
					stalist = busService.showStations(station);
					if(stalist != null && stalist.size() == 1){
						Intent nextIntent = new Intent(getApplicationContext(), BusListActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("station", stalist.get(0).getStation());
						nextIntent.putExtras(bundle);
						startActivity(nextIntent);
						
						
					}else if(stalist.size() > 1){
						
						Intent nextIntent = new Intent(getApplicationContext(), CommonListDialogActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("stalist", (Serializable) stalist);
						bundle.putString("guide", "stationDialog");
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
		
		
		stationText.addTextChangedListener(new TextWatcher() {
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
	                	stationText.setCompoundDrawablesWithIntrinsicBounds(null,
	                            null, null, null);
	                    isnull = true;
	                }
	            } else {
	                if (isnull) {
	                	stationText.setCompoundDrawablesWithIntrinsicBounds(null,
	                            null, mIconSearchClear, null);
	                    isnull = false;
	                }
	            }
			}
		});
		stationText.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
	            switch (event.getAction()) {
	            case MotionEvent.ACTION_UP:
	                int curX = (int) event.getX();
	                if (curX > v.getWidth() - 38
	                        && !TextUtils.isEmpty(stationText.getText())) {
	                	stationText.setText("");
	                    int cacheInputType = stationText.getInputType();// backup  the input type
	                    stationText.setInputType(InputType.TYPE_NULL);// disable soft input
	                    stationText.onTouchEvent(event);// call native handler
	                    stationText.setInputType(cacheInputType);// restore input  type
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
