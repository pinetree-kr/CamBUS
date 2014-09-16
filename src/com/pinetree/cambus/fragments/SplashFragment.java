package com.pinetree.cambus.fragments;

import com.pinetree.cambus.R;
import com.pinetree.cambus.interfaces.FragmentCallbackInterface;
import com.pinetree.cambus.models.BusFilterModel;
import com.pinetree.cambus.models.Model;
import com.pinetree.cambus.utils.DBHandler;
import com.pinetree.cambus.utils.DeviceInfo;
import com.pinetree.cambus.utils.ExcelFileInfo;
import com.pinetree.cambus.utils.FontLoader;
import com.pinetree.cambus.utils.ImageLoader;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashFragment extends BaseFragment {
	protected BusFilterModel filter;
	protected DBHandler handler;
	
	protected ProgressDialog dialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		filter = new BusFilterModel();
		this.setHasOptionsMenu(false);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_splash, container, false);
		
		view.setBackgroundResource(R.drawable.bg);
		
		// title image
		ImageView imageTitle = (ImageView)view.findViewById(R.id.imageTitle);
		Drawable dTitle = ImageLoader.getResizedDrawableFromRes(
				getResources(),
				R.drawable.logo,
				app.rateDpi,
				app.rateWidth,
				app.rateHeight
				);
		//imageTitle.setImageResource(R.drawable.logo);
		imageTitle.setImageDrawable(dTitle);
		
		// title text
		TextView textTitle = (TextView)view.findViewById(R.id.TextTitle);
		//textTitle.setText(R.string.app_name);
		textTitle.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-UltLt.otf"));
		textTitle.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, 19));
		
		
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
		
		/**/
		filter.setCallback(new SplashCallbackInterface());
		filter.getBusData(getActivity(), handler);
		/**/
	}
	@Override
	public void onResume(){
		super.onResume();
		
	}
	
	@Override
	public void onDestroyView(){
		super.onDestroyView();
		handler.close();
	}
	
	protected class SplashCallbackInterface implements FragmentCallbackInterface{
		@Override
		public void onCallback(Model object) {
			Fragment fragment = new FilterFragment((BusFilterModel)object);
			
			sfInterface.switchFragment(fragment, 2000, true);
		}
		
	}

	@Override
	protected void loadTextView() {
		// TODO Auto-generated method stub
		
	}
}
