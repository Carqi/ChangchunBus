package com.carqi.ccbus.adapter;

import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.carqi.ccbus.activity.R;
import com.carqi.ccbus.data.BusStation;

public class StationAdapter implements ListAdapter {
	private List<BusStation> list;
	/** 实例及其对应的视图布局的XML文件 */
	private LayoutInflater layoutInflater;
	public StationAdapter(Context context, List<BusStation> list) {
		this.list = list;
		//layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater = LayoutInflater.from(context);
	}
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			// 加载布局
			convertView = layoutInflater.inflate(R.layout.stationadapter, null);
			// 设置布局内容
			TextView tv_1 = (TextView) convertView.findViewById(R.id.bus_station);
			TextView tv_2 = (TextView) convertView.findViewById(R.id.bus_station_no);
			
			//list.size() -  Log.i("Here", "position"+String.valueOf(position)+"----�ߵ����ˣ�" + list.get(position).getStation());
			tv_1.setText(list.get(position).getStation());
			tv_2.setText(String.valueOf(position+1));
		}
		return convertView;
	}

	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return true;
	}
	/**
	 * true所有项目可选择可点击
	 */
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	/**
	 * 是否显示分割线
	 */
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return true;
	}

}
