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

	private String[] sf = new String[] { "直辖市", "特别行政区", "河北省", "山西省", "内蒙",
			"辽宁省", "吉林省", "黑龙江省", "江苏省", "浙江省", "安徽省", "福建省", "江西省", "山东省",
			"河南省", "湖北省", "湖南省", "广东省", "广西", "海南省", "四川省", "贵州省", "云南省",
			"陕西省", "甘肃省", "青海省", "宁夏", "新疆" };
	private String[][] city = new String[][] {
			{ "北京市", "天津市", "上海市", "重庆市" },
			{ "香港", "澳门" },
			{ "石家庄市", "唐山市", "秦皇岛市", "邯郸市", "邢台市", "保定市", "张家口市", "承德市", "沧州市",
					"廊坊市", "衡水市", "辛集市", "藁城市", "晋州市", "新乐市", "鹿泉市", "遵化市",
					"丰南市", "迁安市", "武安市", "南宫市", "沙河市", "涿州市", "定州市", "安国市",
					"高碑店市", "泊头市", "任丘市", "黄骅市", "河间市", "霸州市", "三河市", "冀州市",
					"深州市" },
			{ "太原市", "大同市", "阳泉市", "长治市", "晋城市", "朔州市", "古交市", "潞城市", "高平市",
					"忻州市", "原平市", "孝义市", "离石市", "汾阳市", "榆次市", "介休市", "临汾市",
					"侯马市", "霍州市", "运城市", "永济市", "河津市" },
			{ "呼和浩特市", "包头市", "乌海市", "赤峰市", "通辽市", "霍林郭勒市", "海拉尔市", "满洲里市",
					"扎兰屯市", "牙克石市", "根河市", "额尔古纳市", "乌兰浩特市", "二连浩特市", "锡林浩特市",
					"集宁市", "丰镇市", "东胜市", "临河市" },
			{ "沈阳市", "大连市", "鞍山市", "抚顺市", "本溪市", "丹东市", "锦州市", "营口市", "阜新市",
					"辽阳市", "盘锦市", "铁岭市", "朝阳市", "葫芦岛市", "新民市", "瓦房店市", "普兰店市",
					"庄河市", "海城市", "东港市", "凤城市", "凌海市", "北宁市", "盖州市", "大石桥市",
					"灯塔市", "铁法市", "开原市", "北票市", "凌源市", "兴城市" },
			{ "长春市", "吉林市", "四平市", "辽源市", "通化市", "白山市", "松原市", "白城市", "九台市",
					"榆树市", "德惠市", "蛟河市", "桦甸市", "舒兰市", "磐石市", "公主岭市", "双辽市",
					"梅河口市", "集安市", "临江市", "洮南市", "大安市", "延吉市", "图们市", "敦化市",
					"珲春市", "龙井市", "和龙市" },
			{ "哈尔滨市", "齐齐哈尔市", "鸡西市", "鹤岗市", "双鸭山市", "大庆市", "伊春市", "佳木斯市",
					"七台河市", "牡丹江市", "黑河市", "阿城市", "双城市", "尚志市", "五常市", "讷河市",
					"虎林市", "密山市", "铁力市", "同江市", "富锦市", "绥芬河市", "海林市", "宁安市",
					"穆棱市", "北安市", "五大连池市", "绥化市", "安达市", "肇东市", "海伦市" },
			{ "南京市", "无锡市", "徐州市", "常州市", "苏州市", "南通市", "连云港市", "淮阴市", "盐城市",
					"扬州市", "镇江市", "泰州市", "宿迁市", "江阴市", "宜兴市", "锡山市", "新沂市",
					"邳州市", "溧阳市", "金坛市", "武进市", "常熟市", "张家港市", "昆山市", "吴江市",
					"太仓市", "吴县市", "启东市", "如皋市", "通州市", "海门市", "淮安市", "东台市",
					"大丰市", "仪征市", "高邮市", "江都市", "丹阳市", "扬中市", "句容市", "兴化市",
					"靖江市", "泰兴市", "姜堰市" },
			{ "杭州市", "宁波市", "温州市", "嘉兴市", "湖州市", "绍兴市", "金华市", "衢州市", "舟山市",
					"台州市", "萧山市", "建德市", "富阳市", "余杭市", "临安市", "余姚市", "慈溪市",
					"奉化市", "瑞安市", "乐清市", "海宁市", "平湖市", "桐乡市", "诸暨市", "上虞市",
					"嵊州市", "兰溪市", "义乌市", "东阳市", "永康市", "江山市", "温岭市", "临海市",
					"丽水市", "龙泉市" },
			{ "合肥市", "芜湖市", "蚌埠市", "淮南市", "马鞍山市", "淮北市", "铜陵市", "安庆市", "黄山市",
					"滁州市", "阜阳市", "宿州市", "巢湖市", "六安市", "桐城市", "天长市", "明光市",
					"亳州市", "界首市", "宣州市", "宁国市", "贵池市" },

			{ "福州市", "厦门市", "莆田市", "三明市", "泉州市", "漳州市", "南平市", "福清市", "长乐市",
					"永安市", "石狮市", "晋江市", "南安市", "龙海市", "邵武市", "武夷山市", "建瓯市",
					"建阳市", "漳平市", "宁德市", "福安市", "福鼎市", "龙岩市" },

			{ "南昌市", "景德镇市", "萍乡市", "九江市", "新余市", "鹰潭市", "赣州市", "乐平市", "瑞昌市",
					"贵溪市", "瑞金市", "南康市", "宜春市", "丰城市", "樟树市", "高安市", "上饶市",
					"德兴市", "吉安市", "井冈山市", "临川市" },
			{ "济南市", "青岛市", "淄博市", "枣庄市", "东营市", "烟台市", "潍坊市", "济宁市", "泰安市",
					"威海市", "日照市", "莱芜市", "临沂市", "德州市", "聊城市", "章丘市", "胶州市",
					"即墨市", "平度市", "胶南市", "莱西市", "滕州市", "龙口市", "莱阳市", "莱州市",
					"蓬莱市", "招远市", "栖霞市", "海阳市", "青州市", "诸城市", "寿光市", "安丘市",
					"高密市", "昌邑市", "曲阜市", "兖州市", "邹城市", "新泰市", "肥城市", "文登市",
					"荣成市", "乳山市", "乐陵市", "禹城市", "临清市", "滨州市", "菏泽市" },
			{ "郑州市", "开封市", "洛阳市", "平顶山市", "安阳市", "鹤壁市", "新乡市", "焦作市", "濮阳市",
					"许昌市", "漯河市", "三门峡市", "南阳市", "商丘市", "信阳市", "巩义市", "荥阳市",
					"新密市", "新郑市", "登封市", "偃师市", "舞钢市", "汝州市", "林州市", "卫辉市",
					"辉县市", "济源市", "沁阳市", "孟州市", "禹州市", "长葛市", "义马市", "灵宝市",
					"邓州市", "永城市", "周口市", "项城市", "驻马店市" },
			{ "武汉市", "黄石市", "十堰市", "宜昌市", "襄樊市", "鄂州市", "荆门市", "孝感市", "荆州市",
					"黄冈市", "咸宁市", "大冶市", "丹江口市", "枝城市", "当阳市", "枝江市", "老河口市",
					"枣阳市", "宜城市", "钟祥市", "应城市", "安陆市", "广水市", "汉川市", "石首市",
					"洪湖市", "松滋市", "麻城市", "武穴市", "赤壁市", "恩施市", "利川市", "随州市",
					"仙桃市", "潜江市", "天门市" },
			{ "长沙市", "株洲市", "湘潭市", "衡阳市", "邵阳市", "岳阳市", "常德市", "张家界市", "益阳市",
					"郴州市", "永州市", "怀化市", "娄底市", "浏阳市", "醴陵市", "湘乡市", "韶山市",
					"耒阳市", "常宁市", "武冈市", "汩罗市", "临湘市", "津市市", "沅江市", "资兴市",
					"洪江市", "冷水江市", "涟源市", "吉首市" },
			{ "广州市", "韶关市", "深圳市", "珠海市", "汕头市", "佛山市", "江门市", "湛江市", "茂名市",
					"肇庆市", "惠州市", "梅州市", "汕尾市", "河源市", "阳江市", "清远市", "东莞市",
					"中山市", "潮州市", "揭阳市", "云浮市", "番禺市", "花都市", "增城市", "从化市",
					"乐昌市", "南雄市", "潮阳市", "澄海市", "顺德市", "南海市", "三水市", "高明市",
					"台山市", "新会市", "开平市", "鹤山市", "恩平市", "廉江市", "雷州市", "吴川市",
					"高州市", "化州市", "信宜市", "高要市", "四会市", "惠阳市", "兴宁市", "陆丰市",
					"阳春市", "英德市", "连州市", "普宁市", "罗定市" },
			{ "南宁市", "柳州市", "桂林市", "梧州市", "北海市", "防城港市", "钦州市", "贵港市", "玉林市",
					"岑溪市", "东兴市", "桂平市", "北流市", "凭祥市", "合山市", "贺州市", "百色市",
					"河池市", "宜州市" },
			{ "海口市", "三亚市", "通什市", "琼海市", "儋州市", "琼山市", "文昌市", "万宁市", "东方市" },
			{ "成都市", "自贡市", "攀枝花市", "泸州市", "德阳市", "绵阳市", "广元市", "遂宁市", "内江市",
					"乐山市", "南充市", "宜宾市", "广安市", "达州市", "都江堰市", "彭州市", "邛崃市",
					"崇州市", "广汉市", "什邡市", "绵竹市", "江油市", "峨眉山市", "阆中市", "华蓥市",
					"万源市", "雅安市", "西昌市", "巴中市", "资阳市", "简阳市" },
			{ "贵阳市", "六盘水市", "遵义市", "清镇市", "赤水市", "仁怀市", "铜仁市", "兴义市", "毕节市",
					"安顺市", "凯里市", "都匀市", "福泉市" },
			{ "昆明市", "曲靖市", "玉溪市", "安宁市", "宣威市", "昭通市", "楚雄市", "个旧市", "开远市",
					"思茅市", "景洪市", "大理市", "保山市", "瑞丽市", "潞西市" },
			{ "西安市", "铜川市", "宝鸡市", "咸阳市", "渭南市", "延安市", "汉中市", "榆林市", "兴平市",
					"韩城市", "华阴市", "安康市", "商州市" },
			{ "兰州市", "嘉峪关市", "金昌市", "白银市", "天水市", "玉门市", "酒泉市", "敦煌市", "张掖市",
					"武威市", "平凉市", "西峰市", "临夏市", "合作市" },
			{ "西宁市", "格尔木市", "德令哈市" },
			{ "银川市", "石嘴山市", "吴忠市", "青铜峡市", "灵武市" },
			{ "乌鲁木齐市", "克拉玛依市", "吐鲁番市", "哈密市", "昌吉市", "阜康市", "米泉市", "博乐市",
					"库尔勒市", "阿克苏市", "阿图什市", "喀什市", "和田市", "奎屯市", "伊宁市", "塔城市",
					"乌苏市", "阿勒泰市" },

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
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(
				R.layout.area_picker_layout, null);

		// 设置Dialog最小宽度为屏幕宽度
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
			okBt.setText("确定");
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
			cancelBt.setText("取消");
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
