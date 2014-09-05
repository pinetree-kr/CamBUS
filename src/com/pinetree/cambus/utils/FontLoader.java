package com.pinetree.cambus.utils;

import android.content.res.AssetManager;
import android.graphics.Typeface;

public class FontLoader {
	public static Typeface getFontTypeface(AssetManager asset, String font_type){
		return Typeface.createFromAsset(asset, font_type);
	}
	
	public static int getFontSizeFromPt(float dpi, float pt){
		//mac pt to px
		return (int) (pt * dpi);
		
		// window pt to px
		//return (int) (((pt * 96) / 72) * dpi);
	}
}
