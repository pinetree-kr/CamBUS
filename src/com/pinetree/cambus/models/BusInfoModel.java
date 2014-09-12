package com.pinetree.cambus.models;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import android.util.Log;

import com.pinetree.cambus.utils.DateUtils;
import com.pinetree.cambus.utils.NumberUtils;

public class BusInfoModel extends Model {
	protected int bus_no = -1;
	//protected String preference = "";
	protected int departure_no = -1;
	protected String departure = "";
	protected int destination_no = -1;
	protected String destination = "";
	protected int middleCity_no = -1;
	protected String middleCity = "";
	
	protected int company_no = -1;
	protected String company = "";
	
	protected int departureHour = -1;
	protected int departureMin = -1; 
	
	protected int arrivalHour = -1;
	protected int arrivalMin = -1;
	
	protected double durationTime = 0;
	
	protected String remarks = "";
	protected String quality = "";
	protected String operation = "";
	
	protected int type_no = -1;
	protected String type = "";

	protected double nativePrice = 0;
	protected double foreignerPrice = 0;
	protected double visa = 0;
	
	protected String dn = "";
	protected String lastUpdated = "";
	//protected Date lastUpdate = null;
	
	public void setBusNo(int no){
		this.bus_no = no;
	}
	public int getBusNo(){
		return bus_no;
	}
	/*/
	public void setPreference(String preference){
		this.preference = preference;
	}
	public String getPreference(){
		return preference;
	}
	/**/
	public void setDepartureNo(int no){
		this.departure_no = no;
	}
	public int getDepartureNo(){
		return departure_no;
	}
	public void setDestinationNo(int no){
		this.destination_no = no;
	}
	public int getDestinationNo(){
		return destination_no;
	}
	public void setMiddleCityNo(int no){
		this.middleCity_no = no;
	}
	public int getMiddleCityNo(){
		return middleCity_no;
	}
	
	public void setDeparture(String departure){
		this.departure = departure;
	}
	public String getDeparture(){
		return departure;
	}
	public void setDestination(String destination){
		this.destination = destination;
	}
	public String getDestination(){
		return destination;
	}
	public void setMiddleCity(String middleCity){
		this.middleCity = middleCity;
	}
	public String getMiddleCity(){
		return middleCity;
	}
	public void setCompanyNo(int companyNo){
		this.company_no = companyNo;
	}
	public void setCompany(String company){
		this.company = company;
	}
	public String getCompany(){
		return company;
	}
	public int getCompanyNo(){
		return company_no;
	}
	public void setDepartureTime(Date departureTime){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(departureTime);
		
		departureHour = calendar.get(Calendar.HOUR_OF_DAY);
		departureMin = calendar.get(Calendar.MINUTE);
		
		// 04:00 이전은 심야, 이후는 오전
		if(departureHour<4)
			departureHour += 24;
		
	}
	public void setDepartureTime(String departureTime){
		String[] time = departureTime.split(":");
		departureHour = Integer.parseInt(time[0]);
		departureMin = Integer.parseInt(time[1]);
		
		// 04:00 이전은 심야, 이후는 오전
		if(departureHour<4)
			departureHour += 24;
	}
	/*
	public Date getDepartureTime(){
		return departureTime;
	}
	*/
	public int getDepartureHour(){
		return departureHour;
	}
	public int getDepartureMin(){
		return departureMin;
	}
	public String getDepartureTime(){
		return String.format("%02d:%02d", departureHour, departureMin);
		//return DateUtils.getTimes(departureHour, departureMin);
	}
	public void setArrivalTime(Date arrivalTime){
		if(arrivalTime!=null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(arrivalTime);
			arrivalHour = calendar.get(Calendar.HOUR_OF_DAY);
			arrivalMin = calendar.get(Calendar.MINUTE);
			
			// 04:00 이전은 심야, 이후는 오전
			if(arrivalHour<4)
				arrivalHour += 24;
		}
	}
	public void setArrivalTime(String arrivalTime){
		String[] time = arrivalTime.split(":");
		if(NumberUtils.isNumeric(time[0]) && NumberUtils.isNumeric(time[1])){
			arrivalHour = Integer.parseInt(time[0]);
			arrivalMin = Integer.parseInt(time[1]);
			
			// 04:00 이전은 심야, 이후는 오전
			if(arrivalHour<4)
				arrivalHour += 24;
		}
	}
	/*
	public Date getArrivalTime(){
		return arrivalTime;
	}
	*/
	public int getArrivalHour(){
		return arrivalHour;
	}
	public int getArrivalMin(){
		return arrivalMin;
	}
	public String getArrivalTime(){
		if(arrivalHour<0 || arrivalMin<0)
			return "--:--";
		return String.format("%02d:%02d", arrivalHour, arrivalMin);
		//return DateUtils.getTimes(arrivalHour, arrivalMin);
	}
	public void setDurationTime(double durationTime){
		this.durationTime = durationTime;
	}
	public double getDurationTime(){
		return durationTime;
	}
	public String getNearBy(){
		String nearBy = "";
		// 소수값이 있는지 확인
		
		if((durationTime - (int)durationTime)>0){
			nearBy = (int)durationTime + "~" + ((int)durationTime+1);
		}else{
			nearBy = (int)durationTime + "";
		}
		
		return nearBy;
	}
	public void setTypeNo(int no){
		this.type_no = no;
	}
	public int getTypeNo(){
		return type_no;
	}
	public void setType(String type){
		this.type = type;
	}
	public String getType(){
		return type;
	}
	public void setNativePrice(double nativePrice){
		this.nativePrice = nativePrice;
	}
	public double getNativePrice(){
		return nativePrice;
	}
	public void setForeignerPrice(double foreignerPrice){
		this.foreignerPrice = foreignerPrice;
	}
	public double getForeignerPrice(){
		return foreignerPrice;
	}
	public void setVisa(double visa){
		this.visa = visa;
	}
	public double getVisa(){
		return visa;
	}
	public void setDN(String dn){
		this.dn = dn;
	}
	public String getDN(){
		return dn;
	}
	public void setLastUpdate(Date lastUpdated){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(lastUpdated);
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		this.lastUpdated = String.format("%04d-%02d-%02d",year,month,day);
	}
	public void setLastUpdate(String lastUpdated){
		this.lastUpdated = lastUpdated;
	}
	public String getLastUpdated(){
		return lastUpdated;
	}
	public void setRemarks(String remarks){
		this.remarks = remarks;
	}
	public String getRemarks(){
		return remarks;
	}
	public void setOperation(String operation){
		this.operation = operation;
	}
	public String getOperation(){
		return operation;
	}
	public void setQuality(String quality){
		this.quality = quality;
	}
	public String getQuality(){
		return quality;
	}
	
	// order by departure time asc
	public static class DepartureTimeAscCompare implements Comparator<BusInfoModel>{
		@Override
		public int compare(BusInfoModel lhs, BusInfoModel rhs) {
			return lhs.getDepartureTime().compareTo(rhs.getDepartureTime());
		}
	}
	// order by departure time desc
	public static class DepartureTimeDescCompare implements Comparator<BusInfoModel>{
		@Override
		public int compare(BusInfoModel lhs, BusInfoModel rhs) {
			return rhs.getDepartureTime().compareTo(lhs.getDepartureTime());
		}
	}
	
	// order by foreigner price asc
	public static class ForeignerPriceAscCompare implements Comparator<BusInfoModel>{
		@Override
		public int compare(BusInfoModel lhs, BusInfoModel rhs) {
			return
				lhs.getForeignerPrice() < rhs.getForeignerPrice() ? -1 :
				lhs.getForeignerPrice() > rhs.getForeignerPrice() ? 1 :
				0;
		}
	}
	// order by foreigner price desc
	public static class ForeignerPriceDescCompare implements Comparator<BusInfoModel>{
		@Override
		public int compare(BusInfoModel lhs, BusInfoModel rhs) {
			return
				rhs.getForeignerPrice() < lhs.getForeignerPrice() ? -1 :
				rhs.getForeignerPrice() > lhs.getForeignerPrice() ? 1 :
				0;
		}
	}
	
	// order by nearby asc
	public static class NearByAscCompare implements Comparator<BusInfoModel>{
		@Override
		public int compare(BusInfoModel lhs, BusInfoModel rhs) {
			return
				lhs.getDurationTime() < rhs.getDurationTime() ? -1 :
				lhs.getDurationTime() > rhs.getDurationTime() ? 1 :
				0;
		}
	}
	// order by nearby desc
	public static class NearByDescCompare implements Comparator<BusInfoModel>{
		@Override
		public int compare(BusInfoModel lhs, BusInfoModel rhs) {
			return
				rhs.getDurationTime() < lhs.getDurationTime() ? -1 :
				rhs.getDurationTime() > lhs.getDurationTime() ? 1 :
				0;
		}
	}
}
