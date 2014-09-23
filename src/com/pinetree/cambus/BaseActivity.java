
package com.pinetree.cambus;


import io.userhabit.service.Userhabit;

import com.pinetree.cambus.handlers.SwitchActivityHandler;
import com.pinetree.cambus.handlers.SwitchFragmentHandler;
import com.pinetree.cambus.interfaces.SwitchActivityInterface;
import com.pinetree.cambus.interfaces.SwitchFragmentInterface;
import com.pinetree.cambus.utils.DeviceInfo;
import com.pinetree.cambus.utils.ExcelFileInfo;
import com.pinetree.cambus.utils.FontLoader;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public abstract class BaseActivity extends Activity
		implements SwitchActivityInterface, SwitchFragmentInterface{
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setWindowAnimations(android.R.style.Animation_Toast);
        getWindow().setWindowAnimations(android.R.style.Animation);
        
    }
    
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
	
	@Override
	protected void onResume(){
		super.onResume();
	}
	
	@Override
	protected void onStart(){
		super.onStart();
		Userhabit.activityStart(this);
		Userhabit.setSessionEndTime(15);
	}
	@Override
	protected void onStop(){
		super.onStop();
		Userhabit.activityStop(this);
	}
	
	@Override
	public void reloadFragment(){
		Fragment fragment = this.getFragmentManager().findFragmentById(R.id.base_fragment);
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.detach(fragment);
		transaction.attach(fragment);
		transaction.commit();
	}
	
	@Override
	public void switchFragment(Fragment fragment, boolean close) {
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
		if(close){
			transaction.replace(R.id.base_fragment, fragment);
		}else{
			transaction.replace(R.id.base_fragment, fragment);
			transaction.addToBackStack(null);
		}
		transaction.commit();
	}

	@Override
	public void switchFragment(Fragment fragment, int time, boolean close) {
		Handler handler = new Handler();
		handler.postDelayed(new SwitchFragmentHandler(this, fragment, close),  time);
	}

	@Override
	public void switchActivity(Intent intent, boolean close) {
		startActivity(intent);
		
		if(close)
			this.finish();
	}

	@Override
	public void switchActivity(Intent intent, int time, boolean close) {
		Handler handler = new Handler();
		handler.postDelayed(new SwitchActivityHandler(this, intent, close),  time);
	}

}
