package com.pinetree.cambus;

import com.pinetree.cambus.fragments.FilterFragment;
import com.pinetree.cambus.fragments.SplashFragment;
import com.pinetree.cambus.models.Model;

import android.R.drawable;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;

public class FilterActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(savedInstanceState == null){
			Fragment fragment;
			switch(getResources().getConfiguration().orientation){
			// 세로형 
			case Configuration.ORIENTATION_PORTRAIT:
				//fragment = FilterFragment.getInstances(model);
				fragment = FilterFragment.getInstances(null);
				switchFragment(fragment, true);
				break;
			// 가로형 
			case Configuration.ORIENTATION_LANDSCAPE:
				//fragment = FilterFragment.getInstances(model);
				fragment = FilterFragment.getInstances(null);
				switchFragment(fragment, true);
				break;
			}
			// fragment에 activity로부터 전달받은 값을 전달
			
		}
	}
}
