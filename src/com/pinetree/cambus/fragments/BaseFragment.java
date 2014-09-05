package com.pinetree.cambus.fragments;

import java.util.Locale;

import com.pinetree.cambus.R;
import com.pinetree.cambus.interfaces.FragmentCallbackInterface;
import com.pinetree.cambus.interfaces.SwitchActivityInterface;
import com.pinetree.cambus.interfaces.SwitchFragmentInterface;
import com.pinetree.cambus.models.Model;
import com.pinetree.cambus.utils.DeviceInfo;

import android.R.drawable;
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
	public void onResume(){
		super.onResume();
		loadTextView();
	}
	
	protected abstract void loadTextView();
	
}
