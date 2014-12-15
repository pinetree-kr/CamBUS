package com.pinetree.cambus.fragments;

import java.io.IOException;
import java.util.Calendar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinetree.cambus.FilterActivity;
import com.pinetree.cambus.R;
import com.pinetree.cambus.interfaces.FragmentCallbackInterface;
import com.pinetree.cambus.interfaces.ModelAsyncTaskInterface;
import com.pinetree.cambus.interfaces.ModelCallbackInterface;
import com.pinetree.cambus.models.Model;
import com.pinetree.cambus.utils.DBHandler;
import com.pinetree.cambus.utils.ExcelFileInfo;
import com.pinetree.cambus.utils.ExcelHandler;
import com.pinetree.cambus.utils.FontLoader;
import com.pinetree.cambus.utils.ImageLoader;
import com.pinetree.cambus.utils.ModelAsyncTask;

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
		
		ImageView title = (ImageView) view.findViewById(R.id.AppTitle);
		title.setBackgroundDrawable(
				ImageLoader.getResizedDrawable(
						getResources(),
						R.drawable.bg,
						app.getScaledRate()
						));
		title.setImageDrawable(
				ImageLoader.getResizedDrawable(
						getResources(),
						R.drawable.main,
						app.getScaledRate()
						));
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		handler = DBHandler.getInstance(getActivity().getApplicationContext(), false);
			
	}
	@Override
	public void onStart(){
		super.onStart();
		/**/
		ConvertModel model = new ConvertModel();
		model.setCallback(new SplashCallbackInterface());
		this.getBusData(model);
		/**/		
	}
	@Override
	public void onResume(){
		super.onResume();
		
	}
	
	@Override
	public void onDestroyView(){
		super.onDestroyView();
		
	}
	
	// AsyncTask 후의 Callback
	protected class SplashCallbackInterface implements FragmentCallbackInterface{

		@Override
		public void onCallback(Model object) {
			Intent intent = new Intent(getActivity(), FilterActivity.class);
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
			long lastupdate = handler.getDBLastUpdate();
			// check last update(get new)
			if(!app.checkDBLastUpdate(lastupdate)){
				Log.i("DebugPrint","GET NEW DB");
				try {
					ExcelHandler.Data2SQLite(getActivity(), handler, ExcelFileInfo.ExcelFile);
					long time= System.currentTimeMillis();
					handler.updateDBLastUpdate(time);
					app.setDBLastUpdate(time);
				} catch (IOException e) {
					e.printStackTrace();
					Log.i("DebugPrint",e.getMessage());
				}
			}
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
		if(this.getActivity()!=null){
			dialog = ProgressDialog.show(this.getActivity(), "", "Converting to DB..",true);
		}
		
	}
	public void postAsyncTask() {
		if(dialog!=null){
			dialog.dismiss();
		}
		
	}
	
	private class ConvertModel extends Model implements ModelCallbackInterface, ModelAsyncTaskInterface{
		@Override
		public void onAsyncTask(Model object) {
			fcInterface.onAsyncTask(object);
		}

		@Override
		public void setCallback(FragmentCallbackInterface fcInterface) {
			this.fcInterface = fcInterface;
		}

		@Override
		public void doCallback() {
			fcInterface.onPostAsyncTask();
			fcInterface.onCallback(this);
		}
	}
}
