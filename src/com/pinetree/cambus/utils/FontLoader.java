package com.pinetree.cambus.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;

public class FontLoader {
	public static Typeface getFontTypeface(AssetManager asset, String font_type){
		return Typeface.createFromAsset(asset, font_type);
	}
	/*/
	public static int getFontSizeFromPt(Context con, float pt){
		//mac pt to px
		
		//int size = (int)(pt*3.0);
		DeviceInfo dev = (DeviceInfo)con;
		
		//int size = (int)(pt*dev.getScaledDensity());
		int size = (int)(pt*dev.getDensity());
		//Log.i("DebugPrint","font-size:"+size);
		return size;
		
		// window pt to px
		//return (int) (((pt * 96) / 72) * dpi);
	}
	/**/
}
