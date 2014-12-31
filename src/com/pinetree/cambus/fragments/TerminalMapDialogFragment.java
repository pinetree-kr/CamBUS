package com.pinetree.cambus.fragments;

import java.util.ArrayList;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;

import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pinetree.cambus.R;
import com.pinetree.cambus.models.DBModel.Terminal;
import com.pinetree.cambus.utils.DeviceInfo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class TerminalMapDialogFragment extends DialogFragment {
	private ArrayList<Terminal> terminal_list;
	//private LinearLayout infoView;
	private SupportMapFragment mapView;
	private GoogleMap googleMap;
	public static TerminalMapDialogFragment getInstances(ArrayList<Terminal> model){
		Bundle args = new Bundle();
		args.putSerializable("model", model);
		
		TerminalMapDialogFragment dialog = new TerminalMapDialogFragment();
		dialog.setArguments(args);
		
		return dialog;
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
	}
	@Override
	public void onDismiss(DialogInterface dialog){
		super.onDismiss(dialog);
	}
	
	@Override
	public void onDetach(){
		super.onDetach();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Window window = getDialog().getWindow();
		window.requestFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.fragment_dialog_terminal_map, container, false);
		setupUI(view);
		
		return view;
	}
	
	public void setupUI(View view){
		mapView = SupportMapFragment.newInstance();
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		ft.add(R.id.map, mapView);
		ft.commit();
		mapView.getMapAsync(new gMapCallback());
	}
	
	private class gMapCallback implements OnMapReadyCallback{
		@Override
		public void onMapReady(GoogleMap googleMap) {
			googleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());

			for(Terminal terminal : terminal_list){
				//TODO: 좌표가 없는 터미널은 어떻게 표시해줄것인가? 연락처라도 알아내야함
				if(terminal.hasPosition())
					drawMarker(googleMap, terminal);
			}
		}
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
	}
	@Override
	public void onStart(){
		Window window = getDialog().getWindow();
		
		int height = (int)(((DeviceInfo)getActivity().getApplicationContext()).getHeight()*7/10);
		int width = (int)(((DeviceInfo)getActivity().getApplicationContext()).getWidth()*9/10);
		window.setLayout(width, height);
		window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		super.onStart();
	}
	public void drawMarker(GoogleMap map, Terminal terminal){
		MarkerOptions marker = new MarkerOptions();
		
		marker.position(terminal.getPosition());
		marker.title(terminal.getName());
		String snippet = "";
		if(terminal.getAddress()!=null){
			snippet = terminal.getAddress();
		}
		marker.snippet(snippet+"\n"+terminal.getPhone());
		if(map!=null){
			map.addMarker(marker).showInfoWindow();
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(terminal.getPosition(), 12));
			map.setOnInfoWindowClickListener(new InfoWindowClickListener());
		}
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}
	
	protected class InfoWindowClickListener implements OnInfoWindowClickListener{
		@Override
		public void onInfoWindowClick(Marker marker) {
			String snippet = marker.getTitle()+"\n"+marker.getSnippet();
			TerminalInfoDialogFragment dialog = TerminalInfoDialogFragment.getInstances(snippet);
			dialog.show(getFragmentManager(), "terminal_info");
		}
	
	}
	
	protected class MarkerInfoWindowAdapter implements InfoWindowAdapter{
		int width = (int)(((DeviceInfo)getActivity().getApplicationContext()).getWidth()*7/10);
		
		@Override
		public View getInfoContents(Marker marker) {
			LinearLayout view = new LinearLayout(getActivity().getApplicationContext());
			
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					//width,
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT
					);
			view.setOrientation(LinearLayout.VERTICAL);
			lp.setMargins(5, 5, 5, 5);
			view.setLayoutParams(lp);
			
			//LayoutParams params1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			LayoutParams params1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

			TextView tTitle = new TextView(getActivity().getApplicationContext());
			tTitle.setGravity(Gravity.CENTER);
			params1.setMargins(5, 0, 0, 10);
			tTitle.setLayoutParams(params1);
			tTitle.setTextColor(Color.BLACK);
			tTitle.setTypeface(null, Typeface.BOLD);
			//tTitle.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
			
			tTitle.setText(marker.getTitle());
			view.addView(tTitle);

			return view;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
