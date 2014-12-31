package com.pinetree.cambus.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pinetree.cambus.R;
import com.pinetree.cambus.adapters.ModelListAdapter;
import com.pinetree.cambus.models.DBModel.CityRoute;
import com.pinetree.cambus.utils.DBHandler;

public class CityRouteFragment extends BaseFragment {
	private DBHandler handler;
	private TextView textTitle, textRouteInfo;
	private ListView listView;
	private ListAdapter listAdapter;
	
	private int line_no;
	private ImageView imageLine[] = new ImageView[3];
	
	public static Fragment getInstances(int line_no){
		Bundle args = new Bundle();
		
		args.putInt("line_no", line_no);
		Fragment fragment = new CityRouteFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		Bundle args = this.getArguments();
		if(args != null){
			line_no = (int) args.getInt("line_no");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_city_route, container, false);
		
		ImageView imageTitle = (ImageView)view.findViewById(R.id.titleBg);
		imageTitle.setBackgroundDrawable(
				imageLoader.getResizedDrawable(R.drawable.top));
		textTitle = (TextView)view.findViewById(R.id.TextTitle);
		textRouteInfo = (TextView)view.findViewById(R.id.ListHeader);
		
		imageLine[0] = (ImageView)view.findViewById(R.id.RouteBtnLine1);
		imageLine[0].setTag(0);
		imageLine[0].setOnClickListener(new OnButtonClickListener());
		
		imageLine[1] = (ImageView)view.findViewById(R.id.RouteBtnLine2);
		imageLine[1].setTag(1);
		imageLine[1].setOnClickListener(new OnButtonClickListener());
		
		imageLine[2] = (ImageView)view.findViewById(R.id.RouteBtnLine3);
		imageLine[2].setTag(2);
		imageLine[2].setOnClickListener(new OnButtonClickListener());
		
		listView = (ListView) view.findViewById(R.id.ListView);
		listView.setEmptyView(view.findViewById(R.id.EmptyListView));
		
		return view;
	}

	@Override
	public void onStop(){
		super.onStop();
	}
	@Override
	public void onDestroyView(){
		super.onDestroyView();
		//Log.i("DebugPrint","destroy??in filter");
		//handler.close();
	}

	@Override
	public void onDestroy(){
		super.onDestroyView();
	}
	
	@Override
	public void onDetach(){
		super.onDetach();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		handler = DBHandler.getInstance(getActivity().getApplicationContext(), true);
		
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
	
	public void loadListAdapter(){
		listAdapter = new ModelListAdapter<CityRoute>(
				this.getActivity().getApplicationContext(),
				R.layout.route_stations_list_row,
				handler.getCityRouteList(0, line_no+1)
				);
		listView.setAdapter(listAdapter);
		onClickedOrderButton();
	}
	public void onClickedOrderButton(){
		// 선택된 것에 맞게 변경 
		String[] selectedIds = getResources().getStringArray(R.array.line_selected);
		String[] unselectedIds = getResources().getStringArray(R.array.line_unselected);
		
		int dLine;
		for(int i=0; i<imageLine.length; i++){
			//selected
			if(line_no==i){
				dLine = getResources().getIdentifier(selectedIds[i],"drawable",this.getActivity().getPackageName());
			}
			//unselected
			else{
				dLine = getResources().getIdentifier(unselectedIds[i],"drawable",this.getActivity().getPackageName());				
			}
			imageLine[i].setImageDrawable(imageLoader.getResizedDrawable(dLine));
		}
	}
	protected void loadTextView(){
		fontLoader.setTextViewTypeFace(
				textTitle,
				"City bus routes",
				R.string.lato_medium,
				(float)9.17);
		fontLoader.setTextViewTypeFace(
				textRouteInfo,
				R.string.route_info,
				R.string.lato_medium,
				(float)6.2);
	}
	protected class OnButtonClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			int tag = (Integer)v.getTag();
			
			if(line_no!=tag){
				line_no = tag;
				loadListAdapter();
			}			
		}
	}
	
}
