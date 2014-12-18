package com.fly.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fly.FlyApplication;
import com.fly.R;
import com.fly.debug.Debug;
import com.fly.sdk.Option;
import com.fly.sdk.Question;
import com.fly.sdk.User;

public class CheckQuizItemResult extends BaseFramgment {
	
	private FragmentActivity  attachedActivity ;
	private TextView ktTv ;
	private ImageView ktIv;
	private LinearLayout ktItemsContainer ;
	private TextView   countTextTv ;
	private TextView   userIdTv ;
	private TextView   userNameTv ;
	
	private ArrayList<Question> questions = new ArrayList<Question>();
	private int totalCount = 0 ;
	private int  curQuestionIndex = 0;
	

	
	public CheckQuizItemResult(ArrayList<Question> item,int position)
	{
		this.questions.addAll(item);
		totalCount = this.questions.size() ;
		this.curQuestionIndex = position ;
	}

      @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    }
      
      @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onViewCreated(view, savedInstanceState);
    }
      
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	View rootView  = inflater.inflate(R.layout.check_result_layout2, container, false);
    	initView(rootView);
    	return rootView ;
    }
    
    private void initView(View rootView)
    {
    	 View  backView = rootView.findViewById(R.id.back_img);
    	 backView.setOnClickListener(this);
    	 
    	 View  shareView = rootView.findViewById(R.id.share_img);
    	 shareView.setOnClickListener(this); 
    	 shareView.setVisibility(View.INVISIBLE);
    	 
    	 View priTestView = rootView.findViewById(R.id.sy);
    	 priTestView.setOnClickListener(this);
    	 
    	 countTextTv  = (TextView)rootView.findViewById(R.id.ks_djd_time);
    	 
    	 TextView title = (TextView)rootView.findViewById(R.id.title);
    	 title.setText(R.string.shiti_an_title);
    	 
    	 View nestTestView = rootView.findViewById(R.id.jk);
    	 nestTestView.setOnClickListener(this);

    	 ktTv = (TextView)rootView.findViewById(R.id.qt_title_tv);
    	 ktIv = (ImageView)rootView.findViewById(R.id.qt_title_iv);
    	 
    	 ktItemsContainer = (LinearLayout)rootView.findViewById(R.id.qt_items);
    	 
    	 userIdTv = (TextView)rootView.findViewById(R.id.user_id);
    	 userNameTv = (TextView)rootView.findViewById(R.id.user_name);
    	 
    	 if(questions.size() > 0)
    	 {
    		 showQustion();
    	 }	 
    }
    
    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	User user = FlyApplication.getLoginUser();
    	if(user != null)
    	{
    	  userIdTv.setText(getString(R.string.user_id_info, user.getId()));
    	  userNameTv.setText(getString(R.string.user_name_info, user.getName()));
    	}
    }
    private void  showQustion()
    {
   	
   	 if(questions.isEmpty())
   	 {
   		 return ;
   	 }
   	 if(curQuestionIndex < 0 )
   	 {
   		 curQuestionIndex = 0 ;
   	 }
   	 
   	 if(curQuestionIndex == questions.size() )
   	 {
   		 curQuestionIndex -- ;
   	 }
   	 
   	 if(curQuestionIndex > questions.size())
   	 {
   		 return ;
   	 }
   	 
   	 Question qt = questions.get(curQuestionIndex);    	
   	 ktTv.setText(curQuestionIndex+1+"¡¢	"+qt.getSubject());
   	 
   	 ktItemsContainer.removeAllViews();
   	 
   	 Resources res =  getResources();
   	 ArrayList<Option> items = qt.getOptions();
   	 RadioGroup  optsGroup = new RadioGroup(attachedActivity);
   	 optsGroup.setTag(qt);
   	 optsGroup.removeAllViews();
   	 int id = 0 ;
   	 for(Option opt:items)
   	 {
   		 RadioButton ckBox = new RadioButton(attachedActivity);
   		 if(id == qt.getAnwseredOptIndex())
   		 {
   			 ckBox.setChecked(true);
   			 if(opt.isCorrect())
   			 {
   			    ckBox.setTextColor(res.getColor(R.color.bight_green_color));
   			 }else
   			 {
   				ckBox.setTextColor(res.getColor(R.color.bright_red_color));
   			 }
   				 
   		 }else
   		 {
   			 if(opt.isCorrect())
   			 {
   			    ckBox.setTextColor(res.getColor(R.color.bight_green_color));
   			 }else
   			 {
   				 ckBox.setTextColor(res.getColor(R.color.ks_text_color));
   			 }
   		 }
   		 ckBox.setId(id++);
   		 ckBox.setEnabled(false);
   		 ckBox.setTag(opt);
   		 ckBox.setText(opt.getContent());
   		 ckBox.setTextSize(TypedValue.COMPLEX_UNIT_PX,res.getDimensionPixelSize(R.dimen.pt_22));
   	     ckBox.setButtonDrawable(R.drawable.question_check_selector);
   		
   		 android.widget.RadioGroup.LayoutParams paraLay = new  android.widget.RadioGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
   		 paraLay.topMargin = 50;
   		 optsGroup.addView(ckBox,paraLay);
   		 
   	 }
     countTextTv.setText(curQuestionIndex+1 + "/" + totalCount);
   	 ktItemsContainer.addView(optsGroup);
    }
      
    @Override
    public void onAttach(Activity activity) {
    	// TODO Auto-generated method stub
    	super.onAttach(activity);
    	attachedActivity = (FragmentActivity)activity ;
    }
    
    public void  clickView(View v)
    {
    	switch(v.getId())
    	{
    	 case R.id.back_img:
    	 {
    		 FragmentManager  frg  =   attachedActivity.getSupportFragmentManager();
//   		     frg.beginTransaction().remove(this).commit();
//    		 Debug.log.i("count",frg.getBackStackEntryCount()+"");
//    		 if(frg.getBackStackEntryCount() > 0)
//    		 {
//    			 Debug.log.i("count", frg.popBackStackImmediate()+"");
//    		 }
    		  FragmentTransaction   ftr = frg.beginTransaction();
  			  CheckQuizResult  page = new CheckQuizResult(this.questions);
  			  ftr.replace(R.id.total_view, page);
  			  ftr.commit();
    	 }
    		break;
      	 case R.id.sy:
      	 {
      		 curQuestionIndex -- ;
      		 showQustion();
      	 }
    		break;
      	 case R.id.jk:
      	 {
      		 curQuestionIndex ++ ;
      		 showQustion();
      	 }
      		 break;
    	}
    }
}
