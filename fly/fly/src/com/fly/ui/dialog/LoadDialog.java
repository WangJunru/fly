package com.fly.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fly.R;

public class LoadDialog {
     
	private Dialog  dialog ;
	private TextView message ;
	private Context context ;
	
    public LoadDialog(Context context)
    {
       this.context = context ;
    }
    
    public LoadDialog builder()
    {
    	View  dlgView = LayoutInflater.from(context).inflate(R.layout.login_tip_dialog_layout, null);
    	message = (TextView)dlgView.findViewById(R.id.login_tip);
    	// 定义Dialog布局和参数
		dialog = new Dialog(context, R.style.AlertDialogStyle);
		dialog.setContentView(dlgView);
		return this ;
    }
    
    public LoadDialog setMessage(String messge)
    {
    	message.setText(messge);
    	return this ;
    }
    public LoadDialog setMessage(int messge)
    {
    	message.setText(messge);
    	return this ;
    }
    
    public void show()
    {
    	dialog.show();
    }
    
    public LoadDialog setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
		return this;
	}
	public LoadDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}
	
	public void dismiss()
	{
		dialog.dismiss();
	}
	
	public void cancel()
	{
		dialog.cancel();
	}
    
}
