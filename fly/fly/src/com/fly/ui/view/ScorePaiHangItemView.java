package com.fly.ui.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fly.R;
import com.fly.sdk.Score;

public class ScorePaiHangItemView extends FrameLayout implements OnClickListener {

	private RoundImageView  userPicView ;
	private TextView        userNameTv ;
	private TextView  userScoreTv ;
	private TextView  dateTimeTv ;
	
	private Context context ;
	private Score score ;
	private OnUserPicClickListener clickListener ;
	
	public ScorePaiHangItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context ;
		initView();
	}
	public ScorePaiHangItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context ;
		initView();
	}
	public ScorePaiHangItemView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context ;
		initView();
	}
	
	private void initView()
	{
		LayoutInflater.from(context).inflate(R.layout.paihang_bang_item_layout, this);
		userPicView = (RoundImageView)findViewById(R.id.ph_user_picture_round);
		userPicView.setOnClickListener(this);
		
		userNameTv  = (TextView)findViewById(R.id.user_name_tv);
		userScoreTv = (TextView)findViewById(R.id.user_score_iv);
		dateTimeTv  = (TextView)findViewById(R.id.user_score_time_iv);
	}
	
	public void setScore(Score score)
	{
		if(score == null)
			return ;
		
		this.score = score ;
		Options opt = new Options();
		opt.inJustDecodeBounds = true ;
		
		BitmapFactory.decodeResource(getResources(),R.drawable.phb_default_uer_pic, opt);
		userPicView.setNetIamgeUrl(score.getUserUrlPic(), 
				R.drawable.phb_default_uer_pic, opt.outWidth, opt.outHeight);
		userNameTv.setText(score.getUserName());
		userScoreTv.setText(context.getString(R.string.score_str, score.getScore()));
		String[] times = score.getCreateTime().split("T");
		if(times.length == 2)
		{
		   dateTimeTv.setText(times[0]);
		}
	}
	
	public Score  getScore()
	{
		return this.score ;
	}
	
	public void setUserPicClickListener(OnUserPicClickListener click)
	{
		this.clickListener = click ;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		   case R.id.user_picture_round:
		   {
			   if(clickListener != null)
			   {
				   clickListener.clickUserPic(this, this.score);
			   }
		   }break;
			 
		}
	}
	
	public interface OnUserPicClickListener
	{
		public void  clickUserPic(View v,Score score);	
	}

}
