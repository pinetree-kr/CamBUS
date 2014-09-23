package com.pinetree.cambus.fragments;

import com.pinetree.cambus.R;
import com.pinetree.cambus.adapters.ModelListAdapter;
import com.pinetree.cambus.models.BusFilterModel;
import com.pinetree.cambus.models.BusListModel;
import com.pinetree.cambus.models.DBModel.LineBusTime;
import com.pinetree.cambus.models.Model;
import com.pinetree.cambus.utils.DBHandler;
import com.pinetree.cambus.utils.DateUtils;
import com.pinetree.cambus.utils.FontLoader;
import com.pinetree.cambus.utils.ImageLoader;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BusListFragment extends BaseFragment {
	protected TextView textTitle, textEmpty;
	protected TextView filterInfo, timeInfo, distanceInfo;
	protected TextView textBtnTime, textBtnPrice, textBtnNearBy;
	
	protected ImageView imageTime, imagePrice, imageNearby;
	protected Drawable dSelected, dUnSelected;
	
	protected ListView listView;
	protected ListAdapter listAdapter;
	//protected LineBusTime linebustime_list;
	protected BusListModel buslistinfo;
	
	protected int list_type;
	protected DBHandler handler;
	
	public static Fragment getInstances(Model model){
		Bundle args = new Bundle();
		args.putSerializable("model", model);
		
		Fragment fragment = new BusListFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		Bundle args = this.getArguments();
		if(args != null){
			//bus_list = (LineBusTime)args.getSerializable("model");
			buslistinfo = (BusListModel)args.getSerializable("model");
			list_type = 0;
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_bus_list, container, false);
		
		// title
		textTitle = (TextView)view.findViewById(R.id.TextTitle);
		
		View titleBg = view.findViewById(R.id.TitleBackGround);
		titleBg.setBackgroundResource(R.drawable.top);
		View filterInfoBg = view.findViewById(R.id.FilterInfoBackGround);
		filterInfoBg.setBackgroundResource(R.drawable.top2);
		View lineBg = view.findViewById(R.id.LineView);
		lineBg.setBackgroundResource(R.drawable.line);
		
		filterInfo = (TextView) view.findViewById(R.id.TextFilterInfo);
		timeInfo = (TextView) view.findViewById(R.id.TextTimeInfo);
		distanceInfo = (TextView) view.findViewById(R.id.TextDistanceInfo);
		
		textEmpty = (TextView) view.findViewById(R.id.EmptyListView);
		textBtnTime = (TextView) view.findViewById(R.id.TextButtonTime);
		textBtnPrice = (TextView) view.findViewById(R.id.TextButtonPrice);
		textBtnNearBy = (TextView) view.findViewById(R.id.TextButtonNearBy);
		/*/
		btnTime = (Button) view.findViewById(R.id.ButtonTime);
		btnTime.setTag("time");
		btnTime.setOnClickListener(new OnSortButtonClickListener());
		btnPrice = (Button) view.findViewById(R.id.ButtonPrice);
		btnPrice.setTag("price");
		btnPrice.setOnClickListener(new OnSortButtonClickListener());
		btnNearBy = (Button) view.findViewById(R.id.ButtonNearBy);
		btnNearBy.setTag("nearby");
		btnNearBy.setOnClickListener(new OnSortButtonClickListener());
		/**/
		View btnTime = view.findViewById(R.id.ButtonTime);
		btnTime.setTag("time");
		btnTime.setOnClickListener(new OnSortButtonClickListener());
		View btnPrice = view.findViewById(R.id.ButtonPrice);
		btnPrice.setTag("price");
		btnPrice.setOnClickListener(new OnSortButtonClickListener());
		View btnNearBy = view.findViewById(R.id.ButtonNearBy);
		btnNearBy.setTag("nearby");
		btnNearBy.setOnClickListener(new OnSortButtonClickListener());
		
		imageTime = (ImageView)view.findViewById(R.id.ImageButtonTime);
		imagePrice = (ImageView)view.findViewById(R.id.ImageButtonPrice);
		imageNearby = (ImageView)view.findViewById(R.id.ImageButtonNearBy);
		
		dSelected = ImageLoader.getResizedDrawableFromRes(
				getResources(),
				R.drawable.btnselected,
				app.rateDpi,
				app.rateWidth,
				app.rateHeight
				);
		dUnSelected = ImageLoader.getResizedDrawableFromRes(
				getResources(),
				R.drawable.unselected,
				app.rateDpi,
				app.rateWidth,
				app.rateHeight
				);
		//imageSearch.setImageDrawable(dSearch);
		
		listView = (ListView) view.findViewById(R.id.ListView);
		listView.setEmptyView(view.findViewById(R.id.EmptyListView));
		loadListAdapter("time");
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		handler = DBHandler.open(getActivity().getApplicationContext());
	}
	@Override
	public void onStart(){
		super.onStart();
	}
	@Override
	public void onResume(){
		super.onResume();
		loadTextView();
	}
	
	@Override
	public void onDestroyView(){
		super.onDestroyView();
		handler.close();
	}
	
	public void loadBtn(String order){
		// 기본은 오름차순.
		textBtnTime.setText(R.string.sort_time);
		textBtnPrice.setText(R.string.sort_price);
		textBtnNearBy.setText(R.string.sort_nearby);
		/*/
		textBtnTime.setText(R.string.sort_time_asc);
		textBtnPrice.setText(R.string.sort_price_asc);
		textBtnNearBy.setText(R.string.sort_nearby_asc);
		/**/
		textEmpty.setText(R.string.no_data);
		textEmpty.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		textEmpty.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)6.5));
		
		
		textBtnTime.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		textBtnTime.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)7.2));
		textBtnPrice.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		textBtnPrice.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)7.2));
		textBtnNearBy.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		textBtnNearBy.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)7.2));
		
		// 기본은 흐릿하게.
		/*/
		textBtnTime.setAlpha((float) 0.50);
		textBtnPrice.setAlpha((float) 0.50);
		textBtnNearBy.setAlpha((float) 0.50);
		/**/
		
		// 기본은 선택 x
		imageTime.setImageDrawable(dUnSelected);
		imagePrice.setImageDrawable(dUnSelected);
		imageNearby.setImageDrawable(dUnSelected);
		
		// 선택된 것에 맞게 변경 
		if(order.equals("time")){
			imageTime.setImageDrawable(dSelected);
			/*/
			textBtnTime.setAlpha((float) 1);
			if(bus_list.isAsc())
				textBtnTime.setText(R.string.sort_time_asc);
			else
				textBtnTime.setText(R.string.sort_time_desc);
			/**/
		}else if(order.equals("price")){
			imagePrice.setImageDrawable(dSelected);
			/*/
			textBtnPrice.setAlpha((float) 1);
			if(bus_list.isAsc())
				textBtnPrice.setText(R.string.sort_price_asc);
			else
				textBtnPrice.setText(R.string.sort_price_desc);
			/**/
		}else if(order.equals("nearby")){
			imageNearby.setImageDrawable(dSelected);
			/*/
			textBtnNearBy.setAlpha((float) 1);
			if(bus_list.isAsc())
				textBtnNearBy.setText(R.string.sort_nearby_asc);
			else
				textBtnNearBy.setText(R.string.sort_nearby_desc);
			/**/
		}
	}
	
	public void loadListAdapter(String order){
		
		listAdapter = new ModelListAdapter<LineBusTime>(
				this.getActivity().getApplicationContext(),
				R.layout.bus_list_row,
				//TODO order
				buslistinfo.getSortedLineBusTimeList(order));
				//bus_list.getBusList(order));
		listView.setAdapter(listAdapter);
		
		loadBtn(order);
	}
	
	protected class OnSortButtonClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			
			String order = (String)v.getTag();
			
			if(order==null || order.equals(buslistinfo.getOrder()))
				return ;
			loadListAdapter(order);
		}
	}

	protected void loadTextView() {
		textTitle.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		textTitle.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, 8));
		
		// Text FilterInfos
		String dep2des = buslistinfo.getLineInfo().getDeptName() + " > " + buslistinfo.getLineInfo().getDestName();
		filterInfo.setText(dep2des);
		filterInfo.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		filterInfo.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)5.5));
		
		timeInfo.setText(DateUtils.getTimes(buslistinfo.getDeptTime()));
		timeInfo.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		timeInfo.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)5.5));
		
		int distance = buslistinfo.getLineInfo().getDistance();
		String km = distance > 0 ? String.valueOf(distance):"--";
		distanceInfo.setText(km+" km");
		distanceInfo.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		distanceInfo.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)5.5));
		
	}
}
