package com.pinetree.cambus.models;

import java.util.Comparator;

import com.pinetree.cambus.utils.NumberUtils;

public class DBModel{
	public static class City extends Model{
		protected int city_no;
		protected String city_name;
		protected boolean high;
		
		public void setCityNo(int no){
			city_no = no;
		}
		public int getCityNo(){
			return city_no;
		}
		public void setCityName(String name){
			city_name = name;
		}
		public String getCityName(){
			return city_name;
		}
		public void setHigh(boolean high){
			this.high = high;
		}
		public boolean getHigh(){
			return high;
		}
		
		// order by preference asc
		public static class PreferenceAscCompare implements Comparator<City>{
			@Override
			public int compare(City lhs, City rhs) {
				// if it is dummy, do not sorting
				if(lhs.city_no<1 || rhs.city_no <1)
					return 0;
				else
					return
						!lhs.high && rhs.high ? -1 :
							lhs.high && !rhs.high ? 1 :
								0;
			}
		}
		// order by preference desc
		public static class PreferenceDescCompare implements Comparator<City>{
			@Override
			public int compare(City lhs, City rhs) {
				// if it is dummy, do not sorting
				if(lhs.city_no<1 || rhs.city_no <1)
					return 0;
				else
					return
						!rhs.high && lhs.high ? -1 :
							rhs.high && !lhs.high ? 1 :
								0;
			}
		}
	}
	public static class DepartureCity extends City{}
	public static class DestinationCity extends City{}
	
	public static class Company extends Model{
		protected int company_no;
		protected String company_name;
		
		public void setCompanyNo(int no){
			company_no = no;
		}
		public int getCompanyNo(){
			return company_no;
		}
		public void setCompanyName(String name){
			company_name = name;
		}
		public String getCompanyName(){
			return company_name;
		}
	}
	public static class BusType extends Model{
		protected int type_no;
		protected String type_name;
		
		public void setTypeNo(int no){
			type_no = no;
		}
		public int getTypeNo(){
			return type_no;
		}
		public void setTypeName(String name){
			type_name = name;
		}
		public String getTypeName(){
			return type_name;
		}
	}
	
	public static class Office extends Model{
		protected int office_no;
		protected String office_name;
		protected int city_no;
		protected String city_name;
		protected int company_no;
		protected String company_name;
		protected String phone_no;
		protected boolean purchase;
		protected boolean get_in;
		protected boolean get_off;
		protected String link;
		protected String address;
		protected String misc_en;
		protected String misc_ko;
		
		public void setPhoneNo(String no){
			phone_no = no;
		}
		public String getPhoneNo(){
			return phone_no;
		}
		public void setPurchase(boolean purchase){
			this.purchase = purchase;
		}
		public boolean isPurchase(){
			return purchase;
		}
		public void setGetIn(boolean in){
			get_in = in;
		}
		public boolean isGetIn(){
			return get_in;
		}
		public void setGetOff(boolean off){
			get_off = off;
		}
		public boolean isGetOff(){
			return get_off;
		}
		public void setLink(String link){
			this.link = link;
		}
		public String getLink(){
			return link;
		}
		public void setAddress(String address){
			this.address = address;
		}
		public String getAddress(){
			return address;
		}
		public void setMiscEn(String misc){
			misc_en = misc;
		}
		public String getMiscEn(){
			return misc_en;
		}
		public void setMiscKo(String misc){
			misc_ko = misc;
		}
		public String getMiscKo(){
			return misc_ko;
		}
		public void setOfficeNo(int no){
			office_no = no;
		}
		public int getOfficeNo(){
			return office_no;
		}
		public void setOfficeName(String name){
			office_name = name;
		}
		public String getOfficeName(){
			return office_name;
		}
		public void setCityNo(int no){
			city_no = no;
		}
		public int getCityNo(){
			return city_no;
		}
		public void setCityName(String name){
			city_name = name;
		}
		public String getCityName(){
			return city_name;
		}
		public void setCompanyNo(int no){
			company_no = no;
		}
		public int getCompanyNo(){
			return company_no;
		}
		public void setCompanyName(String name){
			company_name = name;
		}
		public String getCompanyName(){
			return company_name;
		}
		public String[] getPhoneNoList(){
			String[] list = phone_no.split("/");
			for(int i=0; i<list.length; i++){
				list[i] = list[i].trim();
			}
			return list;
		}
	}
	public static class Line extends Model{
		protected int line_no;
		protected int dept_no;
		protected String dept_name;
		protected boolean dept_high;
		protected int dest_no;
		protected String dest_name;
		protected boolean dest_high;
		protected int distance;
		
		public void setLineNo(int no){
			line_no = no;
		}
		public int getLineNo(){
			return line_no;
		}
		public void setDeptNo(int no){
			dept_no = no;
		}
		public int getDeptNo(){
			return dept_no;
		}
		public void setDestNo(int no){
			dest_no = no;
		}
		public int getDestNo(){
			return dest_no;
		}
		public void setDeptName(String name){
			dept_name = name;
		}
		public String getDeptName(){
			return dept_name;
		}
		public void setDestName(String name){
			dest_name = name;
		}
		public String getDestName(){
			return dest_name;
		}
		public void setDeptHigh(boolean high){
			dept_high = high;
		}
		public boolean getDeptHigh(){
			return dept_high;
		}
		public void setDestHigh(boolean high){
			dest_high = high;
		}
		public boolean getDestHigh(){
			return dest_high;
		}
		public void setDistance(int distance){
			this.distance = distance;
		}
		public int getDistance(){
			return distance;
		}
	}
	
	public static class LineBus extends Line{
		protected int linebus_no;
		protected int company_no;
		protected String company_name;
		protected int type_no;
		protected String type_name;
		protected double duration_time = 0;
		protected double native_price = 0;
		protected double foreigner_price = 0;
		protected double visa = 0;
		protected String dn;
		
		public void setLineBusNo(int no){
			linebus_no = no;
		}
		public int getLineBusNo(){
			return linebus_no;
		}
		public void setCompanyNo(int no){
			company_no = no;
		}
		public int getCompanyNo(){
			return company_no;
		}
		public void setCompanyName(String name){
			company_name = name;
		}
		public String getCompanyName(){
			return company_name;
		}
		public void setTypeNo(int no){
			type_no = no;
		}
		public int getTypeNo(){
			return type_no;
		}
		public void setTypeName(String name){
			type_name = name;
		}
		public String getTypeName(){
			return type_name;
		}
		public void setDurationTime(double time){
			duration_time = time;
		}
		public double getDurationTime(){
			return duration_time;
		}
		public void setNativePrice(double price){
			native_price = price;
		}
		public double getNativePrice(){
			return native_price;
		}
		public void setForeignerPrice(double price){
			foreigner_price = price;
		}
		public double getForeignerPrice(){
			return foreigner_price;
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
	}
	
	public static class LineBusTime extends LineBus{
		protected int linebustime_no;
		protected int mid_no;
		protected String middle_city;
		
		protected int dept_hour;
		protected int dept_min;
		
		protected int arrival_hour;
		protected int arrival_min;
		
		public int getDeptHour(){
			return dept_hour;
		}
		public void setLineBusTimeNo(int no){
			linebustime_no = no;
		}
		public int getLineBusTimeNo(){
			return linebustime_no;
		}
		public void setMidNo(int no){
			mid_no = no;
		}
		public int getMidNo(){
			return mid_no;
		}
		public void setMiddleCity(String name){
			middle_city = name;
		}
		public String getMiddleCity(){
			return middle_city;
		}
		
		public void setDeptTime(String dept_time){
			String[] time = dept_time.split(":");
			dept_hour = Integer.parseInt(time[0]);
			dept_min = Integer.parseInt(time[1]);
			
			// 04:00 이전은 심야, 이후는 오전
			if(dept_hour<4)
				dept_hour += 24;
		}
		public void setArrivalTime(String arrival_time){
			String[] time = arrival_time.split(":");
			if(NumberUtils.isNumeric(time[0]) && NumberUtils.isNumeric(time[1])){
				arrival_hour = Integer.parseInt(time[0]);
				arrival_min = Integer.parseInt(time[1]);
				
				// 04:00 이전은 심야, 이후는 오전
				if(arrival_hour<4)
					arrival_hour += 24;
			}			
		}
		
		public String getDeptTime(){
			return String.format("%02d:%02d", dept_hour, dept_min);
		}
		public String getArrivalTime(){
			if(arrival_hour<=0 && arrival_min<=0)
				return "--:--";
			return String.format("%02d:%02d", arrival_hour, arrival_min);
		}
		// order by departure time asc
		public static class DepartureTimeAscCompare implements Comparator<LineBusTime>{
			@Override
			public int compare(LineBusTime lhs, LineBusTime rhs) {
				return lhs.getDeptTime().compareTo(rhs.getDeptTime());
			}
		}
		// order by departure time desc
		public static class DepartureTimeDescCompare implements Comparator<LineBusTime>{
			@Override
			public int compare(LineBusTime lhs, LineBusTime rhs) {
				return rhs.getDeptTime().compareTo(lhs.getDeptTime());
			}
		}
		
		// order by foreigner price asc
		public static class ForeignerPriceAscCompare implements Comparator<LineBusTime>{
			@Override
			public int compare(LineBusTime lhs, LineBusTime rhs) {
				return
						lhs.foreigner_price < rhs.foreigner_price ? -1 :
							lhs.foreigner_price > rhs.foreigner_price ? 1 :
								0;
			}
		}
		// order by foreigner price desc
		public static class ForeignerPriceDescCompare implements Comparator<LineBusTime>{
			@Override
			public int compare(LineBusTime lhs, LineBusTime rhs) {
				return
						rhs.foreigner_price < lhs.foreigner_price ? -1 :
							rhs.foreigner_price > lhs.foreigner_price ? 1 :
								0;
			}
		}
		
		// order by distance asc
		public static class DistanceAscCompare implements Comparator<LineBusTime>{
			@Override
			public int compare(LineBusTime lhs, LineBusTime rhs) {
				return
						lhs.distance < rhs.distance ? -1 :
							lhs.distance > rhs.distance ? 1 :
								0;
			}
		}
		// order by distance desc
		public static class DistanceDescCompare implements Comparator<LineBusTime>{
			@Override
			public int compare(LineBusTime lhs, LineBusTime rhs) {
				return
						rhs.distance < lhs.distance ? -1 :
							rhs.distance > lhs.distance ? 1 :
								0;
			}
		}
	}
}
