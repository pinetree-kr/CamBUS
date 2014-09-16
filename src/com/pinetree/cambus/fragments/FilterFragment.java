package com.pinetree.cambus.fragments;

import com.pinetree.cambus.R;
import com.pinetree.cambus.adapters.SpinnerAdapter;
import com.pinetree.cambus.models.BusListModel;
import com.pinetree.cambus.models.BusFilterModel;
import com.pinetree.cambus.models.DepartureModel;
import com.pinetree.cambus.models.DestinationModel;
import com.pinetree.cambus.models.TypeModel;
import com.pinetree.cambus.utils.DBHandler;
import com.pinetree.cambus.utils.FontLoader;
import com.pinetree.cambus.utils.ImageLoader;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FilterFragment extends BaseFragment {
	protected Spinner
		spinnerDeparture,
		spinnerDestination,
		spinnerTime,
		spinnerType; 
	protected SpinnerAdapter
		adapterDeparture,
		adapterDestination,
		adapterTime,
		adapterType;
	protected TextView
		textDeparture,
		textDestination,
		textTime,
		textType,
		textSearch;
	
	protected BusFilterModel filter;
	
	protected View search;
	protected DBHandler handler;
	
	public FilterFragment(BusFilterModel filter){
		this.filter = filter;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_filter, container, false);
		
		//view.setBackgroundResource(R.drawable.background);
		
		// title
		TextView textTitle = (TextView)view.findViewById(R.id.TextTitle);
		textTitle.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		textTitle.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, 8));
		
		View titleBg = view.findViewById(R.id.TitleBackGround);
		titleBg.setBackgroundResource(R.drawable.top);
		
		// text
		textDeparture = (TextView)view.findViewById(R.id.filter_departure);
		textDestination = (TextView)view.findViewById(R.id.filter_destination);
		textTime = (TextView)view.findViewById(R.id.filter_time);
		textType = (TextView)view.findViewById(R.id.filter_type);
		
		textSearch = (TextView)view.findViewById(R.id.TextSearch);
		
		// image
		
		ImageView imageSearch = (ImageView)view.findViewById(R.id.ImageSearch);
		ImageView imageBus = (ImageView)view.findViewById(R.id.ImageBus);
		ImageView imageTri = (ImageView)view.findViewById(R.id.ImageTri);
		/*/
		ImageView imageBackground = (ImageView)view.findViewById(R.id.ImageFilterBackGround);
		
		Drawable dBg = ImageLoader.getResizedDrawableFromRes(
				getResources(),
				R.drawable.background,
				app.rateDpi,
				app.rateWidth,
				app.rateHeight
				);
		imageBackground.setImageDrawable(dBg);
		/**/
		Drawable dSearch = ImageLoader.getResizedDrawableFromRes(
				getResources(),
				R.drawable.searchbtn,
				app.rateDpi,
				app.rateWidth,
				app.rateHeight
				);
		imageSearch.setImageDrawable(dSearch);
		
		Drawable dBus = ImageLoader.getResizedDrawableFromRes(
				getResources(),
				R.drawable.bus,
				app.rateDpi,
				app.rateWidth,
				app.rateHeight
				);
		imageBus.setImageDrawable(dBus);
		Drawable dTri = ImageLoader.getResizedDrawableFromRes(
				getResources(),
				R.drawable.arrow,
				app.rateDpi,
				app.rateWidth,
				app.rateHeight
				);
		imageTri.setImageDrawable(dTri);
		
		spinnerDeparture = (Spinner)view.findViewById(R.id.SpinnerDeparture);
		spinnerDeparture.setOnItemSelectedListener(new OnSpinnerItemSelectedListener());
		
		spinnerDestination = (Spinner)view.findViewById(R.id.SpinnerDestination);
		spinnerTime = (Spinner)view.findViewById(R.id.SpinnerTime);
		spinnerType = (Spinner)view.findViewById(R.id.SpinnerType);
		
		search = view.findViewById(R.id.ButtonSearch);
		search.setOnClickListener(new OnButtonClickListener());

		loadDepartureAdapter();
		loadTimeAdapter();
		loadTypeAdapter();
		
		return view;
	}

	@Override
	public void onStop(){
		super.onStop();
	}
	@Override
	public void onDestroyView(){
		super.onDestroyView();
		handler.close();
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
		handler = DBHandler.open(getActivity().getApplicationContext());
	}
	@Override
	public void onStart(){
		super.onStart();
	}
	@Override
	public void onResume(){
		super.onResume();
	}
	
	@Override
	protected void loadTextView(){
		textDeparture.setText(R.string.departure);
		textDestination.setText(R.string.destination);
		textTime.setText(R.string.time);
		textType.setText(R.string.bus_type);
		textSearch.setText(R.string.search);
		
		textDeparture.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		textDeparture.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)7.2));
		textDestination.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		textDestination.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)7.2));
		textTime.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		textTime.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)7.2));
		textType.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		textType.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)7.2));
		textSearch.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		textSearch.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, 7));
	}
	
	protected void loadDepartureAdapter(){
		adapterDeparture = new SpinnerAdapter<DepartureModel>(
			this.getActivity().getApplicationContext(),
			R.layout.custom_spinner,
			filter.getDepartureList());
		
		this.spinnerDeparture.setAdapter(adapterDeparture);
		this.spinnerDeparture.setSelection(0);		
	}
	
	protected void loadDestinationAdapter(){
		adapterDestination = new SpinnerAdapter<DestinationModel>(
			this.getActivity().getApplicationContext(),
			R.layout.custom_spinner,
			filter.getDestinationList());
		
		this.spinnerDestination.setAdapter(adapterDestination);
		this.spinnerDestination.setSelection(0);
	}
	
	protected void loadTimeAdapter(){
		adapterTime = new SpinnerAdapter<Integer>(
			this.getActivity().getApplicationContext(),
			R.layout.custom_spinner,
			filter.getTimeList());
		
		this.spinnerTime.setAdapter(adapterTime);
		this.spinnerTime.setSelection(0);
	}
	protected void loadTypeAdapter(){
		adapterType = new SpinnerAdapter<TypeModel>(
				this.getActivity().getApplicationContext(),
				R.layout.custom_spinner,
				filter.getTypeList());
		
		this.spinnerType.setAdapter(adapterType);
		this.spinnerType.setSelection(0);
	}
	
	
	protected class OnButtonClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			DepartureModel departure = (DepartureModel) spinnerDeparture.getSelectedItem();
			DestinationModel destination = (DestinationModel) spinnerDestination.getSelectedItem();
			int time = (Integer) spinnerTime.getSelectedItem();
			TypeModel type = (TypeModel) spinnerType.getSelectedItem();
			
			if(departure.getCityNo()<0){
				Toast.makeText(getActivity().getApplicationContext(), "Select Departure", 1000).show();
			}else if(destination.getCityNo()<0){
				Toast.makeText(getActivity().getApplicationContext(), "Select Destination", 1000).show();
			}else if(time<0){
				Toast.makeText(getActivity().getApplicationContext(), "Select Time", 1000).show();
			}else{
				BusListModel bus_list = new BusListModel();
				bus_list.setDeparture(departure);
				bus_list.setDestination(destination);
				bus_list.setTime(time);
				bus_list.setType(type);
				
				bus_list.getBusList(handler);
				Fragment fragment = new BusListFragment(bus_list);
				sfInterface.switchFragment(fragment, false);
			}
		}
		
	}
	
	protected class OnSpinnerItemSelectedListener implements OnItemSelectedListener{
				@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			if(view!=null){
				Object object = view.getTag();
				
				if(object.getClass().equals(DepartureModel.class)){
					DepartureModel model = (DepartureModel)object;
					if(model.getCityNo()>=0){
						filter.updateDestinationList(handler, model.getCityNo());
					}else{
						filter.getDestinationList().clear();
						filter.getDestinationList().add(new DestinationModel());
					}
					loadDestinationAdapter();
				}else if(object.getClass().equals(DestinationModel.class)){
					//DestinationModel model = (DestinationModel)object;
				}
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	}
}
