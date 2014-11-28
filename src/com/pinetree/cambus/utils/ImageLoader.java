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
		
		//Log.i("DebugPrint","Resize(w,h)-"+resized.getWidth()+","+resized.getHeight());
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
	/*/
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
			Bitmap bitmap, double density, double rateWidth, double rateHeight){
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		
		int reqWidth = (int)(width * rateWidth / density);
		int reqHeight = (int)(height * rateHeight / density);
		
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
	
	public static Drawable getResizedDrawableFromRes(Resources res,
			int resId, double density, double rateWidth, double rateHeight) {
		Bitmap bitmap = BitmapFactory.decodeResource(res, resId);
		
		return getDrawableFromBitmap(
				res,
				getResizedBitmap(bitmap, density, rateWidth, rateHeight)
				);
	}
	/**/
}
