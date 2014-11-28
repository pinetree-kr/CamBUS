package com.pinetree.cambus.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import android.util.Log;

import com.pinetree.cambus.interfaces.FragmentCallbackInterface;
import com.pinetree.cambus.interfaces.ModelAsyncTaskInterface;
import com.pinetree.cambus.interfaces.ModelCallbackInterface;
import com.pinetree.cambus.utils.DBHandler;
import com.pinetree.cambus.models.DBModel.*;

public class BusFilterModel extends Model implements ModelCallbackInterface, ModelAsyncTaskInterface, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 20140825L;
	
	protected ArrayList<Line> line_list;
	
	protected ArrayList<BusType> type_list;
	protected ArrayList<Integer> time_list;
	
	public BusFilterModel(){
		line_list = new ArrayList<Line>();
		
		type_list = new ArrayList<BusType>();
		time_list = new ArrayList<Integer>();
		
		initTimeList();
	}
	public void initTimeList(){
		time_list.clear();
		time_list.add(-1);
		for(int i = 4; i<27; i++){
			time_list.add(i);
		}
	}
	
	public ArrayList<Line> getLineList(){
		return line_list;
	}
	public ArrayList<Integer> getTimeList(){
		return time_list;
	}
	public void setTypeList(ArrayList<BusType> list){
		type_list = list;
	}
	public ArrayList<BusType> getTypeList(){
		return type_list;
	}
	
	public ArrayList<DepartureCity> getDepartureList(){
		ArrayList<DepartureCity> objects = new ArrayList<DepartureCity>();
		HashMap<String, String> hash = new HashMap<String,String>();
		DepartureCity city;
		// dummy : select ...
		objects.add(new DepartureCity());
		
		for(Line object : line_list){
			
			city = new DepartureCity();
			city.setCityNo(object.getDeptNo());
			city.setCityName(object.getDeptName());
			city.setHigh(object.getDeptHigh());
			city.setOrder(object.getDeptOrder());
			
			if(!hash.containsKey(String.valueOf(city.getCityNo()))){
				hash.put(String.valueOf(city.getCityNo()), city.getCityName());
				objects.add(city);
			}
		}
		hash.clear();
		// 추천수가 높은 순으로 정렬
		//Collections.sort(objects, new City.PreferenceDescCompare());
		Collections.sort(objects, new City.PrefOrderDescCompare());
		return objects;
	}
	public ArrayList<DestinationCity> getDestinationList(int dept_no){
		ArrayList<DestinationCity> objects = new ArrayList<DestinationCity>();
		HashMap<String, String> hash = new HashMap<String,String>();
		DestinationCity city;
		// dummy : select ...
		objects.add(new DestinationCity());
		
		for(Line object : line_list){
			// dept_no 가 같은 라인만
			if(object.getDeptNo() == dept_no){
				city = new DestinationCity();
				city.setCityNo(object.getDestNo());
				city.setCityName(object.getDestName());
				city.setHigh(object.getDestHigh());
				city.setOrder(object.getDestOrder());
				if(!hash.containsKey(String.valueOf(city.getCityNo()))){
					hash.put(String.valueOf(city.getCityNo()), city.getCityName());
					objects.add(city);
				}
			}
		}
		hash.clear();
		// 추천수가 높은 순으로 정렬
		//Collections.sort(objects, new City.PreferenceDescCompare());
		Collections.sort(objects, new City.PrefOrderDescCompare());
		return objects;
	}
	public Line getLineInfo(int dept_no, int dest_no){
		for(Line object : line_list){
			if(object.getDeptNo() == dept_no && object.getDestNo() == dest_no){
				return object;
			}
		}
		return null;
	}
	public void updateLineList(DBHandler handler){
		line_list = handler.getLineList();
	}
	public void updateBusTypeList(DBHandler handler){
		type_list = handler.getBusTypeList();
	}
	@Override
	public void onAsyncTask(Model object) {
		fcInterface.onAsyncTask(object);
	}
	public void setCallback(FragmentCallbackInterface fcInterface){
		this.fcInterface = fcInterface;
	}
	public void doCallback(){
		fcInterface.onPostAsyncTask();
		fcInterface.onCallback(this);
	}
	
	
}
