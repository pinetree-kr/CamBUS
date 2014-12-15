package com.pinetree.cambus;

import com.pinetree.cambus.fragments.CityRoutesFragment;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

public class CityRoutesActivity extends BaseActivity {
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
				fragment = CityRoutesFragment.getInstances(null);
				switchFragment(fragment, true);
				break;
			// 가로형 
			case Configuration.ORIENTATION_LANDSCAPE:
				//fragment = FilterFragment.getInstances(model);
				fragment = CityRoutesFragment.getInstances(null);
				switchFragment(fragment, true);
				break;
			}
			// fragment에 activity로부터 전달받은 값을 전달
			
		}
	}
}
