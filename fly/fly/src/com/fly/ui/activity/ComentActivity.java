package com.fly.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fly.R;
import com.fly.app.FlyApplication;
import com.fly.sdk.Comment;
import com.fly.sdk.ErrorMsg;
import com.fly.sdk.FlyProduct;
import com.fly.sdk.Product;
import com.fly.sdk.School;
import com.fly.sdk.User;
import com.fly.sdk.job.CommentCreate;
import com.fly.sdk.job.GetCommentLists;
import com.fly.sdk.threading.FlyTaskManager.ResultCallback;
import com.fly.sdk.util.TextUtils;
import com.fly.ui.dialog.LoadDialog;
import com.fly.ui.view.CommentItemView;

public class ComentActivity extends BaseActivity implements OnRefreshListener {

	private Button sendBt;
	private SwipeRefreshLayout swiptRefreshLayout;
	private LinearLayout commentContainer;
	private EditText editText;
	private FlyProduct product;
	private int currnetPage = 1;
	private boolean  comviewRight = true ;
	
	private ArrayList<Comment> items = new ArrayList<Comment>();
	private  LinearLayout.LayoutParams itemPara;
	private LoadDialog  loadDialog ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pinglun_layouy);
		initData();
		initView();
		loadNetComment();
	}

	private void initView() {
		View title = findViewById(R.id.title);
		title.setVisibility(View.GONE);
		View backView = findViewById(R.id.back_img);
		backView.setOnClickListener(this);
		View shareView = findViewById(R.id.share_img);
		shareView.setVisibility(View.INVISIBLE);

		sendBt = (Button) findViewById(R.id.send_bt);
		sendBt.setOnClickListener(this);
		
		swiptRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
		swiptRefreshLayout.setOnRefreshListener(this);
		swiptRefreshLayout.setColorScheme(R.color.holo_green_dark,
				R.color.holo_green_light, R.color.holo_orange_light,
				R.color.holo_red_light);
		commentContainer = (LinearLayout) findViewById(R.id.pl_con);
		editText = (EditText) findViewById(R.id.edit_ev);
		Options opt = new Options();
		opt.inJustDecodeBounds = true ;	
		BitmapFactory.decodeResource(getResources(),R.drawable.pinlun_et_bg_0, opt);
		editText.setMaxWidth(opt.outWidth);
		editText.setMinWidth(opt.outWidth);
	
	}

	private void loadNetComment() {
		if (product != null) {
		
			GetCommentLists	getCommet = new GetCommentLists(currnetPage,
						(int) product.getId(), product instanceof School);
		 
			if(loadDialog == null)
			{
				loadDialog = new LoadDialog(this).builder().setMessage("ÕýÔÚ¼ÓÔØ...");
			}
//			loadDialog.show();
			taskManager.commitJob(getCommet, new ResultCallback() {

				@Override
				public void notifyResult(Object result) {
					dealwithCommintResult(result);
				}
			});
			
		}
	}

	private void dealwithCommintResult(Object result)
	{
		// TODO Auto-generated method stub
		if (result != null) {
			if (result instanceof List) {
				boolean hasNew = false;
				int size = ((List<Comment>) result).size();
				if (size > 0) {
					currnetPage++;
				}
				ArrayList<Comment> newComs = new ArrayList<Comment>();
				for (Comment com : (List<Comment>) result) {
					if (!items.contains(com)) {
						items.add(com);
						newComs.add(com);
						hasNew = true;
					}
				}
				if (hasNew)
				{
					uiHandler.obtainMessage(0, newComs).sendToTarget();
				}else
				{
					uiHandler.obtainMessage(0).sendToTarget();
				}
				

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
	public void handleUIHandlerMessage(Message msg) {
		swiptRefreshLayout.setRefreshing(false);
		if(loadDialog != null)
		{
			loadDialog.dismiss();
		}
		switch (msg.what) {
		case 0:
			editText.setText("");
			addCommentView((List<Comment>)msg.obj);
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

	public void clickView(View v) {
		switch (v.getId()) {
		case R.id.back_img:
			finish();
		case R.id.send_bt: {
			String comStr = editText.getText().toString();
			if (TextUtils.isEmpty(comStr)) {
				Toast.makeText(this, R.string.empty_comment, Toast.LENGTH_SHORT)
						.show();
				return;
			}
			sendComment(comStr);
		}
			break;
		}
	}

	private void sendComment(String text) {
          if(TextUtils.isEmpty(text))
          {
        	  return ;
          }
          User user = FlyApplication.getLoginUser();
          if(user != null && product != null)
          {
             CommentCreate createCom = new CommentCreate((int) product.getId(), 
            		 user.getEmail(), user.getUserToken(), text, product instanceof School) ;
             taskManager.commitJob(createCom, new ResultCallback() {
				@Override
				public void notifyResult(Object result) {
					// TODO Auto-generated method stub
					dealwithCommintResult(result);
				}
			});
					
          }
	}
	   private void addCommentView(List<Comment> coms)
	   {
		   if(coms == null || coms.isEmpty())
		   {
			   return ;
		   }
		   if(itemPara == null)
		    {
		    	itemPara =  new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 
			    		LayoutParams.WRAP_CONTENT);
				itemPara.topMargin = 30 ; 
				itemPara.gravity = Gravity.CENTER_HORIZONTAL;
		    }
		   CommentItemView view = null ;
		   for(Comment com:coms)
		   {
			   if(comviewRight)
			   {
				    view = new CommentItemView(this, comviewRight);
				    view.setComment(com);
				   comviewRight = false ;
				 
			   }else
			   {
				    view = new CommentItemView(this, comviewRight);
				    view.setComment(com);
				    comviewRight = true ;
			   }
			   commentContainer.addView(view, itemPara);
		   }
	   }

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		loadNetComment();
	}

	private void initData() {
		Intent intent = getIntent();
		product = (FlyProduct) intent.getSerializableExtra("product");
	}
}
