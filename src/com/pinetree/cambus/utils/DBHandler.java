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
		values.put("city_name", city.city_name);
		values.put("preference",city.high?1:0);
		rv = db.insert("Cambus_CityTable", null, values);
		if(rv<0){
			throw new SQLException("City Insert Error:"+values);
		}
		return rv;
	}
	
	public long insertCompany(Company company) throws SQLiteException{
		ContentValues values = new ContentValues();
		long rv = -1;
		values.put("company_name", company.company_name);
		rv = db.insert("Cambus_CompanyTable", null, values);
		if(rv<0){
			throw new SQLException("Company Insert Error:"+values);
		}
		return rv;
	}
	
	public long insertBusType(BusType type) throws SQLiteException{
		ContentValues values = new ContentValues();
		long rv = -1;
		values.put("type_name", type.type_name);
		rv = db.insert("Cambus_TypeTable", null, values);
		if(rv<0){
			throw new SQLException("Type Insert Error:"+values);
		}
		return rv;
	}	
	
	public long insertOffice(Office office) throws SQLiteException{
		ContentValues values = new ContentValues();
		long rv = -1;
		values.put("office_name", office.office_name);
		values.put("city_no", office.city_no);
		values.put("company_no", office.company_no);
		values.put("phone_no", office.phone_no);
		values.put("purchase", office.purchase?1:0);
		values.put("get_in", office.get_in?1:0);
		values.put("get_off", office.get_off?1:0);
		values.put("link", office.link);
		values.put("address", office.address);
		values.put("misc_en", office.misc_en);
		values.put("misc_ko", office.misc_ko);
		rv = db.insert("Cambus_OfficeTable", null, values);
		if(rv<0){
			throw new SQLException("Office Insert Error:"+values);
		}
		return rv;
	}
	
	public long insertLine(Line line) throws SQLiteException{
		ContentValues values = new ContentValues();
		long rv = -1;
		values.put("dept_no", line.dept_no);
		values.put("dest_no", line.dest_no);
		values.put("distance", line.distance);
		rv = db.insert("Cambus_LineTable", null, values);
		if(rv<0){
			throw new SQLException("Line Insert Error:"+values);
		}
		return rv;
	}
	
	public long insertLineBus(LineBus linebus) throws SQLiteException{
		ContentValues values = new ContentValues();
		long rv = -1;
		values.put("line_no", linebus.line_no);
		values.put("company_no", linebus.company_no);
		values.put("type_no", linebus.type_no);
		values.put("duration_time", linebus.duration_time);
		values.put("native_price", linebus.native_price);
		values.put("foreigner_price", linebus.foreigner_price);
		values.put("visa", linebus.visa);
		values.put("dn", linebus.dn);
		rv = db.insert("Cambus_LineBusTable", null, values);
		if(rv<0){
			throw new SQLException("LineBus Insert Error:"+values);
		}
		return rv;
	}
	
	public long insertLineBusTime(LineBusTime linebustime) throws SQLiteException{
		ContentValues values = new ContentValues();
		long rv = -1;
		values.put("linebus_no", linebustime.linebus_no);
		values.put("mid_no", linebustime.mid_no);
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
					object.city_no = city_no;
					object.city_name = city_name;
					object.high = isHigh;
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
				object.type_no = cursor.getInt(cursor.getColumnIndex("type_no"));
				object.type_name = cursor.getString(cursor.getColumnIndex("type_name"));
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
					object.city_no = city_no;
					object.city_name = city_name;
					object.high = isHigh;
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
			LineBusTime object;
			do{
				object = new LineBusTime();
				object.linebustime_no = cursor.getInt(cursor.getColumnIndex("linebustime_no"));
				object.dept_no = cursor.getInt(cursor.getColumnIndex("dept_no"));
				object.dept_name = cursor.getString(cursor.getColumnIndex("dept_name"));
				object.dest_no = cursor.getInt(cursor.getColumnIndex("dest_no"));
				object.dest_name = cursor.getString(cursor.getColumnIndex("dest_name"));
				object.company_no = cursor.getInt(cursor.getColumnIndex("company_no"));
				object.company_name = cursor.getString(cursor.getColumnIndex("company_name"));
				object.type_no = cursor.getInt(cursor.getColumnIndex("type_no"));
				object.type_name = cursor.getString(cursor.getColumnIndex("type_name"));
				object.mid_no = cursor.getInt(cursor.getColumnIndex("mid_no"));
				object.middle_city = cursor.getString(cursor.getColumnIndex("middle_city"));
				object.setDeptTime(cursor.getString(cursor.getColumnIndex("departure_time")));
				object.setArrivalTime(cursor.getString(cursor.getColumnIndex("arrival_time")));
				object.distance = cursor.getInt(cursor.getColumnIndex("distance"));
				object.duration_time = cursor.getDouble(cursor.getColumnIndex("duration_time"));
				object.native_price = cursor.getDouble(cursor.getColumnIndex("native_price"));
				object.foreigner_price = cursor.getDouble(cursor.getColumnIndex("foreigner_price"));
				object.visa = cursor.getDouble(cursor.getColumnIndex("visa"));
				
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
				object.line_no = cursor.getInt(cursor.getColumnIndex("line_no"));
				object.dept_no = cursor.getInt(cursor.getColumnIndex("dept_no"));
				object.dest_no = cursor.getInt(cursor.getColumnIndex("dest_no"));
				object.dept_name = cursor.getString(cursor.getColumnIndex("dept_name"));
				object.dest_name = cursor.getString(cursor.getColumnIndex("dest_name"));
				object.dept_high = cursor.getInt(cursor.getColumnIndex("dept_pref"))>0?true:false;
				object.dest_high = cursor.getInt(cursor.getColumnIndex("dest_pref"))>0?true:false;
				object.distance = cursor.getInt(cursor.getColumnIndex("distance"));
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
				
				object.city_no = cursor.getInt(cursor.getColumnIndex("city_no"));
				object.city_name = cursor.getString(cursor.getColumnIndex("city_name"));
				object.high = cursor.getInt(cursor.getColumnIndex("preference"))>0?true:false;
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
				
				object.company_no = cursor.getInt(cursor.getColumnIndex("company_no"));
				object.company_name = cursor.getString(cursor.getColumnIndex("company_name"));
							
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
	public ArrayList<Office> getOfficeList(int city_no, int company_no){
		Cursor cursor = this.getOfficeListCursor(city_no, company_no);
		ArrayList<Office> objects = new ArrayList<Office>();
		
		if(cursor != null && cursor.moveToFirst()){
			Office object;
			do{
				object = new Office();
				
				object.office_no = cursor.getInt(cursor.getColumnIndex("office_no"));
				object.office_name = cursor.getString(cursor.getColumnIndex("office_name"));
				object.company_no = cursor.getInt(cursor.getColumnIndex("company_no"));
				object.company_name = cursor.getString(cursor.getColumnIndex("company_name"));
				object.city_no = cursor.getInt(cursor.getColumnIndex("city_no"));
				object.city_name = cursor.getString(cursor.getColumnIndex("city_name"));
				object.phone_no = cursor.getString(cursor.getColumnIndex("phone_no"));
				object.link = cursor.getString(cursor.getColumnIndex("link"));
				object.address = cursor.getString(cursor.getColumnIndex("address"));
				object.purchase = cursor.getInt(cursor.getColumnIndex("purchase"))>0?true:false;
				object.get_in = cursor.getInt(cursor.getColumnIndex("get_in"))>0?true:false;
				object.get_off = cursor.getInt(cursor.getColumnIndex("get_off"))>0?true:false;
				object.misc_en = cursor.getString(cursor.getColumnIndex("misc_en"));
				object.misc_ko = cursor.getString(cursor.getColumnIndex("misc_ko"));
							
				objects.add(object);
			}while(cursor.moveToNext());
		}
		
		if(cursor!=null)
			cursor.close();
		return objects;
	}
	public Cursor getOfficeListCursor(int city_no, int company_no){
		if(company_no<0){
			throw new NullPointerException("NullCompany:"+company_no);
		}
		Cursor cursor = db.query(
				true,
				"CamBUS_OfficeView",
				null,
				"city_no='" + city_no + "' AND company_no='"+company_no+"'",
				null, null, null, null, null, null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
	public Cursor getOfficeListCursor(){
		Cursor cursor = db.query(
				true,
				"CamBUS_OfficeListTable",
				null,
				null,
				null, null, null, null, null, null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
}
