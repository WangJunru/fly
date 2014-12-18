package com.fly.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.fly.R;
import com.fly.sdk.Question;

public class CheckQuizResult extends BaseFramgment implements
		OnItemClickListener, OnPreDrawListener {

	private FragmentActivity attachedActivity;
	private ArrayList<Question> qts = new ArrayList<Question>();
	private GridView gridView;
	private View fragmentView;
	private View topView;
	private int pageWidth = 0;

	public CheckQuizResult(ArrayList<Question> qts) {
		this.qts.addAll(qts);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView(fragmentView);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		fragmentView = inflater.inflate(R.layout.check_result_layout,
				container, false);
		return fragmentView;
	}

	@Override
	public boolean onPreDraw() {
		// TODO Auto-generated method stub
		pageWidth = topView.getWidth();
		((BaseAdapter) gridView.getAdapter()).notifyDataSetChanged();
		topView.getViewTreeObserver().removeOnPreDrawListener(this);
		return true;
	}

	private void initView(View rootView) {
		TextView title = (TextView) rootView.findViewById(R.id.title);
		title.setText(R.string.check_result_title);

		View shaView = rootView.findViewById(R.id.share_img);
		shaView.setVisibility(View.INVISIBLE);

		View backView = rootView.findViewById(R.id.back_img);
		backView.setOnClickListener(this);
		gridView = (GridView) rootView.findViewById(R.id.mid_grid_view);

		topView = rootView.findViewById(R.id.top_view);
		topView.getViewTreeObserver().addOnPreDrawListener(this);

		gridView.setPadding(0, 0, 0, 0);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridView.setBackgroundColor(getResources().getColor(
				R.color.grid_bg_color));
		gridView.setVerticalSpacing(2);
		gridView.setHorizontalSpacing(2);
		gridView.setAdapter(new BaseAdapter() {
			@Override
			public View getView(final int position, View convertView,
					ViewGroup parent) {
				// TODO Auto-generated method stub
				Button text = null;
				if (convertView == null) {
					text = new Button(attachedActivity);
					text.setLayoutParams(new AbsListView.LayoutParams(
							AbsListView.LayoutParams.MATCH_PARENT,
							AbsListView.LayoutParams.MATCH_PARENT));
					text.setGravity(Gravity.CENTER);
					// text.setClickable(false);
					text.setFocusable(false);
					text.setFocusableInTouchMode(false);
					text.setTextColor(Color.BLACK);
					text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources()
							.getDimension(R.dimen.pt_20));
				} else {
					text = (Button) convertView;
				}

				text.setWidth(pageWidth / 5);
				text.setHeight(pageWidth / 5);

				Question qt = qts.get(position);
				if (qt.getAnwseredOptIndex() == -1) {
					// no answer
					text.setBackgroundResource(R.drawable.anwser_no_selector);
				} else if (qt.isThisQtsAnwseredRight()) {
					text.setBackgroundResource(R.drawable.anwser_right_selector);
				} else {
					text.setBackgroundResource(R.drawable.anwser_wrong_selector);
				}

				text.setText(position + 1 + "");
				text.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						CheckQuizItemResult checkItem = new CheckQuizItemResult(
								qts, position);
						FragmentManager frg = attachedActivity
								.getSupportFragmentManager();
						frg.beginTransaction()
								.replace(R.id.total_view, checkItem).commit();
					}
				});
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
				return true;
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
		gridView.setOnItemClickListener(this);

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		attachedActivity = (FragmentActivity) activity;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		CheckQuizItemResult checkItem = new CheckQuizItemResult(qts, arg2);
		FragmentManager frg = attachedActivity.getSupportFragmentManager();
		FragmentTransaction  frf = frg.beginTransaction();
		frf.replace(R.id.total_view, checkItem);
		frf.addToBackStack(null);
		frf.commit();
	}

	public void clickView(View view) {
		switch (view.getId()) {
		case R.id.back_img: {
			FragmentManager frg = attachedActivity.getSupportFragmentManager();
			frg.beginTransaction().remove(this).commit();
//			frg.popBackStack();
		}
			break;
		}
	}
}
