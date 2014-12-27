package com.fly.view.ui.utils;

import android.content.Context;

import com.fly.ui.dialog.LoadDialog;

public class DialogUtils {

	public static LoadDialog showLoadDlg(Context context, String str) {
		LoadDialog loadDig = new LoadDialog(context).builder();
		loadDig.setMessage(str);
		loadDig.show();
		return loadDig;
	}
}
