package com.pinetree.cambus.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinetree.cambus.R;
import com.pinetree.cambus.models.DBModel.City;
import com.pinetree.cambus.models.DBModel.Company;
import com.pinetree.cambus.models.DBModel.Type;
import com.pinetree.cambus.utils.DeviceInfo;
import com.pinetree.utils.FontLoader;
import com.pinetree.utils.FormatUtil;
import com.pinetree.utils.ImageLoader;
import com.pinetree.utils.ViewHolder;

public class SpinnerAdapter<T> extends ArrayAdapter<T>{
	protected LayoutInflater inflater;
	protected ArrayList<T> objects;
	protected DeviceInfo app;
	protected FontLoader fontLoader;
	protected ImageLoader imageLoader;
	
	public SpinnerAdapter(Context context, int resource, ArrayList<T> objects) {
		super(context, resource, objects);
		inflater = LayoutInflater.from(context);
		this.objects = objects;
		app = (DeviceInfo)context;
		fontLoader = new FontLoader(context);
		imageLoader = new ImageLoader(context.getResources(), app.getScaledRate());
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
		
		T object = objects.get(position);
		
		if(view == null){
			view = inflater.inflate(R.layout.spinner_row, parent, false);
		}
		
		TextView textName = ViewHolder.get(view, R.id.spinnerText);
		//TextView textName = (TextView)view.findViewById(R.id.spinnerText);
		ImageView imageBg = ViewHolder.get(view, R.id.spinnerBg);
		//ImageView imageBg= (ImageView)view.findViewById(R.id.spinnerBg);
		
		imageBg.setImageDrawable(
				imageLoader.getResizedDrawable(R.drawable.drop_down_bg));
		
		//view.setTag(object);
		
		// city이면
		if(object.getClass().getSuperclass().equals(City.class)){
			String cityName =((City)object).getName();
			if(((City)object).getPref()){
				textName.setTextColor(Color.RED);
				textName.setText("*"+cityName);
			}else{
				textName.setTextColor(getContext().getResources().getColor(R.color.search_select));
				textName.setText(cityName);
			}
			textName.setHint(R.string.select_city);
		}
		// type이면
		else if(object.getClass().equals(Type.class)){
			if((((Type)object).getId()<1))
				textName.setText(R.string.all_type);
			else	
				textName.setText(((Type)object).getName());
		}
		// time이면
		else if(object.getClass().equals(Integer.class)){
			// 시간출력 
			int time = (Integer)object;
			if(time>0){
				textName.setText(FormatUtil.getTimes(time));
			}else{
				textName.setText("");
			}
			textName.setHint(R.string.select_time);
		}
		// company이면
		else if(object.getClass().equals(Company.class)){
			if((((Company)object).getId()<1))
				textName.setText(R.string.all_company);
			else	
				textName.setText(((Company)object).getName());
			textName.setHint(R.string.select_company);
		}
		
		fontLoader.setTextViewTypeFace(textName, R.string.lato_light, (float)7);
		return view;
	}
}
