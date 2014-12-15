package com.pinetree.cambus.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class ImageLoader {
	public static Drawable getDrawableFromBitmap(Resources res, Bitmap bitmap){
		Drawable d = new BitmapDrawable(res, bitmap);
		return d;
	}
	
	// resizing
	public static Bitmap getResizedBitmap(Bitmap bitmap, int reqWidth, int reqHeight){
		Bitmap resized = Bitmap.createScaledBitmap(bitmap, reqWidth, reqHeight, true);
		
		return resized;
	}
	
	public static Bitmap getResizedBitmap(Bitmap bitmap, int reqSize, boolean isWidth){
		int source = isWidth?bitmap.getWidth():bitmap.getHeight();
		int target = isWidth?bitmap.getHeight():bitmap.getWidth();
		
		Bitmap resized = null;
		//while(source>reqSize){
		resized = Bitmap.createScaledBitmap(
			bitmap,
			isWidth?reqSize:(int)(target*((float)reqSize/source)),
			isWidth?(int)(target*((float)reqSize/source)):reqSize,
			true);
		source = isWidth?resized.getWidth():resized.getHeight();
		target = isWidth?resized.getHeight():resized.getWidth();
		//}
		//Log.i("DebugPrint","Resize()-"+resized.getWidth()+","+resized.getHeight());
		return resized;
	}

	public static Bitmap getResizedBitmap(Bitmap bitmap, float scaledRate){
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		
		//Log.i("DebugPrint","w,h:"+width+","+height);
		
		Bitmap resized = null;
		//while(source>reqSize){
		resized = Bitmap.createScaledBitmap(
				bitmap,
				(int)(width*scaledRate),
				(int)(height*scaledRate),
				true);
		//}
		//Log.i("DebugPrint","re-w,h:"+resized.getWidth()+","+resized.getHeight());
		//Log.i("DebugPrint","Resize()-"+resized.getWidth()+","+resized.getHeight());
		return resized;
	}
	
	public static Bitmap getResizedBitmap(Bitmap bitmap){
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		
		Bitmap resized = null;
		resized = Bitmap.createScaledBitmap(
				bitmap,width,height,true);
		//Log.i("DebugPrint","Resize(null)-"+resized.getWidth()+","+resized.getHeight());
		return resized;
	}
	
	public static Drawable getResizedDrawable(
			Resources res, int resId, int reqWidth, int reqHeight){
		Bitmap bitmap = BitmapFactory.decodeResource(res, resId);
		return getDrawableFromBitmap(
				res,
				getResizedBitmap(bitmap, reqWidth, reqHeight)
				);
	}
	
	public static Drawable getResizedDrawable(
			Resources res, int resId){
		Bitmap bitmap = BitmapFactory.decodeResource(res, resId);
		return getDrawableFromBitmap(
				res,
				getResizedBitmap(bitmap)
				);
	}
	
	public static Drawable getResizedDrawable(
			Resources res, int resId, int reqSize, boolean isWidth){
		Bitmap bitmap = BitmapFactory.decodeResource(res, resId);
		return getDrawableFromBitmap(
				res,
				getResizedBitmap(bitmap, reqSize, isWidth)
				);
	}
	
	public static Drawable getResizedDrawable(
			Resources res, int resId, float scaledRate){
		Bitmap bitmap = BitmapFactory.decodeResource(res, resId);
		return getDrawableFromBitmap(
				res,
				getResizedBitmap(bitmap, scaledRate)
				);
	}
}
