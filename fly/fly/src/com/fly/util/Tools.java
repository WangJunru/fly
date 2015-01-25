package com.fly.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.fly.R;
import com.fly.app.FlyApplication;
import com.fly.sdk.util.TextUtils;

public class Tools {

	public static boolean isMobileNO(String mobiles) {
		if (TextUtils.isEmpty(mobiles)) {
			return false;
		}
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	public static boolean isEmail(String email) {
		if (TextUtils.isEmpty(email)) {
			return false;
		}
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}

	public static Bitmap compressImage(Bitmap image, int maxKByte) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		image.recycle();
		int options = 100;
		while (baos.toByteArray().length / 1024 > maxKByte) {
			baos.reset();
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);
			options -= 10;
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
		return bitmap;

	}

	public static Bitmap comp(Bitmap image, int newWidth, int newHeight) {

		if (image == null)
			return null;

		float width = image.getWidth(), height = image.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = 0, scaleHeight = 0;

		scaleWidth = (float) newWidth / width;
		scaleHeight = (float) newHeight / height;

		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap b = Bitmap.createBitmap(image, 0, 0, (int) width, (int) height,
				matrix, false);
		image.recycle();
		return b;/* compressImage(b); */// 压缩好比例大小后再进行质量压缩
	}

	public static void closeIO(Closeable... closeables) {
		if (null == closeables || closeables.length <= 0) {
			return;
		}
		for (Closeable cb : closeables) {
			try {
				if (null == cb) {
					continue;
				}
				cb.close();
			} catch (IOException e) {
				Debug.log.e("closeIO", "close IO ERROR...", e);
			}
		}
	}

	public static int getViewMeasuredHeight(View view) {
		// int height = view.getMeasuredHeight();
		// if(0 < height){
		// return height;
		// }
		calcViewMeasure(view);
		return view.getMeasuredHeight();
	}

	public static int getViewMeasuredWidth(View view) {
		// int width = view.getMeasuredWidth();
		// if(0 < width){
		// return width;
		// }
		calcViewMeasure(view);
		return view.getMeasuredWidth();
	}

	public static void calcViewMeasure(View view) {
		// int width =
		// View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
		// int height =
		// View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
		// view.measure(width,height);

		int width = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int expandSpec = View.MeasureSpec.makeMeasureSpec(
				Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
		view.measure(width, expandSpec);
	}

	public static float pixelToDp(Context context, float val) {
		float density = context.getResources().getDisplayMetrics().density;
		return val * density;
	}

	public static Drawable getDefaultUserPic(Context context) {
		Bitmap b = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.default_user_pic);
		Bitmap b2 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.app_icon);
		Bitmap userPic = Tools.comp(b2, b.getWidth(), b.getHeight());
		BitmapDrawable drawable = new BitmapDrawable(userPic);
		b.recycle();
		b2.recycle();
		return drawable;
	}
}
