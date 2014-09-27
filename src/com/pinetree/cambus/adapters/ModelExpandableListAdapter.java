package com.pinetree.cambus.adapters;

import java.util.ArrayList;

import com.pinetree.cambus.R;
import com.pinetree.cambus.models.DBModel.LineBus;
import com.pinetree.cambus.models.DBModel.LineBusTime;
import com.pinetree.cambus.utils.DateUtils;
import com.pinetree.cambus.utils.DeviceInfo;
import com.pinetree.cambus.utils.FontLoader;
import com.pinetree.cambus.viewholders.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ModelExpandableListAdapter extends BaseExpandableListAdapter{

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
	/*
	protected LayoutInflater inflater;
	protected ArrayList<LineBus> groups;
	protected ArrayList<ArrayList<LineBusTime>> childs;
	
	protected DeviceInfo app;
	
	public ModelExpandableListAdapter(Context context,
			ArrayList<LineBus> groups, ArrayList<ArrayList<LineBusTime>> childs){
		super();
		this.inflater = LayoutInflater.from(context);
		this.groups = groups;
		this.childs = childs;
		app = (DeviceInfo)context;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childs.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View view = convertView;
		//Log.i("DebugPrint","getChild "+groupPosition+","+childPosition + ":" + (Integer)this.getChild(groupPosition, childPosition));
		
		if(view == null){
			view = inflater.inflate(R.layout.layout_interview_childlist, null);
		}
		ImageView imageView = (ImageView)view.findViewById(R.id.imageViewForList);
		imageView.setImageResource((Integer)this.getChild(groupPosition, childPosition));
		return view;
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
		
		//LineBusTime object = (LineBusTime) getItem(position);
		LineBus object = (LineBus) getItem(position);
		
		textCompany.setText((position+1) + ". "+object.getCompanyName());
		textCompany.setTypeface(FontLoader.getFontTypeface(
				getContext().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		textCompany.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)7.2));
		
		textType.setText("["+object.getTypeName()+"]");
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
		
		DurationTimeInfo.setText(String.valueOf(object.getDurationTime()));
		DurationTimeInfo.setTypeface(FontLoader.getFontTypeface(
				getContext().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		DurationTimeInfo.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)6));
		
		textNextTime.setTypeface(FontLoader.getFontTypeface(
				getContext().getAssets(),
				"HelveticaNeueLTStd-Lt.otf"));
		textNextTime.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)5));
		
		if(object.getTimeList().size()>0){
			NextTimeInfo.setText(DateUtils.getTimes(object.getTimeList().get(0).getDeptTime()));
			NextTimeInfo.setTypeface(FontLoader.getFontTypeface(
					getContext().getAssets(),
					"HelveticaNeueLTStd-Lt.otf"));
			NextTimeInfo.setTextSize(FontLoader.getFontSizeFromPt(app.rateDpi, (float)6));
		}else{
			textNextTime.setText(R.string.none_bus);
		}
		
		return view;
		
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
	*/
}
