package com.pinetree.cambus.utils;

import java.util.ArrayList;
import java.util.HashMap;
/*
import com.pinetree.cambus.models.CityModel;
import com.pinetree.cambus.models.CompanyModel;
import com.pinetree.cambus.models.DepartureModel;
import com.pinetree.cambus.models.DestinationModel;
import com.pinetree.cambus.models.OfficeInfoModel;
import com.pinetree.cambus.models.TypeModel;
*/
import com.pinetree.cambus.models.DBModel.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class DBHandler {
	protected DBHelper dbHelper;
	protected SQLiteDatabase db;
	
	protected DBHandler(Context context){
		this.dbHelper = new DBHelper(context, ExcelFileInfo.ExcelFileVersion);
		this.db = dbHelper.getWritableDatabase();
	}
	
	public static DBHandler open(Context context) throws SQLException{
		DBHandler handler = new DBHandler(context);
		
		return handler;
	}
	
	public void close(){
		dbHelper.close();
	}
	
	
	public void beginTransaction(){
		db.beginTransaction();
	}
	public void endTransaction(){
		db.endTransaction();
	}
	public void setTransactionSuccessful(){
		db.setTransactionSuccessful();
	}
	
	public long insertCity(City city) throws SQLiteException{
		ContentValues values = new ContentValues();
		long rv = -1;
		values.put("city_name", city.getCityName());
		values.put("preference",city.getHigh()?1:0);
		rv = db.insert("Cambus_CityTable", null, values);
		if(rv<0){
			throw new SQLException("City Insert Error:"+values);
		}
		return rv;
	}
	
	public long insertCompany(Company company) throws SQLiteException{
		ContentValues values = new ContentValues();
		long rv = -1;
		values.put("company_name", company.getCompanyName());
		rv = db.insert("Cambus_CompanyTable", null, values);
		if(rv<0){
			throw new SQLException("Company Insert Error:"+values);
		}
		return rv;
	}
	
	public long insertBusType(BusType type) throws SQLiteException{
		ContentValues values = new ContentValues();
		long rv = -1;
		values.put("type_name", type.getTypeName());
		rv = db.insert("Cambus_TypeTable", null, values);
		if(rv<0){
			throw new SQLException("Type Insert Error:"+values);
		}
		return rv;
	}	
	
	public long insertTerminal(Terminal terminal) throws SQLiteException{
		ContentValues values = new ContentValues();
		long rv = -1;
		values.put("terminal_name", terminal.getTerminalName());
		values.put("city_no", terminal.getCityNo());
		values.put("company_no", terminal.getCompanyNo());
		values.put("phone_no", terminal.getPhoneNo());
		values.put("purchase", terminal.isPurchase()?1:0);
		values.put("get_in", terminal.isGetIn()?1:0);
		values.put("get_off", terminal.isGetOff()?1:0);
		values.put("link", terminal.getLink());
		values.put("address", terminal.getAddress());
		values.put("misc_en", terminal.getMiscEn());
		values.put("misc_ko", terminal.getMiscKo());
		rv = db.insert("Cambus_TerminalTable", null, values);
		if(rv<0){
			throw new SQLException("Terminal Insert Error:"+values);
		}
		return rv;
	}
	
	public long insertLine(Line line) throws SQLiteException{
		ContentValues values = new ContentValues();
		long rv = -1;
		values.put("dept_no", line.getDeptNo());
		values.put("dest_no", line.getDestNo());
		values.put("distance", line.getDistance());
		rv = db.insert("Cambus_LineTable", null, values);
		if(rv<0){
			throw new SQLException("Line Insert Error:"+values);
		}
		return rv;
	}
	
	public long insertLineBus(LineBus linebus) throws SQLiteException{
		ContentValues values = new ContentValues();
		long rv = -1;
		values.put("line_no", linebus.getLineNo());
		values.put("company_no", linebus.getCompanyNo());
		values.put("type_no", linebus.getTypeNo());
		values.put("duration_time", linebus.getDurationTime());
		values.put("native_price", linebus.getNativePrice());
		values.put("foreigner_price", linebus.getForeignerPrice());
		values.put("visa", linebus.getVisa());
		values.put("dn", linebus.getDN());
		rv = db.insert("Cambus_LineBusTable", null, values);
		if(rv<0){
			throw new SQLException("LineBus Insert Error:"+values);
		}
		return rv;
	}
	
	public long insertLineBusTime(LineBusTime linebustime) throws SQLiteException{
		ContentValues values = new ContentValues();
		long rv = -1;
		values.put("linebus_no", linebustime.getLineBusNo());
		values.put("mid_no", linebustime.getMidNo());
		values.put("departure_time", linebustime.getDeptTime());
		values.put("arrival_time", linebustime.getDeptTime());
		rv = db.insert("Cambus_LineBusTimeTable", null, values);
		if(rv<0){
			throw new SQLException("LineBusTime Insert Error:"+values);
		}
		return rv;
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
	public ArrayList<DepartureCity> getDepartureList(){
		Cursor cursor = getDepartureListCursor();
		ArrayList<DepartureCity> departure_list = new ArrayList<DepartureCity>();
		
		departure_list.add(new DepartureCity());
		
		HashMap<String, String> hash = new HashMap<String,String>();
		
		if(cursor != null && cursor.moveToFirst()){
			int city_no;
			String city_name;
			boolean isHigh;
			DepartureCity object;
			do{
				city_no = cursor.getInt(cursor.getColumnIndex("city_no"));
				city_name = cursor.getString(cursor.getColumnIndex("city_name"));
				isHigh = cursor.getInt(cursor.getColumnIndex("preference"))>0?true:false;
				
				if(!hash.containsKey(String.valueOf(city_no))){
					hash.put(String.valueOf(city_no), city_name);
					object = new DepartureCity();
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
	public Cursor getDepartureListCursor(){
		String sql = 
				"SELECT distinct b.* " +
				"FROM Cambus_LineView a " +
				"INNER JOIN Cambus_CityTable b " +
				"ON a.dept_no = b.city_no " +
				"ORDER BY b.preference desc, b.city_name asc" +
				";";
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
	
	//  Type List
	public ArrayList<BusType> getBusTypeList(){
		Cursor cursor = getBusTypeListCursor();
		ArrayList<BusType> type_list = new ArrayList<BusType>();
		
		if(cursor != null && cursor.moveToFirst()){
			BusType object = new BusType();
			type_list.add(object);
			do{
				object = new BusType();
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
				"FROM Cambus_TypeTable " +
				"ORDER BY type_name ASC;";
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
	
	//  Destination List
	public ArrayList<DestinationCity> getDestinationList(int dept_no){
		Cursor cursor = getDestinationListCursor(dept_no);
		ArrayList<DestinationCity> destination_list = new ArrayList<DestinationCity>();
		
		HashMap<String, String> hash = new HashMap<String,String>();
		
		destination_list.add(new DestinationCity());
		
		if(cursor != null && cursor.moveToFirst()){
			int city_no;
			String city_name;
			boolean isHigh;
			DestinationCity object;
			do{
				city_no = cursor.getInt(cursor.getColumnIndex("city_no"));
				city_name = cursor.getString(cursor.getColumnIndex("city_name"));
				isHigh = cursor.getInt(cursor.getColumnIndex("preference"))>0?true:false;
				
				if(!hash.containsKey(String.valueOf(city_no))){
					hash.put(String.valueOf(city_no), city_name);
					object = new DestinationCity();
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
	public Cursor getDestinationListCursor(int dept_no){
		String sql =
				"SELECT b.* " +
				"FROM Cambus_LineView a " +
				"INNER JOIN Cambus_CityTable b " +
				"ON a.dest_no = b.city_no " +
				"WHERE a.dept_no = '" + dept_no + "' " +
				"ORDER BY b.preference desc, b.city_name asc " +
				";";
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		
		return cursor;
	}
	
	public ArrayList<LineBusTime> getLineBusTimeList(int dept_no, int dest_no, int time, int type_no){
		Cursor cursor = getLineBusTimeListCursor(dept_no, dest_no, time, type_no);
		ArrayList<LineBusTime> objects = new ArrayList<LineBusTime>();
		
		if(cursor != null && cursor.moveToFirst()){
			// 관련된 회사 목록들
			ArrayList<Terminal> terminals = getTerminalListInCity(dept_no);
			LineBusTime object;
			do{
				object = new LineBusTime();
				object.setLineBusTimeNo(cursor.getInt(cursor.getColumnIndex("linebustime_no")));
				object.setDeptNo(cursor.getInt(cursor.getColumnIndex("dept_no")));
				object.setDeptName(cursor.getString(cursor.getColumnIndex("dept_name")));
				object.setDestNo(cursor.getInt(cursor.getColumnIndex("dest_no")));
				object.setDestName(cursor.getString(cursor.getColumnIndex("dest_name")));
				object.setCompanyNo(cursor.getInt(cursor.getColumnIndex("company_no")));
				object.setCompanyName(cursor.getString(cursor.getColumnIndex("company_name")));
				object.setTypeNo(cursor.getInt(cursor.getColumnIndex("type_no")));
				object.setTypeName(cursor.getString(cursor.getColumnIndex("type_name")));
				object.setMidNo(cursor.getInt(cursor.getColumnIndex("mid_no")));
				object.setMiddleCity(cursor.getString(cursor.getColumnIndex("middle_city")));
				object.setDeptTime(cursor.getString(cursor.getColumnIndex("departure_time")));
				object.setArrivalTime(cursor.getString(cursor.getColumnIndex("arrival_time")));
				object.setDistance(cursor.getInt(cursor.getColumnIndex("distance")));
				object.setDurationTime(cursor.getDouble(cursor.getColumnIndex("duration_time")));
				object.setNativePrice(cursor.getDouble(cursor.getColumnIndex("native_price")));
				object.setForeignerPrice(cursor.getDouble(cursor.getColumnIndex("foreigner_price")));
				object.setVisa(cursor.getDouble(cursor.getColumnIndex("visa")));
				
				for(Terminal terminal : terminals){
					if(terminal.getCityNo() == object.getDeptNo()){
						object.setTerminal(terminal);
						break;
					}
				}
				
				objects.add(object);
			}while(cursor.moveToNext());
		}
		return objects;
	}
	
	public Cursor getLineBusTimeListCursor(int dept_no, int dest_no, int time, int type_no){
		String sql =
				"SELECT * " +
				"FROM Cambus_LineBusTimeView " +
				"WHERE dept_no='" + dept_no + "' " +
				"AND dest_no='" + dest_no +"' " +
				"AND departure_time>='" + String.format("%02d:00",time) + "' ";
		
		// 0 is All type
		if(type_no>0){
			sql += "AND type_no='" + type_no +"' ";
		}
		sql += ";";
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}

	public ArrayList<Line> getLineList(){
		ArrayList<Line> objects = new ArrayList<Line>();
		Cursor cursor = getLineListCursor();
		
		if(cursor!=null&& cursor.moveToFirst()){
			Line object;
			do{
				object = new Line();
				object.setLineNo(cursor.getInt(cursor.getColumnIndex("line_no")));
				object.setDeptNo(cursor.getInt(cursor.getColumnIndex("dept_no")));
				object.setDestNo(cursor.getInt(cursor.getColumnIndex("dest_no")));
				object.setDeptName(cursor.getString(cursor.getColumnIndex("dept_name")));
				object.setDestName(cursor.getString(cursor.getColumnIndex("dest_name")));
				object.setDeptHigh(cursor.getInt(cursor.getColumnIndex("dept_pref"))>0?true:false);
				object.setDestHigh(cursor.getInt(cursor.getColumnIndex("dest_pref"))>0?true:false);
				object.setDistance(cursor.getInt(cursor.getColumnIndex("distance")));
				objects.add(object);
			}while(cursor.moveToNext());
		}
		return objects;
	}
	
	public Cursor getLineListCursor(){
		String sql =
				"SELECT a.*, b.preference as dept_pref, c.preference as dest_pref " +
				"FROM Cambus_LineView a " +
				"INNER JOIN Cambus_CityTable b " +
				"ON a.dept_no = b.city_no " +
				"INNER JOIN Cambus_CityTable c " +
				"ON a.dest_no = c.city_no " +
				";";
		//Log.i("DebugPrint","linesql:"+sql);
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
	public ArrayList<City> getCityList(){
		ArrayList<City> objects = new ArrayList<City>();
		
		Cursor cursor = getCityListCursor();
		if(cursor != null && cursor.moveToFirst()){
			City object;
			do{
				object = new City();
				
				object.setCityNo(cursor.getInt(cursor.getColumnIndex("city_no")));
				object.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				object.setHigh(cursor.getInt(cursor.getColumnIndex("preference"))>0?true:false);
				objects.add(object);
			}while(cursor.moveToNext());
		}
		
		if(cursor!=null)
			cursor.close();
		return objects;
	}
	
	public Cursor getCityListCursor(){
		Cursor cursor = db.query(
				true,
				"CamBUS_CityTable",
				null,
				null,
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
	public ArrayList<Company> getCompanyList(){
		ArrayList<Company> objects = new ArrayList<Company>();
		
		Cursor cursor = getCompanyListCursor();
		if(cursor != null && cursor.moveToFirst()){
			Company object;
			do{
				object = new Company();
				
				object.setCompanyNo(cursor.getInt(cursor.getColumnIndex("company_no")));
				object.setCompanyName(cursor.getString(cursor.getColumnIndex("company_name")));
							
				objects.add(object);
			}while(cursor.moveToNext());
		}
		
		if(cursor!=null)
			cursor.close();
		return objects;
	}
	public Cursor getCompanyListCursor(){
		Cursor cursor = db.query(
				true,
				"CamBUS_CompanyTable",
				null,
				null,
				null, null, null, null, null, null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
	
	public ArrayList<Terminal> getTerminalListInCity(int city_no){
		Cursor cursor = this.getTerminalListInCityCursor(city_no);
		ArrayList<Terminal> objects = new ArrayList<Terminal>();
		
		if(cursor != null && cursor.moveToFirst()){
			Terminal object;
			do{
				object = new Terminal();
				
				object.setTerminalNo(cursor.getInt(cursor.getColumnIndex("terminal_no")));
				object.setTerminalName(cursor.getString(cursor.getColumnIndex("terminal_name")));
				object.setCompanyNo(cursor.getInt(cursor.getColumnIndex("company_no")));
				object.setCompanyName(cursor.getString(cursor.getColumnIndex("company_name")));
				object.setCityNo(cursor.getInt(cursor.getColumnIndex("city_no")));
				object.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				object.setPhoneNo(cursor.getString(cursor.getColumnIndex("phone_no")));
				object.setLink(cursor.getString(cursor.getColumnIndex("link")));
				object.setAddress(cursor.getString(cursor.getColumnIndex("address")));
				object.setPurchase(cursor.getInt(cursor.getColumnIndex("purchase"))>0?true:false);
				object.setGetIn(cursor.getInt(cursor.getColumnIndex("get_in"))>0?true:false);
				object.setGetOff(cursor.getInt(cursor.getColumnIndex("get_off"))>0?true:false);
				object.setMiscEn(cursor.getString(cursor.getColumnIndex("misc_en")));
				object.setMiscKo(cursor.getString(cursor.getColumnIndex("misc_ko")));
							
				objects.add(object);
			}while(cursor.moveToNext());
		}
		
		if(cursor!=null)
			cursor.close();
		return objects;
	}
	
	public ArrayList<Terminal> getTerminalList(int city_no, int company_no){
		Cursor cursor = this.getTerminalListCursor(city_no, company_no);
		ArrayList<Terminal> objects = new ArrayList<Terminal>();
		
		if(cursor != null && cursor.moveToFirst()){
			Terminal object;
			do{
				object = new Terminal();
				
				object.setTerminalNo(cursor.getInt(cursor.getColumnIndex("terminal_no")));
				object.setTerminalName(cursor.getString(cursor.getColumnIndex("terminal_name")));
				object.setCompanyNo(cursor.getInt(cursor.getColumnIndex("company_no")));
				object.setCompanyName(cursor.getString(cursor.getColumnIndex("company_name")));
				object.setCityNo(cursor.getInt(cursor.getColumnIndex("city_no")));
				object.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				object.setPhoneNo(cursor.getString(cursor.getColumnIndex("phone_no")));
				object.setLink(cursor.getString(cursor.getColumnIndex("link")));
				object.setAddress(cursor.getString(cursor.getColumnIndex("address")));
				object.setPurchase(cursor.getInt(cursor.getColumnIndex("purchase"))>0?true:false);
				object.setGetIn(cursor.getInt(cursor.getColumnIndex("get_in"))>0?true:false);
				object.setGetOff(cursor.getInt(cursor.getColumnIndex("get_off"))>0?true:false);
				object.setMiscEn(cursor.getString(cursor.getColumnIndex("misc_en")));
				object.setMiscKo(cursor.getString(cursor.getColumnIndex("misc_ko")));
							
				objects.add(object);
			}while(cursor.moveToNext());
		}
		
		if(cursor!=null)
			cursor.close();
		return objects;
	}
	public Cursor getTerminalListInCityCursor(int city_no){
		Cursor cursor = db.query(
				true,
				"CamBUS_TerminalView",
				null,
				"city_no='" + city_no + "'",
				null, null, null, null, null, null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
	public Cursor getTerminalListCursor(int city_no, int company_no){
		if(company_no<0){
			throw new NullPointerException("NullCompany:"+company_no);
		}
		Cursor cursor = db.query(
				true,
				"CamBUS_TerminalView",
				null,
				"city_no='" + city_no + "' AND company_no='"+company_no+"'",
				null, null, null, null, null, null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
	public Cursor getTerminalListCursor(){
		Cursor cursor = db.query(
				true,
				"CamBUS_TerminalListTable",
				null,
				null,
				null, null, null, null, null, null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
}
