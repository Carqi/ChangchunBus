package com.carqi.ccbus.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.carqi.ccbus.activity.R;
import com.carqi.ccbus.data.Bus;

public class BusAdapter extends BaseAdapter {
	private List<Bus> list;
	/** 实例及其对应的视图布局的XML文件 */
	private LayoutInflater layoutInflater;
	public BusAdapter(Context context, List<Bus> list) {
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
		TextView lineText = null;
		TextView introText = null;
		if(convertView == null){
			// 加载布局
			convertView = layoutInflater.inflate(R.layout.busadapter, null);
			// 设置布局内容
			lineText = (TextView) convertView.findViewById(R.id.bus_line);
			introText = (TextView) convertView.findViewById(R.id.bus_intro);
			ViewCache cache = new ViewCache();
			cache.lineText = lineText;	
			cache.introText = introText;		
			convertView.setTag(cache);
		}else{
			ViewCache cache = (ViewCache) convertView.getTag();
			lineText = cache.lineText;
			introText = cache.introText;
		}
		Bus bus = list.get(position);
		//下面代码实现数据绑定
		lineText.setText(bus.getLine());
		introText.setText(bus.getIntro());
		return convertView;
	}
	private final class ViewCache{
		public TextView lineText;
		public TextView introText;
	}

}
