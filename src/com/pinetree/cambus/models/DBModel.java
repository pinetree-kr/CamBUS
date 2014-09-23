package com.pinetree.cambus.models;

import java.util.Comparator;

import com.pinetree.cambus.utils.NumberUtils;

public class DBModel{
	public static class City extends Model{
		public int city_no;
		public String city_name;
		public boolean high;
		
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
		public int company_no;
		public String company_name;
	}
	public static class BusType extends Model{
		public int type_no;
		public String type_name;
	}
	public static class Office extends Model{
		public int office_no;
		public String office_name;
		public int city_no;
		public String city_name;
		public int company_no;
		public String company_name;
		public String phone_no;
		public boolean purchase;
		public boolean get_in;
		public boolean get_off;
		public String link;
		public String address;
		public String misc_en;
		public String misc_ko;
		
		public String[] getPhoneNoList(){
			String[] list = phone_no.split("/");
			for(int i=0; i<list.length; i++){
				list[i] = list[i].trim();
			}
			return list;
		}
	}
	public static class Line extends Model{
		public int line_no;
		public int dept_no;
		public String dept_name;
		public boolean dept_high;
		public int dest_no;
		public String dest_name;
		public boolean dest_high;
		public int distance;
	}
	public static class LineBus extends Line{
		public int linebus_no;
		public int company_no;
		public String company_name;
		public int type_no;
		public String type_name;
		public double duration_time = 0;
		public double native_price = 0;
		public double foreigner_price = 0;
		public double visa = 0;
		public String dn;
	}
	public static class LineBusTime extends LineBus{
		public int linebustime_no;
		public int mid_no;
		public String middle_city;
		//public String departure_time;
		public int dept_hour;
		public int dept_min;
		
		//public String arrival_time;
		public int arrival_hour;
		public int arrival_min;
		
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
