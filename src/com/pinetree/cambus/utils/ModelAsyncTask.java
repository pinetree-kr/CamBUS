package com.pinetree.cambus.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.pinetree.cambus.interfaces.ModelAsyncTaskInterface;
import com.pinetree.cambus.interfaces.ModelCallbackInterface;
import com.pinetree.cambus.models.Model;

public class ModelAsyncTask extends AsyncTask<Model, Model, Model>{

	@Override
	protected Model doInBackground(Model... objects) {
		if(isCancelled()){
			return null;
		}
		//Log.i("DebugPrint","doInBackground");
		
		((ModelAsyncTaskInterface)objects[0]).onAsyncTask(objects[0]);
		
		return objects[0];
	}

	@Override
	protected void onCancelled(){
		super.onCancelled();
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	@Override
	protected void onPostExecute(Model object){
		super.onPostExecute(object);
		((ModelCallbackInterface)object).doCallback();
	}
	// UI 처리
	@Override
	protected void onProgressUpdate(Model... objects){
		super.onProgressUpdate(objects);
	}
}
