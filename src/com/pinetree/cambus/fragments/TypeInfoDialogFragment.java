package com.pinetree.cambus.fragments;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pinetree.cambus.R;
import com.pinetree.cambus.models.DBModel.Type;
import com.pinetree.cambus.utils.DeviceInfo;
import com.pinetree.utils.FontLoader;
import com.pinetree.utils.ImageLoader;

public class TypeInfoDialogFragment extends DialogFragment {
	private boolean isModal;
	private LinearLayout infoView;
	private String[] type_list;
	private DeviceInfo app;
	private ImageLoader imageLoader;
	private FontLoader fontLoader;
	public static TypeInfoDialogFragment getInstances(ArrayList<Type> types){
		Bundle args = new Bundle();
		
		String[] typeList = new String[types.size()-1];
		for(int i=0; i<types.size()-1; i++){
			typeList[i] = types.get(i+1).getName();
		}
		args.putStringArray("types", typeList);
		
		TypeInfoDialogFragment dialog = new TypeInfoDialogFragment();
		dialog.setArguments(args);
		
		return dialog;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		//Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		app = (DeviceInfo)getActivity().getApplicationContext();
		fontLoader = new FontLoader(getActivity().getApplicationContext());
		imageLoader = new ImageLoader(getResources(), app.getScaledRate());
		
		View view = inflater.inflate(R.layout.fragment_dialog_type_info, null);
		view.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		builder.setView(view);
		/*/
		final Drawable d = new ColorDrawable(Color.TRANSPARENT);
		//d.setAlpha(130);
		dialog.getWindow().setBackgroundDrawable(d);
		dialog.getWindow().setContentView(view);
		//getDialog().getWindow().setBackgroundDrawable(d);
		//builder.setView(view);
		final WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
	//params.gr
		params.gravity = Gravity.CENTER;
		params.y = params.y - 50;
		/**/
		setupUI(view);			
		isModal = true;
		
		//dialog.setCanceledOnTouchOutside(true);
		//return dialog;
		return builder.create();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Bundle args = this.getArguments();
		if(args != null){
			type_list = args.getStringArray("types");
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
			View view = inflater.inflate(R.layout.fragment_dialog_type_info, container, false);
			/*/
			getDialog().getWindow().setBackgroundDrawable(
					new ColorDrawable(0));
					/**/
			setupUI(view);
			return view;
		}
	}
	
	public void setupUI(View view){
		ImageView typeInfo = (ImageView)view.findViewById(R.id.imageTypeInfo);
		typeInfo.setImageDrawable(imageLoader.getResizedDrawable(R.drawable.popup));
		
		/*/
		if(infoView==null){
			infoView = (LinearLayout)view.findViewById(R.id.type_info);
		}
		infoView.removeAllViews();
		
		LinearLayout row;
		TextView textType;
		int rows = (int)((type_list.length-1)/3) + 1;
		LayoutParams lpRow = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		LayoutParams lpRowFirst = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		LayoutParams lpRowLast = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lpRow.setMargins(40, 0, 40, 0);
		lpRowFirst.setMargins(40, 40, 40, 0);
		lpRowLast.setMargins(40, 0, 40, 40);
		LayoutParams lpCol = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
		lpCol.setMargins(0, 0, 0, 0);
		
		for(int i=0; i<rows; i++){
			row = new LinearLayout(getActivity().getApplicationContext());
			if(i==0){
				row.setLayoutParams(lpRowFirst);
			}else if(i==rows-1){
				row.setLayoutParams(lpRowLast);
			}else{
				row.setLayoutParams(lpRow);
			}
			row.setWeightSum(3);
			row.setOrientation(LinearLayout.HORIZONTAL);
			infoView.addView(row);
			for(int j=i*3; j<(i+1)*3 && j<type_list.length; j++){
				textType = new TextView(getActivity().getApplicationContext());
				textType.setLayoutParams(lpCol);
				textType.setText("- " + type_list[j]);
				textType.setTextColor(Color.rgb(75, 75, 75));		
				row.addView(textType);
			}
		}
		/**/
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		//getDialog().getWindow().getAttributes().alpha=0.9f;
	}
	@Override
	public void onStart(){
		super.onStart();
		/*/
		getDialog().getWindow().setBackgroundDrawable(
				new ColorDrawable(android.R.color.transparent));
				/**/
		//android.view.WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
		//getDialog().getWindow().setAttributes(lp);
	}
	@Override
	public void onResume(){
		super.onResume();
	}
}
