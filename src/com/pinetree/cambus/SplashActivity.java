package com.pinetree.cambus;

import com.pinetree.cambus.fragments.SplashFragment;
import com.pinetree.cambus.utils.DeviceInfo;
import com.pinetree.cambus.utils.ExcelFileInfo;
import com.pinetree.cambus.utils.FontLoader;

import android.R.drawable;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class SplashActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setWindowAnimations(android.R.style.Animation_Toast);
		
		// Version Display
        setContentView(R.layout.activity_main);
        
        /**/
 		TextView textVersion = (TextView)findViewById(R.id.TextVersion);
 		textVersion.setTypeface(FontLoader.getFontTypeface(
 				this.getAssets(),
 				"HelveticaNeueLTStd-UltLt.otf"));
 		textVersion.setTextColor(Color.WHITE);
 		textVersion.setTextSize(FontLoader.getFontSizeFromPt(((DeviceInfo)this.getApplicationContext()).rateDpi, 6));
 		textVersion.setText("ver."+ExcelFileInfo.ExcelFileVersion);
		/**/
		if(savedInstanceState == null){
			Fragment fragment = new SplashFragment();
			switchFragment(fragment, true);
		}
	}
}
