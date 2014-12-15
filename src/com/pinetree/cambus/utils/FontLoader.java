package com.pinetree.cambus.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

public class FontLoader {
	public static Typeface getFontTypeface(Context context, int font){
		String fontType = context.getResources().getText(font).toString();
		return Typeface.createFromAsset(context.getAssets(), fontType);
	}
	
	public static void setTextViewTypeFace(Context context, TextView view, int font, float size){
		view.setTypeface(FontLoader.getFontTypeface(
				context,
				font));
		view.setTextSize(TypedValue.COMPLEX_UNIT_PT, size);
	}
	public static void setTextViewTypeFace(Context context, TextView view, int text, int font, float size){
		view.setText(text);
		//view.setTextColor(color);
		setTextViewTypeFace(context, view, font, size);
	}
	
	public static void setTextViewTypeFace(Context context, TextView view, String text, int font, float size){
		view.setText(text);
		//view.setTextColor(color);
		setTextViewTypeFace(context, view, font, size);
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
