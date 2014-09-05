package com.pinetree.cambus;

import com.pinetree.cambus.fragments.SplashFragment;

import android.R.drawable;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(savedInstanceState == null){
			Fragment fragment = new SplashFragment();
			switchFragment(fragment, true);
		}
	}
}
