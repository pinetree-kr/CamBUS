package com.pinetree.cambus.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pinetree.cambus.BusListActivity;
import com.pinetree.cambus.CityRoutesActivity;
import com.pinetree.cambus.R;
import com.pinetree.cambus.adapters.SpinnerAdapter;
import com.pinetree.cambus.models.DBModel.Bus;
import com.pinetree.cambus.models.DBModel.Company;
import com.pinetree.cambus.models.DBModel.Departure;
import com.pinetree.cambus.models.DBModel.Destination;
import com.pinetree.cambus.models.DBModel.Type;
import com.pinetree.cambus.models.Model;
import com.pinetree.cambus.utils.DBHandler;

public class FilterFragment extends BaseFragment {
	private Spinner
		spinnerDeparture,
		spinnerDestination,
		spinnerTime,
		spinnerCompany,
		spinnerType; 
	private SpinnerAdapter
		adapterDeparture,
		adapterDestination,
		adapterTime,
		adapterCompany,
		adapterType;
	private TextView
		textTitle,
		textDeparture,
		textDestination,
		textTime,
		textType,
		textCompany,
		textSearch;
	
	private View search;
	private DBHandler handler;
	
	public static Fragment getInstances(Model model){
		Fragment fragment = new FilterFragment();
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		Bundle args = this.getArguments();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_filter, container, false);
		
		textTitle = (TextView)view.findViewById(R.id.TextTitle);
		
		ImageView titleBg = (ImageView)view.findViewById(R.id.titleBg);
		Drawable dBg = imageLoader.getResizedDrawable(R.drawable.top);
		titleBg.setImageDrawable(dBg);
		
		// text
		textDeparture = (TextView)view.findViewById(R.id.filter_departure);
		textDestination = (TextView)view.findViewById(R.id.filter_destination);
		textTime = (TextView)view.findViewById(R.id.filter_time);
		textType = (TextView)view.findViewById(R.id.filter_type);
		textCompany = (TextView)view.findViewById(R.id.filter_company);
		
		textSearch = (TextView)view.findViewById(R.id.TextSearch);
		
		// image
		
		ImageView imageTypeInfo = (ImageView)view.findViewById(R.id.imageTypeInfo);
		Drawable dTypeInfo = imageLoader.getResizedDrawable(R.drawable.info_icon);
		imageTypeInfo.setImageDrawable(dTypeInfo);
		
		ImageView imageSearch = (ImageView)view.findViewById(R.id.ImageSearch);
		Drawable dSearch = imageLoader.getResizedDrawable(R.drawable.search_btn);
		imageSearch.setImageDrawable(dSearch);
		
		ImageView imageIntercityBg = (ImageView)view.findViewById(R.id.intercityBg);
		Drawable dInterBg = imageLoader.getResizedDrawable(R.drawable.citybus_flip);
		imageIntercityBg.setImageDrawable(dInterBg);
		
		ImageView imageIntercityBtn = (ImageView)view.findViewById(R.id.intercityButton);
		Drawable dInterBtn = imageLoader.getResizedDrawable(R.drawable.citybus_icon);
		imageIntercityBtn.setImageDrawable(dInterBtn);
		imageIntercityBtn.setTag("routes");
		imageIntercityBtn.setOnClickListener(new OnButtonClickListener());
		
		spinnerDeparture = (Spinner)view.findViewById(R.id.SpinnerDeparture);
		spinnerDeparture.setOnItemSelectedListener(new OnSpinnerItemSelectedListener());
		
		spinnerDestination = (Spinner)view.findViewById(R.id.SpinnerDestination);
		spinnerTime = (Spinner)view.findViewById(R.id.SpinnerTime);
		spinnerType = (Spinner)view.findViewById(R.id.SpinnerType);
		spinnerCompany = (Spinner)view.findViewById(R.id.SpinnerCompany);
		
		search = view.findViewById(R.id.ButtonSearch);
		search.setTag("search");
		search.setOnClickListener(new OnButtonClickListener());
		
		imageTypeInfo.setTag("typeInfo");
		imageTypeInfo.setOnClickListener(new OnButtonClickListener());

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
		
		loadDepartureAdapter();
		loadTimeAdapter();
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
	
	protected void loadTextView(){
		fontLoader.setTextViewTypeFace(textTitle, "CamBUS", R.string.lato_medium, (float)9.17);
		
		fontLoader.setTextViewTypeFace(textDeparture, R.string.departure, R.string.lato_regular, (float)7);
		fontLoader.setTextViewTypeFace(textDestination, R.string.destination, R.string.lato_regular, (float)7);
		fontLoader.setTextViewTypeFace(textTime, R.string.time, R.string.lato_regular, (float)7);
		fontLoader.setTextViewTypeFace(textType, R.string.bus_type, R.string.lato_regular, (float)7);
		fontLoader.setTextViewTypeFace(textCompany, R.string.bus_company, R.string.lato_regular, (float)7);
		fontLoader.setTextViewTypeFace(textSearch, R.string.search, R.string.lato_regular, (float)8);
	}
	
	protected void loadDepartureAdapter(){
		adapterDeparture = new SpinnerAdapter<Departure>(
			this.getActivity().getApplicationContext(),
			R.layout.custom_spinner,
			handler.getDepartureList()
			);
		
		this.spinnerDeparture.setAdapter(adapterDeparture);
		this.spinnerDeparture.setSelection(0);		
	}
	
	protected void loadDestinationAdapter(int dept_no){
		adapterDestination = new SpinnerAdapter<Destination>(
			this.getActivity().getApplicationContext(),
			R.layout.custom_spinner,
			handler.getDestinationList(dept_no)
			);
		
		this.spinnerDestination.setAdapter(adapterDestination);
		this.spinnerDestination.setSelection(0);
	}
	
	protected void loadTimeAdapter(){
		adapterTime = new SpinnerAdapter<Integer>(
			this.getActivity().getApplicationContext(),
			R.layout.custom_spinner,
			handler.getTimeList()
			);
		
		this.spinnerTime.setAdapter(adapterTime);
		this.spinnerTime.setSelection(0);
	}
	protected void loadTypeAdapter(int dept_id, int dest_id){
		adapterType = new SpinnerAdapter<Type>(
				this.getActivity().getApplicationContext(),
				R.layout.custom_spinner,
				handler.getTypeList(dept_id, dest_id)
				);
		
		this.spinnerType.setAdapter(adapterType);
		this.spinnerType.setSelection(0);
	}
	protected void loadCompanyAdapter(int dept_id, int dest_id){
		adapterCompany = new SpinnerAdapter<Company>(
				this.getActivity().getApplicationContext(),
				R.layout.custom_spinner,
				handler.getCompanyList(dept_id, dest_id)
				);
					
		this.spinnerCompany.setAdapter(adapterCompany);
		this.spinnerCompany.setSelection(0);
	}
	
	protected class OnButtonClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			String tag = (String)v.getTag();
			
			if(tag.equals("search")){
				Departure departure = (Departure) spinnerDeparture.getSelectedItem();
				Destination destination = (Destination) spinnerDestination.getSelectedItem();
				Type type = (Type) spinnerType.getSelectedItem();
				Company company = (Company) spinnerCompany.getSelectedItem();
	
				int time = (Integer) spinnerTime.getSelectedItem();
				
				if(departure.getId() < 1){
					Toast.makeText(getActivity().getApplicationContext(), R.string.select_departure, 1000).show();
				}else if(destination.getId() < 1){
					Toast.makeText(getActivity().getApplicationContext(), R.string.select_destination, 1000).show();
				}else if(time<0){
					Toast.makeText(getActivity().getApplicationContext(), R.string.select_time, 1000).show();
				}else{
					Intent intent = new Intent(getActivity(), BusListActivity.class);
					
					Bus bus = new Bus();
					bus.setDeptId(departure.getId());
					bus.setDestId(destination.getId());
					bus.setTypeId(type.getId());
					bus.setCompanyId(company.getId());
					
					intent.putExtra("bus", bus);
					intent.putExtra("time", time);
					
					saInterface.switchActivity(intent, false);
				}
			}else if(tag.equals("typeInfo")){
				//Log.i("DebugPrint","choose type");
				TypeInfoDialogFragment dialog = TypeInfoDialogFragment.getInstances(
						handler.getTypeList(0, 0)
						);
	    		dialog.show(getFragmentManager(), "typeinfo");
			}else if(tag.equals("routes")){
				//Log.i("DebugPrint","city routes");
				
				Intent intent = new Intent(getActivity(), CityRoutesActivity.class);
				saInterface.switchActivity(intent, false);
			}
		}
		
	}
	
	protected class OnSpinnerItemSelectedListener implements OnItemSelectedListener{
				@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			if(view!=null){
				Object object = parent.getItemAtPosition(position);
				//Object object = view.getTag();
				int dept_id = 0;
				int dest_id = 0;
				if(object.getClass().equals(Departure.class)){
					Departure model = (Departure)object;
					dept_id = model.getId();
					loadDestinationAdapter(dept_id);
				}
				else if(object.getClass().equals(Destination.class)){
					Destination model = (Destination)object;
					dest_id = model.getId();
				}
				
				loadTypeAdapter(dept_id, dest_id);
				loadCompanyAdapter(dept_id, dest_id);
				
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	}
}
