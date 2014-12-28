package com.fly.fragments;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.fly.ui.view.BshElasticView.IScrollOverable;

public class MyListView extends ListView implements IScrollOverable {
	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyListView(Context context) {
		super(context);
	}

	@Override
	public boolean isScrollOnTop() {
		return 0 == getFirstVisiblePosition() ? true : false;
	}

	@Override
	public boolean isScrollOnBtm() {
		return (getCount() - 1) == getLastVisiblePosition() ? true : false;
	}
}
