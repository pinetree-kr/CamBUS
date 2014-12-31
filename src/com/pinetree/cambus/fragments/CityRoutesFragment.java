package com.pinetree.cambus.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinetree.cambus.CityRouteActivity;
import com.pinetree.cambus.R;
import com.pinetree.cambus.models.Model;

public class CityRoutesFragment extends BaseFragment {
	
	private TextView textTitle;
	private TextView textRoute1, textRoute2, textRoute3;
	private TextView textVia1, textVia2, textVia3;
	
	public static Fragment getInstances(Model model){
		Fragment fragment = new CityRoutesFragment();
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_city_routes, container, false);
		
		ImageView imageTitle = (ImageView)view.findViewById(R.id.titleBg);
		imageTitle.setBackgroundDrawable(
				imageLoader.getResizedDrawable(R.drawable.top));
		textTitle = (TextView)view.findViewById(R.id.TextTitle);
		
		ImageView imageLine1 = (ImageView)view.findViewById(R.id.RouteBtnLine1);
		imageLine1.setBackgroundDrawable(
				imageLoader.getResizedDrawable(R.drawable.city_line_1));
		imageLine1.setTag(0);
		imageLine1.setOnClickListener(new OnButtonClickListener());
		ImageView imageLine2 = (ImageView)view.findViewById(R.id.RouteBtnLine2);
		imageLine2.setBackgroundDrawable(
				imageLoader.getResizedDrawable(R.drawable.city_line_2));
		imageLine2.setTag(1);
		imageLine2.setOnClickListener(new OnButtonClickListener());
		ImageView imageLine3 = (ImageView)view.findViewById(R.id.RouteBtnLine3);
		imageLine3.setBackgroundDrawable(
				imageLoader.getResizedDrawable(R.drawable.city_line_3));
		imageLine3.setTag(2);
		imageLine3.setOnClickListener(new OnButtonClickListener());
		
		textRoute1 = (TextView)view.findViewById(R.id.RouteTextLine1);
		textRoute2 = (TextView)view.findViewById(R.id.RouteTextLine2);
		textRoute3 = (TextView)view.findViewById(R.id.RouteTextLine3);
		textVia1 = (TextView)view.findViewById(R.id.RouteTextViaLine1);
		textVia2 = (TextView)view.findViewById(R.id.RouteTextViaLine2);
		textVia3 = (TextView)view.findViewById(R.id.RouteTextViaLine3);
		
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
		//handler = DBHandler.getInstance(getActivity().getApplicationContext(), true);
		
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
		fontLoader.setTextViewTypeFace(
				textTitle,
				"City bus routes",
				R.string.lato_medium,
				(float)9.17);
		
		fontLoader.setTextViewTypeFace(
				textRoute1,
				R.string.route1,
				R.string.lato_regular,
				(float)6.53);
		fontLoader.setTextViewTypeFace(
				textRoute2,
				R.string.route2,
				R.string.lato_regular,
				(float)6.53);
		fontLoader.setTextViewTypeFace(
				textRoute3,
				R.string.route3,
				R.string.lato_regular,
				(float)6.53);
		fontLoader.setTextViewTypeFace(
				textVia1,
				R.string.via1,
				R.string.lato_regular,
				(float)6.42);
		fontLoader.setTextViewTypeFace(
				textVia2,
				R.string.via2,
				R.string.lato_regular,
				(float)6.42);
		fontLoader.setTextViewTypeFace(
				textVia3,
				R.string.via3,
				R.string.lato_regular,
				(float)6.42);
	}
	protected class OnButtonClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			Integer tag = (Integer)v.getTag();
			if(tag>=0){
				Intent intent = new Intent(getActivity(), CityRouteActivity.class);
				intent.putExtra("line_no", tag);
				saInterface.switchActivity(intent, false);
			}
		}
	}
	
}
