package com.pinetree.cambus;

import com.pinetree.cambus.fragments.SplashFragment;
import com.pinetree.cambus.utils.DeviceInfo;
import com.pinetree.cambus.utils.ExcelFileInfo;
import com.pinetree.cambus.utils.FontLoader;

import android.R.drawable;
import android.app.Fragment;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.widget.TextView;

public class SplashActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setWindowAnimations(android.R.style.Animation_Toast);
		
		// Version Display
        setContentView(R.layout.activity_splash);
        
        /*/
 		TextView textVersion = (TextView)findViewById(R.id.TextVersion);
 		textVersion.setTypeface(FontLoader.getFontTypeface(
 				this.getAssets(),
 				"HelveticaNeueLTStd-UltLt.otf"));
 		textVersion.setTextColor(Color.WHITE);
 		//textVersion.setTextSize(FontLoader.getFontSizeFromPt(this.getApplicationContext(), 6));
 		textVersion.setTextSize(TypedValue.COMPLEX_UNIT_PT, (float)6.0);
 		
 		String version = "";
		try {
			PackageInfo i = this.getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
			version = i.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
 		textVersion.setText(version);
		/**/
		if(savedInstanceState == null){
			Fragment fragment = new SplashFragment();
			switchFragment(fragment, true);
		}
	}
}
