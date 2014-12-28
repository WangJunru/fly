package com.fly.ui.activity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.fly.R;
import com.fly.app.FlyApplication;
import com.fly.sdk.ErrorMsg;
import com.fly.sdk.Option;
import com.fly.sdk.Question;
import com.fly.sdk.Score;
import com.fly.sdk.User;
import com.fly.sdk.job.QuestionDownload;
import com.fly.sdk.job.UserScoreCreate;
import com.fly.sdk.threading.FlyTaskManager.ResultCallback;
import com.fly.ui.dialog.AlertDialog;
import com.fly.ui.dialog.LoadDialog;
import com.fly.util.Debug;

public class QuizActivity extends BaseActivity  implements OnCheckedChangeListener,OnItemClickListener{
	
	private char  mKsnr ;
	private char  mJx ;
	private char  mJzlx ;
	
	private boolean isPageShown = true ;
	
	private TextView ksDjsView ;
	private TextView ktTv ;
	private ImageView ktIv;
	private View titleView ;
	private View  midView ;
    private int  midWith , midHeight ,titleHeight ;
	private LinearLayout ktItemsContainer ;
	private PopupWindow weiZuoPopupWindow ;
	private ArrayList<Integer> weiZuoIndex  = new ArrayList<Integer>();;
	
	private LoadDialog loadQstMaskDialog ;
	private ArrayList<Question>  questions  = new ArrayList<Question>();
	private HashMap<Question, Bitmap> questionDrawables = new HashMap<Question, Bitmap>();
	
	private int curQuestionIndex = 0;
	private int ksTotalTimes = 2*60*60;
	private int hours,minutes,seconds ;
	private StringBuilder timeStringBuilder = new StringBuilder();
	
	private DecimalFormat numberFormat = new DecimalFormat("00");
	
	private UserScoreCreate userScoreCommit ;
     public void  onCreate(Bundle savedInstanceState)
     {
    	 super.onCreate(savedInstanceState);
    	 getIntentData();
    	 setContentView(R.layout.quiz);
    	 initView();
    	 downLoadQuizQuestions();
    	 showTaskDoingDialog(R.string.download_kt_tip);
     }
     
     
     private void getIntentData()
     {
       Intent intent = getIntent();
       mKsnr = intent.getCharExtra("ksnr", 'm');
       
       mJx   = intent.getCharExtra("jx", 'x');
       mJzlx = intent.getCharExtra("jzlx",'s');
     }
     
     
     private void  initView()
     {
    	 View  backView = findViewById(R.id.back_img);
    	 backView.setOnClickListener(this);
    	 
    	 View  shareView = findViewById(R.id.share_img);
    	 shareView.setVisibility(View.INVISIBLE);
    	 shareView.setOnClickListener(this); 
    	 
    	 View priTestView = findViewById(R.id.sy);
    	 priTestView.setOnClickListener(this);
    	 
    	 View wzTestView = findViewById(R.id.ks);
    	 wzTestView.setOnClickListener(this);
    	 
    	 View jjTestView = findViewById(R.id.hx);
    	 jjTestView.setOnClickListener(this);
    	 
    	 View nestTestView = findViewById(R.id.jk);
    	 nestTestView.setOnClickListener(this);
    	 
    	 ksDjsView = (TextView)findViewById(R.id.ks_djd_time); 
    	 
    	 ktTv = (TextView)findViewById(R.id.qt_title_tv);
    	 ktIv = (ImageView)findViewById(R.id.qt_title_iv);
    	 
    	 ktItemsContainer = (LinearLayout)findViewById(R.id.qt_items);
    	 
         TextView  title = (TextView)findViewById(R.id.title);
    	 if('x' == mJx)
 		 {
 			title.setText(getString(R.string.mnks_title, getString(R.string.zsj_ks)));
 		 }else if('g' == mJx)
 		 {
 			title.setText(getString(R.string.mnks_title, getString(R.string.gdy_ks)));
 		 }
    	 
    	 titleView = findViewById(R.id.top_view);
    	 titleView.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
			
			@Override
			public boolean onPreDraw() {
				// TODO Auto-generated method stub
				 titleHeight = titleView.getHeight() ;
				return true;
			}
		});
    	 
         midView = findViewById(R.id.mid_view);
    	 midView.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
			
			@Override
			public boolean onPreDraw() {
				// TODO Auto-generated method stub
				 midWith   = midView.getWidth();
		    	 midHeight = midView.getHeight();
				return  true;
			}
		});
     }
   

     private void   showTaskDoingDialog(int strId)
 	{
 		if(loadQstMaskDialog  == null)
 		{
 			loadQstMaskDialog  = new LoadDialog(this).builder();
 			loadQstMaskDialog.setCancelable(false);
 		}
 		loadQstMaskDialog.setMessage(strId);
 		loadQstMaskDialog.show();
 	}
     
     private void downLoadQuizQuestions()
     {
    	   User user = FlyApplication.getLoginUser();
    	   String userEmal="",userToken ="" ,language = QuestionDownload.QUESTION_LAG_ZH,
    			   qtsGroup =  mKsnr == 'm'?"":QuestionDownload.QUESTION_GROUP_PENDING,
    			   planeType = QuestionDownload.PLANE_TYPE_ROTOR_PERSONAL;
    	   if(mJx ==  'x' )
    	   {
    		   if(mJzlx == 's')
    		   {
    		       planeType = QuestionDownload.PLANE_TYPE_ROTOR_PERSONAL;
    		   }else if(mJzlx == 'b')
    		   {
    			   planeType = QuestionDownload.PLANE_TYPE_ROTOR_COMMERCIAL;
    		   }
    	   }else if(mJx ==  'g')
    	   {
    		   if(mJzlx == 's')
    		   {
    		       planeType = QuestionDownload.PLANE_TYPE_FIX_WING_PERSONAL;
    		   }else if(mJzlx == 'b')
    		   {
    			   planeType = QuestionDownload.PLANE_TYPE_FIX_WING_COMMERCIAL;
    		   }
    	   }
    	   if(user != null)
    	   {
    		  
    		   userEmal  =  user.getEmail();
    		   userToken =  user.getUserToken();
	    	   final  QuestionDownload  questionDownJob = new QuestionDownload(userEmal,userToken,language,qtsGroup,planeType);
	    	   
	           taskManager.commitJob(questionDownJob, new ResultCallback() {
	
	  			@Override
	  			public void notifyResult(Object result) {
	  				// TODO Auto-generated method stub
	  				if(result != null )
	  				{
	  					if(result instanceof List<?>)
	  					{
	  						ArrayList<Question> qts =  (ArrayList<Question>)result;
	  						Collections.sort(qts, new Comparator<Question>() {
								@Override
								public int compare(Question lhs, Question rhs) {
									// TODO Auto-generated method stub
									return lhs.getQuestionId() - rhs.getQuestionId();
								}	
							 });
	  					  Message.obtain(uiHandler, 0, qts).sendToTarget();
	  					}else if(result instanceof ErrorMsg)
	  					{
	  						ErrorMsg error = questionDownJob.getError();
		  					switch (error.getErrorCode()) {
		  					  case ErrorMsg.ERROR_NETWORK_IO_ERROR: {
		  						if (uiHandler != null)
		  							uiHandler.obtainMessage(4).sendToTarget();
		  					}
		  						break;
		  					}
	  					}
	  				  
	  				}else
	  				{
	  					ErrorMsg error = questionDownJob.getError();
	  					switch (error.getErrorCode()) {
	  					  case ErrorMsg.ERROR_NETWORK_IO_ERROR: {
	  						if (uiHandler != null)
	  							uiHandler.obtainMessage(4).sendToTarget();
	  					}
	  						break;
	  					}
	  				}
	  			}
	          	 
	           });
    	   }
     }
     public void handleUIHandlerMessage(Message msg)
     {
    	 switch(msg.what)
    	 {
    	   case 0:
    	   {
    		   Object obj = msg.obj ;
    		   if(obj != null && obj instanceof List)
    		   {
    			   questions.addAll((List)obj);
    			   if(loadQstMaskDialog != null)
    			   {
    				   loadQstMaskDialog.dismiss();
    				   curQuestionIndex = 0 ;
    				   if(!questions.isEmpty())
    				   { 
    				      showQustion();
    				      uiHandler.sendEmptyMessageDelayed(1, 1000);
    				   }else
    				   {
    					   Toast.makeText(QuizActivity.this, R.string.no_kt, Toast.LENGTH_SHORT).show();
    					   this.finish();
    				   }
    			   }
    		   }
    	   }
    		 break;
    	   case 1: // 考试倒计时
    	   {
    		   ksTotalTimes -- ;
    		   hours = ksTotalTimes/3600;
    		   minutes = (ksTotalTimes%3600)/60;
    		   seconds =  (ksTotalTimes%3600)%60;
    		   if(timeStringBuilder.length() != 0)
    		   {
    			   timeStringBuilder.delete(0, timeStringBuilder.length());
    		   }
    		   timeStringBuilder.append(numberFormat.format(hours));
    		   timeStringBuilder.append(":");
    		   timeStringBuilder.append(numberFormat.format(minutes));
    		   timeStringBuilder.append(":");
    		   timeStringBuilder.append(numberFormat.format(seconds));
    		   ksDjsView.setText(timeStringBuilder.toString());
    		   if(ksTotalTimes != 0)
    		   {
    		     uiHandler.sendEmptyMessageDelayed(1, 1000);
    		   }else
    		   {
    			 uiHandler.removeMessages(1);
    			 showQuizTimeoutDialog();
    		   }
    	   } break;
    	   case 2: // 提交成功
    	   {
    		   if(loadQstMaskDialog != null)
			   {
				   loadQstMaskDialog.dismiss();
				   Toast.makeText(this, R.string.commit_success, Toast.LENGTH_SHORT).show();
			   }
    		   Object obj = msg.obj;
    		   if(obj != null )
    		   {
    			   List<Score>  scores = (List<Score>)obj;
    			   User user = FlyApplication.getLoginUser();
    			   if(scores.size() > 0 && user != null)
    			   {
    				   Score  score = scores.get(0);
	    			   Intent intent = new Intent(this,ResultActivity.class);
	    			   intent.putExtra("user_name", user.getName());
	    			   intent.putExtra("score", score.getScore());
	    			   intent.putExtra("qts", questions);
	    			   long usedSeconds = 2*60*60 - ksTotalTimes  ;
	    			   intent.putExtra("minutes", usedSeconds/60);
	    			   intent.putExtra("seconds", usedSeconds%60);
	    			   startActivity(intent);
    			   }
    			   this.finish();   
    		   }
    	   }break;
    	   case 3://  // 提交失败
    	   {
    		   if(loadQstMaskDialog != null)
			   {
    			   Toast.makeText(this, R.string.commit_fail, Toast.LENGTH_SHORT).show();
				   loadQstMaskDialog.dismiss();
				   return ;
			   }
    		   
    	   }break;
    	   case 4:
    	   {
    		   if(loadQstMaskDialog != null)
			   {
    			   Toast.makeText(this, R.string.network_io_error, Toast.LENGTH_SHORT).show();
				   loadQstMaskDialog.dismiss();
				   return ;
			   }
    	   }
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
    	 ktTv.setText(curQuestionIndex+1+"、"+qt.getSubject());
    	 
    	 ktItemsContainer.removeAllViews();
    	 
    	 Resources res =  getResources();
    	 ArrayList<Option> items = qt.getOptions();
    	 RadioGroup  optsGroup = new RadioGroup(this);
    	 optsGroup.setTag(qt);
    	 optsGroup.removeAllViews();
    	 optsGroup.setOnCheckedChangeListener(this);
    	 int id = 0 ;
    	 for(Option opt:items)
    	 {
    		 RadioButton ckBox = new RadioButton(this);
    		 if(id == qt.getAnwseredOptIndex())
    		 {
    			 ckBox.setChecked(true);
    		 }
    		 ckBox.setId(id++);
    		 ckBox.setTag(opt);
    		 ckBox.setText(opt.getContent());
    		 ckBox.setTextSize(TypedValue.COMPLEX_UNIT_PX,res.getDimensionPixelSize(R.dimen.pt_22));
    		 ckBox.setTextColor(res.getColor(R.color.ks_text_color));
    		 ckBox.setButtonDrawable(R.drawable.question_check_selector);
    		 android.widget.RadioGroup.LayoutParams paraLay = new  android.widget.RadioGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    		 paraLay.topMargin = 50;
    		 optsGroup.addView(ckBox,paraLay);
    	 }
    	 ktItemsContainer.addView(optsGroup);
     }
     public void clickView(View v)
     {
        switch(v.getId())
        {
           case R.id.back_img:
           {  	   
        	   AlertDialog alertDlg = new AlertDialog(this).builder().
        	   setMsg(getString(isFininishKt() == false? R.string.tuichu_ks:R.string.tuichu_wc_ks))
           	  .setPositiveButton("继续答题", new OnClickListener() {
 				@Override
 				public void onClick(View v) {
 					// TODO Auto-generated method stub
// 					dealWithJiaoJuanJob();
//   					showTaskDoingDialog(R.string.commit_quiz_dlg_info);
 				}
   			}).setNegativeButton("退出", new OnClickListener() {
 				@Override
 				public void onClick(View v) {
 					// TODO Auto-generated method stub	
 					QuizActivity.this.finish();
 				}
   			});
              alertDlg.setCanceledOnTouchOutside(false);
           	alertDlg.setCancelable(false);
             alertDlg.show();       
			  
           } break;
           case R.id.sy: // 上一题
        	  curQuestionIndex--;
        	  showQustion();
        	break;
           case R.id.ks: // 未做
        	  showWeizuoPopWindown();
        	break;
           case R.id.hx: // 交卷
           {
        	  showJiaoJuanDialog();
           } break;
           case R.id.jk: // 下一题
        	  curQuestionIndex++;
        	  showQustion();
        	break;
        }
     }

    private void  showJiaoJuanDialog()
    {
          AlertDialog alertDlg = new AlertDialog(this).builder().setMsg(isFininishKt() == false?
        		  getString(R.string.jq_wdw):getString(R.string.jq_wdw))
          	  .setPositiveButton(getString(R.string.yes), new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dealWithJiaoJuanJob();
  					showTaskDoingDialog(R.string.commit_quiz_dlg_info);
				}
  			}).setNegativeButton(getString(R.string.cancel), new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub	
				}
  			});
             alertDlg.setCanceledOnTouchOutside(false);
          	alertDlg.setCancelable(false);
            alertDlg.show();       
    }
    
    private boolean isFininishKt()
    {
    	 int size = questions.size();
 	    for(int i = 0 ;i < size ; i ++ )
 	    {
 	    	if(questions.get(i).getAnwseredOptIndex() == -1)
 	    	{
 	    	   return false ;
 	    	}
 	    }
 	    return true ;
    }
    private void dealWithJiaoJuanJob()
    {
    	ArrayList<Question> allAnsweredQts = new ArrayList<Question>();
    	float  perScore = 100.0f / questions.size();
    	double score = 0 ;
    	for(Question qts:this.questions)
    	{
    	    if(qts.isThisQtsAnwseredRight())
    	    {
    	    	score += perScore ;
    	    }
    		if(qts.getAnwseredOptIndex() != -1)
    		{
    			allAnsweredQts.add(qts);
    		}
    	}
    	
    	User user = FlyApplication.getLoginUser();
    	if (taskManager != null && user != null) {
    		
    		userScoreCommit = new  UserScoreCreate(user.getUserToken(), user.getEmail(), user.getId(), score,allAnsweredQts);
			taskManager.commitJob(userScoreCommit,new CommitScoreResultCapture() );
		}
    	
    }
    private final class  CommitScoreResultCapture  implements  ResultCallback
    {
		@Override
		public void notifyResult(Object result) {
			// TODO Auto-generated method stub
			if(result != null)
			{
				if(result instanceof List)
				{
				   Message.obtain(uiHandler, 2, result).sendToTarget();
				}else if(result instanceof ErrorMsg)
				{
					ErrorMsg error = userScoreCommit.getError() ;
					if(userScoreCommit != null && error != null)
					{	
						Message.obtain(uiHandler, 3, result).sendToTarget();
					}
					switch (error.getErrorCode()) {
					  case ErrorMsg.ERROR_NETWORK_IO_ERROR: {
						if (uiHandler != null)
							Message.obtain(uiHandler, 4, result).sendToTarget();
					   }
						break;
					}
				}
			}else{
				ErrorMsg error = userScoreCommit.getError();
				switch (error.getErrorCode()) {
				  case ErrorMsg.ERROR_NETWORK_IO_ERROR: {
					if (uiHandler != null)
						Message.obtain(uiHandler, 4, result).sendToTarget();
				}
					break;
			}
			}
		}
    }
    
    private void showQuizTimeoutDialog()
    {
    	    AlertDialog alertDlg = new AlertDialog(this).builder().setMsg(getString(R.string.quiz_timeout))
              	  .setPositiveButton(getString(R.string.yes), new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dealWithJiaoJuanJob();
					}
      			}).setNegativeButton(getString(R.string.cancel), new OnClickListener() {
					@Override
					public void onClick(View v) {

					}
      			});
              	alertDlg.setCanceledOnTouchOutside(false);
              	alertDlg.setCancelable(false);
                alertDlg.show();       
    }
    
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		Debug.log.i("radio_index", checkedId+"");
	    Question qts = (Question)group.getTag();
	    qts.setAnwseredOptIndex(checkedId);
	    qts.setThisQtsAnwseredRight(qts.getOptions().get(checkedId).isCorrect());
	}
     
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		curQuestionIndex = weiZuoIndex.get(arg2) -1;
		showQustion();
		if(weiZuoPopupWindow != null)
		    weiZuoPopupWindow.dismiss();
	}
	
	private void  showWeizuoPopWindown()
	{
		weiZuoIndex.clear();
	    int size = questions.size();
	    for(int i = 0 ;i < size ; i ++ )
	    {
	    	if(questions.get(i).getAnwseredOptIndex() == -1)
	    	{
	    		weiZuoIndex.add(i+1);
	    	}
	    }
	    
	    if(weiZuoIndex.isEmpty()) 
	    {
	    	Toast.makeText(this, R.string.no_weizuo_ti, Toast.LENGTH_SHORT).show();
	    	return ;
	    }
	    
		GridView grid = new GridView(this);
		grid.setPadding(0, 0, 0, 0);
		grid.setSelector(new ColorDrawable(Color.TRANSPARENT));
		grid.setBackgroundColor(getResources().getColor(R.color.grid_bg_color));
		grid.setVerticalSpacing(2);
		grid.setHorizontalSpacing(2);
		grid.setOnItemClickListener(this);
		grid.setNumColumns(5);
		grid.setAdapter(new BaseAdapter() {	
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				TextView text = null ;
				if(convertView == null)
				{
				    text  = new TextView(QuizActivity.this);
				    text.setWidth(midWith/5);
				    text.setHeight(midWith/5);
				    text.setLayoutParams(new AbsListView.LayoutParams( AbsListView.LayoutParams.MATCH_PARENT,   AbsListView.LayoutParams.MATCH_PARENT));
				    text.setGravity(Gravity.CENTER);
					text.setTextColor(Color.WHITE);
					text.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.pt_20));
					text.setBackgroundResource(R.drawable.weiz_kt_item_selector);
				}else
				{
					text = (TextView)convertView;
				}
				text.setText(weiZuoIndex.get(position)+"");
				return text;
			}
			
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}
			
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return weiZuoIndex.get(position);
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return weiZuoIndex.size();
			}
		});
		
	    weiZuoPopupWindow = new PopupWindow(grid,midWith,midHeight,true);
//	    weiZuoPopupWindow.setAnimationStyle(R.style.pop_window_animation);
	    weiZuoPopupWindow.setTouchable(true);
	    weiZuoPopupWindow.setOutsideTouchable(true);
		weiZuoPopupWindow.showAsDropDown(titleView);
	}
     @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    }
    
    public void onResume()
    {
    	super.onResume();
    	isPageShown = true ;
    }
    public void onStop()
    {
    	super.onStop();
    	if(uiHandler != null)
    	{
    		uiHandler.removeMessages(1);
    	}
    	isPageShown = false ;
    }
    
    public void onDestroy()
    {
    	  super.onDestroy();
    }


	


	
} 
