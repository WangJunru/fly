package com.fly.view;

import java.util.ArrayList;

import com.fly.R;
import com.fly.fragments.CheckQuizItemResult;
import com.fly.sdk.Question;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.AbsListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

public class CheckQuizResultView extends FrameLayout implements OnPreDrawListener{

	private ArrayList<Question>  qts = new ArrayList<Question>();
	private GridView gridView; 
	private View     topView ;
	private int  pageWidth = 0 ;
	
	private Context context ;
	
	private OnItemClickListener onGridItemClickListener ;
	private OnClickListener     onClcikListener ;
	public void  setOnItemClickListener(OnItemClickListener listener)
	{
		this.onGridItemClickListener = listener ;
	}
	public void  setOnClickListener(OnClickListener listener)
	{
		this.onClcikListener = listener ;
	}
	
	public void setQuestionsData(ArrayList<Question> data)
	{
		qts.clear();
		qts.addAll(data);
		refreshData();	
	}
	public ArrayList<Question>  getQuestionsData()
	{
		return qts ;
	}
	
	private void refreshData()
	{
		BaseAdapter  adapter = (BaseAdapter) gridView.getAdapter();
		adapter.notifyDataSetChanged();
	}
	
	public CheckQuizResultView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context  =  context ;
		initView(this);
	}
	public CheckQuizResultView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context  =  context ;
		initView(this);
	}
	public CheckQuizResultView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context  =  context ;
		initView(this);
	}
	
	@Override
	public boolean onPreDraw() {
		// TODO Auto-generated method stub
		pageWidth = topView.getWidth();
		((BaseAdapter)gridView.getAdapter()).notifyDataSetChanged();
		topView.getViewTreeObserver().removeOnPreDrawListener(this);
		return false;
	}
	private void initView(View rootView)
	{
		LayoutInflater.from(context).inflate(R.layout.check_result_layout, this);

    	TextView  title  = (TextView)rootView.findViewById(R.id.title);
    	title.setText(R.string.check_result_title);
    	
    	View  shaView  =  rootView.findViewById(R.id.share_img);
    	shaView.setVisibility(View.INVISIBLE);
    	
    	View backView =  rootView.findViewById(R.id.back_img);
    	backView.setOnClickListener(onClcikListener);
    	
        gridView = (GridView)rootView.findViewById(R.id.mid_grid_view);
    	
    	topView = rootView.findViewById(R.id.top_view);
    	topView.getViewTreeObserver().addOnPreDrawListener(this);

    	gridView.setOnItemClickListener(onGridItemClickListener);
    	gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
    	gridView.setBackgroundColor(getResources().getColor(R.color.grid_bg_color));
    	gridView.setVerticalSpacing(2);
    	gridView.setHorizontalSpacing(2);
    	gridView.setAdapter(new BaseAdapter() {		
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				TextView text = null ;
				if(convertView == null)
				{
				    text  = new TextView(context);			
				    text.setLayoutParams(new AbsListView.LayoutParams( AbsListView.LayoutParams.MATCH_PARENT,   AbsListView.LayoutParams.MATCH_PARENT));
				    text.setGravity(Gravity.CENTER);
				   // text.setClickable(false);
				    text.setFocusable(false);
				    text.setFocusableInTouchMode(false);
					text.setTextColor(Color.BLACK);
					text.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.pt_20));
				}else
				{
					text = (TextView)convertView;
				}

			    text.setWidth(pageWidth/5);
			    text.setHeight(pageWidth/5);
			    
				Question  qt = qts.get(position);
				if(qt.getAnwseredOptIndex() == -1)
				{
					// no answer
					text.setBackgroundResource(R.drawable.anwser_no_selector);
				}else if(qt.isThisQtsAnwseredRight())
				{
					text.setBackgroundResource(R.drawable.anwser_right_selector);
				}else
				{
					text.setBackgroundResource(R.drawable.anwser_wrong_selector);
				}
				
				text.setText(position+1+"");
				return text;
			}
			
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}
			
			@Override
			public boolean isEnabled(int position) {
				// TODO Auto-generated method stub
				return  true ;
			}
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return qts.get(position);
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return qts.size();
			}
		});
    	
	}
}
