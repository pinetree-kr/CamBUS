package com.pinetree.cambus;

import com.pinetree.cambus.fragments.FilterFragment;
import com.pinetree.cambus.fragments.SplashFragment;
import com.pinetree.cambus.models.Model;

import android.R.drawable;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class FilterActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(savedInstanceState == null){
			Intent intent = getIntent();
			Model model = (Model)intent.getSerializableExtra("model");
			
			// fragment에 activity로부터 전달받은 값을 전달
			Fragment fragment = FilterFragment.getInstances(model);
			
			switchFragment(fragment, true);
		}
	}
}
