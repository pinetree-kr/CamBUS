package com.pinetree.cambus.models;

import java.util.ArrayList;
import java.util.Collections;

import com.pinetree.cambus.utils.DBHandler;

import android.content.Context;

public class BusListModel extends Model {
	protected ArrayList<BusInfoModel> bus_list;
	protected DepartureModel departure;
	protected DestinationModel destination;
	protected TypeModel type;
	protected int time;
	protected int distance;
	protected String order;
	protected boolean asc;
	
	public BusListModel(){
		bus_list = new ArrayList<BusInfoModel>();
		departure = new DepartureModel();
		destination = new DestinationModel();
		type = new TypeModel();
		time = -1;
		distance = 0;
		order = "";
		asc = true;
	}
	
	public ArrayList<BusInfoModel> getBusList(String order){
		if(order.equals(this.order)){
			asc = !asc;
		}else{
			asc = true;
		}
		
		//기본 정렬을 시킨다(오름차순)
		Collections.sort(bus_list, new BusInfoModel.NearByAscCompare());
		Collections.sort(bus_list, new BusInfoModel.ForeignerPriceAscCompare());
		Collections.sort(bus_list, new BusInfoModel.DepartureTimeAscCompare());
		
		if(order.equals("time")){
			if(asc){
				Collections.sort(bus_list, new BusInfoModel.DepartureTimeAscCompare());
			}else{
				Collections.sort(bus_list, new BusInfoModel.DepartureTimeDescCompare());
			}
		}else if(order.equals("price")){
			if(asc){
				Collections.sort(bus_list, new BusInfoModel.ForeignerPriceAscCompare());
			}else{
				Collections.sort(bus_list, new BusInfoModel.ForeignerPriceDescCompare());
			}
		}else if(order.equals("nearby")){
			if(asc){
				Collections.sort(bus_list, new BusInfoModel.NearByAscCompare());
			}else{
				Collections.sort(bus_list, new BusInfoModel.NearByDescCompare());
			}
		}
		
		this.order = order;
		return bus_list;
	}
	
	public boolean isAsc(){
		return asc;
	}
	
	public void setDeparture(DepartureModel departure){
		this.departure = departure;
	}
	public void setDestination(DestinationModel destination){
		this.destination = destination;
	}
	public void setTime(int time){
		this.time = time;
	}
	public void distance(int distance){
		this.distance = distance;
	}
	public void setType(TypeModel type){
		this.type = type;
	}
	
	public DepartureModel getDeparture(){
		return departure;
	}
	public DestinationModel getDestination(){
		return destination;
	}
	public int getTime(){
		return time;
	}
	public TypeModel getType(){
		return type;
	}
	public int getDistance(){
		return distance;
	}
	
	public void getBusList(DBHandler handler){
		bus_list = handler.getBusInfoList(
				departure.getCityNo(),
				destination.getCityNo(),
				time,
				type.getTypeNo());
	}
	
	public String getOrder(){
		return order;
	}
}
