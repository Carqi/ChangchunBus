package com.carqi.ccbus.activity;

import java.util.HashMap;
import java.util.List;

import com.carqi.ccbus.data.BusStation;
import com.carqi.ccbus.service.BusService;
import com.carqi.ccbus.utils.MyLetterListView;
import com.carqi.ccbus.utils.MyLetterListView.OnTouchingLetterChangedListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 城市列表
 * 
 * @author sy
 * 
 */
public class AllStationActivity extends Activity{
	
	//private static final String TAG = "AllStationActivity";
	public static int RESULT = 1;
	public static int REQUEST = 0;
	private BaseAdapter adapter;
	private ListView listView;
	private TextView overlay;
	private TextView guidestation;
	private Button back_btn;
	private MyLetterListView letterListView;
	private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
	private String[] sections;// 存放存在的汉语拼音首字母
	private Handler handler;
	private OverlayThread overlayThread;
	private List<BusStation> allstalist;
	private String title_text;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.allstation_list);
		back_btn = (Button) this.findViewById(R.id.title_back);
		back_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(RESULT);
				finish();
			}
		});



		listView = (ListView) findViewById(R.id.city_list);
		letterListView = (MyLetterListView) findViewById(R.id.cityLetterListView);

		
		
		
		
		
		guidestation = (TextView) findViewById(R.id.title_text);
		title_text = getIntent().getExtras().getString("guide");
		guidestation.setText(title_text);
		
		
		
		
		
		BusService busService = new BusService(getApplicationContext());
		allstalist = busService.getAllStations();
		
		letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
		alphaIndexer = new HashMap<String, Integer>();
		handler = new Handler();
		overlayThread = new OverlayThread();
		initOverlay();
		setAdapter(allstalist);
		listView.setOnItemClickListener(new ListOnItemClick());

	}


	/**
	 * 城市列表点击事件
	 * 
	 * @author sy
	 * 
	 */
	class ListOnItemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
				long arg3) {
			BusStation busStation = (BusStation) listView.getAdapter().getItem(pos);
			Intent intent = new Intent();
			if(title_text.equals("选择起点")){
				intent.putExtra("startStation", busStation.getStation());
				intent.putExtra("guide", "start");
			}else{
				intent.putExtra("endStation", busStation.getStation());
				intent.putExtra("guide", "end");
			}
			setResult(30, intent);
			finish();
		}

	}

	/**
	 * 为ListView设置适配器
	 * 
	 * @param list
	 */
	private void setAdapter(List<BusStation> list)
	{
		if (list != null)
		{
			adapter = new ListAdapter(this, list);
			listView.setAdapter(adapter);
		}

	}

	/**
	 * ListViewAdapter
	 * 
	 * @author sy
	 * 
	 */
	private class ListAdapter extends BaseAdapter
	{
		private LayoutInflater inflater;
		private List<BusStation> list;

		public ListAdapter(Context context, List<BusStation> list)
		{

			this.inflater = LayoutInflater.from(context);
			this.list = list;
			alphaIndexer = new HashMap<String, Integer>();
			sections = new String[list.size()];

			for (int i = 0; i < list.size(); i++)
			{
				// 当前汉语拼音首字母
				// getAlpha(list.get(i));
				String currentStr = list.get(i).getFristLetter().substring(0, 1);
				// 上一个汉语拼音首字母，如果不存在为“ ”
				String previewStr = (i - 1) >= 0 ? list.get(i - 1).getFristLetter().substring(0, 1) : " ";
				if (!previewStr.equals(currentStr))
				{
					String name = list.get(i).getFristLetter().substring(0, 1);
					alphaIndexer.put(name, i);
					sections[i] = name;
				}
			}

		}

		@Override
		public int getCount()
		{
			return list.size();
		}

		@Override
		public Object getItem(int position)
		{
			return list.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder;
			if (convertView == null)
			{
				convertView = inflater.inflate(R.layout.list_item, null);
				holder = new ViewHolder();
				holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				convertView.setTag(holder);
			} else
			{
				holder = (ViewHolder) convertView.getTag();
			}

			holder.name.setText(list.get(position).getStation());
			String currentStr = list.get(position).getFristLetter().substring(0, 1);
			String previewStr = (position - 1) >= 0 ? list.get(position - 1).getFristLetter().substring(0, 1) : " ";
			if (!previewStr.equals(currentStr))
			{
				holder.alpha.setVisibility(View.VISIBLE);
				holder.alpha.setText(currentStr);
			} else
			{
				holder.alpha.setVisibility(View.GONE);
			}
			return convertView;
		}

		private class ViewHolder
		{
			TextView alpha;
			TextView name;
		}

	}

	// 初始化汉语拼音首字母弹出提示框
	private void initOverlay()
	{
		LayoutInflater inflater = LayoutInflater.from(this);
		overlay = (TextView) inflater.inflate(R.layout.overlay, null);
		overlay.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				PixelFormat.TRANSLUCENT);
		WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(overlay, lp);
	}

	private class LetterListViewListener implements OnTouchingLetterChangedListener
	{

		@Override
		public void onTouchingLetterChanged(final String s)
		{
			if (alphaIndexer.get(s) != null)
			{
				int position = alphaIndexer.get(s);
				listView.setSelection(position);
				overlay.setText(sections[position]);
				overlay.setVisibility(View.VISIBLE);
				handler.removeCallbacks(overlayThread);
				// 延迟一秒后执行，让overlay为不可见
				handler.postDelayed(overlayThread, 1500);
			}
		}

	}

	// 设置overlay不可见
	private class OverlayThread implements Runnable
	{

		@Override
		public void run()
		{
			overlay.setVisibility(View.GONE);
		}

	}

}