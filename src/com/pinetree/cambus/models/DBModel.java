package com.pinetree.cambus.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;
import com.pinetree.utils.FormatUtil;

public class DBModel{
	public static class CityRoute extends Model{
		private String city_name;
		private int city_id;
		private int line_no;
		private int line_order;
		private HashMap<String, String> name;
		public void setCityName(String name){
			city_name = name;
		}
		public String getCityName(){
			return city_name;
		}
		public void setCityId(int id){
			city_id = id;
		}
		public int getCityId(){
			return city_id;
		}
		public void setLineNo(int no){
			line_no = no;
		}
		public int getLineNo(){
			return line_no;
		}
		public void setLineOrder(int order){
			line_order = order;
		}
		public int getLineOrder(){
			return line_order;
		}
		public void setName(String json){
			if(this.name==null){
				this.name = new HashMap<String, String>();
			}
			try {
				JSONArray jsonArray = new JSONArray(json);
				for(int i=0; i<jsonArray.length(); i++){
					JSONObject obj = jsonArray.getJSONObject(i);
					name.put(obj.getString("lang"),obj.getString("name"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		public void setName(String language, String name){
			if(this.name==null){
				this.name = new HashMap<String, String>();
			}
			this.name.put(language, name);
		}
		/*/
		public HashMap<String, String> getName(){
			return name;
		}
		/**/
		public String getName(){
			String val = "";
			if(name!=null){
				for(Entry<String, String> entry: name.entrySet()){
					val += "{\"lang\":\""+entry.getKey()+"\",\"name\":\""+entry.getValue()+"\"},";
				}
				if(val.length()>0){
					val = val.substring(0, val.length()-1);
				}
			}
			return "["+val+"]";
		}
		public String getName(String language){
			if(name!=null)
				return name.get(language);
			else
				return null;
		}
	}
	
	public static class City extends Model{
		private int _id;
		private String name;
		private boolean pref;
		private int index;
		
		public void setId(int id){
			_id = id;
		}
		public int getId(){
			return _id;
		}
		public void setName(String name){
			this.name = name;
		}
		public String getName(){
			return name;
		}
		public void setPref(boolean pref){
			this.pref = pref;
		}
		public boolean getPref(){
			return pref;
		}
		public void setIndex(int index){
			this.index = index;
		}
		public int getIndex(){
			return index;		
		}
		
		// order by preference asc
		public static class PrefOrderAscCompare implements Comparator<City>{
			@Override
			public int compare(City lhs, City rhs) {
				// not changed;
				if(lhs._id<1 || rhs._id <1)
					return 0;
				else{
					// high pref is in advance of low pref
					if(!lhs.pref && rhs.pref){
						return -1;
					}else if(lhs.pref && !rhs.pref){
						return 1;
					}
					// same pref
					else{
						if(lhs.index < rhs.index){
							return -1;
						}else if(lhs.index > rhs.index){
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
				if(lhs._id<1 || rhs._id <1)
					return 0;
				else{
					// high pref is in advance of low pref
					if(!rhs.pref && lhs.pref){
						return -1;
					}else if(rhs.pref && !lhs.pref){
						return 1;
					}
					// same pref
					else{
						if(lhs.index < rhs.index){
							return -1;
						}else if(lhs.index > rhs.index){
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
				if(lhs._id<1 || rhs._id <1)
					return 0;
				else
					return
						!lhs.pref && rhs.pref ? -1 :
							lhs.pref && !rhs.pref ? 1 :
								0;
			}
		}
		// order by preference desc
		public static class PreferenceDescCompare implements Comparator<City>{
			@Override
			public int compare(City lhs, City rhs) {
				// if it is dummy, do not sorting
				if(lhs._id<1 || rhs._id <1)
					return 0;
				else
					return
						!rhs.pref && lhs.pref ? -1 :
							rhs.pref && !lhs.pref ? 1 :
								0;
			}
		}
	}
	public static class Departure extends City{}
	public static class Destination extends City{}
	
	public static class Company extends Model{
		private int _id;
		private String name;
		
		public void setId(int id){
			_id = id;
		}
		public int getId(){
			return _id;
		}
		public void setName(String name){
			this.name = name;
		}
		public String getName(){
			return name;
		}
	}
	public static class Type extends Model{
		private int _id;
		private String name;
		
		public void setId(int id){
			_id = id;
		}
		public int getId(){
			return _id;
		}
		public void setName(String name){
			this.name = name;
		}
		public String getName(){
			return name;
		}
	}
	public static class Terminal extends Model{
		private int _id;
		private String name;
		private int city_id;
		private String city_name;
		private int company_id;
		private String company_name;
		private String phone;
		private boolean purchase;
		private boolean in;
		private boolean off;
		//private String link;
		private String address;
		//private String misc_en;
		//private String misc_ko;
		private HashMap<String,String> misc;
		private double latitude;
		private double longitude;
		private String latlng;
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
		
		public void setPhone(String no){
			phone = no;
		}
		public String getPhone(){
			return phone;
		}
		public void setPurchase(boolean purchase){
			this.purchase = purchase;
		}
		public boolean isPurchase(){
			return purchase;
		}
		public void setGetIn(boolean in){
			this.in = in;
		}
		public boolean isGetIn(){
			return in;
		}
		public void setGetOff(boolean off){
			this.off = off;
		}
		public boolean isGetOff(){
			return off;
		}
		/*/
		public void setLink(String link){
			this.link = link;
		}
		public String getLink(){
			return link;
		}
		/**/
		public void setAddress(String address){
			this.address = address;
		}
		public String getAddress(){
			return address;
		}
		/*/
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
		/**/
		public void setMisc(String language, String misc){
			if(this.misc==null)
				this.misc = new HashMap<String, String>();
			this.misc.put(language,misc);
		}
		public void setMisc(String json){
			if(this.misc==null)
				this.misc = new HashMap<String, String>();
			try {
				JSONArray jsonArray = new JSONArray(json);
				for(int i=0; i<jsonArray.length(); i++){
					JSONObject obj = jsonArray.getJSONObject(i);
					misc.put(obj.getString("lang"),obj.getString("misc"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		/*/
		public HashMap<String,String> getMisc(){
			return misc;
		}
		/**/
		public String getMisc(){
			String val = "";
			if(misc!=null){
				for(Entry<String, String> entry: misc.entrySet()){
					val += "{\"lang\":\""+entry.getKey()+"\",\"misc\":\""+entry.getValue()+"\"},";
				}
				if(val.length()>0){
					val = val.substring(0, val.length()-1);
				}
			}
			return "["+val+"]";
		}
		public String getMisc(String language){
			if(misc!=null){
				return misc.get(language);
			}else{
				return null;
			}
		}
		/**/
		public void setId(int id){
			_id = id;
		}
		public int getId(){
			return _id;
		}
		public void setName(String name){
			this.name = name;
		}
		public String getName(){
			return name;
		}
		public void setCityId(int id){
			city_id = id;
		}
		public int getCityId(){
			return city_id;
		}
		public void setCityName(String name){
			city_name = name;
		}
		public String getCityName(){
			return city_name;
		}
		public void setCompanyId(int id){
			company_id = id;
		}
		public int getCompanyId(){
			return company_id;
		}
		public void setCompanyName(String name){
			company_name = name;
		}
		public String getCompanyName(){
			return company_name;
		}
		public String[] getPhoneList(){
			String[] list = phone.split("/");
			for(int i=0; i<list.length; i++){
				list[i] = list[i].trim().replace('.','-');
			}
			return list;
		}
		
	}
	public static class Line extends Model{
		private int line_id;
		private int dept_id;
		private String dept_name;
		private boolean deptPref;
		private int deptIndex;
		private int dest_id;
		private String dest_name;
		private boolean destPref;
		private int destIndex;
		private int distance;
		
		public void setLineId(int id){
			line_id = id;
		}
		public int getLineId(){
			return line_id;
		}
		public void setDeptId(int id){
			dept_id = id;
		}
		public int getDeptId(){
			return dept_id;
		}
		public void setDestId(int id){
			dest_id = id;
		}
		public int getDestId(){
			return dest_id;
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
		
		public void setDeptPref(boolean pref){
			deptPref = pref;
		}
		public void setDeptIndex(int index){
			deptIndex = index;
		}
		public boolean getDeptPref(){
			return deptPref;
		}
		public int getDeptIndex(){
			return deptIndex;
		}
		public void setDestPref(boolean pref){
			destPref = pref;
		}
		public void setDestIndex(int index){
			destIndex = index;
		}
		public boolean getDestPref(){
			return destPref;
		}
		public int getDestIndex(){
			return destIndex;
		}
		
		public void setDistance(int distance){
			this.distance = distance;
		}
		public int getDistance(){
			return distance;
		}
	}
	
	public static class Bus extends Line{
		private int bus_id;
		private int company_id;
		private String company_name;
		private int type_id;
		private String type_name;
		
		private int mid_id;
		private String mid_name;
		
		private double duration = 0;
		private double _native = 0;
		private double foreign = 0;
		private double visa = 0;
		private boolean abroad;
		
		private int seat = 0;
		
		public void setSeat(int seat){
			this.seat = seat;
		}
		public int getSeat(){
			return seat;
		}
		public void setMidId(int id){
			mid_id = id;
		}
		public int getMidId(){
			return mid_id;
		}
		public void setMidName(String name){
			mid_name = name;
		}
		public String getMidName(){
			return mid_name;
		}
		
		public void setBusId(int id){
			bus_id = id;
		}
		public int getBusId(){
			return bus_id;
		}
		public void setCompanyId(int id){
			company_id = id;
		}
		public int getCompanyId(){
			return company_id;
		}
		public void setCompanyName(String name){
			company_name = name;
		}
		public String getCompanyName(){
			return company_name;
		}
		public void setTypeId(int id){
			type_id = id;
		}
		public int getTypeId(){
			return type_id;
		}
		public void setTypeName(String name){
			type_name = name;
		}
		public String getTypeName(){
			return type_name;
		}
		public void setDuration(double time){
			duration = time;
		}
		public double getDuration(){
			return duration;
		}
		
		public void setNative(double price){
			_native = price;
		}
		public double getNative(){
			return _native;
		}
		public void setForeign(double price){
			foreign = price;
		}
		public double getForeign(){
			return foreign;
		}
		public void setVisa(double visa){
			this.visa = visa;
		}
		public double getVisa(){
			return visa;
		}
		public void setAbroad(boolean abroad){
			this.abroad = abroad;
		}
		public boolean getAbroad(){
			return abroad;
		}
	}
	
	public static class Time extends Bus{
		private int time_id;
		
		private int dept_hour;
		private int dept_min;
		
		private int arrival_hour;
		private int arrival_min;
		
		/**/
		private ArrayList<Terminal> terminal_list = new ArrayList<Terminal>();

		public void addTerminal(Terminal terminal){
			if(!terminal_list.contains(terminal))
				terminal_list.add(terminal);
		}
		
		public ArrayList<Terminal> getTerminalList(){
			return terminal_list;
		}
		/**/
		
		public int getDeptHour(){
			return dept_hour;
		}
		public void setTimeId(int id){
			time_id = id;
		}
		public int getTimeId(){
			return time_id;
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
			if(FormatUtil.isNumeric(time[0]) && FormatUtil.isNumeric(time[1])){
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
		public static class DepartureTimeAscCompare implements Comparator<Time>{
			@Override
			public int compare(Time lhs, Time rhs) {
				return lhs.getDeptTime().compareTo(rhs.getDeptTime());
			}
		}
		// order by departure time desc
		public static class DepartureTimeDescCompare implements Comparator<Time>{
			@Override
			public int compare(Time lhs, Time rhs) {
				return rhs.getDeptTime().compareTo(lhs.getDeptTime());
			}
		}
		
		// order by foreigner price asc
		public static class ForeignerPriceAscCompare implements Comparator<Time>{
			@Override
			public int compare(Time lhs, Time rhs) {
				return
						lhs.getForeign() < rhs.getForeign() ? -1 :
							lhs.getForeign() > rhs.getForeign() ? 1 :
								0;
			}
		}
		// order by foreigner price desc
		public static class ForeignerPriceDescCompare implements Comparator<Time>{
			@Override
			public int compare(Time lhs, Time rhs) {
				return
						rhs.getForeign() < lhs.getForeign() ? -1 :
							rhs.getForeign() > lhs.getForeign() ? 1 :
								0;
			}
		}
		public static class CompanyNameAscCompare implements Comparator<Time>{
			@Override
			public int compare(Time lhs, Time rhs){
				int n = lhs.getCompanyName().toLowerCase().compareTo(rhs.getCompanyName().toLowerCase());
				return n>0 ? 1 :
					n<0 ? -1 : 0;
			}
		}
		public static class CompanyNameDescCompare implements Comparator<Time>{
			@Override
			public int compare(Time lhs, Time rhs){
				int n = rhs.getCompanyName().toLowerCase().compareTo(lhs.getCompanyName().toLowerCase());
				return n>0 ? 1 :
					n<0 ? -1 : 0;
			}
		}
		/*/
		// order by distance asc
		public static class DistanceAscCompare implements Comparator<Time>{
			@Override
			public int compare(Time lhs, Time rhs) {
				return
						lhs.getDistance() < rhs.getDistance() ? -1 :
							lhs.getDistance() > rhs.getDistance() ? 1 :
								0;
			}
		}
		// order by distance desc
		public static class DistanceDescCompare implements Comparator<Time>{
			@Override
			public int compare(Time lhs, Time rhs) {
				return
						rhs.getDistance() < lhs.getDistance() ? -1 :
							rhs.getDistance() > lhs.getDistance() ? 1 :
								0;
			}
		}
		/**/
	}
}
