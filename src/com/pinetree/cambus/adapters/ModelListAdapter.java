package com.pinetree.cambus.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinetree.cambus.R;
import com.pinetree.cambus.models.DBModel.CityRoute;
import com.pinetree.cambus.models.DBModel.Time;
import com.pinetree.cambus.utils.DeviceInfo;
import com.pinetree.utils.FontLoader;
import com.pinetree.utils.ImageLoader;
import com.pinetree.utils.ViewHolder;

public class ModelListAdapter<T> extends ArrayAdapter<T>{
	protected LayoutInflater inflater;
	protected ArrayList<T> objects;
	protected DeviceInfo app;
	protected FontLoader fontLoader;
	protected ImageLoader imageLoader;
	public ModelListAdapter(Context context, int resource,
			ArrayList<T> objects) {		
		super(context, resource);
		
		inflater = LayoutInflater.from(context);
		this.objects = objects;
		app = (DeviceInfo)context;
		fontLoader = new FontLoader(context);
		imageLoader = new ImageLoader(context.getResources(), app.getScaledRate());
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
		
		T object = objects.get(position);
		
		//Log.i("DebugPrint","super:"+object.getClass());
		
		// Time
		if(object.getClass().equals(Time.class)){
			if(view == null){
				view = inflater.inflate(R.layout.bus_list_row, parent, false);
			}
			Time time = (Time) object;
			
			TextView textCompany = ViewHolder.get(view, R.id.textCompany);
			fontLoader.setTextViewTypeFace(textCompany, time.getCompanyName(), R.string.lato_semibold, (float)8);
			
			ImageView imageSeat = ViewHolder.get(view, R.id.imageSeat);
			imageSeat.setImageDrawable(
					imageLoader.getResizedDrawable(R.drawable.chair));
			
			TextView textPrice = ViewHolder.get(view, R.id.textPrice);
			fontLoader.setTextViewTypeFace(textPrice, time.getForeign()+"$", R.string.lato_regular, (float)8.25);

			TextView textDeptTime = ViewHolder.get(view, R.id.textDeptTime);
			fontLoader.setTextViewTypeFace(textDeptTime, time.getDeptTime(), R.string.lato_medium, (float)8.5);
			
			TextView textSeat = ViewHolder.get(view, R.id.textSeat);
			String seat;
			if(time.getSeat()!=1){
				seat = time.getSeat() + " " + getContext().getResources().getString(R.string.seats);
			}else{
				seat = time.getSeat() + " " + getContext().getResources().getString(R.string.seat);			
			}
			fontLoader.setTextViewTypeFace(textSeat, seat, R.string.lato_regular, (float)8.25);
			
			//TODO setTerminal Info
			if(time.getTerminalList().size()>0){
				
				ImageView imageMoreInfo = ViewHolder.get(view, R.id.moreBtn);
				imageMoreInfo.setImageDrawable(
						imageLoader.getResizedDrawable(R.drawable.info_icon));
			}
		}
		//City Route
		else if(object.getClass().equals(CityRoute.class)){
			if(view == null){
				view = inflater.inflate(R.layout.route_stations_list_row, parent, false);
			}
			
			CityRoute route = (CityRoute) object;
			
			ImageView routeDirection = ViewHolder.get(view, R.id.imageStation);
			if(position==0){
				routeDirection.setImageDrawable(
						imageLoader.getResizedDrawable(R.drawable.route_top));
			}else if(position==getCount()-1){
				routeDirection.setImageDrawable(
						imageLoader.getResizedDrawable(R.drawable.route_bottom));
			}else{
				routeDirection.setImageDrawable(
						imageLoader.getResizedDrawable(R.drawable.route_mid));
			}
			TextView textEng = ViewHolder.get(view, R.id.textEng);
			fontLoader.setTextViewTypeFace(textEng, route.getName("eng"), R.string.lato_bold, (float)5.5);
			TextView textKhm = ViewHolder.get(view, R.id.textKhm);
			fontLoader.setTextViewTypeFace(textKhm, route.getName("khm"), R.string.khm_notoserif, (float)5.0);
		}
		
		return view;
		
	}
}
