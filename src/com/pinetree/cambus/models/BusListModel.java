package com.pinetree.cambus.models;

import java.util.ArrayList;
import java.util.Collections;

import com.pinetree.cambus.models.DBModel.*;
import com.pinetree.cambus.utils.DBHandler;

import android.content.Context;
import android.util.Log;

public class BusListModel extends Model {
	protected ArrayList<LineBusTime> linebustime_list;
	protected Line line_info;
	protected int dept_time;
	protected int type_no;
	
	protected String order;
	protected boolean asc;
	
	public BusListModel(Line line_info, int dept_time, int type_no){
		linebustime_list = new ArrayList<LineBusTime>();
		this.line_info = line_info;
		this.dept_time = dept_time;
		this.type_no = type_no;
		order = "time";
		asc = true;
	}
	
	public Line getLineInfo(){
		return line_info;
	}
	
	public ArrayList<LineBusTime> getSortedLineBusTimeList(String order){
		if(order.equals(this.order)){
			//asc = !asc;
			asc = true;
		}else{
			asc = true;
		}
		
		//기본 정렬을 시킨다(오름차순)
		if(order.equals("time")){
			if(asc){
				Collections.sort(linebustime_list, new LineBusTime.DepartureTimeAscCompare());
			}else{
				Collections.sort(linebustime_list, new LineBusTime.DepartureTimeDescCompare());
			}
		}else if(order.equals("price")){
			if(asc){
				Collections.sort(linebustime_list, new LineBusTime.ForeignerPriceAscCompare());
			}else{
				Collections.sort(linebustime_list, new LineBusTime.ForeignerPriceDescCompare());
			}
		}else if(order.equals("nearby")){
			if(asc){
				Collections.sort(linebustime_list, new LineBusTime.DistanceAscCompare());
			}else{
				Collections.sort(linebustime_list, new LineBusTime.DistanceDescCompare());
			}
		}
		
		this.order = order;
		return linebustime_list;
	}
	
	public boolean isAsc(){
		return asc;
	}
	
	public void updateLineBusTimeList(DBHandler handler){
		linebustime_list = handler.getLineBusTimeList(
				line_info.dept_no,
				line_info.dest_no,
				dept_time,
				type_no);
	}
	
	public String getOrder(){
		return order;
	}
	public int getDeptTime(){
		return dept_time;
	}
	public int getTypeNo(){
		return type_no;
	}
}
