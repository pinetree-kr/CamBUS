package com.pinetree.cambus.adapters;

import java.util.ArrayList;

import com.pinetree.cambus.R;
import com.pinetree.cambus.models.DBModel.*;
import com.pinetree.cambus.utils.DateUtils;
import com.pinetree.cambus.utils.DeviceInfo;
import com.pinetree.cambus.utils.FontLoader;

import android.R.color;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerAdapter<T> extends ArrayAdapter<T>{
	protected LayoutInflater inflater;
	protected ArrayList<T> objects;
	protected DeviceInfo app;
	
	public SpinnerAdapter(Context context, int resource, ArrayList<T> objects) {
		super(context, resource, objects);
		inflater = LayoutInflater.from(context);
		this.objects = objects;
		app = (DeviceInfo)context;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent){
		return getSpinnerView(position, convertView, parent);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		return getSpinnerView(position, convertView, parent);
	}
	
	public View getSpinnerView(int position, View convertView, ViewGroup parent){
		View view = convertView;
		TextView textName, textPreference;
		
		if(view == null){
			view = inflater.inflate(R.layout.custom_spinner, parent, false);
		}
		textName = (TextView) view.findViewById(R.id.spinnerText);
		textPreference = (TextView) view.findViewById(R.id.spinnerPreference);
		
		T object = objects.get(position);
		
		view.setTag(object);
		textPreference.setText("");
		// city이면
		if(object.getClass().getSuperclass().equals(City.class)){
			String cityName =((City)object).getCityName();
			if(((City)object).getHigh()){
				textName.setTextColor(Color.RED);
				textName.setText("*"+cityName);
			}else{
				textName.setTextColor(Color.BLACK);
				textName.setText(cityName);
			}
			textName.setHint(R.string.select_city);
		}
		// type이면
		else if(object.getClass().equals(BusType.class)){
			if((((BusType)object).getTypeNo()<1))
				textName.setText(R.string.all_type);
			else	
				textName.setText(((BusType)object).getTypeName());
			textName.setHint(R.string.select_type);
		}
		// time이면
		else if(object.getClass().equals(Integer.class)){
			// 시간출력 
			int time = (Integer)object;
			if(time>0){
				textName.setText(DateUtils.getTimes(time));
			}else{
				textName.setText("");
			}
			textName.setHint(R.string.select_time);
		}
		
		textName.setTypeface(FontLoader.getFontTypeface(
				getContext().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		textName.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)7.2));
		return view;
	}
}
