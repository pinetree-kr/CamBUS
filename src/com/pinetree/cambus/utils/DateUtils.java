package com.pinetree.cambus.utils;

public class DateUtils {
	public static String getTimes(int time){
		String str = "";
		String hours = "";
		if(time<12){
			hours = "AM";
		}else if(time>=12 && time<24){
			hours = "PM";
		}else{
			hours = "AM";
		}
		time %= 12;
		if(time==0){
			str = "12:00 " + hours;					
		}else{
			str = String.format("%02d",time)+":00 " + hours;
		}
		return str;
	}
	
	public static String getTimes(int hour, int min){
		String str = "";
		String hours = "";
		
		if(hour<12){
			hours = "AM";
		}else if(hour>=12 && hour<24){
			hours = "PM";
		}else{
			hours = "AM";
		}
		
		
		if(hour%12==0){
			str = "12";					
		}else{
			str = String.format("%02d", hour%12);
		}
		
		str += String.format(":%02d ", min)+hours;
		
		return str;
	}
	
	public static String getTimes(String time){
		String[] hourmin = time.split(":");
		int hour = Integer.valueOf(hourmin[0]);
		int min = Integer.valueOf(hourmin[1]);
		
		String str = "";
		String hours = "";
		
		if(hour<12){
			hours = "AM";
		}else if(hour>=12 && hour<24){
			hours = "PM";
		}else{
			hours = "AM";
		}
		
		hour %= 12;
		if(hour==0){
			str = "12";					
		}else{
			str = String.format("%02d", hour);
		}
		
		str += String.format(":%02d ", min)+hours;
		
		return str;
	}
}
