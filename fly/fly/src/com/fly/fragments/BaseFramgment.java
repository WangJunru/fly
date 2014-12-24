package com.fly.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.fly.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

public class BaseFramgment extends Fragment implements OnClickListener {

	protected FragmentActivity attachedActivity;

	private void animateClickView(final View v) {
		// float factor = 1.1f;
		ViewPropertyAnimator.animate(v).alpha(0.3f)
				.setListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						// ViewHelper.setScaleX(v, 1);
						// ViewHelper.setScaleY(v, 1);
						ViewHelper.setAlpha(v, 1);

						super.onAnimationEnd(animation);
					}
				});
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		attachedActivity = (FragmentActivity) activity;
	}

	protected void clickView(View v) {
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		animateClickView(v);
		switch (v.getId()) {
		case R.id.share_img: {

		}
			break;
		}
		clickView(v);
	}
}
