package com.pinetree.cambus.models;

import java.util.ArrayList;
import java.util.Comparator;

import com.google.android.gms.maps.model.LatLng;
import com.pinetree.cambus.utils.NumberUtils;

public class DBModel{
	public static class City extends Model{
		private int city_no;
		private String city_name;
		private boolean high;
		private int order;
		
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
		public void setOrder(int order){
			this.order = order;
		}
		public int getOrder(){
			return order;		
		}
		
		// order by preference asc
		public static class PrefOrderAscCompare implements Comparator<City>{
			@Override
			public int compare(City lhs, City rhs) {
				// not changed;
				if(lhs.city_no<1 || rhs.city_no <1)
					return 0;
				else{
					// high pref is in advance of low pref
					if(!lhs.high && rhs.high){
						return -1;
					}else if(lhs.high && !rhs.high){
						return 1;
					}
					// same pref
					else{
						if(lhs.getOrder() < rhs.getOrder()){
							return -1;
						}else if(lhs.getOrder() > rhs.getOrder()){
							return 1;
						}else{
							return 0;
						}
					}
				}
			}
		}
		// order by preference desc
		public static class PrefOrderDescCompare implements Comparator<City>{
			@Override
			public int compare(City lhs, City rhs) {
				// not changed;
				if(lhs.city_no<1 || rhs.city_no <1)
					return 0;
				else{
					// high pref is in advance of low pref
					if(!rhs.high && lhs.high){
						return -1;
					}else if(rhs.high && !lhs.high){
						return 1;
					}
					// same pref
					else{
						if(lhs.getOrder() < rhs.getOrder()){
							return -1;
						}else if(lhs.getOrder() > rhs.getOrder()){
							return 1;
						}else{
							return 0;
						}
					}
				}
			}
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
		private int company_no;
		private String company_name;
		
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
		private int type_no;
		private String type_name;
		
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
	public static class Terminal extends Model{
		private int terminal_no;
		private String terminal_name;
		private int city_no;
		private String city_name;
		private int company_no;
		private String company_name;
		private String phone_no;
		private boolean purchase;
		private boolean get_in;
		private boolean get_off;
		private String link;
		private String address;
		private String misc_en;
		private String misc_ko;
		private double latitude;
		private double longitude;
		private boolean hasPosition;
		
		public void setLatLng(String value){
			try{
				hasPosition = false;
				if(value!=null){
					String[] var = value.split(",");
					latitude = Double.parseDouble(var[0]);
					longitude = Double.parseDouble(var[1]);
					hasPosition = true;
				}
			}catch(NumberFormatException e){
			}
		}
		public String getLatLng(){
			if(hasPosition)
				return latitude+","+longitude;
			else
				return null;
		}
		public boolean hasPosition(){
			return hasPosition;
		}
		//@Override
		public LatLng getPosition(){
			if(hasPosition)
				return new LatLng(latitude, longitude);
			else
				return null;
		}
		
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
		public void setTerminalNo(int no){
			terminal_no = no;
		}
		public int getTerminalNo(){
			return terminal_no;
		}
		public void setTerminalName(String name){
			terminal_name = name;
		}
		public String getTerminalName(){
			return terminal_name;
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
		private int line_no;
		private int dept_no;
		private String dept_name;
		private boolean dept_high;
		private int dept_order;
		private int dest_no;
		private String dest_name;
		private boolean dest_high;
		private int dest_order;
		private int distance;
		
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
		public void setDeptOrder(int order){
			dept_order = order;
		}
		public boolean getDeptHigh(){
			return dept_high;
		}
		public int getDeptOrder(){
			return dept_order;
		}
		public void setDestHigh(boolean high){
			dest_high = high;
		}
		public void setDestOrder(int order){
			dest_order = order;
		}
		public boolean getDestHigh(){
			return dest_high;
		}
		public int getDestOrder(){
			return dest_order;
		}
		public void setDistance(int distance){
			this.distance = distance;
		}
		public int getDistance(){
			return distance;
		}
	}
	
	public static class LineBus extends Line{
		private int linebus_no;
		private int company_no;
		private String company_name;
		private int type_no;
		private String type_name;
		private double duration_time = 0;
		private double native_price = 0;
		private double foreigner_price = 0;
		private double visa = 0;
		private String dn;
		
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
		private int linebustime_no;
		private int mid_no;
		private String middle_city;
		
		private int dept_hour;
		private int dept_min;
		
		private int arrival_hour;
		private int arrival_min;
		
		private ArrayList<Terminal> terminal_list = new ArrayList<Terminal>();
		
		public void addTerminal(Terminal terminal){
			if(!terminal_list.contains(terminal))
				terminal_list.add(terminal);
		}
		public ArrayList<Terminal> getTerminalList(){
			return terminal_list;
		}
		
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
						lhs.getForeignerPrice() < rhs.getForeignerPrice() ? -1 :
							lhs.getForeignerPrice() > rhs.getForeignerPrice() ? 1 :
								0;
			}
		}
		// order by foreigner price desc
		public static class ForeignerPriceDescCompare implements Comparator<LineBusTime>{
			@Override
			public int compare(LineBusTime lhs, LineBusTime rhs) {
				return
						rhs.getForeignerPrice() < lhs.getForeignerPrice() ? -1 :
							rhs.getForeignerPrice() > lhs.getForeignerPrice() ? 1 :
								0;
			}
		}
		
		// order by distance asc
		public static class DistanceAscCompare implements Comparator<LineBusTime>{
			@Override
			public int compare(LineBusTime lhs, LineBusTime rhs) {
				return
						lhs.getDistance() < rhs.getDistance() ? -1 :
							lhs.getDistance() > rhs.getDistance() ? 1 :
								0;
			}
		}
		// order by distance desc
		public static class DistanceDescCompare implements Comparator<LineBusTime>{
			@Override
			public int compare(LineBusTime lhs, LineBusTime rhs) {
				return
						rhs.getDistance() < lhs.getDistance() ? -1 :
							rhs.getDistance() > lhs.getDistance() ? 1 :
								0;
			}
		}
	}
}
