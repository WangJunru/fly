package com.fly.ui.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fly.R;
import com.fly.sdk.Comment;

public class CommentItemView  extends FrameLayout {

	private boolean isRight ;
	private Context context ;
	private RoundImageView userPic ;
	private TextView userName ;
	private TextView commTv ;
	private TextView dateTv ;
	private static int picWidth , picHeight ;
	
	public CommentItemView(Context context,boolean isRight) {
		super(context);
		// TODO Auto-generated constructor stub
		this.isRight = isRight ;
		this.context = context ;
		initView(isRight);
	}
	
	public CommentItemView(Context context, AttributeSet attrs,boolean isRight) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.isRight = isRight ;
		this.context = context ;
		initView(this.isRight);
	}
	public CommentItemView(Context context, AttributeSet attrs, int defStyleAttr,boolean isRight) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		this.isRight = isRight ;
		this.context = context ;
		initView(this.isRight);
	}
	
    private void initView(boolean right)
    {
    	LayoutInflater.from(context).inflate(right?R.layout.commet_view_right:R.layout.commet_view_left,
    			this);
    	userName = (TextView)findViewById(R.id.user_name_tv);
    	commTv   = (TextView)findViewById(R.id.comment_tv);
    	dateTv   = (TextView)findViewById(R.id.date_tv);
    	userPic  =  (RoundImageView)findViewById(R.id.ph_user_picture_round);
    }
    
    public void setComment(Comment com)
    {
    	if(com == null)
    		return ;
    	if(picWidth == 0 ||  picHeight == 0 )
		{
			Options opt = new Options();
			opt.inJustDecodeBounds = true ;	
			BitmapFactory.decodeResource(getResources(),R.drawable.phb_default_uer_pic, opt);
			picWidth = opt.outWidth;
			picHeight = opt.outHeight ;
		}
    	userPic.setNetIamgeUrl(com.getUserPicUrl(),
				R.drawable.phb_default_uer_pic, picWidth, picHeight);
    	
    	userName.setText(com.getUserName());
    	commTv.setText(com.getComment());
    	String[] times = com.getCreateAt().split("T");
		if(times.length == 2)
		{
			dateTv.setText(times[0]);
		}
    }
  
}
