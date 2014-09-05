package com.pinetree.cambus.models;

import java.util.Comparator;

public class CityModel extends Model {
	protected String city_name = "";
	protected int city_no = -1;
	protected boolean isHigh = false;
	
	public void setCityName(String cityName){
		city_name = cityName;
	}
	public void setCityNo(int no){
		city_no = no;
	}
	public String getCityName(){
		return city_name;
	}
	public int getCityNo(){
		return city_no;
	}

	public void setHigh(boolean high){
		isHigh = high;
	}
	
	public boolean isHigh(){
		return isHigh;
	}
	
	/*
	// order by isHigh asc
	public static class IsHighAscCompare implements Comparator<CityModel>{
		@Override
		public int compare(CityModel lhs, CityModel rhs) {
			return
				lhs.isHigh() < rhs.isHigh() ? -1 :
				lhs.getForeignerPrice() > rhs.getForeignerPrice() ? 1 :
				0;
		}
	}
	*/
}
