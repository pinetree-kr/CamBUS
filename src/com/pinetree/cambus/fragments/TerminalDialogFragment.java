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
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
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
	
	@SuppressWarnings("unchecked")
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
		super.onStop();
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
	@Override
	public void onDestroyView(){
		super.onDestroyView();
		KilledView();
		
	}
	@Override
	public void onDismiss(DialogInterface dialog){
		super.onDismiss(dialog);
		KilledView();
	}
	
	@Override
	public void onDetach(){
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

		int height = (int)(((DeviceInfo)getActivity().getApplicationContext()).maxHeight*7/10);
		int width = (int)(((DeviceInfo)getActivity().getApplicationContext()).maxWidth*9/10);
		getDialog().getWindow().setLayout(width, height);
		
		for(Terminal terminal : terminal_list){
			drawMarker(map, terminal);
		}
	}
	public void drawMarker(GoogleMap map, Terminal terminal){
		MarkerOptions marker = new MarkerOptions();
		
		marker.position(terminal.getLocation());
		marker.title(terminal.getTerminalName());
		marker.snippet(terminal.getPhoneNo() +"\n" + terminal.getAddress());
		
		map.addMarker(marker).showInfoWindow();
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(terminal.getLocation(), 13));
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}
	
	protected class MarkerInfoWindowAdapter implements InfoWindowAdapter{
		int width = (int)(((DeviceInfo)getActivity().getApplicationContext()).maxWidth*7/10);
		
		@Override
		public View getInfoContents(Marker marker) {
			
			View view = getActivity().getLayoutInflater().inflate(R.layout.custom_marker, null);
			
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					width,
					LinearLayout.LayoutParams.WRAP_CONTENT
					);
			view.setLayoutParams(lp);
			
			TextView terminalTitle = (TextView)view.findViewById(R.id.TerminalTitle);
			TextView terminalPhoneNo = (TextView)view.findViewById(R.id.TerminalPhoneNo);
			TextView terminalAddress = (TextView)view.findViewById(R.id.TerminalAddress);
			
			terminalTitle.setText(marker.getTitle());
			
			String[] terminalInfo = marker.getSnippet().split("\n");
			
			terminalPhoneNo.setText(terminalInfo[0]);
			terminalAddress.setText(terminalInfo[1]);
			
			
			//view.getLayoutParams();
			//Log.i("DebugPrint","param:"+view.getLayoutParams());
			
			/*
			LayoutParams lp = view.getLayoutParams();
			//lp.width = (int)(lp.width*9.0/10);
			Log.i("DebugPrint","width:"+lp.width);
			*/
			return view;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
