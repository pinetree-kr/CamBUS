package com.pinetree.cambus.adapters;

import java.util.ArrayList;

import com.pinetree.cambus.R;
import com.pinetree.cambus.models.BusInfoModel;
import com.pinetree.cambus.utils.DateUtils;
import com.pinetree.cambus.utils.DeviceInfo;
import com.pinetree.cambus.utils.FontLoader;
import com.pinetree.cambus.viewholders.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ModelListAdapter<T> extends ArrayAdapter<T>{
	protected LayoutInflater inflater;
	protected ArrayList<T> objects;
	protected DeviceInfo app;
	public ModelListAdapter(Context context, int resource,
			ArrayList<T> objects) {		
		super(context, resource);
		
		inflater = LayoutInflater.from(context);
		this.objects = objects;
		app = (DeviceInfo)context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return objects.size();
	}
	
	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return (T) objects.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		if(view == null){
			view = inflater.inflate(R.layout.bus_list_row, parent, false);
		}
		
		TextView textCompany = ViewHolder.get(view, R.id.TextCompany);
		TextView textType = ViewHolder.get(view, R.id.TextType);
		TextView textFee = ViewHolder.get(view, R.id.TextFee);
		TextView FeeInfo = ViewHolder.get(view, R.id.FeeInfo);
		TextView textHour = ViewHolder.get(view, R.id.TextHour);
		
		TextView textDiv = ViewHolder.get(view, R.id.TextDiv);
		TextView textAverage = ViewHolder.get(view, R.id.TextAverage);
		TextView DurationTimeInfo = ViewHolder.get(view, R.id.DurationTimeInfo);
		TextView textNextTime = ViewHolder.get(view, R.id.TextNextTime);
		TextView NextTimeInfo = ViewHolder.get(view, R.id.NextTimeInfo);
		
		BusInfoModel object = (BusInfoModel) getItem(position);
		
		textCompany.setText((position+1) + ". "+object.getCompany());
		textCompany.setTypeface(FontLoader.getFontTypeface(
				getContext().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		textCompany.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)7.2));
		
		textType.setText("["+object.getType()+"]");
		textType.setTypeface(FontLoader.getFontTypeface(
				getContext().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		textType.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)6));
		
		// 
		textFee.setTypeface(FontLoader.getFontTypeface(
				getContext().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		textFee.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)5));
		FeeInfo.setText(String.valueOf(object.getForeignerPrice())+"$");
		FeeInfo.setTypeface(FontLoader.getFontTypeface(
				getContext().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		FeeInfo.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)6));
		
		textDiv.setTypeface(FontLoader.getFontTypeface(
				getContext().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		textDiv.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)5));
		
		
		textAverage.setTypeface(FontLoader.getFontTypeface(
				getContext().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		textAverage.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)5));
		
		if(object.getDurationTime()<=1){
			textHour.setText(R.string.hour);
		}else{
			textHour.setText(R.string.hours);
		}
		textHour.setTypeface(FontLoader.getFontTypeface(
				getContext().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		textHour.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)5));
		
		DurationTimeInfo.setText(object.getNearBy());
		DurationTimeInfo.setTypeface(FontLoader.getFontTypeface(
				getContext().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		DurationTimeInfo.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)6));
		
		textNextTime.setTypeface(FontLoader.getFontTypeface(
				getContext().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		textNextTime.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)5));
		
		NextTimeInfo.setText(
				DateUtils.getTimes(object.getDepartureTime()));
		NextTimeInfo.setTypeface(FontLoader.getFontTypeface(
				getContext().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		NextTimeInfo.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)6));
		
		return view;
		
	}
}
