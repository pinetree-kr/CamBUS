package com.pinetree.cambus.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class ImageLoader {
	
	public static int calculateInSampleSize(
			BitmapFactory.Options options, int reqWidth, int reqHeight){
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if( height > reqHeight || width > reqWidth){
			final int heightRatio = Math.round((float)height / (float)reqHeight);
			final int widthRatio = Math.round((float)width / (float)reqWidth);
			
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		
		return inSampleSize;
	}
	
	public static Bitmap decodeSampleBitmapFromResource(
			Resources res, int resId, int reqWidth, int reqHeight){
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(
				res,
				resId,
				options);
		
		options.inSampleSize = calculateInSampleSize(
				options,
				reqWidth,
				reqHeight);
		
		// 로드하기 위해서는 위에서 true 로 설정했던 inJustDecodeBounds 의 값을 false 로 설정합니다. 
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}
	
	// bitmap to drawable
	public static Drawable getDrawableFromBitmap(Resources res, Bitmap bitmap){
		Drawable d = new BitmapDrawable(res, bitmap);
		return d;
	}
	
	public static Drawable getDrawableFixedSizeFromRes(
			Resources res, int resId, int reqWidth, int reqHeight){
		Bitmap bitmap = decodeSampleBitmapFromResource(
				res, resId, reqWidth, reqHeight);
		return getDrawableFromBitmap(res, bitmap);
	}
	
	public static Bitmap getResizedBitmap(
			Bitmap bitmap, float rateDpi, float rateWidth, float rateHeight){
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		
		int reqWidth = (int)(width * rateWidth / rateDpi);
		int reqHeight = (int)(height * rateHeight / rateDpi);
		
		Bitmap resized = null;
		resized = Bitmap.createScaledBitmap(
				bitmap, reqWidth, reqHeight, true);
		
		return resized;
	}
	
	public static Bitmap getResizedBitmap(Bitmap bitmap, int reqWidth){
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		
		//Log.i("DebugPrint","image-w:"+width);
		//Log.i("DebugPrint","image-h:"+height);
		Bitmap resized = null;
		while(width>reqWidth){
			resized = Bitmap.createScaledBitmap(
					bitmap, reqWidth, (height*reqWidth)/width, true);
			height = resized.getHeight();
			width = resized.getWidth();
		}
		return resized;
	}
	
	public static Drawable getResizedDrawableFromRes(
			Resources res, int resId, int reqWidth){
		
		Bitmap bitmap = BitmapFactory.decodeResource(res, resId);
		
		return getDrawableFromBitmap(
				res,
				getResizedBitmap(bitmap, reqWidth)
				);
	}
	
	public static Drawable getResizedDrawableFromRes(
			Resources res, int resId, float rateDpi, float rateWidth, float rateHeight){
		
		Bitmap bitmap = BitmapFactory.decodeResource(res, resId);
		
		return getDrawableFromBitmap(
				res,
				getResizedBitmap(bitmap, rateDpi, rateWidth, rateHeight)
				);
	}

}
