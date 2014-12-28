package com.fly.ui.view;

import java.util.Calendar;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fly.R;

/**
 * 本类旨在实现通用的弹性刷新效果。只要实现了IScrollOverable的view都可以据有弹性效果。上弹下弹均可以哦！
 */
public class BshElasticView extends LinearLayout
{
	final int RELEASE_H = 0;
	final int PULL = 1;
	final int REFRESHING_H = 2;
	final int PUSH = 3;
	final int RELEASE_F = 4;
	final int REFRESHING_F = 5;
	final int NORMAL = 6;
	int factor = 3;
	float maxElastic = 0.2f;
	int critical = 2;
	int maxElasticH, maxElasticF;
	boolean isTopRecored;
	boolean isBtmRecored = false;
	int startY;
	private IRefresh irefresh;
	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;
	int state = NORMAL;
	boolean isBack;

	
	View rv;
	RelativeLayout headerView, footerView, continer, main_continer;
	ProgressBar hPro, fPro;
	ImageView hArow, fArow;
	TextView tvTipH, tvTipF, tvLstH, tvLstF;
	Context ctx;
	private IScrollOverable overable;
	public int continerH, continerW, headerH, headerW, footerH, footerW, rvH,
			rvW;
	Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			switch ( msg.what )
			{
			case 0:
				Log.d( "bsh", "刷新完成，到normal状态" );
				state = NORMAL;
				changeFooterViewByState();
				break;
			}
		}

	};
	
	public void setIRefresh(IRefresh refresh)
	{
		this.irefresh = refresh;
	}

	public BshElasticView(Context context, AttributeSet attrs, int defStyle)
	{
		super( context, attrs );
		init( context );
	}

	public BshElasticView(Context context, AttributeSet attrs)
	{
		super( context, attrs );
		init( context );
	}

	public BshElasticView(Context context)
	{
		super( context );
		init( context );
	}

	public void setScrollOverable(IScrollOverable o)
	{
		overable = o;
		addView( ( View ) o );
	}

	public void init(Context ctx)
	{
		this.ctx = ctx;
		rv = View.inflate( ctx, R.layout.elastic_view, null );
		main_continer = ( RelativeLayout ) rv.findViewById( R.id.main_continer );

		headerView = ( RelativeLayout ) rv.findViewById( R.id.header );
		hPro = ( ProgressBar ) rv.findViewById( R.id.pro_h );
		hArow = ( ImageView ) rv.findViewById( R.id.iv_harow );
		tvTipH = ( TextView ) rv.findViewById( R.id.tv_htip );
		tvLstH = ( TextView ) rv.findViewById( R.id.tv_lst );
		measureViewHeight( headerView );
		headerH = headerView.getMeasuredHeight();
		headerW = headerView.getMeasuredWidth();
		footerView = ( RelativeLayout ) rv.findViewById( R.id.footer );
		fPro = ( ProgressBar ) rv.findViewById( R.id.pro_f );
		fArow = ( ImageView ) rv.findViewById( R.id.iv_farow );
		tvTipF = ( TextView ) rv.findViewById( R.id.tv_ftip );
		tvLstF = ( TextView ) rv.findViewById( R.id.tv_lstf );
		footerH = headerH;
		footerW = headerW;

		continer = ( RelativeLayout ) rv.findViewById( R.id.continer );
		addView( rv, new ViewGroup.LayoutParams( LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT ) );
		main_continer.setPadding( 0, -1 * headerH, 0, 0 );
		rv.invalidate();
		rv.requestLayout();

		animation = new RotateAnimation( 0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f );
		animation.setDuration( 500 );
		animation.setFillAfter( true );

		reverseAnimation = new RotateAnimation( -180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f );
		reverseAnimation.setDuration( 500 );
		reverseAnimation.setFillAfter( true );

		state = NORMAL;
	}

	private void measureViewHeight(View child)
	{
		ViewGroup.LayoutParams p = child.getLayoutParams();

		if ( null == p )
		{
			p = new ViewGroup.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.FILL_PARENT );
		}

		int childHeightSpec = ViewGroup
				.getChildMeasureSpec( 0, 0 + 0, p.height );
		int lpWidth = p.width;
		int childWidthSpec;
		if ( lpWidth > 0 )
		{
			childWidthSpec = MeasureSpec.makeMeasureSpec( lpWidth,
					MeasureSpec.EXACTLY );
		}
		else
		{
			childWidthSpec = MeasureSpec.makeMeasureSpec( 0,
					MeasureSpec.UNSPECIFIED );
		}
		child.measure( childWidthSpec, childHeightSpec );
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		super.onLayout( changed, l, t, r, b );
		rvH = b;
		rvW = r;
		footerH = headerH;
		footerW = headerW;
		continerH = rvH;
		continerW = rvW;
		maxElasticH = ( int ) (headerH + continerH * maxElastic);
		maxElasticF = 0 - maxElasticH;
		RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams( rvW,
				rvH );
		rl.addRule( RelativeLayout.BELOW, R.id.header );
		continer.setLayoutParams( rl );
		continer.requestLayout();
	}

	public interface IScrollOverable
	{
		public boolean isScrollOnTop();

		public boolean isScrollOnBtm();
	}

	public void addView(View v)
	{
		continer.addView( v, new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.FILL_PARENT ) );
	}

	public void changeHeaderViewByState()
	{
		switch ( state )
		{
		case RELEASE_H:
			hArow.setVisibility( View.VISIBLE );
			hPro.setVisibility( View.GONE );
			tvTipH.setVisibility( View.VISIBLE );
			tvLstH.setVisibility( View.VISIBLE );
			hArow.clearAnimation();
			hArow.startAnimation( animation );
			tvTipH.setText( "松开刷新" );
			Log.d( "bsh", "当前状态，松开刷新" );
			break;
		case PULL:
			hPro.setVisibility( View.GONE );
			tvTipH.setVisibility( View.VISIBLE );
			tvLstH.setVisibility( View.VISIBLE );
			hArow.clearAnimation();
			hArow.setVisibility( View.VISIBLE );
			if ( isBack )
			{
				isBack = false;
				hArow.clearAnimation();
				hArow.startAnimation( reverseAnimation );
			}
			tvTipH.setText( "下拉刷新" );
			Log.d( "bsh", "当前状态，上推刷新" );
			break;
		case REFRESHING_H:
			hPro.setVisibility( View.VISIBLE );
			hArow.clearAnimation();
			hArow.setVisibility( View.GONE );
			tvTipH.setText( "正在刷新..." );
			tvLstH.setVisibility( View.INVISIBLE );
			tvLstH.setText( "上次更新："
					+ Calendar.getInstance().getTime().toLocaleString() );
			Log.d( "bsh", "当前状态,正在刷新..." );
			break;
		case NORMAL:
			setRvPadding( -1 * headerH );
			hPro.setVisibility( View.GONE );
			hArow.clearAnimation();
			hArow.setImageResource( R.drawable.arrow_down );
			tvTipH.setText( "下拉刷新" );
			tvLstH.setVisibility( View.VISIBLE );
			Log.d( "bsh", "当前状态，normalf" );
			break;
		}
	}

	public void changeFooterViewByState()
	{
		switch ( state )
		{
		case RELEASE_F:
			fArow.setVisibility( View.VISIBLE );
			fPro.setVisibility( View.GONE );
			tvTipF.setVisibility( View.VISIBLE );
			tvLstF.setVisibility( View.VISIBLE );
			fArow.clearAnimation();
			fArow.startAnimation( animation );
			tvTipF.setText( "松开刷新" );
			Log.d( "bsh", "当前状态，松开刷新" );
			break;
		case PUSH:
			fPro.setVisibility( View.GONE );
			tvTipF.setVisibility( View.VISIBLE );
			tvLstF.setVisibility( View.VISIBLE );
			fArow.clearAnimation();
			fArow.setVisibility( View.VISIBLE );
			if ( isBack )
			{
				isBack = false;
				fArow.clearAnimation();
				fArow.startAnimation( reverseAnimation );
			}
			tvTipF.setText( "上推刷新" );
			Log.d( "bsh", "当前状态，上推刷新" );
			break;
		case REFRESHING_F:
			fPro.setVisibility( View.VISIBLE );
			fArow.clearAnimation();
			fArow.setVisibility( View.GONE );
			tvTipF.setText( "正在刷新..." );
			tvLstF.setVisibility( View.INVISIBLE );
			tvLstF.setText( "上次更新："
					+ Calendar.getInstance().getTime().toLocaleString() );
			Log.d( "bsh", "当前状态,正在刷新..." );
			break;
		case NORMAL:
			setRvPadding( -1 * headerH );
			fPro.setVisibility( View.GONE );
			fArow.clearAnimation();
			fArow.setImageResource( R.drawable.arrow_up );
			tvTipF.setText( "上推刷新" );
			tvLstF.setVisibility( View.VISIBLE );
			Log.d( "bsh", "当前状态，normalf" );
			break;
		}
	}

	public void setRvPadding(int padding)
	{
		Log.d( "bsh", "padding:" + padding );
		if ( padding < maxElasticF )
		{
			return;
		}
		else if ( padding > maxElasticH )
		{
			return;
		}

		main_continer.setPadding( 0, padding, 0, 0 );
		rv.requestLayout();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event)
	{
		switch ( event.getAction() )
		{
		case MotionEvent.ACTION_DOWN:
			startRecord( event );
			break;
		case MotionEvent.ACTION_UP:
			handleUpF();
			handleUpH();
			break;
		case MotionEvent.ACTION_MOVE:
			handleMove( event );
			break;
		}
		if ( state == NORMAL || state == REFRESHING_H || state == REFRESHING_F )
		{
			return super.dispatchTouchEvent( event );
		}
		else
			return true;
	}

	public void handleMove(MotionEvent event)
	{
		int tempY = ( int ) event.getY();
		tempY = tempY < 0 ? 0 : tempY;
		Log.d( "bsh", "get temp:" + tempY );
		startRecord( event );
		if ( state != REFRESHING_F && isBtmRecored )
		{
			handleMoveF( tempY );
		}
		else if ( state != REFRESHING_H && isTopRecored )
		{
			handleMoveH( tempY );
		}
	}

	public void handleMoveH(int tempY)
	{
		Log.d( "bsh", "release to refresh h" );
		if ( state == RELEASE_H )
		{
			Log.d( "bsh", "release to refresh h" );
			if ( ((tempY - startY) / factor < headerH * critical)
					&& (tempY - startY) > 0 )
			{
				state = PULL;
				changeHeaderViewByState();
				Log.d( "bsh", "由松开刷新状态转变到下拉刷新状态" );
			}
			else if ( startY - tempY > 0 )
			{
				state = NORMAL;
				changeHeaderViewByState();
				Log.d( "bsh", "由松开刷新状态转变到normal状态" );
			}
		}
		if ( state == PULL )
		{
			Log.d( "bsh", "push to refresh" );
			if ( (tempY - startY) / factor >= headerH * critical )
			{
				state = RELEASE_H;
				isBack = true;
				changeHeaderViewByState();
				Log.d( "bsh", "由normal或者下拉刷新状态转变到松开刷新" );
			}
			else if ( tempY - startY <= 0 )
			{
				state = NORMAL;
				changeHeaderViewByState();
				Log.d( "bsh", "由normal或者下拉刷新状态转变到normal状态" );
			}
		}

		if ( state == NORMAL )
		{
			if ( tempY - startY > 0 )
			{
				Log.d( "bsh", "normalf to push to refersh" );
				state = PULL;
				changeHeaderViewByState();
			}
		}

		// 这里就是处理弹性效果的地方
		if ( state == PULL || state == RELEASE_H )
		{
			setRvPadding( (tempY - startY) / factor - headerH );
		}
	}

	public void handleMoveF(int tempY)
	{
		if ( state == RELEASE_F )
		{
			Log.d( "bsh", "release to refresh f" );
			if ( ((tempY - startY) / factor > 0 - headerH * critical)
					&& (tempY - startY) < 0 )
			{
				state = PUSH;
				changeFooterViewByState();
				Log.d( "bsh", "由松开刷新状态转变到上推刷新状态" );
			}
			else if ( tempY - startY >= 0 )
			{
				state = NORMAL;
				changeFooterViewByState();
				Log.d( "bsh", "由松开刷新状态转变到normal状态" );
			}
		}
		if ( state == PUSH )
		{
			Log.d( "bsh", "push to refresh" );
			if ( (tempY - startY) / factor <= (0 - headerH * critical) )
			{
				state = RELEASE_F;
				isBack = true;
				changeFooterViewByState();
				Log.d( "bsh", "由normal或者上推刷新状态转变到松开刷新" );
			}
			else if ( tempY - startY >= 0 )
			{
				state = NORMAL;
				changeFooterViewByState();
				Log.d( "bsh", "由normal或者上推刷新状态转变到normal状态" );
			}
		}

		if ( state == NORMAL )
		{
			if ( tempY - startY < 0 )
			{
				Log.d( "bsh", "normalf to push to refersh" );
				state = PUSH;
				changeFooterViewByState();
			}
		}

		// 这里就是处理弹性效果的地方。
		if ( state == PUSH || state == RELEASE_F )
		{
			setRvPadding( (tempY - startY) / factor - headerH * 2 );
		}
	}

	public void startRecord(MotionEvent event)
	{
		if ( overable.isScrollOnTop() && !isTopRecored )
		{
			isTopRecored = true;
			startY = ( int ) event.getY();
		}
		if ( overable.isScrollOnBtm() && !isBtmRecored )
		{
			isBtmRecored = true;
			startY = ( int ) event.getY();
		}
	}

	public void handleUpF()
	{
		if ( state != REFRESHING_F )
		{
			if ( state == PUSH )
			{
				state = NORMAL;
				changeFooterViewByState();
				Log.d( "bsh", "由上推刷新状态，到normal状态" );
			}

			if ( state == RELEASE_F )
			{
				state = REFRESHING_F;
				changeFooterViewByState();
				setRvPadding( -1 * headerH - 1 );
				irefresh.refreshBtm();
			}
		}
		isBtmRecored = false;
		isBack = false;
	}

	public void handleUpH()
	{
		if ( state != REFRESHING_H )
		{
			if ( state == PULL )
			{
				state = NORMAL;
				changeHeaderViewByState();
				Log.d( "bsh", "由下拉状态，到normal状态" );
			}

			if ( state == RELEASE_H )
			{
				state = REFRESHING_H;
				changeHeaderViewByState();
				setRvPadding( 0 );
				irefresh.refreshTop();
			}
		}
		isTopRecored = false;
		isBack = false;
	}

	public void onRefreshComplete()
	{
		handler.sendEmptyMessage( 0 );
	}

	public interface IRefresh
	{
		public boolean refreshTop();

		public boolean refreshBtm();
	}

	/***
	 * 设置拉动效果幅度建议值（1-5）
	 * 
	 * @param factor
	 */
	public void setFactor(int factor)
	{
		this.factor = factor;
	}

	/***
	 * 设置最大拉动的位置，请在（0.0f-1.0f）之间取值
	 * 
	 * @param maxElastic
	 */
	public void setMaxElastic(float maxElastic)
	{
		this.maxElastic = maxElastic;
	}

	/***
	 * 设置拉动和松开状态切换的临界值，1-5之间
	 * 
	 * @param critical
	 */
	public void setCritical(int critical)
	{
		this.critical = critical;
	}

}
