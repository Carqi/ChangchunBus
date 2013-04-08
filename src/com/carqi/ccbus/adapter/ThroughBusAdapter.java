package com.carqi.ccbus.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.carqi.ccbus.activity.R;
import com.carqi.ccbus.data.BusExchange;

public class ThroughBusAdapter extends BaseAdapter {
	private List<BusExchange> list;
	private int[] stationNum;
	/** 实例及其对应的视图布局的XML文件 */
	private LayoutInflater layoutInflater;
	public ThroughBusAdapter(Context context, List<BusExchange> list, int[] stationNum) {
		this.list = list;
		this.stationNum = stationNum;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView lineText = null;
		TextView plan_noText = null;
		TextView stanumText = null;
		TextView exchagne_roleText = null;
		if(convertView == null){
			// 加载布局
			convertView = layoutInflater.inflate(R.layout.exchangeadapter, null);
			// 设置布局内容
			lineText = (TextView) convertView.findViewById(R.id.bus_line);
			plan_noText = (TextView) convertView.findViewById(R.id.plan_no);
			stanumText = (TextView) convertView.findViewById(R.id.stanum);
			exchagne_roleText = (TextView) convertView.findViewById(R.id.exchange_role);
			ViewCache cache = new ViewCache();
			cache.lineText = lineText;	
			cache.plan_noText = plan_noText;	
			cache.stanumText = stanumText;	
			cache.exchagne_roleText = exchagne_roleText;	
			convertView.setTag(cache);
		}else{
			ViewCache cache = (ViewCache) convertView.getTag();
			lineText = cache.lineText;
			plan_noText = cache.plan_noText;
			stanumText = cache.stanumText;
			exchagne_roleText = cache.exchagne_roleText;
		}
		BusExchange bus = list.get(position);
		//下面代码实现数据绑定
		lineText.setText(bus.getLine1());
		plan_noText.setText("方案"+(position+1));
		stanumText.setText(String.valueOf(Math.abs(stationNum[position])));
		exchagne_roleText.setText("直达  共");
		return convertView;
	}
	private final class ViewCache{
		public TextView lineText;
		public TextView plan_noText;
		public TextView stanumText;
		public TextView exchagne_roleText;
	}

}
