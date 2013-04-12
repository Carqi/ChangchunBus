package com.carqi.ccbus.activity;


import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.carqi.ccbus.data.Bus;
import com.carqi.ccbus.data.BusExchange;
import com.carqi.ccbus.service.BusService;


public class Exchange_OnceDetailActivity extends BaseActivity {
	private static final String TAG = "ExchangeBusDetailActivity";
	public static int RESULT = 1;
	public static int REQUEST = 0;
	private TextView startStationText;
	private TextView exchangeStationText;
	private TextView endStationText;
	private TextView titleText;
	private Button back_btn;
	private Button home_btn;
	private String line1; //换乘线路1
	private String startStation;	//换乘查询起点
	private String endStation;	//换乘查询终点

	private List<BusExchange> exchange_list;
	private List<Bus> temp_list; //临时存储换乘线路Line1的list
	private List<Bus> temp_list1; //临时存储换乘线路Line2的list
	
	private int screenWidth;
    private static int TextStartID = 0x2000000;
    private static int TextEndID = 0x3000000;
    private int k;
    private int z;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exchange_once_detail);
		init();
		
		startStation = getIntent().getExtras().getString("startStation");
		String exchangeStation = getIntent().getExtras().getString("exchangeStation");
		endStation = getIntent().getExtras().getString("endStation");
		String plan_no = getIntent().getExtras().getString("plan_no");

		startStationText.setText(startStation);
		endStationText.setText(endStation);
		exchangeStationText.setText(exchangeStation);
		titleText.setText(plan_no);
		
		
		
		BusService busService = new BusService(this);
		exchange_list = busService.once_exchange_detail(startStation, exchangeStation, endStation);
		
		
		/***************************处理换乘上半部分内容********************************/
		temp_list = new ArrayList<Bus>();
		RelativeLayout relalayout = (RelativeLayout) findViewById(R.id.line_layout);
		/*Log.i(TAG, "屏幕宽度："+screenWidth);*/
		LinearLayout.LayoutParams linear_params=new LinearLayout.LayoutParams(screenWidth-50, LinearLayout.LayoutParams.WRAP_CONTENT); 
		relalayout.setLayoutParams(linear_params);
		
		RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT); 
		//relalayout.setLayoutParams(params);
		
		StringBuilder sb = new StringBuilder();
		sb.append("乘坐 ");
		k = 0;
		int position = 0; //显示位置0为右边，1为左边
		for(int i=0 ; i<exchange_list.size() ; i++){
			
			line1 = exchange_list.get(i).getLine1();
			if(k==0){
				temp_list.add(new Bus(line1, null, null));
				String line1_stopCount = exchange_list.get(i).getLine1_StopCount();
				
				String content = "";
				int sub = 0;
				int sub1 = 0;
				
				content = "乘坐 "+line1+"("+line1_stopCount+"站)/";
				sub = 2;
				sub1 = sub+1;
				
				Log.i(TAG, "我走到这了~~~~~~~~~~k:"+k);
				TextView line_Text = new TextView(this);
				SpannableString spannableString1 = new SpannableString(content);
				spannableString1.setSpan(new ForegroundColorSpan(Color.BLUE), sub, exchange_list.get(i).getLine1().length()+sub1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
				
				
				
				line_Text.setText(spannableString1);
				
				line_Text.setId(TextStartID+k);
			
				Log.i(TAG, k+"TextView值:"+(TextStartID+k));
				line_Text.setTextSize(16);
				line_Text.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						int x = v.getId() - TextStartID;
						Log.i(TAG, "X is ~~~~~~~~~~~"+x);
						BusService busService = new BusService(getApplicationContext());
						Log.i(TAG, "83~~~:"+temp_list.get(x).getLine());
						List<Bus> buslist = busService.findBus_exact(temp_list.get(x).getLine());
						Intent nextIntent = new Intent(getApplicationContext(), TabStationActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("bus", buslist.get(0));
						nextIntent.putExtras(bundle);
						startActivity(nextIntent);
					}
				});
				
				params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				
				 Log.i(TAG, "k值~~~~~~~"+k);
				 
				 
				 if(temp_list.get(k).getLine().length()>10 && position==0){
						if(k==0){
							//params.addRule(RelativeLayout.BELOW, TextStartID + k);
						}else{
							params.addRule(RelativeLayout.BELOW, TextStartID + k - 1);
						}
						position = 0;
					}else if(temp_list.get(k).getLine().length()>10 && position==1){
						params.addRule(RelativeLayout.BELOW, TextStartID + k - 1);
						position = 0;
					}else if(temp_list.get(k).getLine().length()<10 && position==0){
						params.addRule(RelativeLayout.BELOW, TextStartID + k - 1);
						position = 1;
					}else{
						params.addRule(RelativeLayout.RIGHT_OF, TextStartID + k - 1);
						params.addRule(RelativeLayout.BELOW, TextStartID + k - 2);
						position = 0;
					}
					
				
				line_Text.setLayoutParams(params);

				relalayout.addView(line_Text,params);
				k++;
				
			}else{
				if(!line1.equals(temp_list.get(k-1).getLine())){
					temp_list.add(new Bus(line1, null, null));
					String line1_stopCount = exchange_list.get(i).getLine1_StopCount();
					
					String content = "";
					content = line1+"("+line1_stopCount+"站)/";
					
					Log.i(TAG, "我走到这了~~~~~~~~~~k:"+k);
					TextView line_Text = new TextView(this);
					SpannableString spannableString1 = new SpannableString(content);
					spannableString1.setSpan(new ForegroundColorSpan(Color.BLUE), 0, exchange_list.get(i).getLine1().length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
					
					
					
					line_Text.setText(spannableString1);
					
					line_Text.setId(TextStartID+k);
					
					
					Log.i(TAG, k+"TextView值:"+(TextStartID+k));
					line_Text.setTextSize(16);
					line_Text.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							int x = v.getId() - TextStartID;
							//int w = v.getId();
							Log.i(TAG, "X is ~~~~~~~~~~~"+x);
							BusService busService = new BusService(getApplicationContext());
							Log.i(TAG, "83~~~:"+temp_list.get(x).getLine());
							List<Bus> buslist = busService.findBus_exact(temp_list.get(x).getLine());
							Intent nextIntent = new Intent(getApplicationContext(), TabStationActivity.class);
							Bundle bundle = new Bundle();
							bundle.putSerializable("bus", buslist.get(0));
							nextIntent.putExtras(bundle);
							startActivity(nextIntent);
						}
					});
					
					
					
					 params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					
					 Log.i(TAG, "k值~~~~~~~"+k);
					 
					 
					 if(temp_list.get(k).getLine().length()>10 && position==0){
							if(k==0){
								//params.addRule(RelativeLayout.BELOW, TextStartID + k);
							}else{
								params.addRule(RelativeLayout.BELOW, TextStartID + k - 1);
							}
							position = 0;
						}else if(temp_list.get(k).getLine().length()>10 && position==1){
							params.addRule(RelativeLayout.BELOW, TextStartID + k - 1);
							position = 0;
						}else if(temp_list.get(k).getLine().length()<10 && position==0){
							params.addRule(RelativeLayout.BELOW, TextStartID + k - 1);
							position = 1;
						}else{
							params.addRule(RelativeLayout.RIGHT_OF, TextStartID + k - 1);
							params.addRule(RelativeLayout.BELOW, TextStartID + k - 2);
							position = 0;
						}
						
					
					line_Text.setLayoutParams(params);

					relalayout.addView(line_Text,params);
					k++;
				}
			}
		}
		int sub =0;
		if((k-1)==0){
			sub = 3;
		}
		TextView last_Text = (TextView)relalayout.findViewById(TextStartID+k-1);
		String text_str = last_Text.getText().toString();
		String last_str = text_str.replace("/", " 至");
		Log.i(TAG, "last_str is :"+last_str);
		SpannableString spannable_last = new SpannableString(last_str);
		spannable_last.setSpan(new ForegroundColorSpan(Color.BLUE), sub, temp_list.get(k-1).getLine().length()+sub, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		last_Text.setText(spannable_last);
		
		
		
		
		
		
		
		/***************************处理换乘下半部分内容********************************/
		temp_list1 = new ArrayList<Bus>();
		RelativeLayout relalayout1 = (RelativeLayout) findViewById(R.id.line_layout1);
		/*Log.i(TAG, "屏幕宽度："+screenWidth);*/
		LinearLayout.LayoutParams linear_params1=new LinearLayout.LayoutParams(screenWidth-50, LinearLayout.LayoutParams.WRAP_CONTENT); 
		relalayout1.setLayoutParams(linear_params1);
		
		RelativeLayout.LayoutParams params1=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT); 
		//relalayout.setLayoutParams(params);
		
		z = 0;
		int position1 = 0; //显示位置0为右边，1为左边
		for(int i=0 ; i<exchange_list.size() ; i++){
			
			line1 = exchange_list.get(i).getLine2();
			if(z==0){
				temp_list1.add(new Bus(line1, null, null));
				String line2_stopCount = exchange_list.get(i).getLine2_StopCount();
				
				String content = "";
				
				content = "乘坐 "+line1+"("+line2_stopCount+"站)/";
				
				Log.i(TAG, "我走到这了~~~~~~~~~~k:"+k);
				TextView line_Text = new TextView(this);
				SpannableString spannableString1 = new SpannableString(content);
				spannableString1.setSpan(new ForegroundColorSpan(Color.BLUE), 2, exchange_list.get(i).getLine2().length()+3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
				
				
				
				line_Text.setText(spannableString1);
				
				line_Text.setId(TextEndID+z);
			
				Log.i(TAG, z+"TextView值:"+(TextEndID+z));
				line_Text.setTextSize(16);
				line_Text.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						int x = v.getId() - TextEndID;
						Log.i(TAG, "X is ~~~~~~~~~~~"+x);
						BusService busService = new BusService(getApplicationContext());
						Log.i(TAG, "83~~~:"+temp_list1.get(x).getLine());
						List<Bus> buslist = busService.findBus_exact(temp_list1.get(x).getLine());
						Intent nextIntent = new Intent(getApplicationContext(), TabStationActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("bus", buslist.get(0));
						nextIntent.putExtras(bundle);
						startActivity(nextIntent);
					}
				});
				
				params1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				
				 Log.i(TAG, "z值~~~~~~~"+z);
				 
				 
				 if(temp_list1.get(z).getLine().length()>10 && position1==0){
						if(k==0){
							//params.addRule(RelativeLayout.BELOW, TextStartID + k);
						}else{
							params1.addRule(RelativeLayout.BELOW, TextEndID + z - 1);
						}
						position1 = 0;
					}else if(temp_list1.get(z).getLine().length()>10 && position1==1){
						params1.addRule(RelativeLayout.BELOW, TextEndID + z - 1);
						position1 = 0;
					}else if(temp_list1.get(z).getLine().length()<10 && position1==0){
						params1.addRule(RelativeLayout.BELOW, TextEndID + z - 1);
						position1 = 1;
					}else{
						params1.addRule(RelativeLayout.RIGHT_OF, TextEndID + z - 1);
						params1.addRule(RelativeLayout.BELOW, TextEndID + z - 2);
						position1 = 0;
					}
					
				
				line_Text.setLayoutParams(params1);

				relalayout1.addView(line_Text,params1);
				z++;
				
			}else{
				if(!line1.equals(temp_list1.get(z-1).getLine())){
					temp_list1.add(new Bus(line1, null, null));
					String line2_stopCount = exchange_list.get(i).getLine2_StopCount();
					
					String content = "";
					content = line1+"("+line2_stopCount+"站)/";
					
					Log.i(TAG, "我走到这了~~~~~~~~~~k:"+z);
					TextView line_Text = new TextView(this);
					SpannableString spannableString1 = new SpannableString(content);
					spannableString1.setSpan(new ForegroundColorSpan(Color.BLUE), 0, exchange_list.get(i).getLine2().length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
					
					
					
					line_Text.setText(spannableString1);
					
					line_Text.setId(TextEndID+z);
					
					
					Log.i(TAG, k+"TextView值:"+(TextEndID+z));
					line_Text.setTextSize(16);
					line_Text.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							int x = v.getId() - TextEndID;
							//int w = v.getId();
							Log.i(TAG, "X is ~~~~~~~~~~~"+x);
							BusService busService = new BusService(getApplicationContext());
							Log.i(TAG, "83~~~:"+temp_list1.get(x).getLine());
							List<Bus> buslist = busService.findBus_exact(temp_list1.get(x).getLine());
							Intent nextIntent = new Intent(getApplicationContext(), TabStationActivity.class);
							Bundle bundle = new Bundle();
							bundle.putSerializable("bus", buslist.get(0));
							nextIntent.putExtras(bundle);
							startActivity(nextIntent);
						}
					});
					
					
					
					 params1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					
					 Log.i(TAG, "z值~~~~~~~"+z);
					 
					 
					 if(temp_list1.get(z).getLine().length()>10 && position1==0){
							if(z==0){
								//params.addRule(RelativeLayout.BELOW, TextStartID + k);
							}else{
								params1.addRule(RelativeLayout.BELOW, TextEndID + z - 1);
							}
							position1 = 0;
						}else if(temp_list1.get(z).getLine().length()>10 && position1==1){
							params1.addRule(RelativeLayout.BELOW, TextEndID + z - 1);
							position1 = 0;
						}else if(temp_list1.get(z).getLine().length()<10 && position1==0){
							params1.addRule(RelativeLayout.BELOW, TextEndID + z - 1);
							position1 = 1;
						}else{
							params1.addRule(RelativeLayout.RIGHT_OF, TextEndID + z - 1);
							params1.addRule(RelativeLayout.BELOW, TextEndID + z - 2);
							position1 = 0;
						}
						
					
					line_Text.setLayoutParams(params1);

					relalayout1.addView(line_Text,params1);
					z++;
				}
			}
		}
		int sub1 =0;
		if((z-1)==0){
			sub1 = 3;
		}
		TextView last_Text1 = (TextView)relalayout1.findViewById(TextEndID+z-1);
		String text_str1 = last_Text1.getText().toString();
		String last_str1 = text_str1.replace("/", " 至");
		Log.i(TAG, "last_str1 is :"+last_str1);
		SpannableString spannable_last1 = new SpannableString(last_str1);
		spannable_last1.setSpan(new ForegroundColorSpan(Color.BLUE), sub1, temp_list1.get(z-1).getLine().length()+sub1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		last_Text1.setText(spannable_last1);
		
		
		
		
		
		
		
		
		
		

		
		
	}
	
	private void init() {
		startStationText = (TextView) this.findViewById(R.id.startStation);
		exchangeStationText = (TextView) this.findViewById(R.id.exchangeStation);
		endStationText = (TextView) this.findViewById(R.id.endStation);
		titleText = (TextView) this.findViewById(R.id.title_text);
		
		DisplayMetrics dm = new DisplayMetrics();
		// 取得窗口属性
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		// 窗口的宽度
		screenWidth = dm.widthPixels;

		// 窗口高度
		//screenHeight = dm.heightPixels;
		
		
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
