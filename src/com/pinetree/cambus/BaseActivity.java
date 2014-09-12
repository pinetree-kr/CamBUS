
package com.pinetree.cambus;


import io.userhabit.service.Userhabit;

import com.pinetree.cambus.handlers.SwitchActivityHandler;
import com.pinetree.cambus.handlers.SwitchFragmentHandler;
import com.pinetree.cambus.interfaces.SwitchActivityInterface;
import com.pinetree.cambus.interfaces.SwitchFragmentInterface;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public abstract class BaseActivity extends Activity
		implements SwitchActivityInterface, SwitchFragmentInterface{
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setWindowAnimations(android.R.style.Animation_Toast);

    }
    
	@Override
	protected void onResume(){
		super.onResume();
	}
	
	@Override
	protected void onStart(){
		super.onStart();
		Userhabit.activityStart(this);
		
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
			transaction.replace(R.id.base_fragment, fragment);//.commit();
		}else{
			transaction.replace(R.id.base_fragment, fragment);//.commit();
			
			//transaction.add(R.id.base_fragment, fragment).commit();
			transaction.addToBackStack(null);
			//transaction.commit();
		}
		transaction.commit();
	}

	@Override
	public void switchFragment(Fragment fragment, int time, boolean close) {
		Handler handler = new Handler();
		handler.postDelayed(new SwitchFragmentHandler(this, fragment, close),  time);
	}

	@Override
	public void switchActivity(Class<?> name, boolean close) {
		Intent intent = new Intent(this, name);
		startActivity(intent);
		
		if(close)
			this.finish();
	}

	@Override
	public void switchActivity(Class<?> name, int time, boolean close) {
		Handler handler = new Handler();
		handler.postDelayed(new SwitchActivityHandler(this, name, close),  time);
	}

}
