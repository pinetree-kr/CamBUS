package com.pinetree.cambus;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.pinetree.cambus.fragments.BusListFragment;
import com.pinetree.cambus.models.DBModel.Bus;

public class BusListActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				
		if(savedInstanceState == null){
			Intent intent = getIntent();
			
			Bus bus = (Bus)intent.getSerializableExtra("bus");
			int time = intent.getIntExtra("time", 4);
			
			// fragment에 activity로부터 전달받은 값을 전달
			Fragment fragment = BusListFragment.getInstances(bus, time);
			
			switchFragment(fragment, true);
		}
	}
}
