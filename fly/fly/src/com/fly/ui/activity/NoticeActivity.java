package com.fly.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fly.R;
import com.fly.sdk.ErrorMsg;
import com.fly.sdk.Notice;
import com.fly.sdk.job.GetNotice;
import com.fly.sdk.threading.FlyTaskManager.ResultCallback;
import com.fly.ui.dialog.LoadDialog;
import com.fly.view.ui.utils.DialogUtils;

public class NoticeActivity extends BaseActivity implements OnRefreshListener, OnItemClickListener {

	private ListView listView;
	private SwipeRefreshLayout mSwipeLayout;
	private TextView emptyView;
	private ArrayList<Notice> items = new ArrayList<Notice>();
	private BaseAdapter adapter;
	private LoadDialog loadDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_layout);

		initView();

		mSwipeLayout.setRefreshing(true);
		
		loadNotice("");
	}

	private void initView()
      {
      	TextView title = (TextView)findViewById(R.id.title);
      	title.setText(R.string.my_notice);
      	View  back = findViewById(R.id.back_img);
      	back.setOnClickListener(this);
      	View shareView = findViewById(R.id.share_img);
      	shareView.setVisibility(View.INVISIBLE);
      	
      	emptyView = (TextView)findViewById(R.id.empty_tv);
      	mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);

		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(R.color.holo_green_dark,R.color.holo_green_light,
				R.color.holo_orange_light, R.color.holo_red_light);
		
      	listView = ( ListView ) findViewById( R.id.strech_view);
      	listView.setDivider(new ColorDrawable(getResources().getColor(R.color.notice_split_color)));
      	listView.setDividerHeight(2);
      	listView.setOnItemClickListener(this);
      	listView.setAdapter(this.adapter = new BaseAdapter() {
			
      		final class ViewHolder {
      			TextView title ;
      			TextView content ;
      			TextView date ;
      		}
      		private LayoutInflater  layin= LayoutInflater.from(NoticeActivity.this);
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				ViewHolder viewHolder ;
				if(convertView == null)
				{
					convertView = layin.inflate(R.layout.notice_item_layout, null);
					viewHolder  = new ViewHolder();
					viewHolder.title = (TextView)convertView.findViewById(R.id.title_tv);
					viewHolder.content = (TextView)convertView.findViewById(R.id.content_tv);
					viewHolder.date    = (TextView)convertView.findViewById(R.id.date_tv);
					convertView.setTag(R.id.view_holder, viewHolder);
				}else
				{
					viewHolder = (ViewHolder) convertView.getTag(R.id.view_holder);
				}
				Notice  notice = items.get(position);
				if(notice != null)
				{
					viewHolder.title.setText(notice.getTitle());
					viewHolder.content.setText(notice.getAbstractStr());
					viewHolder.date.setText(notice.getDateStr());
				}
				return convertView;
			}
			
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}
			
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return items.get(position);
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return items.size();
			}
		});     	
      }

	public void clickView(View v) {
		switch (v.getId()) {
		case R.id.back_img:
			finish();
			break;
		case R.id.empty_tv:
			loadNotice("");
			break;
		}
	}

	@Override
	protected void handleUIHandlerMessage(Message msg) {
		// TODO Auto-generated method stub
		mSwipeLayout.setRefreshing(false);
		if (loadDialog != null) {
			loadDialog.dismiss();
		}
		switch (msg.what) {
		case 0:
			
			if (items.isEmpty()) {
				emptyView.setVisibility(View.VISIBLE);
			} else {
				emptyView.setVisibility(View.GONE);
				adapter.notifyDataSetChanged();
			}
			break;
		case 1:
			Toast.makeText(this, R.string.request_timeout, Toast.LENGTH_SHORT)
					.show();
			break;
		case 2: {
			Toast.makeText(this, R.string.network_io_error, Toast.LENGTH_SHORT)
					.show();
		}
			break;
		}
	}

	
	private void loadNotice(String date) {
		GetNotice getNotice = new GetNotice(date);
		if (loadDialog == null) {
			loadDialog = DialogUtils.showLoadDlg(this, "正在请求数据...");
		} else {
			loadDialog.show();
		}
		taskManager.commitJob(getNotice, new ResultCallback() {

			@Override
			public void notifyResult(Object result) {
				// TODO Auto-generated method stub
				if (result != null) {
					if (result instanceof List) {
						for(Notice notice:(List<Notice>) result)
						{
							if(!items.contains(notice))
							{
								items.add(notice);
							}
						}
						uiHandler.sendEmptyMessage(0);
					} else if (result instanceof ErrorMsg) {
						ErrorMsg error = (ErrorMsg) result;
						switch (error.getErrorCode()) {
						case ErrorMsg.ERROR_EXECUTE_TIMEOUT:
							uiHandler.sendEmptyMessage(1);
							break;
						case ErrorMsg.ERROR_NETWORK_IO_ERROR:
							uiHandler.sendEmptyMessage(2);
							break;
						}
					}

				}
			}
		});
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

		 Log.e("xxx", Thread.currentThread().getName());
//		 UI Thread
		 loadNotice("");
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		 Intent intent = new Intent(this,FlyProductDetails.class);
		 intent.putExtra("product", items.get(arg2));
		 startActivity(intent);
	}

}
