package com.pinetree.cambus.fragments;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pinetree.cambus.R;
import com.pinetree.cambus.models.DBModel.Terminal;
import com.pinetree.cambus.models.Model;
import com.pinetree.cambus.utils.DeviceInfo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TerminalDialogFragment extends DialogFragment {
	private boolean isModal;
	private ArrayList<Terminal> terminal_list;
	//private MapView mapView;
	private MapFragment mapView;
	private GoogleMap map;
	
	
	public static TerminalDialogFragment getInstances(ArrayList<Terminal> model){
		Bundle args = new Bundle();
		args.putSerializable("model", model);
		
		TerminalDialogFragment dialog = new TerminalDialogFragment();
		dialog.setArguments(args);
		
		return dialog;
	}
	

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		View view = inflater.inflate(R.layout.fragment_dialog_terminal, null);
		
		builder.setView(view);
		setupUI(view);			
		isModal = true;
		
		return builder.create();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Bundle args = this.getArguments();
		if(args != null){
			terminal_list = (ArrayList<Terminal>)args.getSerializable("model");
		}
	}
	
	@Override
	public void onStop(){
		Log.i("DebugPrint","dialogStop");
		super.onStop();
	}
	@Override
	public void onDestroy(){
		Log.i("DebugPrint","dialogDestroy");
		super.onDestroy();
	}
	@Override
	public void onDestroyView(){
		Log.i("DebugPrint","dialogDestroyView");
		super.onDestroyView();
		KilledView();
		
	}
	@Override
	public void onDismiss(DialogInterface dialog){
		Log.i("DebugPrint","dialogDismiss");
		super.onDismiss(dialog);
		KilledView();
	}
	
	@Override
	public void onDetach(){
		Log.i("DebugPrint","dialogDetach");
		super.onDetach();
		KilledView();
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if(isModal){
			return super.onCreateView(inflater, container, savedInstanceState);
		}else{
			View view = inflater.inflate(R.layout.fragment_dialog_terminal, container, false);
			setupUI(view);
			return view;
		}
		/*/
		if(view!=null)
			return super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_dialog_terminal, container, false);
		setupUI(view);
		return view;
		*/
	}
	
	public void setupUI(View view){
		mapView = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
		map = mapView.getMap();
		map.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
	}
	
	private void KilledView(){
		if(mapView != null){
			FragmentManager fm = getFragmentManager();
			fm.beginTransaction().remove(mapView).commit();
		}
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
	}
	@Override
	public void onStart(){
		super.onStart();

		int height = (int)(((DeviceInfo)getActivity().getApplicationContext()).maxHeight*3/5);
		int width = (int)(((DeviceInfo)getActivity().getApplicationContext()).maxWidth*4/5);
		getDialog().getWindow().setLayout(width, height);
		
		LatLng latlng = new LatLng(terminal_list.get(0).getLat(),terminal_list.get(0).getLng());
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 13));
		
		for(Terminal terminal : terminal_list){
			drawMarker(map, terminal);
			/*
			MarkerOptions marker;
			marker = new MarkerOptions();
			marker.position(latlng);
			
			String title = terminal.getTerminalName()+"\n" + 
					terminal.getPhoneNo()+"\n" +
					terminal.getAddress();
			//Log.i("DebugPrint",title);
			marker.title(title);
			map.addMarker(marker);
			*/
		}
	}
	public void drawMarker(GoogleMap map, Terminal terminal){
		MarkerOptions marker = new MarkerOptions();
		marker.position(new LatLng(terminal.getLat(), terminal.getLng()));
		//marker.title(terminal.getTerminalName());
		
		map.addMarker(marker);
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}
	
	protected class MarkerInfoWindowAdapter implements InfoWindowAdapter{

		@Override
		public View getInfoContents(Marker marker) {

			View view = getActivity().getLayoutInflater().inflate(R.layout.custom_marker, null);
			TextView terminalName = (TextView)view.findViewById(R.id.TerminalName);
			TextView terminalPhoneNo = (TextView)view.findViewById(R.id.TerminalPhoneNo);
			TextView terminalAddress = (TextView)view.findViewById(R.id.TerminalAddress);
			
			//terminalName.setText();
			return null;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

}
