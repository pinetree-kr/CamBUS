package com.pinetree.cambus.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class DeviceInfo extends Application{
	private final String PACKAGE_NAME = "CamBUS";
	private final float WIDTH = 360;
	private final float HEIGHT = 640;
	
	private SharedPreferences sharedPref;
	
	private float deviceWidth;
	private float deviceHeight;
	/*/
	public DisplayMetrics displayMetrics;	
	private float density;
	private float rateWidth;
	private float rateHeight;
	private float maxWidth;
	private float maxHeight;
	private double screenSize; 
	/**/
	private float scaledRate;
	
	private int rotation;
	
	public String getVersion(){
		String version = "";
		try {
			PackageInfo i = this.getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
			version = i.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}
	
	public double getWidth(){
		return (double)deviceWidth;
	}
	public double getHeight(){
		return (double)deviceHeight;
	}
	
	/*/
	public double getRateWidth(){
		return (double)rateWidth;
	}
	public double getRateHeight(){
		return (double)rateHeight;
	}
	
	public double getDpi(){
		return (int)(Math.sqrt(Math.pow(maxWidth,2) + Math.pow(maxHeight, 2))/screenSize);
	}
	public double getDensity(){
		return density;
	}
	public double getScaledDensity(){
		return (int)((double)getDpi()/160);
	}
	/**/
	public float getScaledRate(){
		return scaledRate;
	}
	/*/
	private final int oWidth = 640;
	private final int oHeight = 1136;
	/**/
	
	@Override
	public void onCreate(){
		super.onCreate();
		getSharedPref();
		getScreenInfo();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
	}
	
	private void getSharedPref(){
		sharedPref = getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);
		
	}
	public long getDBLastUpdate(){
		return sharedPref.getLong("db_lastupdate", 0);
	}
	public void setDBLastUpdate(long date){
		SharedPreferences.Editor sharedEditor = sharedPref.edit();
		sharedEditor.putLong("db_lastupdate", date);
		sharedEditor.commit();
	}
	public boolean checkDBLastUpdate(long date){
		long last = getDBLastUpdate();

		// is synched
		if(last>0 && last==date)
			return true;
		// new
		else
			return false;
	}
	
	private void getScreenInfo(){
		WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		rotation = display.getRotation();
		
		DisplayMetrics displayMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(displayMetrics);
		
		/*/
		Log.i("DebugPrint","dpi:"+displayMetrics.densityDpi);
		Log.i("DebugPrint","scaledDpi:"+displayMetrics.scaledDensity);
		Log.i("DebugPrint","xDpi:"+displayMetrics.xdpi);
		Log.i("DebugPrint","yDpi:"+displayMetrics.ydpi);
		Log.i("DebugPrint","width:"+displayMetrics.widthPixels);
		Log.i("DebugPrint","height:"+displayMetrics.heightPixels);
		/**/
		Log.i("DebugPrint","density:"+displayMetrics.density);
		
		float density = displayMetrics.scaledDensity;
		deviceWidth = displayMetrics.widthPixels;
		deviceHeight = displayMetrics.heightPixels;
		/*/
		rateWidth = (float)maxWidth/oWidth;
		rateHeight = (float)maxHeight/oHeight;
		/**/
		//Log.i("DebugPrint","width*density:"+WIDTH*density);
		//Log.i("DebugPrint","deviceWidth:"+deviceWidth);
		if(WIDTH*density>deviceWidth){
			scaledRate = deviceWidth/(WIDTH*density);
			Log.i("DebugPrint","rate"+scaledRate);
		}else{
			scaledRate = 1;
		}
		
		/*/
		double x = Math.pow(displayMetrics.widthPixels/displayMetrics.xdpi, 2);
		double y = Math.pow(displayMetrics.heightPixels/displayMetrics.ydpi, 2);
		screenSize = Math.sqrt(x+y);
		
		Log.i("DebugPrint","size:"+screenSize);
		/**/
	}
}
