package com.pinetree.cambus.fragments;

import java.util.ArrayList;

import com.pinetree.cambus.R;
import com.pinetree.cambus.adapters.ModelListAdapter;
import com.pinetree.cambus.models.BusFilterModel;
import com.pinetree.cambus.models.BusListModel;
import com.pinetree.cambus.models.DBModel.*;
import com.pinetree.cambus.models.Model;
import com.pinetree.cambus.utils.DBHandler;
import com.pinetree.cambus.utils.DateUtils;
import com.pinetree.cambus.utils.FontLoader;
import com.pinetree.cambus.utils.ImageLoader;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BusListFragment extends BaseFragment {
	private TextView textTitle, textEmpty;
	private TextView filterInfo, timeInfo, distanceInfo;
	private TextView textBtnTime, textBtnPrice, textBtnNearest;
	
	private ImageView imageTime, imagePrice, imageNearest;
	private Drawable dSelected, dUnSelected;
	
	private ListView listView;
	private ListAdapter listAdapter;
	//private LineBusTime linebustime_list;
	private BusListModel buslistinfo;
	
	private int list_type;
	private DBHandler handler;
	
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
		textBtnNearest = (TextView) view.findViewById(R.id.TextButtonNearest);
		
		View btnTime = view.findViewById(R.id.ButtonTime);
		btnTime.setTag("time");
		btnTime.setOnClickListener(new OnSortButtonClickListener());
		View btnPrice = view.findViewById(R.id.ButtonPrice);
		btnPrice.setTag("price");
		btnPrice.setOnClickListener(new OnSortButtonClickListener());
		View btnNearest = view.findViewById(R.id.ButtonNearest);
		btnNearest.setTag("nearest");
		btnNearest.setOnClickListener(new OnSortButtonClickListener());
		
		imageTime = (ImageView)view.findViewById(R.id.ImageButtonTime);
		imagePrice = (ImageView)view.findViewById(R.id.ImageButtonPrice);
		imageNearest = (ImageView)view.findViewById(R.id.ImageButtonNearest);
		
		dSelected = ImageLoader.getResizedDrawable(
				getResources(),
				R.drawable.btnselected
				);
		dUnSelected = ImageLoader.getResizedDrawable(
				getResources(),
				R.drawable.unselected
				);
		
		//imageSearch.setImageDrawable(dSearch);
		
		listView = (ListView) view.findViewById(R.id.ListView);
		listView.setEmptyView(view.findViewById(R.id.EmptyListView));
		listView.setOnItemClickListener(new ListViewClickListener());
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
	
	public void onClickedOrderButton(String order){
		// 선택된 것에 맞게 변경 
		if(order.equals("time")){
			initImageButton();
			imageTime.setImageDrawable(dSelected);
			/*/
			textBtnTime.setAlpha((float) 1);
			if(bus_list.isAsc())
				textBtnTime.setText(R.string.sort_time_asc);
			else
				textBtnTime.setText(R.string.sort_time_desc);
			/**/
		}else if(order.equals("price")){
			initImageButton();
			imagePrice.setImageDrawable(dSelected);
			/*/
			textBtnPrice.setAlpha((float) 1);
			if(bus_list.isAsc())
				textBtnPrice.setText(R.string.sort_price_asc);
			else
				textBtnPrice.setText(R.string.sort_price_desc);
			/**/
		}else if(order.equals("nearest")){
			/*/
			initImageButton();
			imageNearest.setImageDrawable(dSelected);
			/**/
			/*/
			textBtnNearest.setAlpha((float) 1);
			if(bus_list.isAsc())
				textBtnNearest.setText(R.string.sort_Nearest_asc);
			else
				textBtnNearest.setText(R.string.sort_Nearest_desc);
			/**/
		}
	}
	
	public void initImageButton(){
		// 기본은 선택 x
		imageTime.setImageDrawable(dUnSelected);
		imagePrice.setImageDrawable(dUnSelected);
		imageNearest.setImageDrawable(dUnSelected);
	}
	
	public void loadListAdapter(String order){
		listAdapter = new ModelListAdapter<LineBusTime>(
				this.getActivity().getApplicationContext(),
				R.layout.bus_list_row,
				buslistinfo.getSortedLineBusTimeList(order));
		listView.setAdapter(listAdapter);
		
		onClickedOrderButton(order);
	}
	
	private class OnSortButtonClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			
			String order = (String)v.getTag();
			
			if(order==null || order.equals(buslistinfo.getOrder()))
				return ;
			
			//TODO : 아직 미지원 
			if(order.equals("nearest")){
				Toast.makeText(getActivity().getApplicationContext(), R.string.not_supported_yet, 1000).show();
			}else{
				loadListAdapter(order);
			}
		}
	}

	private void loadTextView() {
		textTitle.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		//textTitle.setTextSize(FontLoader.getFontSizeFromPt(app, 8));
		textTitle.setTextSize(TypedValue.COMPLEX_UNIT_PT, (float)8.0);
		
		// Text FilterInfos
		String dep2des = buslistinfo.getLineInfo().getDeptName() + " > " + buslistinfo.getLineInfo().getDestName();
		filterInfo.setText(dep2des);
		filterInfo.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		//filterInfo.setTextSize(FontLoader.getFontSizeFromPt(app, (float)5.5));
		filterInfo.setTextSize(TypedValue.COMPLEX_UNIT_PT, (float)5.5);
		
		timeInfo.setText(DateUtils.getTimes(buslistinfo.getDeptTime()));
		timeInfo.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		//timeInfo.setTextSize(FontLoader.getFontSizeFromPt(app, (float)5.5));
		timeInfo.setTextSize(TypedValue.COMPLEX_UNIT_PT, (float)5.5);
		
		int distance = buslistinfo.getLineInfo().getDistance();
		String km = distance > 0 ? String.valueOf(distance):"--";
		distanceInfo.setText(km+" km");
		distanceInfo.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		//distanceInfo.setTextSize(FontLoader.getFontSizeFromPt(app, (float)5.5));
		distanceInfo.setTextSize(TypedValue.COMPLEX_UNIT_PT, (float)5.5);
		
		textBtnTime.setText(R.string.sort_time);
		textBtnPrice.setText(R.string.sort_price);
		textBtnNearest.setText(R.string.sort_nearest);
		
		textEmpty.setText(R.string.no_data);
		
		textEmpty.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		//textEmpty.setTextSize(FontLoader.getFontSizeFromPt(app, (float)6.5));
		textEmpty.setTextSize(TypedValue.COMPLEX_UNIT_PT, (float)6.5);
		
		textBtnTime.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		//textBtnTime.setTextSize(FontLoader.getFontSizeFromPt(app, (float)7.2));
		textBtnTime.setTextSize(TypedValue.COMPLEX_UNIT_PT, (float)7.2);
		textBtnPrice.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		//textBtnPrice.setTextSize(FontLoader.getFontSizeFromPt(app, (float)7.2));
		textBtnPrice.setTextSize(TypedValue.COMPLEX_UNIT_PT, (float)7.2);
		textBtnNearest.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		//textBtnNearest.setTextSize(FontLoader.getFontSizeFromPt(app, (float)7.2));
		textBtnNearest.setTextSize(TypedValue.COMPLEX_UNIT_PT, (float)7.2);
	}
	
	private class ListViewClickListener implements AdapterView.OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			LineBusTime object = (LineBusTime)parent.getItemAtPosition(position);
		    ArrayList<Terminal> terminals = object.getTerminalList();

		    if(terminals.size()!=0){
		    	int i;
		    	for(i=0;i<terminals.size();i++){
		    		if(terminals.get(i).hasPosition())
		    			break;
		    	}
		    	//터미널좌표가 있는 정보 GOTO 맵다이얼로그 
		    	if(i!=terminals.size()){
		    		TerminalMapDialogFragment dialog = TerminalMapDialogFragment.getInstances(object.getTerminalList());
		    		dialog.show(getFragmentManager(), "googlemap");		    		
		    	}
		    	/*/
		    	//좌표가 없는 정보 GOTO 연락처다이얼로그
		    	else{
		    		TerminalMapDialogFragment dialog = TerminalMapDialogFragment.getInstances(object.getTerminalList());
		    		dialog.show(getFragmentManager(), "googlemap");
		    	}
		    	/**/
		    }
		}
		
	}
}
