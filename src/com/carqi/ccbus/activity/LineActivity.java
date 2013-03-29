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
import com.carqi.ccbus.data.Bus;
import com.carqi.ccbus.service.BusService;

public class LineActivity extends BaseActivity {
	private static final String TAG = "LineActivity";
	private Button queryButton;
	private AutoCompleteTextView lineText;
	private Drawable mIconSearchClear; // 搜索文本框清除文本内容图标
	//private Bus bus = new Bus();
	//private List<BusStation> stalist = new ArrayList<BusStation>();
	private List<Bus> buslist;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.line);
		init();

		
	}
	

	private void init() {
		final Resources res = getResources();
		mIconSearchClear = res.getDrawable(R.drawable.txt_search_clear);
		
		queryButton = (Button) this.findViewById(R.id.query_button);
		
		AutoCompleteAdater cursorAdapter = new AutoCompleteAdater(this, R.layout.simple_dropdown_item_1line, null, "line", android.R.id.text1);
		// 设置输入一个字符就弹出提示列表(默认输入两个字符时才弹出提示)
		lineText = (AutoCompleteTextView) this.findViewById(R.id.autoCompleteTextView1);
		lineText.setThreshold(1);
		lineText.setAdapter(cursorAdapter);
		queryButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				buslist = new ArrayList<Bus>();
				String line = lineText.getText().toString();
				if(line != null && !line.equals("")){
					Log.i(TAG, "站点：" + line);
					BusService busService = new BusService(getApplicationContext());
					buslist = busService.findBus(line);
					if(buslist != null && buslist.size() == 1){
						Intent nextIntent = new Intent(getApplicationContext(), TabStationActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("bus", buslist.get(0));
						nextIntent.putExtras(bundle);
						startActivity(nextIntent);
					}else if(buslist.size() > 1){
						Intent nextIntent = new Intent(getApplicationContext(), CommonListDialogActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("buslist", (Serializable) buslist);
						bundle.putString("guide", "lineDialog");
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
		

		lineText.addTextChangedListener(new TextWatcher() {
			//缓存上一次文本框内是否为空
	        private boolean isnull = true;
			@Override
			public void afterTextChanged(Editable s) {
				if (TextUtils.isEmpty(s)) {
	                if (!isnull) {
	                	lineText.setCompoundDrawablesWithIntrinsicBounds(null,
	                            null, null, null);
	                    isnull = true;
	                }
	            } else {
	                if (isnull) {
	                	lineText.setCompoundDrawablesWithIntrinsicBounds(null,
	                            null, mIconSearchClear, null);
	                    isnull = false;
	                }
	            }
				
			}
			/**
	         * 随着文本框内容改变动态改变列表内容
	         */
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
		lineText.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
	            switch (event.getAction()) {
	            case MotionEvent.ACTION_UP:
	                int curX = (int) event.getX();
	                if (curX > v.getWidth() - 38
	                        && !TextUtils.isEmpty(lineText.getText())) {
	                	lineText.setText("");
	                    int cacheInputType = lineText.getInputType();// backup  the input type
	                    lineText.setInputType(InputType.TYPE_NULL);// disable soft input
	                    lineText.onTouchEvent(event);// call native handler
	                    lineText.setInputType(cacheInputType);// restore input  type
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
			new AlertDialog.Builder(LineActivity.this)
					.setIcon(R.drawable.ic_launcher)
					.setTitle("长春离线公交")
					.setMessage("你确定退出了哦?")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface arg0,
										int arg1) {
									/*android.os.Process.killProcess(android.os.Process.myPid());
									LineActivity.this.finish();*/
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
        sendBroadcast(intent);// 该函数用于发送广播  
        finish();  
    } 
	
	
	
	
	
}
