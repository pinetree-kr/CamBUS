package com.pinetree.cambus.fragments;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pinetree.cambus.R;
import com.pinetree.cambus.adapters.ModelListAdapter;
import com.pinetree.cambus.models.DBModel.Bus;
import com.pinetree.cambus.models.DBModel.Line;
import com.pinetree.cambus.models.DBModel.Terminal;
import com.pinetree.cambus.models.DBModel.Time;
import com.pinetree.cambus.utils.DBHandler;

public class BusListFragment extends BaseFragment {
	private TextView textTitle, textEmpty;
	
	private TextView textDept, textDest, textDistance;
	private TextView textBtnTime, textBtnPrice,
		//textBtnNearest;
		textBtnName;
	
	private ImageView imageTime, imagePrice,
		//imageNearest;
		imageName;
	
	private Drawable dSelected, dUnSelected;
	
	private ListView listView;
	private ListAdapter listAdapter;
	
	private Bus bus;
	private int time;
	private String order = "time";
	
	private DBHandler handler;
	public static Fragment getInstances(Bus bus, int time){
		Bundle args = new Bundle();
		
		args.putSerializable("bus", bus);
		args.putInt("time", time);
		
		Fragment fragment = new BusListFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		Bundle args = this.getArguments();
		if(args != null){
			bus = (Bus) args.getSerializable("bus");
			time = args.getInt("time");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_bus_list, container, false);
		
		// title
		textTitle = (TextView)view.findViewById(R.id.TextTitle);
		
		ImageView titleBg = (ImageView)view.findViewById(R.id.titleBg);
		Drawable dBg = imageLoader.getResizedDrawable(R.drawable.top);
		titleBg.setImageDrawable(dBg);
		
		ImageView filterBg = (ImageView)view.findViewById(R.id.filterBg);
		Drawable dFilterBg = imageLoader.getResizedDrawable(R.drawable.filter_bar);
		filterBg.setImageDrawable(dFilterBg);
		
		textDept = (TextView) view.findViewById(R.id.textDept);
		textDest = (TextView) view.findViewById(R.id.textDest);
		textDistance = (TextView) view.findViewById(R.id.textDistance);
		
		ImageView imageBus = (ImageView) view.findViewById(R.id.imageBus);
		Drawable dBusIcon = imageLoader.getResizedDrawable(R.drawable.bus_icon);
		imageBus.setImageDrawable(dBusIcon);
		
		
		textEmpty = (TextView) view.findViewById(R.id.EmptyListView);
		textBtnTime = (TextView) view.findViewById(R.id.TextButtonTime);
		textBtnPrice = (TextView) view.findViewById(R.id.TextButtonPrice);
		textBtnName = (TextView) view.findViewById(R.id.TextButtonName);
		
		View btnTime = view.findViewById(R.id.ButtonTime);
		btnTime.setTag("time");
		btnTime.setOnClickListener(new OnSortButtonClickListener());
		View btnPrice = view.findViewById(R.id.ButtonPrice);
		btnPrice.setTag("price");
		btnPrice.setOnClickListener(new OnSortButtonClickListener());
		View btnName = view.findViewById(R.id.ButtonName);
		btnName.setTag("name");
		btnName.setOnClickListener(new OnSortButtonClickListener());
		
		imageTime = (ImageView)view.findViewById(R.id.ImageButtonTime);
		imagePrice = (ImageView)view.findViewById(R.id.ImageButtonPrice);
		imageName = (ImageView)view.findViewById(R.id.ImageButtonName);
		
		dSelected = imageLoader.getResizedDrawable(R.drawable.sort_selected);
		dUnSelected = imageLoader.getResizedDrawable(R.drawable.sort_unselected);
		
		listView = (ListView) view.findViewById(R.id.ListView);
		listView.setEmptyView(view.findViewById(R.id.EmptyListView));
		listView.setOnItemClickListener(new ListViewClickListener());
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		handler = DBHandler.getInstance(getActivity().getApplicationContext(), false);
		
		setLineInfo();
		loadListAdapter();
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
	}
	
	public void onClickedOrderButton(){
		// 선택된 것에 맞게 변경 
		if(order.equals("time")){
			initImageButton();
			imageTime.setImageDrawable(dSelected);
		}else if(order.equals("price")){
			initImageButton();
			imagePrice.setImageDrawable(dSelected);
		}else if(order.equals("nearest")){
		}else if(order.equals("name")){
			initImageButton();
			imageName.setImageDrawable(dSelected);
		}
	}
	
	public void initImageButton(){
		// 기본은 선택 x
		imageTime.setImageDrawable(dUnSelected);
		imagePrice.setImageDrawable(dUnSelected);
		//imageNearest.setImageDrawable(dUnSelected);
		imageName.setImageDrawable(dUnSelected);
	}
	
	public void loadListAdapter(){
		listAdapter = new ModelListAdapter<Time>(
				this.getActivity().getApplicationContext(),
				R.layout.bus_list_row,
				handler.getTimeList(
						bus.getDeptId(),
						bus.getDestId(),
						bus.getTypeId(),
						bus.getCompanyId(),
						time,
						order)
				);
		listView.setAdapter(listAdapter);
		onClickedOrderButton();
	}
	
	private class OnSortButtonClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			
			String c_order = (String)v.getTag();
			
			if(c_order == null || c_order.equals(order))
				return ;

			//TODO : 아직 미지원 
			if(c_order.equals("nearest")){
				Toast.makeText(getActivity().getApplicationContext(), R.string.not_supported_yet, 1000).show();
			}
			else{
				order = c_order;
				loadListAdapter();
			}
		}
	}

	private void setLineInfo(){
		// Text FilterInfos
		Line line = handler.getLineInfo(bus.getDeptId(), bus.getDestId());
		fontLoader.setTextViewTypeFace(textDept, line.getDeptName(), R.string.lato_regular, (float)6.88);
		fontLoader.setTextViewTypeFace(textDest, line.getDestName(), R.string.lato_regular, (float)6.88);
		fontLoader.setTextViewTypeFace(textDistance, line.getDistance()+"km", R.string.lato_regular, (float)6.88);
	}
	private void loadTextView() {
		fontLoader.setTextViewTypeFace( textTitle, "CamBUS", R.string.lato_medium, (float)9.17);
		
		fontLoader.setTextViewTypeFace( textEmpty, R.string.no_data, R.string.lato_medium, (float)9.5);
		fontLoader.setTextViewTypeFace( textBtnTime, R.string.sort_time, R.string.lato_regular, (float)8.25);
		fontLoader.setTextViewTypeFace( textBtnPrice, R.string.sort_price, R.string.lato_regular, (float)8.25);
		fontLoader.setTextViewTypeFace( textBtnName, R.string.sort_name, R.string.lato_regular, (float)8.25);
	}
	
	private class ListViewClickListener implements AdapterView.OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Time object = (Time)parent.getItemAtPosition(position);
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
