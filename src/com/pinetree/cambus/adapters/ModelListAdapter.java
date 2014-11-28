package com.pinetree.cambus.adapters;

import java.util.ArrayList;

import com.pinetree.cambus.R;
import com.pinetree.cambus.models.DBModel.LineBusTime;
import com.pinetree.cambus.utils.DateUtils;
import com.pinetree.cambus.utils.DeviceInfo;
import com.pinetree.cambus.utils.FontLoader;
import com.pinetree.cambus.utils.ImageLoader;
import com.pinetree.cambus.viewholders.ViewHolder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
		//TextView textType = ViewHolder.get(view, R.id.TextType);
		ImageView imageBusType = ViewHolder.get(view, R.id.ImageBusType);
		
		TextView textFee = ViewHolder.get(view, R.id.TextFee);
		TextView FeeInfo = ViewHolder.get(view, R.id.FeeInfo);
		TextView textHour = ViewHolder.get(view, R.id.TextHour);
		
		TextView textAverage = ViewHolder.get(view, R.id.TextAverage);
		TextView DurationTimeInfo = ViewHolder.get(view, R.id.DurationTimeInfo);
		TextView textNextTime = ViewHolder.get(view, R.id.TextNextTime);
		TextView NextTimeInfo = ViewHolder.get(view, R.id.NextTimeInfo);
		
		LineBusTime object = (LineBusTime) getItem(position);
		
		textCompany.setText(object.getCompanyName());
		textCompany.setTypeface(FontLoader.getFontTypeface(
				getContext().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		//textCompany.setTextSize(FontLoader.getFontSizeFromPt(app, (float)7.2));
		textCompany.setTextSize(TypedValue.COMPLEX_UNIT_PT, (float)7.2);
		
		//TODO : 버스타입에 따른 다른 이미지 
		Drawable dBusType = ImageLoader.getResizedDrawable(
				this.getContext().getResources(),
				R.drawable.busicon
				);
		
		imageBusType.setImageDrawable(dBusType);
		
		// 
		textFee.setTypeface(FontLoader.getFontTypeface(
				getContext().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		//textFee.setTextSize(FontLoader.getFontSizeFromPt(app, (float)5));
		textFee.setTextSize(TypedValue.COMPLEX_UNIT_PT, (float)5.0);
		FeeInfo.setText(String.valueOf(object.getForeignerPrice())+"$");
		FeeInfo.setTypeface(FontLoader.getFontTypeface(
				getContext().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		//FeeInfo.setTextSize(FontLoader.getFontSizeFromPt(app, (float)6));
		FeeInfo.setTextSize(TypedValue.COMPLEX_UNIT_PT, (float)6.0);
		
		textAverage.setTypeface(FontLoader.getFontTypeface(
				getContext().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		//textAverage.setTextSize(FontLoader.getFontSizeFromPt(app, (float)5));
		textAverage.setTextSize(TypedValue.COMPLEX_UNIT_PT, (float)5.0);
		
		if(object.getDurationTime()<=1){
			textHour.setText(R.string.hour);
		}else{
			textHour.setText(R.string.hours);
		}
		textHour.setTypeface(FontLoader.getFontTypeface(
				getContext().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		//textHour.setTextSize(FontLoader.getFontSizeFromPt(app, (float)5));
		textHour.setTextSize(TypedValue.COMPLEX_UNIT_PT, (float)5.0);
		
		DurationTimeInfo.setText(DateUtils.getDurationTime(object.getDurationTime()));
		DurationTimeInfo.setTypeface(FontLoader.getFontTypeface(
				getContext().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		//DurationTimeInfo.setTextSize(FontLoader.getFontSizeFromPt(app, (float)6));
		DurationTimeInfo.setTextSize(TypedValue.COMPLEX_UNIT_PT, (float)6.0);
		
		textNextTime.setTypeface(FontLoader.getFontTypeface(
				getContext().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		//textNextTime.setTextSize(FontLoader.getFontSizeFromPt(app, (float)5));
		textNextTime.setTextSize(TypedValue.COMPLEX_UNIT_PT, (float)5.0);
		
		NextTimeInfo.setText(
				DateUtils.getTimes(object.getDeptTime()));
		NextTimeInfo.setTypeface(FontLoader.getFontTypeface(
				getContext().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		//NextTimeInfo.setTextSize(FontLoader.getFontSizeFromPt(app, (float)6));
		NextTimeInfo.setTextSize(TypedValue.COMPLEX_UNIT_PT, (float)6.0);
		
		//TODO setTerminal Info
		if(object.getTerminalList().size()>0){
			//Log.i("DebugPrint","Terminal:"+object.getTerminalList().size());
			//NextTimeInfo.setText(""+object.getTerminalList().size());
		}
		
		return view;
		
	}
}
