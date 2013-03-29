package com.carqi.ccbus.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.carqi.ccbus.activity.R;
import com.carqi.ccbus.data.BusStation;

public class CommonStationAdapter extends BaseAdapter {
	private List<BusStation> list;
	/** 实例及其对应的视图布局的XML文件 */
	private LayoutInflater layoutInflater;
	public CommonStationAdapter(Context context, List<BusStation> list) {
		this.list = list;
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
		TextView commonText = null;
		if(convertView == null){
			// 加载布局
			convertView = layoutInflater.inflate(R.layout.common_dialog_adatper, null);
			// 设置布局内容
			commonText = (TextView) convertView.findViewById(R.id.common_text);
			ViewCache cache = new ViewCache();
			cache.commonText = commonText;		
			convertView.setTag(cache);
		}else{
			ViewCache cache = (ViewCache) convertView.getTag();
			commonText = cache.commonText;
		}
		
		BusStation station = list.get(position);
		//下面代码实现数据绑定
		commonText.setText(station.getStation());
	
		return convertView;
	}
	private final class ViewCache{
		public TextView commonText;
	}

}
