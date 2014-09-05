package com.pinetree.cambus.models;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.pinetree.cambus.interfaces.FragmentCallbackInterface;
import com.pinetree.cambus.interfaces.ModelAsyncTaskInterface;
import com.pinetree.cambus.interfaces.ModelCallbackInterface;
import com.pinetree.cambus.utils.DBHandler;
import com.pinetree.cambus.utils.ExcelHandler;
import com.pinetree.cambus.utils.ModelAsyncTask;

public class BusFilterModel extends Model implements ModelCallbackInterface, ModelAsyncTaskInterface, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 20140825L;
	protected ArrayList<DepartureModel> departure_list;
	protected ArrayList<DestinationModel> destination_list;
	protected ArrayList<Integer> time_list;
	protected ArrayList<TypeModel> type_list;
	public boolean isLoaded;
	
	protected Context context;
	protected DBHandler handler;
	protected String fileName;
	//protected int version;
	
	protected ModelAsyncTask request;
	protected ProgressDialog dialog;
	
	public BusFilterModel(){
		//bus_list = new ArrayList<BusInfoModel>();
		departure_list = new ArrayList<DepartureModel>();
		destination_list = new ArrayList<DestinationModel>();
		time_list = new ArrayList<Integer>();
		type_list = new ArrayList<TypeModel>();
		isLoaded = false;
		context = null;
		handler = null;
		fileName = "";
		request = null;
		dialog = null;
		initTimeList();
	}
	public void initTimeList(){
		time_list.clear();
		time_list.add(-1);
		for(int i = 4; i<27; i++){
			time_list.add(i);
		}
	}
	
	public ArrayList<DepartureModel> getDepartureList(){
		return departure_list;
	}
	public ArrayList<DestinationModel> getDestinationList(){
		return destination_list;
	}
	public ArrayList<Integer> getTimeList(){
		return time_list;
	}
	public ArrayList<TypeModel> getTypeList(){
		return type_list;
	}
	
	// 로컬에 저장된 버스데이터를 불러온다
	public void getBusData(Activity activity, DBHandler handler, String fileName){
		this.context = activity.getApplicationContext();
		this.handler = handler;
		this.fileName = fileName;
		//this.version = Integer.parseInt(fileName.split(".")[0].split("_")[1]);
		
		preAsyncTask(activity);
		if(request==null || request.getStatus().equals(AsyncTask.Status.FINISHED)){
			request = new ModelAsyncTask();
		}
		if(request.getStatus().equals(AsyncTask.Status.PENDING)){
			request.execute(this);
		}
	}
	
	public void setDepartureList(Context context, DBHandler handler){
		departure_list = handler.getBusDepartureList();
	}
	
	public void setDestinationList(int departure_no, Context context, DBHandler handler){
		destination_list = handler.getBusDestinationList(departure_no);
	}
	public void setBusTypeList(Context context, DBHandler handler){
		type_list = handler.getBusTypeList();
	}
	// XLS에 있는 데이터를 로컬DB로 옮긴
	private void XLS2SQLite(Context context, String fileName, DBHandler handler){
		for(BusInfoModel object : ExcelHandler.getBusFromXLS(context, fileName)){
			//Log.i("DebugPrint","insertData");
			object.setBusNo((int)handler.insertBusTime(object));
		}
	}
	
	@Override
	public void onAsyncTask() {
		//XLS2SQLite(context, fileName, handler);
		/**/
		departure_list = handler.getBusDepartureList();
		// 로컬에 저장되어 있는 데이터가 없으면 XLS을 읽어들여 옮긴다.
		
		if(departure_list.size() < 2){
			//Log.i("DebugPrint","XLS2SQLite");
			XLS2SQLite(context, fileName, handler);
			departure_list = handler.getBusDepartureList();
			isLoaded = true;
		}
		
		type_list = handler.getBusTypeList();
		//Log.i("DebugPrint", "type:"+type_list.size());
		/**/
	}
	public void setCallback(FragmentCallbackInterface fcInterface){
		this.fcInterface = fcInterface;
	}
	public void doCallback(){
		postAsyncTask();
		fcInterface.onCallback(this);
	}
	@Override
	public void preAsyncTask(Object object) {
		dialog = ProgressDialog.show((Activity)object, "", "Converting to DB..",true);
	}
	@Override
	public void postAsyncTask() {
		if(dialog!=null)
			dialog.dismiss();
	}
}
