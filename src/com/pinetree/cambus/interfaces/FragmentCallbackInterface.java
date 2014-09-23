package com.pinetree.cambus.interfaces;

import com.pinetree.cambus.models.Model;

public interface FragmentCallbackInterface {
	public void onPreAsyncTask();
	public void onPostAsyncTask();
	public void onAsyncTask(Model object);
	public void onCallback(Model object);
}
