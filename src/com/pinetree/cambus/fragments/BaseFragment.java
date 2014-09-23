package com.pinetree.cambus.fragments;

import io.userhabit.service.Userhabit;

import java.util.Locale;

import com.pinetree.cambus.R;
import com.pinetree.cambus.interfaces.SwitchActivityInterface;
import com.pinetree.cambus.interfaces.SwitchFragmentInterface;
import com.pinetree.cambus.models.BusFilterModel;
import com.pinetree.cambus.models.Model;
import com.pinetree.cambus.utils.DeviceInfo;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public abstract class BaseFragment extends Fragment {
	protected SwitchActivityInterface saInterface;
	protected SwitchFragmentInterface sfInterface;
	//protected FragmentCallbackInterface fcInterface = callbacks;
	protected CharSequence fragmentTitle;
	protected DeviceInfo app;
	
	// 액티비티에 Add될때의 이벤트
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		saInterface = (SwitchActivityInterface)activity;
		sfInterface = (SwitchFragmentInterface)activity;
		app = (DeviceInfo)activity.getApplicationContext();
	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		fragmentTitle = this.getClass().getSimpleName();
		// use menu button
		//this.setHasOptionsMenu(true);
	}
	
	@Override
	public void onDetach(){
		super.onDetach();
		
	}	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.main, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.menu_language:
			if(getResources().getConfiguration().locale.equals(Locale.KOREA)){
				Configuration config = new Configuration();
				config.locale = Locale.US;
				getResources().updateConfiguration(config, getResources().getDisplayMetrics());
			}else if(getResources().getConfiguration().locale.equals(Locale.US)){
				Configuration config = new Configuration();
				config.locale = Locale.KOREA;
				getResources().updateConfiguration(config, getResources().getDisplayMetrics());
			}
			
			sfInterface.reloadFragment();
			
			return true;
		default:
			break;
		}
		return false;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		//Log.i("DebugPrint","onActivityCreated:"+fragmentTitle);
		//Userhabit.openSubview(fragmentTitle.toString());		
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}
	@Override
	public void onPause(){
		super.onPause();
		//Log.i("DebugPrint","onPause");
		//Userhabit.closeSubview();
	}
	@Override
	public void onStop(){
		super.onStop();
		//Userhabit.activityStop(getActivity());
		//Userhabit.closeSubview();
		//Log.i("DebugPrint","onStop");
	}
	@Override
	public void onStart(){
		super.onStart();
		//Userhabit.activityStart(getActivity());
		//Userhabit.openSubview(fragmentTitle.toString());
		//Log.i("DebugPrint","onStart");
	}
	@Override
	public void onDestroyView(){
		super.onDestroyView();
		//Log.i("DebugPrint","onDestroyView:"+fragmentTitle);
		//Userhabit.closeSubview();
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
		//Log.i("DebugPrint","onDestroy");
	}
	
}
