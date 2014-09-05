package com.pinetree.cambus.interfaces;

public interface ModelAsyncTaskInterface {
	public void onAsyncTask();
	public void preAsyncTask(Object object);
	public void postAsyncTask();
}
