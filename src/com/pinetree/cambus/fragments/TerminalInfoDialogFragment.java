package com.pinetree.cambus.fragments;

import java.util.ArrayList;

import org.json.JSONException;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pinetree.cambus.R;
import com.pinetree.cambus.models.DBModel.Terminal;
import com.pinetree.cambus.utils.DeviceInfo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class TerminalInfoDialogFragment extends DialogFragment {
	private boolean isModal;
	/*/
	private String terminalName;
	private String[] phoneNo;
	private String address;
	/**/
	private LinearLayout infoView;
	private Terminal terminal;
	
	public static TerminalInfoDialogFragment getInstances(String snippet){
		Bundle args = new Bundle();
		args.putString("snippet", snippet);
		
		TerminalInfoDialogFragment dialog = new TerminalInfoDialogFragment();
		dialog.setArguments(args);
		
		return dialog;
	}
	public static TerminalInfoDialogFragment getInstances(Terminal terminal){
		Bundle args = new Bundle();
		args.putSerializable("model", terminal);
		
		TerminalInfoDialogFragment dialog = new TerminalInfoDialogFragment();
		dialog.setArguments(args);
		
		return dialog;
	}
	

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		View view = inflater.inflate(R.layout.fragment_dialog_terminal_info, null);
		
		builder.setView(view);
		setupUI(view);			
		isModal = true;
		
		return builder.create();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Bundle args = this.getArguments();
		if(args != null){
			Terminal obj = (Terminal)args.getSerializable("model");
			if(obj!=null){
				terminal = obj;
			}else{
				String snippet = (String)args.getString("snippet","");
				terminal = new Terminal();
				if(!snippet.equals("")){
					String[] info = snippet.split("\n");
					if(info.length>=3){
						terminal.setTerminalName(info[0].trim());
						terminal.setAddress(info[1].trim());
						terminal.setPhoneNo(info[2].trim());
					}
				}
			}
			
		}
	}
	
	@Override
	public void onStop(){
		super.onStop();
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
	@Override
	public void onDestroyView(){
		super.onDestroyView();
	}
	@Override
	public void onDismiss(DialogInterface dialog){
		super.onDismiss(dialog);
	}
	
	@Override
	public void onDetach(){
		super.onDetach();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(isModal){
			return super.onCreateView(inflater, container, savedInstanceState);
		}else{
			View view = inflater.inflate(R.layout.fragment_dialog_terminal_info, container, false);
			setupUI(view);
			return view;
		}
	}
	
	public void setupUI(View view){
		if(infoView==null){
			infoView = (LinearLayout)view.findViewById(R.id.terminal_info);
		}
		infoView.removeAllViews();
		
		TextView textName = new TextView(getActivity().getApplicationContext());
		LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		param.setMargins(5,5,5,5);
		textName.setLayoutParams(param);
		textName.setGravity(Gravity.CENTER);
		textName.setTextColor(Color.BLACK);
		textName.setText(terminal.getTerminalName());
		textName.setTextSize(TypedValue.COMPLEX_UNIT_PT, (float)8.0);
		infoView.addView(textName);
		
		if(terminal.getAddress()!=null && !terminal.getAddress().equals("")){
			TextView textAddress = new TextView(getActivity().getApplicationContext());
			LayoutParams param2 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			param2.setMargins(20, 10, 20, 10);
			textAddress.setMaxLines(10);
			textAddress.setLayoutParams(param2);
			textAddress.setGravity(Gravity.LEFT);
			textAddress.setTextColor(Color.BLACK);
			textAddress.setText("Address\n"+terminal.getAddress());
			textAddress.setTextSize(TypedValue.COMPLEX_UNIT_PT, (float)6.0);
			infoView.addView(textAddress);
		}
		
		LayoutParams param3 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		param3.setMargins(20, 5, 20, 5);
		
		Button phoneView;
		String phoneText;
		for(String phone : terminal.getPhoneNoList()){
			if(!phone.equals("")){
				phoneText = phone.trim().replaceAll("\\D", "");
				phoneView = new Button(getActivity().getApplicationContext());
				phoneView.setBackgroundResource(R.drawable.button_default);
				phoneView.setText(phoneText);
				phoneView.setTag(phoneText);
				phoneView.setOnClickListener(new CallClickListener());
				phoneView.setLayoutParams(param3);
				infoView.addView(phoneView);
			}
		}
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
	}
	@Override
	public void onStart(){
		super.onStart();
		android.view.WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
		lp.height = LayoutParams.WRAP_CONTENT;
		lp.width = (int)(((DeviceInfo)getActivity().getApplicationContext()).getWidth()*17/20);
		getDialog().getWindow().setLayout(lp.width, lp.height);
		
	}
	@Override
	public void onResume(){
		super.onResume();
	}
	
	protected class CallClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			String phone = (String)v.getTag();
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:"+phone));
			startActivity(callIntent);
		}
	}
}
