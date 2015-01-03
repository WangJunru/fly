package com.fly.ui.dialog;

import java.util.Arrays;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fly.R;
import com.fly.ui.view.TosGallery;
import com.fly.ui.view.WheelView;
import com.fly.util.Tools;

public class CitySelectDialog {

	private String[] sf = new String[] { "ֱϽ��", "�ر�������", "�ӱ�ʡ", "ɽ��ʡ", "����",
			"����ʡ", "����ʡ", "������ʡ", "����ʡ", "�㽭ʡ", "����ʡ", "����ʡ", "����ʡ", "ɽ��ʡ",
			"����ʡ", "����ʡ", "����ʡ", "�㶫ʡ", "����", "����ʡ", "�Ĵ�ʡ", "����ʡ", "����ʡ",
			"����ʡ", "����ʡ", "�ຣʡ", "����", "�½�" };
	private String[][] city = new String[][] {
			{ "������", "�����", "�Ϻ���", "������" },
			{ "���", "����" },
			{ "ʯ��ׯ��", "��ɽ��", "�ػʵ���", "������", "��̨��", "������", "�żҿ���", "�е���", "������",
					"�ȷ���", "��ˮ��", "������", "޻����", "������", "������", "¹Ȫ��", "����",
					"������", "Ǩ����", "�䰲��", "�Ϲ���", "ɳ����", "������", "������", "������",
					"�߱�����", "��ͷ��", "������", "������", "�Ӽ���", "������", "������", "������",
					"������" },
			{ "̫ԭ��", "��ͬ��", "��Ȫ��", "������", "������", "˷����", "�Ž���", "º����", "��ƽ��",
					"������", "ԭƽ��", "Т����", "��ʯ��", "������", "�ܴ���", "������", "�ٷ���",
					"������", "������", "�˳���", "������", "�ӽ���" },
			{ "���ͺ�����", "��ͷ��", "�ں���", "�����", "ͨ����", "���ֹ�����", "��������", "��������",
					"��������", "����ʯ��", "������", "���������", "����������", "����������", "���ֺ�����",
					"������", "������", "��ʤ��", "�ٺ���" },
			{ "������", "������", "��ɽ��", "��˳��", "��Ϫ��", "������", "������", "Ӫ����", "������",
					"������", "�̽���", "������", "������", "��«����", "������", "�߷�����", "��������",
					"ׯ����", "������", "������", "�����", "�躣��", "������", "������", "��ʯ����",
					"������", "������", "��ԭ��", "��Ʊ��", "��Դ��", "�˳���" },
			{ "������", "������", "��ƽ��", "��Դ��", "ͨ����", "��ɽ��", "��ԭ��", "�׳���", "��̨��",
					"������", "�»���", "�Ժ���", "�����", "������", "��ʯ��", "��������", "˫����",
					"÷�ӿ���", "������", "�ٽ���", "�����", "����", "�Ӽ���", "ͼ����", "�ػ���",
					"������", "������", "������" },
			{ "��������", "���������", "������", "�׸���", "˫Ѽɽ��", "������", "������", "��ľ˹��",
					"��̨����", "ĵ������", "�ں���", "������", "˫����", "��־��", "�峣��", "ګ����",
					"������", "��ɽ��", "������", "ͬ����", "������", "��Һ���", "������", "������",
					"������", "������", "���������", "�绯��", "������", "�ض���", "������" },
			{ "�Ͼ���", "������", "������", "������", "������", "��ͨ��", "���Ƹ���", "������", "�γ���",
					"������", "����", "̩����", "��Ǩ��", "������", "������", "��ɽ��", "������",
					"������", "������", "��̳��", "�����", "������", "�żҸ���", "��ɽ��", "�⽭��",
					"̫����", "������", "������", "�����", "ͨ����", "������", "������", "��̨��",
					"�����", "������", "������", "������", "������", "������", "������", "�˻���",
					"������", "̩����", "������" },
			{ "������", "������", "������", "������", "������", "������", "����", "������", "��ɽ��",
					"̨����", "��ɽ��", "������", "������", "�ຼ��", "�ٰ���", "��Ҧ��", "��Ϫ��",
					"���", "����", "������", "������", "ƽ����", "ͩ����", "������", "������",
					"������", "��Ϫ��", "������", "������", "������", "��ɽ��", "������", "�ٺ���",
					"��ˮ��", "��Ȫ��" },
			{ "�Ϸ���", "�ߺ���", "������", "������", "��ɽ��", "������", "ͭ����", "������", "��ɽ��",
					"������", "������", "������", "������", "������", "ͩ����", "�쳤��", "������",
					"������", "������", "������", "������", "�����" },

			{ "������", "������", "������", "������", "Ȫ����", "������", "��ƽ��", "������", "������",
					"������", "ʯʨ��", "������", "�ϰ���", "������", "������", "����ɽ��", "�����",
					"������", "��ƽ��", "������", "������", "������", "������" },

			{ "�ϲ���", "��������", "Ƽ����", "�Ž���", "������", "ӥ̶��", "������", "��ƽ��", "�����",
					"��Ϫ��", "�����", "�Ͽ���", "�˴���", "�����", "������", "�߰���", "������",
					"������", "������", "����ɽ��", "�ٴ���" },
			{ "������", "�ൺ��", "�Ͳ���", "��ׯ��", "��Ӫ��", "��̨��", "Ϋ����", "������", "̩����",
					"������", "������", "������", "������", "������", "�ĳ���", "������", "������",
					"��ī��", "ƽ����", "������", "������", "������", "������", "������", "������",
					"������", "��Զ��", "��ϼ��", "������", "������", "�����", "�ٹ���", "������",
					"������", "������", "������", "������", "�޳���", "��̩��", "�ʳ���", "�ĵ���",
					"�ٳ���", "��ɽ��", "������", "�����", "������", "������", "������" },
			{ "֣����", "������", "������", "ƽ��ɽ��", "������", "�ױ���", "������", "������", "�����",
					"�����", "�����", "����Ͽ��", "������", "������", "������", "������", "������",
					"������", "��֣��", "�Ƿ���", "��ʦ��", "�����", "������", "������", "������",
					"������", "��Դ��", "������", "������", "������", "������", "������", "�鱦��",
					"������", "������", "�ܿ���", "�����", "פ�����" },
			{ "�人��", "��ʯ��", "ʮ����", "�˲���", "�差��", "������", "������", "Т����", "������",
					"�Ƹ���", "������", "��ұ��", "��������", "֦����", "������", "֦����", "�Ϻӿ���",
					"������", "�˳���", "������", "Ӧ����", "��½��", "��ˮ��", "������", "ʯ����",
					"�����", "������", "�����", "��Ѩ��", "�����", "��ʩ��", "������", "������",
					"������", "Ǳ����", "������" },
			{ "��ɳ��", "������", "��̶��", "������", "������", "������", "������", "�żҽ���", "������",
					"������", "������", "������", "¦����", "�����", "������", "������", "��ɽ��",
					"������", "������", "�����", "������", "������", "������", "�佭��", "������",
					"�齭��", "��ˮ����", "��Դ��", "������" },
			{ "������", "�ع���", "������", "�麣��", "��ͷ��", "��ɽ��", "������", "տ����", "ï����",
					"������", "������", "÷����", "��β��", "��Դ��", "������", "��Զ��", "��ݸ��",
					"��ɽ��", "������", "������", "�Ƹ���", "��خ��", "������", "������", "�ӻ���",
					"�ֲ���", "������", "������", "�κ���", "˳����", "�Ϻ���", "��ˮ��", "������",
					"̨ɽ��", "�»���", "��ƽ��", "��ɽ��", "��ƽ��", "������", "������", "�⴨��",
					"������", "������", "������", "��Ҫ��", "�Ļ���", "������", "������", "½����",
					"������", "Ӣ����", "������", "������", "�޶���" },
			{ "������", "������", "������", "������", "������", "���Ǹ���", "������", "�����", "������",
					"�Ϫ��", "������", "��ƽ��", "������", "ƾ����", "��ɽ��", "������", "��ɫ��",
					"�ӳ���", "������" },
			{ "������", "������", "ͨʲ��", "����", "������", "��ɽ��", "�Ĳ���", "������", "������" },
			{ "�ɶ���", "�Թ���", "��֦����", "������", "������", "������", "��Ԫ��", "������", "�ڽ���",
					"��ɽ��", "�ϳ���", "�˱���", "�㰲��", "������", "��������", "������", "������",
					"������", "�㺺��", "ʲ����", "������", "������", "��üɽ��", "������", "������",
					"��Դ��", "�Ű���", "������", "������", "������", "������" },
			{ "������", "����ˮ��", "������", "������", "��ˮ��", "�ʻ���", "ͭ����", "������", "�Ͻ���",
					"��˳��", "������", "������", "��Ȫ��" },
			{ "������", "������", "��Ϫ��", "������", "������", "��ͨ��", "������", "������", "��Զ��",
					"˼é��", "������", "������", "��ɽ��", "������", "º����" },
			{ "������", "ͭ����", "������", "������", "μ����", "�Ӱ���", "������", "������", "��ƽ��",
					"������", "������", "������", "������" },
			{ "������", "��������", "�����", "������", "��ˮ��", "������", "��Ȫ��", "�ػ���", "��Ҵ��",
					"������", "ƽ����", "������", "������", "������" },
			{ "������", "���ľ��", "�������" },
			{ "������", "ʯ��ɽ��", "������", "��ͭϿ��", "������" },
			{ "��³ľ����", "����������", "��³����", "������", "������", "������", "��Ȫ��", "������",
					"�������", "��������", "��ͼʲ��", "��ʲ��", "������", "������", "������", "������",
					"������", "����̩��" },

	};

	private String[] currentCitys = city[0];
	private Context context;
	private Button cancelBt;
	private Button okBt;

	private TextView title;

	private WheelView sfSelectView;
	private WheelView cySelectView;
	private Display display;

	private Dialog dialog;
	private LinearLayout wheelViewContainer;

	private WheelTextAdapter sfWheelAdapter;
	private WheelTextAdapter cityWheelAdapter;

	private String sfValue;
	private String cityValue;

	public CitySelectDialog(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	public CitySelectDialog builder() {
		// ��ȡDialog����
		View view = LayoutInflater.from(context).inflate(
				R.layout.area_picker_layout, null);

		// ����Dialog��С���Ϊ��Ļ���
		view.setMinimumWidth(display.getWidth());

		wheelViewContainer = (LinearLayout) view.findViewById(R.id.mid_view);
		wheelViewContainer.setMinimumHeight(display.getHeight() / 3);

		cancelBt = (Button) view.findViewById(R.id.cancel);
		cancelBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		okBt = (Button) view.findViewById(R.id.ok);
		title = (TextView)view.findViewById(R.id.title);
		
		sfSelectView = (WheelView) view.findViewById(R.id.sf_select_view);
		cySelectView = (WheelView) view.findViewById(R.id.city_select_view);
		sfSelectView.setScrollCycle(true);
		cySelectView.setScrollCycle(true);

		cySelectView.setOnEndFlingListener(mListener);
		sfSelectView.setOnEndFlingListener(mListener);

		cySelectView.setSoundEffectsEnabled(true);
		sfSelectView.setSoundEffectsEnabled(true);

		cityWheelAdapter = new WheelTextAdapter(context);
		cityWheelAdapter.setData(Arrays.asList(currentCitys));

		cySelectView.setAdapter(cityWheelAdapter);

		sfWheelAdapter = new WheelTextAdapter(context);
		sfWheelAdapter.setData(Arrays.asList(sf));
		sfSelectView.setAdapter(sfWheelAdapter);
		
		sfValue = sf[0];
		cityValue = currentCitys[0];

		dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
		dialog.setContentView(view);
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.BOTTOM);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.x = 0;
		lp.y = 0;
		lp.width = display.getWidth();
		dialogWindow.setAttributes(lp);

		return this;
	}

	private TosGallery.OnEndFlingListener mListener = new TosGallery.OnEndFlingListener() {
		@Override
		public void onEndFling(TosGallery v) {
			int pos = v.getSelectedItemPosition();

			if (v == cySelectView) {
				cityValue = currentCitys[pos];
			} else if (v == sfSelectView) {
				sfValue = sf[pos];
				currentCitys = city[pos];
				cityValue = currentCitys[0];
				cityWheelAdapter.setData(Arrays.asList(currentCitys));
			}
		}
	};

	public CitySelectDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}

	public CitySelectDialog setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
		return this;
	}

	public CitySelectDialog setPositiveButton(String text,
			final OnClickListener listener) {
		if ("".equals(text)) {
			okBt.setText("ȷ��");
		} else {
			okBt.setText(text);
		}
		okBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null)
					listener.onClick(v);
				dialog.dismiss();
			}
		});
		return this;
	}

	public String getAreaString() {
		return sfValue + "\t" + cityValue;
	}

	public CitySelectDialog setAreaString(String value) {
		String[] ss = value.split("\t");
		if(ss.length == 2)
		{
			sfValue = ss[0].trim();
			cityValue = ss[1].trim();
			
			int sfIndex = Arrays.asList(sf).indexOf(sfValue);
			if(sfIndex > -1)
			{
			   sfSelectView.setSelection(sfIndex);
			   sfValue =sf[sfIndex].trim();
			   int cityIndex = Arrays.asList(city[sfIndex]).indexOf(cityValue);
			   cityWheelAdapter.setData(Arrays.asList(city[sfIndex]));
			   if(cityIndex > -1)
			   {
				   cySelectView.setSelection(cityIndex);
				   cityValue = city[sfIndex][cityIndex].trim();
			   }else
			   {
				   cityValue =  city[sfIndex][0].trim();
			   }
			   currentCitys = city[sfIndex] ;
			}else
			{
				 sfValue = ss[0].trim();
				 cityValue = city[0][0].trim();
				 currentCitys = city[0] ;
			}
		}
		return this;
	}

	public CitySelectDialog setNegativeButton(String text,
			final OnClickListener listener) {
		if ("".equals(text)) {
			cancelBt.setText("ȡ��");
		} else {
			cancelBt.setText(text);
		}
		cancelBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null)
					listener.onClick(v);
				dialog.dismiss();
			}
		});
		return this;
	}

	public void show() {
		dialog.show();
	}

	private class WheelTextAdapter extends BaseAdapter {
		List<String> mData = null;
		int mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
		int mHeight = 50;
		Context mContext = null;

		public WheelTextAdapter(Context context) {
			mContext = context;
			mHeight = (int) Tools.pixelToDp(context, mHeight);
		}

		public void setData(List<String> list) {
			mData = list;
			this.notifyDataSetChanged();
		}

		public void setItemSize(int width, int height) {
			mWidth = width;
			mHeight = (int) Tools.pixelToDp(mContext, height);
		}

		@Override
		public int getCount() {
			return (null != mData) ? mData.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView = null;

			if (null == convertView) {
				convertView = new TextView(mContext);
				convertView.setLayoutParams(new TosGallery.LayoutParams(mWidth,
						mHeight));
				textView = (TextView) convertView;
				textView.setGravity(Gravity.CENTER);
				textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
				textView.setTextColor(Color.BLACK);
			}

			if (null == textView) {
				textView = (TextView) convertView;
			}

			String info = mData.get(position);
			textView.setText(info);
			// textView.setTextColor(info.mColor);

			return convertView;
		}
	}
}
