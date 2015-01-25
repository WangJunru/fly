package com.fly.ui.activity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
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
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

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
import com.fly.util.Tools;

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
	private String localProducPictPath ;

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
//		mWebView.getSettings().setBuiltInZoomControls(true);
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

	private void showShare(boolean silent, String platform, boolean captureView) {
		Context context = getBaseContext();
		final OnekeyShare oks = new OnekeyShare();
		oks.setTitle(((FlyProduct)product).getAbstractString());
		oks.setTitleUrl("http://www.mfeiji.com/");
	    oks.setText(((FlyProduct)product).getAbstractString());
		if (captureView) {
			oks.setViewToShare(mWebView);
		} else {
//			oks.setImagePath(localProducPictPath);
			
			oks.setImageUrl(((FlyProduct)product).getFirstImageUrl());
		}
		oks.setUrl("http://www.mfeiji.com/");
//		oks.setFilePath(localProducPictPath);
		oks.setSite(context.getString(R.string.app_name));
		oks.setSiteUrl("http://www.mfeiji.com/");
		oks.setComment("来，说两句");
		oks.setSilent(silent);
		oks.setTheme(OnekeyShareTheme.CLASSIC);
		if (platform != null) {
			oks.setPlatform(platform);
		}
    	oks.setDialogMode();
		// 为EditPage设置一个背景的View
		oks.setEditPageBackground(mWebView);
		oks.setInstallUrl("http://www.mfeiji.com/");
		oks.show(context);
	}


	
	public void clickView(View v) {
		switch (v.getId()) {
		case R.id.back_img: {
			this.finish();
		}
		   break;
		case R.id.share_img: {
			 showShare(true, null, false);
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
						Object obj = list.get(0);
						uiHandler.obtainMessage(0, obj).sendToTarget();
						if(obj instanceof FlyProduct)
						{
							try {
								URL url = new URL(((FlyProduct)obj).getFirstImageUrl());
								URLConnection urlConn = url.openConnection();
								Bitmap  pic = BitmapFactory.decodeStream(urlConn
											.getInputStream());
								if(pic != null )
								{
									pic = Tools.compressImage(pic,30);
									localProducPictPath = cn.sharesdk.framework.utils.R.getCachePath(FlyProductDetails.this, null);
								    localProducPictPath = localProducPictPath + ((FlyProduct)obj).getId()+".jpg";
									FileOutputStream fos = new FileOutputStream(localProducPictPath);
									pic.compress(CompressFormat.JPEG, 100, fos);
									fos.flush();
									fos.close();
								}
							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
//						localProductPath
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
