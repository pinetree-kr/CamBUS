package com.pinetree.cambus.fragments;

import java.io.IOException;
import java.io.Serializable;

import com.pinetree.cambus.FilterActivity;
import com.pinetree.cambus.R;
import com.pinetree.cambus.interfaces.FragmentCallbackInterface;
import com.pinetree.cambus.interfaces.ModelCallbackInterface;
import com.pinetree.cambus.models.BusFilterModel;
import com.pinetree.cambus.models.Model;
import com.pinetree.cambus.utils.DBHandler;
import com.pinetree.cambus.utils.DeviceInfo;
import com.pinetree.cambus.utils.ExcelFileInfo;
import com.pinetree.cambus.utils.ExcelHandler;
import com.pinetree.cambus.utils.FontLoader;
import com.pinetree.cambus.utils.ImageLoader;
import com.pinetree.cambus.utils.ModelAsyncTask;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashFragment extends BaseFragment {
	
	protected ModelAsyncTask request;
	protected DBHandler handler;
	
	protected ProgressDialog dialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setHasOptionsMenu(false);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_splash, container, false);
		
		view.setBackgroundResource(R.drawable.bg);
		
		// title image
		ImageView imageTitle = (ImageView)view.findViewById(R.id.imageTitle);
		Drawable dTitle = ImageLoader.getResizedDrawable(
				getResources(),
				R.drawable.logo
				);
		imageTitle.setImageDrawable(dTitle);
		
		// title text
		TextView textTitle = (TextView)view.findViewById(R.id.TextTitle);
		textTitle.setTypeface(FontLoader.getFontTypeface(
				getActivity().getAssets(),
				"HelveticaNeueLTStd-UltLt.otf"));
		//textTitle.setTextSize(FontLoader.getFontSizeFromPt(app, 19));
		textTitle.setTextSize(TypedValue.COMPLEX_UNIT_PT, (float)19);
		
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
		BusFilterModel filter = new BusFilterModel();
		filter.setCallback(new SplashCallbackInterface());
		this.getBusData(filter);
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
	
	// AsyncTask 후의 Callback
	protected class SplashCallbackInterface implements FragmentCallbackInterface{

		@Override
		public void onCallback(Model object) {
			Intent intent = new Intent(getActivity(), FilterActivity.class);
			intent.putExtra("model", object);
			
			saInterface.switchActivity(intent, 2000, true);
		}

		@Override
		public void onPreAsyncTask() {
			preAsyncTask();
		}

		@Override
		public void onPostAsyncTask() {
			postAsyncTask();
		}

		@Override
		public void onAsyncTask(Model object) {
			/*
			 * TODO
			 * DB 재저장의 상황을 조절할 필요가 있음
			 * 현재는 Departure리스트가 비어있을때 DB 재저장을 실행함
			 * lastupdate로 조정/shared preferences랑 함께
			 */
			BusFilterModel model = (BusFilterModel)object;
			
			model.updateLineList(handler);
			try {
				// 로컬에 저장되어 있는 데이터가 없으면 XLS을 읽어들여 옮긴다.
				if(model.getLineList().size() < 1){
					// 로컬db로의 저장
					ExcelHandler.Data2SQLite(getActivity(), handler, ExcelFileInfo.ExcelFile);
					model.updateLineList(handler);
				}
			} catch (SQLException e){
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			}
			model.updateBusTypeList(handler);
		}
		
	}
	
	public void getBusData(Model object){
		preAsyncTask();
		
		if(request==null || request.getStatus().equals(AsyncTask.Status.FINISHED)){
			request = new ModelAsyncTask();
		}
		if(request.getStatus().equals(AsyncTask.Status.PENDING)){
			request.execute(object);
		}
	}
	
	public void preAsyncTask() {
		if(this.getActivity()!=null)
			dialog = ProgressDialog.show(this.getActivity(), "", "Converting to DB..",true);
	}
	public void postAsyncTask() {
		if(dialog!=null)
			dialog.dismiss();
	}
}
