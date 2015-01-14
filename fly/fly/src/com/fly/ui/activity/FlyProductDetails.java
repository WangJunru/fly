package com.fly.ui.activity;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.fly.R;
import com.fly.sdk.ErrorMsg;
import com.fly.sdk.FlyProduct;
import com.fly.sdk.Notice;
import com.fly.sdk.Order;
import com.fly.sdk.Product;
import com.fly.sdk.ProductBanner;
import com.fly.sdk.School;
import com.fly.sdk.SchoolPanner;
import com.fly.sdk.SdkConfig;
import com.fly.sdk.job.GetNoticeDetail;
import com.fly.sdk.job.GetProductDetails;
import com.fly.sdk.job.GetSchoolDetails;
import com.fly.sdk.job.Job;
import com.fly.sdk.threading.FlyTaskManager.ResultCallback;
import com.fly.sdk.util.TextUtils;
import com.fly.ui.dialog.LoadDialog;

public class FlyProductDetails extends BaseActivity {

	private TextView comentCountsTv;
	private TextView moneyNumberTv;
	private TextView userActionTipTv;
	private TextView title;

	private View bottomView;
	private View sharedView;

	private WebView mWebView;

	private Object product;
	private Job flyTask;
	private LoadDialog dialog;
	private SwipeRefreshLayout swiptLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fly_product_details_layout);
		initData();
		intiView();
	}

	private void intiView() {
		bottomView = findViewById(R.id.bootom_view);
		comentCountsTv = (TextView) findViewById(R.id.show_comments);
		moneyNumberTv = (TextView) findViewById(R.id.money_tv);
		userActionTipTv = (TextView) findViewById(R.id.conn_us_tv);
		comentCountsTv.setOnClickListener(this);
		userActionTipTv.setOnClickListener(this);
		View backView = findViewById(R.id.back_img);
		backView.setOnClickListener(this);
		sharedView = findViewById(R.id.share_img);
		sharedView.setOnClickListener(this);
		// title = (TextView)findViewById(R.id.title);
		swiptLayout = (SwipeRefreshLayout) findViewById(R.id.mid_pan_swipt);
		swiptLayout.setColorScheme(R.color.holo_green_dark,
				R.color.holo_green_light, R.color.holo_orange_light,
				R.color.holo_red_light);
		swiptLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				loadFlyProductDetailsContent(product);
			}
		});
		swiptLayout.setRefreshing(true);

		mWebView = (WebView) findViewById(R.id.content_web_view);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.getSettings().setDefaultTextEncodingName(
				SdkConfig.HTTP_ENCODING);
		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				swiptLayout.setRefreshing(false);
			}

		});
		updateView(product);
	}

	private void updateView(Object product) {
		if (product != null) {
			String htmlCode = null;
			if (product instanceof School) {
				htmlCode = ((School) product).getPageHtmlCode();
			} else if (product instanceof Product) {
				htmlCode = ((Product) product).getPageHtmlCode();
			} else if (product instanceof Notice) {
				htmlCode = ((Notice) product).getPageHtmlCode();
			}
			if (!TextUtils.isEmpty(htmlCode)) {
				mWebView.loadData("<body>" + htmlCode + "</body>",
						"text/html; charset=UTF-8", null);
			}
			if (product instanceof School) {
				userActionTipTv.setText(R.string.conn_us);
				moneyNumberTv.setVisibility(View.GONE);
				comentCountsTv.setText(getString(R.string.coment_count,
						((FlyProduct) product).getCommentCount()));
			} else if (product instanceof Product) {
				userActionTipTv.setText(R.string.order_product);
				moneyNumberTv.setVisibility(View.VISIBLE);
				DecimalFormat format = new DecimalFormat("0");
				moneyNumberTv.setText(getString(R.string.money_number,
						format.format(((Product) this.product).getPrice())));
				comentCountsTv.setText(getString(R.string.coment_count,
						((FlyProduct) product).getCommentCount()));
			} else if (product instanceof Order) {
				comentCountsTv.setVisibility(View.INVISIBLE);
				bottomView.setVisibility(View.GONE);
			} else if (product instanceof Notice) {
				comentCountsTv.setVisibility(View.INVISIBLE);
				bottomView.setVisibility(View.GONE);
				sharedView.setVisibility(View.GONE);
				// title.setText(((Notice)product).getTitle());
			}
		}
	}

	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字
//		oks.setNotification(R.drawable.ic_launcher,
//				getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.app_name));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://www.mfeiji.com/");
		// text是分享文本，所有平台都需要这个字段
		oks.setText(((FlyProduct)product).getAbstractString());
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//		oks.setImagePath("/sdcard/test.jpg");// 确保SDcard下面存在此张图片
		
		oks.setImageUrl(((FlyProduct)product).getFirstImageUrl());
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://www.mfeiji.com/");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
//		oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://www.mfeiji.com/");

		// 启动分享GUI
		oks.show(this);
	}

	public void clickView(View v) {
		switch (v.getId()) {
		case R.id.back_img: {
			this.finish();
		}
			break;
		case R.id.share_img: {
			showShare();
		}
			break;
		case R.id.conn_us_tv: {
			if (product != null) {
				String telNumber = ((FlyProduct) product).getTel();
				if (!TextUtils.isEmpty(telNumber)) {
					Intent intent = new Intent(Intent.ACTION_CALL,
							Uri.parse("tel:" + telNumber));
					startActivity(intent);
				}
			}
		}
			break;
		case R.id.show_comments: {
			Intent intent = new Intent(this, ComentActivity.class);
			intent.putExtra("product", (FlyProduct) product);
			startActivity(intent);
		}
			break;
		}
	}

	private void loadFlyProductDetailsContent(Object product) {
		if (product == null)
			return;
		if (product instanceof School || product instanceof SchoolPanner) {
			flyTask = new GetSchoolDetails(((FlyProduct) product).getId());
		} else if (product instanceof Product
				|| product instanceof ProductBanner) {
			flyTask = new GetProductDetails(((Product) product).getId());
		} else if (product instanceof Order) {
			flyTask = new GetProductDetails(((Order) product).getProductId());
		} else if (product instanceof Notice) {
			flyTask = new GetNoticeDetail(((Notice) product).getId());
		}
		if (flyTask != null) {
			dialog = new LoadDialog(this).builder().setCancelable(true)
					.setCanceledOnTouchOutside(true).setMessage("正在加载内容...");
			// dialog.show();
			taskManager.commitJob(flyTask, new ProductDetailResultCapture());
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loadFlyProductDetailsContent(product);
	}

	private final class ProductDetailResultCapture implements ResultCallback {

		@Override
		public void notifyResult(Object result) {
			// TODO Auto-generated method stub
			if (result != null) {
				if (result instanceof List) {
					List list = (List) result;
					if (list.size() > 0) {
						uiHandler.obtainMessage(0, list.get(0)).sendToTarget();
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
	}

	@Override
	protected void handleUIHandlerMessage(Message msg) {
		// TODO Auto-generated method stub
		if (dialog != null)
			dialog.dismiss();
		swiptLayout.setRefreshing(false);
		switch (msg.what) {
		case 0: {
			Object obj = msg.obj;
			if (obj != null) {
			    product = obj;
				updateView(obj);
			}
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
		}
	}

	private void initData() {
		Intent intent = getIntent();
		product = intent.getSerializableExtra("product");
	}
}
