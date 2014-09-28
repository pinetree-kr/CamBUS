package com.pinetree.cambus.utils;

import android.app.Application;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class DeviceInfo extends Application{
	private final String PACKAGE_NAME = "CamBUS";
	
	public DisplayMetrics displayMetrics;
	
	public float rateDpi;
	public float rateWidth;
	public float rateHeight;
	public float maxWidth;
	public float maxHeight;
	
	private final int oWidth = 640;
	private final int oHeight = 1136;
	
	
	@Override
	public void onCreate(){
		super.onCreate();
		getScreenInfo();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
	}
	
	private void getScreenInfo(){
		WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
		displayMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(displayMetrics);
		
		/*
		Log.i("DebugPrint","dpi:"+displayMetrics.densityDpi);
		Log.i("DebugPrint","width:"+displayMetrics.widthPixels);
		Log.i("DebugPrint","height:"+displayMetrics.heightPixels);
		*/
		rateDpi = displayMetrics.scaledDensity;
		rateWidth = (float)displayMetrics.widthPixels/oWidth;
		rateHeight = (float)displayMetrics.heightPixels/oHeight;
		maxWidth = displayMetrics.widthPixels;
		maxHeight = displayMetrics.heightPixels;
		
		Log.i("DebugPrint","width:"+rateWidth);
		Log.i("DebugPrint","height:"+rateHeight);
	}
}
