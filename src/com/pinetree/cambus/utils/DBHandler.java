package com.pinetree.cambus.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.pinetree.cambus.R;
import com.pinetree.cambus.models.BusInfoModel;
import com.pinetree.cambus.models.CityModel;
import com.pinetree.cambus.models.DepartureModel;
import com.pinetree.cambus.models.DestinationModel;
import com.pinetree.cambus.models.TypeModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHandler {
	protected DBHelper dbHelper;
	protected SQLiteDatabase db;
	
	protected DBHandler(Context context){
		this.dbHelper = new DBHelper(context);
		this.db = dbHelper.getWritableDatabase();
	}
	
	public static DBHandler open(Context context) throws SQLException{
		DBHandler handler = new DBHandler(context);
		
		return handler;
	}
	
	public void close(){
		dbHelper.close();
	}
	
	public long insertCity(String cityName){
		ContentValues values = new ContentValues();
		values.put("city_name", cityName);
		return db.insert("CamBUS_CityTable", null, values);
	}
	
	public long insertCompany(String companyName){
		ContentValues values = new ContentValues();
		values.put("company_name", companyName);
		return db.insert("CamBUS_CompanyTable", null, values);
	}
	public long insertType(String typeName){
		ContentValues values = new ContentValues();
		values.put("type_name", typeName);
		return db.insert("CamBUS_TypeTable", null, values);
	}
	
	public long insertBusTime(BusInfoModel bus){
		Cursor cursor;
		
		ContentValues values = new ContentValues();
		
		// departure city
		cursor = this.getCityNumberCursor(bus.getDeparture());
		try{
			if(cursor.getCount()>0){
				values.put("departure_no", cursor.getInt(cursor.getColumnIndex("city_no")));
			}else{
				values.put("departure_no", (int)this.insertCity(bus.getDeparture()));
			}
		}catch(NullPointerException e){
			Log.i("DebugPrint",e.getMessage());
		}
		cursor.close();
		
		// destination city
		cursor = this.getCityNumberCursor(bus.getDestination());
		try{
			if(cursor.getCount()>0){
				values.put("destination_no", cursor.getInt(cursor.getColumnIndex("city_no")));
			}else{
				values.put("destination_no", (int)this.insertCity(bus.getDestination()));
			}
		}catch(NullPointerException e){
			Log.i("DebugPrint",e.getMessage());
		}
		cursor.close();
		
		// middle city
		try{
			cursor = this.getCityNumberCursor(bus.getMiddleCity());
			if(cursor.getCount()>0){
				values.put("middlecity_no", cursor.getInt(cursor.getColumnIndex("city_no")));
			}else{
				values.put("middlecity_no", (int)this.insertCity(bus.getMiddleCity()));
			}
		}catch(NullPointerException e){
			//Log.i("DebugPrint","null middle city");
		}
		cursor.close();
		
		// company
		try{
			cursor = this.getCompanyNumberCursor(bus.getBusCompany());
			if(cursor.getCount()>0){
				values.put("company_no", cursor.getInt(cursor.getColumnIndex("company_no")));
			}else{
				values.put("company_no", (int)this.insertCompany(bus.getBusCompany()));
			}
		}catch(NullPointerException e){
			//Log.i("DebugPrint","null company");
		}
		cursor.close();
		
		// Bus Type
		cursor = this.getTypeNumberCursor(bus.getType());
		try{
			if(cursor.getCount()>0){
				values.put("type_no", cursor.getInt(cursor.getColumnIndex("type_no")));
			}else{
				values.put("type_no", (int)this.insertType(bus.getType()));
			}
		}catch(NullPointerException e){
			Log.i("DebugPrint",e.getMessage());
		}
		cursor.close();
		
		values.put("preference", bus.getPreference());
		values.put("departure_time", bus.getDepartureTime());
		values.put("arrival_time", bus.getArrivalTime());
		values.put("duration_time", bus.getDurationTime());
		values.put("remarks", bus.getRemarks());
		values.put("quality", bus.getQuality());
		values.put("operation", bus.getOperation());
		values.put("native_price", bus.getNativePrice());
		values.put("foreigner_price", bus.getForeignerPrice());
		values.put("visa", bus.getVisa());
		values.put("dn", bus.getDN());
		values.put("last_updated", bus.getLastUpdated());
		
		return db.insert("CamBUS_TimeTable", null, values);
	}
	
	public void executeQuery(String sql){
		db.execSQL(sql);
	}
	
	public Cursor selectQuery(String sql){
		Cursor cursor = db.rawQuery(sql, null);
		
		if(cursor != null)
			cursor.moveToFirst();
		return cursor;
	}
	
	//  Departure List
	
	public ArrayList<DepartureModel> getBusDepartureList(){
		Cursor cursor = getBusDepartureListCursor();
		ArrayList<DepartureModel> departure_list = new ArrayList<DepartureModel>();
		
		departure_list.add(new DepartureModel());
		
		HashMap<String, String> hash = new HashMap<String,String>();
		
		if(cursor != null && cursor.moveToFirst()){
			int city_no;
			String city_name;
			boolean isHigh;
			DepartureModel object;
			do{
				city_no = cursor.getInt(cursor.getColumnIndex("city_no"));
				city_name = cursor.getString(cursor.getColumnIndex("city_name"));
				isHigh = cursor.getString(cursor.getColumnIndex("preference")).toUpperCase().equals("HIGH")?true:false;
				if(!hash.containsKey(String.valueOf(city_no))){
					hash.put(String.valueOf(city_no), city_name);
					object = new DepartureModel();
					object.setCityNo(city_no);
					object.setCityName(city_name);
					object.setHigh(isHigh);
					departure_list.add(object);
				}
			}while(cursor.moveToNext());
		}

		if(cursor!=null)
			cursor.close();
		return departure_list;
	}
	
	public Cursor getBusDepartureListCursor(){
		String sql = 
				"SELECT DISTINCT preference, city_no, city_name " +
				"FROM CamBUS_CityTable a " +
				"INNER JOIN CamBUS_TimeTable b " +
				"ON a.city_no = b.departure_no " +
				"ORDER BY preference ASC, city_name ASC;";
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
	
	public ArrayList<TypeModel> getBusTypeList(){
		Cursor cursor = getBusTypeListCursor();
		ArrayList<TypeModel> type_list = new ArrayList<TypeModel>();
		
		if(cursor != null && cursor.moveToFirst()){
			TypeModel object = new TypeModel();
			object.setTypeNo(-1);
			type_list.add(object);
			do{
				object = new TypeModel();
				object.setTypeNo(cursor.getInt(cursor.getColumnIndex("type_no")));
				object.setTypeName(cursor.getString(cursor.getColumnIndex("type_name")));
				type_list.add(object);
			}while(cursor.moveToNext());
		}

		if(cursor!=null)
			cursor.close();
		return type_list;
	}

	public Cursor getBusTypeListCursor(){
		String sql = 
				"SELECT type_no, type_name " +
				"FROM CamBUS_TypeTable " +
				"ORDER BY type_name ASC;";
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
	public ArrayList<DestinationModel> getBusDestinationList(int departure_no){
		Cursor cursor = getBusDestinationListCursor(departure_no);
		ArrayList<DestinationModel> destination_list = new ArrayList<DestinationModel>();
		
		HashMap<String, String> hash = new HashMap<String,String>();
		
		destination_list.add(new DestinationModel());
		
		if(cursor != null && cursor.moveToFirst()){
			int city_no;
			String city_name;
			boolean isHigh;
			DestinationModel object;
			do{
				city_no = cursor.getInt(cursor.getColumnIndex("city_no"));
				city_name = cursor.getString(cursor.getColumnIndex("city_name"));
				isHigh = cursor.getString(cursor.getColumnIndex("preference")).toUpperCase().equals("HIGH")?true:false;
				
				if(!hash.containsKey(String.valueOf(city_no))){
					hash.put(String.valueOf(city_no), city_name);
					object = new DestinationModel();
					object.setCityNo(city_no);
					object.setCityName(city_name);
					object.setHigh(isHigh);
					destination_list.add(object);
				}
			}while(cursor.moveToNext());
		}

		if(cursor!=null)
			cursor.close();
		return destination_list;
	}
	
	public Cursor getBusDestinationListCursor(int departure_no){
		String sql = 
				"SELECT DISTINCT preference, city_no, city_name " +
				"FROM CamBUS_CityTable a " +
				"INNER JOIN CamBUS_TimeTable b " +
				"ON a.city_no = b.destination_no " +
				"WHERE b.departure_no = '" + departure_no + "' " +
				"ORDER BY preference ASC, city_name ASC;";
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		
		return cursor;
	}
	
	public ArrayList<BusInfoModel> getBusInfoList(int departure_no, int destination_no, int time, int bustype_no){
		Cursor cursor = getBusInfoListCursor(departure_no, destination_no, time, bustype_no);
		ArrayList<BusInfoModel> objects = new ArrayList<BusInfoModel>();
		
		if(cursor != null && cursor.moveToFirst()){
			String departure = getCityName(departure_no);
			String destination = getCityName(departure_no);
			BusInfoModel object;
			String company, middlecity, type;
			int company_no, middlecity_no, type_no;
			do{
				object = new BusInfoModel();
				object.setDeparture(departure);
				object.setDepartureNo(departure_no);
				object.setDestination(destination);
				object.setDestinationNo(destination_no);
				
				middlecity_no = cursor.getInt(cursor.getColumnIndex("middlecity_no"));
				middlecity = getCityName(middlecity_no);
				object.setMiddleCity(middlecity);
				object.setMiddleCityNo(middlecity_no);
				
				company_no = cursor.getInt(cursor.getColumnIndex("company_no"));
				company = getCompanyName(company_no);
				object.setCompany(company);
				object.setCompanyNo(company_no);
				
				object.setBusNo(cursor.getInt(cursor.getColumnIndex("bus_no")));
				object.setPreference(cursor.getString(cursor.getColumnIndex("preference")));
				
				object.setDepartureTime(cursor.getString(cursor.getColumnIndex("departure_time")));
				object.setArrivalTime(cursor.getString(cursor.getColumnIndex("arrival_time")));
				object.setDurationTime(cursor.getDouble(cursor.getColumnIndex("duration_time")));
				object.setRemarks(cursor.getString(cursor.getColumnIndex("remarks")));
				object.setQuality(cursor.getString(cursor.getColumnIndex("quality")));
				object.setOperation(cursor.getString(cursor.getColumnIndex("operation")));
				
				type_no = cursor.getInt(cursor.getColumnIndex("type_no"));
				type = getTypeName(type_no);
				object.setTypeNo(type_no);
				object.setType(type);
				
				object.setNativePrice(cursor.getDouble(cursor.getColumnIndex("native_price")));
				object.setForeignerPrice(cursor.getDouble(cursor.getColumnIndex("foreigner_price")));
				object.setVisa(cursor.getDouble(cursor.getColumnIndex("visa")));
				object.setDN(cursor.getString(cursor.getColumnIndex("dn")));
				object.setLastUpdate(cursor.getString(cursor.getColumnIndex("last_updated")));
				
				objects.add(object);
			}while(cursor.moveToNext());
		}
		
		if(cursor!=null)
			cursor.close();
		return objects;
	}
	
	public Cursor getBusInfoListCursor(int departure_no, int destination_no, int time, int type_no){
		String sql =
				"SELECT * " +
				"FROM CamBUS_TimeTable a " +
				"WHERE departure_no='" + departure_no + "' " +
				"AND destination_no='" + destination_no +"' " +
				"AND departure_time>='" + String.format("%02d:00",time) + "' ";
		
		if(type_no>=0){
			sql +=
				"AND type_no='" + type_no +"' ";
		}
		/*
		String orderBy = "ORDER BY ";
		if(order.equals("time")){
			orderBy += "departure_time ASC;";
		}else if(order.equals("price")){
			orderBy += "foreigner_price ASC;";
		}else if(order.equals("nearby")){
			orderBy += "duration_time ASC;";
		}
		Cursor cursor = db.rawQuery(sql + orderBy, null);
		 */
		Cursor cursor = db.rawQuery(sql+";", null);
		
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
	
	public Cursor getBusInfoByBusNo(int bus_no){
		Cursor cursor = db.query(
				true,
				"CamBUS_TimeTable",
				null,
				"bus_no='"+bus_no+"'",
				null, null, null, null, null, null);
		
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
	
	public Cursor getCityNumberCursor(String cityName){
		if(cityName.equals("")){
			throw new NullPointerException("NullCity:"+cityName);
		}
		Cursor cursor = db.query(
				true,
				"CamBUS_CityTable",
				null,
				"city_name='"+cityName+"'",
				null, null, null, null, null, null);
		
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
	public String getCityName(int cityNo){
		Cursor cursor = getCityNameCursor(cityNo);
		if(cursor != null && cursor.moveToFirst()){
			String name =  cursor.getString(cursor.getColumnIndex("city_name"));
			cursor.close();
			return name;
		}
		return null;
	}
	public Cursor getCityNameCursor(int cityNo){
		if(cityNo<0){
			throw new NullPointerException("NullCity:"+cityNo);
		}
		Cursor cursor = db.query(
				true,
				"CamBUS_CityTable",
				null,
				"city_no='" + cityNo + "'",
				null, null, null, null, null, null);
		
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}

	public String getTypeName(int typeNo){
		Cursor cursor = getTypeNameCursor(typeNo);
		if(cursor != null && cursor.moveToFirst()){
			String name = cursor.getString(cursor.getColumnIndex("type_name"));
			cursor.close();
			return name;
		}
		if(cursor != null)
			cursor.close();
		return null;
	}
	public Cursor getTypeNameCursor(int typeNo){
		if(typeNo<0){
			throw new NullPointerException("NullType:"+typeNo);
		}
		Cursor cursor = db.query(
				true,
				"CamBUS_TypeTable",
				null,
				"type_no='" + typeNo + "'",
				null, null, null, null, null, null);
		
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
	public Cursor getTypeNumberCursor(String typeName){
		if(typeName.equals("")){
			throw new NullPointerException("NullType:"+typeName);
		}
		Cursor cursor = db.query(
				true,
				"CamBUS_TypeTable",
				null,
				"type_name='"+typeName+"'",
				null, null, null, null, null, null);
		
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
	public String getCompanyName(int company_no){
		Cursor cursor = getCompanyNameCursor(company_no);
		if(cursor != null && cursor.moveToFirst()){
			String name = cursor.getString(cursor.getColumnIndex("company_name"));
			cursor.close();
			return name;
		}
		
		if(cursor != null)
			cursor.close();
		return null;
	}
	public Cursor getCompanyNameCursor(int company_no){
		if(company_no<0){
			throw new NullPointerException("NullCompany:"+company_no);
		}
		Cursor cursor = db.query(
				true,
				"CamBUS_CompanyTable",
				null,
				"company_no='" + company_no + "'",
				null, null, null, null, null, null);
		
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
	public Cursor getCompanyNumberCursor(String companyName){
		if(companyName.equals("")){
			throw new NullPointerException("NullCompany:"+companyName);
		}
		Cursor cursor = db.query(
				true,
				"CamBUS_CompanyTable",
				null,
				"company_name='"+companyName+"'",
				null, null, null, null, null, null);
		
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
	
}
