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
	private DBHelper dbHelper;
	private SQLiteDatabase db;
	
	private static DBHandler handler;
	
	private DBHandler(Context context){
		this.dbHelper = new DBHelper(context, ExcelFileInfo.ExcelFileVersion);
	}
	public static DBHandler getInstance(Context context, boolean readOnly){
		if(handler==null){
			handler = new DBHandler(context);
		}
		handler.open(readOnly);
		return handler;
	}
	
	public void open(boolean readOnly) throws SQLException{
		if(db!=null && db.isOpen()){
			db.close();
		}
		if(readOnly){
			db = dbHelper.getReadableDatabase();
		}else{
			db = dbHelper.getWritableDatabase();
		}
	}
	
	public void close(){
		if(dbHelper!=null && db.isOpen()){
			//Log.i("DebugPrint","dbClose");
			dbHelper.close();			
		}
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
	public void initTable(){
		String verTable =
				"DELETE FROM Version; ";
		String cityTable =
				"DELETE FROM City; ";
		String companyTable =
				"DELETE FROM Company; ";
		String terminalTable =
				"DELETE FROM Terminal; ";
		String typeTable =
				"DELETE FROM Type; ";
		String lineTable =
				"DELETE FROM Line; ";
		String lineBusTable =
				"DELETE FROM Bus; ";
		String lineBusTimeTable =
				"DELETE FROM Time; ";
		
		db.execSQL(verTable);
		db.execSQL(cityTable);
		db.execSQL(companyTable);
		db.execSQL(terminalTable);
		db.execSQL(typeTable);
		db.execSQL(lineTable);
		db.execSQL(lineBusTable);
		db.execSQL(lineBusTimeTable);
		
		Log.i("DebugPrint","DB INIT");
	}
	
	public long getDBLastUpdate() throws SQLiteException{
		String sql =
				"SELECT * " +
				"FROM Version ORDER BY ver_no DESC;";
		Cursor cursor = db.rawQuery(sql, null);
		long lastupdate = 0;
		if(cursor != null && cursor.moveToFirst()){
			lastupdate = cursor.getLong(cursor.getColumnIndex("db_lastupdate"));
		}
		return lastupdate;
	}
	
	public long updateDBLastUpdate(long date) throws SQLiteException{
		ContentValues values = new ContentValues();
		long rv = -1;
		values.put("db_lastupdate", date);
		rv = db.insert("Version", null, values);
		if(rv<0){
			throw new SQLException("VersionInfo Update Error:"+values);
		}
		return rv;
	}
	
	public long insertRoute(CityRoute route) throws SQLiteException{
		ContentValues values = new ContentValues();
		long rv = -1;
		values.put("city_id", route.getCityId());
		values.put("line_no",route.getLineNo());
		values.put("line_order",route.getLineOrder());
		values.put("name",route.getName());
		
		rv = db.insert("CityRoute", null, values);
		if(rv<0){
			throw new SQLException("CityRoute Insert Error:"+values);
		}
		return rv;
	}
	public long insertCity(City city) throws SQLiteException{
		ContentValues values = new ContentValues();
		long rv = -1;
		values.put("name", city.getName());
		values.put("pref",city.getPref()?1:0);
		values.put("pref_order",city.getIndex());
		
		rv = db.insert("City", null, values);
		if(rv<0){
			throw new SQLException("City Insert Error:"+values);
		}
		return rv;
	}
	
	public long insertCompany(Company company) throws SQLiteException{
		ContentValues values = new ContentValues();
		long rv = -1;
		values.put("name", company.getName());
		rv = db.insert("Company", null, values);
		if(rv<0){
			throw new SQLException("Company Insert Error:"+values);
		}
		return rv;
	}
	
	public long insertType(Type type) throws SQLiteException{
		ContentValues values = new ContentValues();
		long rv = -1;
		values.put("name", type.getName());
		rv = db.insert("Type", null, values);
		if(rv<0){
			throw new SQLException("Type Insert Error:"+values);
		}
		return rv;
	}	
	
	public long insertTerminal(Terminal terminal) throws SQLiteException{
		ContentValues values = new ContentValues();
		long rv = -1;
		values.put("name", terminal.getName());
		values.put("city_id", terminal.getCityId());
		values.put("company_id", terminal.getCompanyId());
		values.put("phone", terminal.getPhone());
		values.put("purchase", terminal.isPurchase()?1:0);
		values.put("getin", terminal.isGetIn()?1:0);
		values.put("getoff", terminal.isGetOff()?1:0);
		//values.put("link", terminal.getLink());
		values.put("address", terminal.getAddress());
		//values.put("misc_en", terminal.getMiscEn());
		//values.put("misc_ko", terminal.getMiscKo());
		values.put("misc", terminal.getMisc());
		values.put("latlng", terminal.getLatLng());
		rv = db.insert("Terminal", null, values);
		if(rv<0){
			throw new SQLException("Terminal Insert Error:"+values);
		}
		return rv;
	}
	
	public long insertLine(Line line) throws SQLiteException{
		ContentValues values = new ContentValues();
		long rv = -1;
		values.put("dept", line.getDeptId());
		values.put("dest", line.getDestId());
		values.put("distance", line.getDistance());
		rv = db.insert("Line", null, values);
		if(rv<0){
			throw new SQLException("Line Insert Error:"+values);
		}
		return rv;
	}
	
	public long insertBus(Bus bus) throws SQLiteException{
		ContentValues values = new ContentValues();
		long rv = -1;
		values.put("line_id", bus.getLineId());
		values.put("company_id", bus.getCompanyId());
		values.put("type_id", bus.getTypeId());
		values.put("duration", bus.getDuration());
		values.put("native", bus.getNative());
		values.put("mid_id", bus.getMidId());
		values.put("foreigner", bus.getForeign());
		values.put("visa", bus.getVisa());
		values.put("abroad", bus.getAbroad());
		rv = db.insert("Bus", null, values);
		if(rv<0){
			throw new SQLException("LineBus Insert Error:"+values);
		}
		return rv;
	}
	
	public long insertTime(Time time) throws SQLiteException{
		ContentValues values = new ContentValues();
		long rv = -1;
		values.put("bus_id", time.getBusId());
		//values.put("mid_id", time.getMidId());
		values.put("dept_t", time.getDeptTime());
		values.put("dest_t", time.getDeptTime());
		rv = db.insert("Time", null, values);
		if(rv<0){
			throw new SQLException("Time Insert Error:"+values);
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
	
	public ArrayList<Integer> getTimeList(){
		ArrayList<Integer> objects = new ArrayList<Integer>();
		objects.add(-1);
		for(int i = 4; i<27; i++){
			objects.add(i);
		}
		return objects;
	}
	// CityRoute List
	public ArrayList<CityRoute> getCityRouteList(int city_id, int line_no){
		/*/
		String sql =
				"SELECT * FROM CityRoute " +
				"WHERE city_id='"+city_id+"' ";
		if(line_no>0){
			sql += "AND line_no='"+line_no+"' ";
		}
		/**/
		String sql =
				"SELECT * FROM CityRoute ";
		if(city_id>0 && line_no>0){
			sql += "WHERE city_id='"+city_id+"' AND line_no='"+line_no+"' ";
		}else if(city_id>0){
			sql += "WHERE city_id='"+city_id+"' ";
		}else if(line_no>0){
			sql += "WHERE line_no='"+line_no+"' ";			
		}
		sql += "ORDER BY line_order ASC ;";
		
		Cursor cursor = db.rawQuery(sql, null);
		
		ArrayList<CityRoute> routeList = new ArrayList<CityRoute>();
		
		if(cursor != null && cursor.moveToFirst()){
			CityRoute route;
			do{
				route = new CityRoute();
				route.setCityId(city_id);
				route.setLineNo(line_no);
				route.setLineOrder(cursor.getInt(cursor.getColumnIndex("line_order")));
				route.setName(cursor.getString(cursor.getColumnIndex("name")));
				routeList.add(route);
			}while(cursor.moveToNext());
		}
		if(cursor!=null)
			cursor.close();
		return routeList;
	}
	
	//  Departure List
	public ArrayList<Departure> getDepartureList(){
		String sql = 
				"SELECT * FROM DepartureListView " +
				"ORDER BY pref desc, pref_order asc, name asc" +
				";";
		Cursor cursor = db.rawQuery(sql, null);
		
		ArrayList<Departure> departure_list = new ArrayList<Departure>();
		departure_list.add(new Departure());
		HashMap<String, String> hash = new HashMap<String,String>();
		
		if(cursor != null && cursor.moveToFirst()){
			int city_no;
			String city_name;
			boolean isHigh;
			int order;
			Departure object;
			do{
				city_no = cursor.getInt(cursor.getColumnIndex("_id"));
				city_name = cursor.getString(cursor.getColumnIndex("name"));
				isHigh = cursor.getInt(cursor.getColumnIndex("pref"))>0?true:false;
				order = cursor.getInt(cursor.getColumnIndex("pref_order"));
				//Log.i("DebugPrint",city_name+":"+order);
				if(!hash.containsKey(String.valueOf(city_no))){
					hash.put(String.valueOf(city_no), city_name);
					object = new Departure();
					object.setId(city_no);
					object.setName(city_name);
					object.setPref(isHigh);
					object.setIndex(order);
					departure_list.add(object);
				}
			}while(cursor.moveToNext());
		}

		if(cursor!=null)
			cursor.close();
		return departure_list;
	}
	
	//  Type List
	public ArrayList<Type> getTypeList(int dept_id, int dest_id){
		String sql =
				"SELECT DISTINCT type_id, type_name " +
				"FROM BusView ";
		if(dept_id>0){
			sql += "WHERE dept='"+dept_id+"' ";
		}
		if(dest_id>0){
			sql += "WHERE dest='"+dest_id+"' ";
		}
		sql += "ORDER BY type_name ASC ;";
		
		Cursor cursor = db.rawQuery(sql, null);
		
		ArrayList<Type> type_list = new ArrayList<Type>();
		
		if(cursor != null && cursor.moveToFirst()){
			Type object = new Type();
			type_list.add(object);
			do{
				object = new Type();
				object.setId(cursor.getInt(cursor.getColumnIndex("type_id")));
				object.setName(cursor.getString(cursor.getColumnIndex("type_name")));
				type_list.add(object);
			}while(cursor.moveToNext());
		}

		if(cursor!=null)
			cursor.close();
		return type_list;
	}
	//  Type List
	public ArrayList<Company> getCompanyList(int dept_id, int dest_id){
		String sql =
				"SELECT DISTINCT company_id, company_name " +
				"FROM BusView ";
		if(dept_id>0){
			sql += "WHERE dept='"+dept_id+"' ";
		}
		if(dest_id>0){
			sql += "WHERE dest='"+dest_id+"' ";
		}
		sql += "ORDER BY company_name ASC ;";
		
		Cursor cursor = db.rawQuery(sql, null);
		
		ArrayList<Company> company_list = new ArrayList<Company>();
		
		if(cursor != null && cursor.moveToFirst()){
			Company object = new Company();
			company_list.add(object);
			do{
				object = new Company();
				object.setId(cursor.getInt(cursor.getColumnIndex("company_id")));
				object.setName(cursor.getString(cursor.getColumnIndex("company_name")));
				company_list.add(object);
			}while(cursor.moveToNext());
		}
		
		if(cursor!=null)
			cursor.close();
		return company_list;
	}
	
	//  Destination List
	public ArrayList<Destination> getDestinationList(int dept_id){
		String sql =
				"SELECT b.* " +
				"FROM LineView a " +
				"INNER JOIN City b " +
				"ON a.dest = b._id " +
				"WHERE a.dept = '" + dept_id + "' " +
				"ORDER BY b.pref desc, b.name asc " +
				";";
		Cursor cursor = db.rawQuery(sql, null);
		
		ArrayList<Destination> destination_list = new ArrayList<Destination>();
		
		HashMap<String, String> hash = new HashMap<String,String>();
		
		destination_list.add(new Destination());
		
		if(cursor != null && cursor.moveToFirst()){
			int city_no;
			String city_name;
			boolean isHigh;
			Destination object;
			do{
				city_no = cursor.getInt(cursor.getColumnIndex("_id"));
				city_name = cursor.getString(cursor.getColumnIndex("name"));
				isHigh = cursor.getInt(cursor.getColumnIndex("pref"))>0?true:false;
				
				if(!hash.containsKey(String.valueOf(city_no))){
					hash.put(String.valueOf(city_no), city_name);
					object = new Destination();
					object.setId(city_no);
					object.setName(city_name);
					object.setPref(isHigh);
					destination_list.add(object);
				}
			}while(cursor.moveToNext());
		}

		if(cursor!=null)
			cursor.close();
		return destination_list;
	}
	
	public Line getLineInfo(int dept_id, int dest_id){
		String sql =
				"SELECT * " +
				"FROM LineView " +
				"WHERE dept=? AND dest=? ;";
		Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(dept_id), String.valueOf(dest_id)});
		
		Line line = new Line();
		if(cursor != null && cursor.moveToFirst()){
			line.setDeptId(cursor.getInt(cursor.getColumnIndex("dept")));
			line.setDestId(cursor.getInt(cursor.getColumnIndex("dest")));
			line.setDeptName(cursor.getString(cursor.getColumnIndex("dept_name")));
			line.setDestName(cursor.getString(cursor.getColumnIndex("dest_name")));
			line.setDistance(cursor.getInt(cursor.getColumnIndex("distance")));
			line.setLineId(cursor.getInt(cursor.getColumnIndex("line_id")));
		}
		return line;
	}
	
	public ArrayList<Time> getTimeList(int dept_id, int dest_id, int type_id, int company_id, int time, String order){
		String sql =
				"SELECT * " +
				"FROM TimeView " +
				"WHERE dept='" + dept_id + "' " +
				"AND dest='" + dest_id +"' " +
				"AND dept_t>='" + String.format("%02d:00",time) + "' ";
		
		// 0 is All type
		if(type_id>0){
			sql += "AND type_id='" + type_id +"' ";
		}
		// 0 is All company
		if(company_id>0){
			sql += "AND company_id='" + company_id +"' ";
		}
		
		sql += "ORDER BY ";
		if(order.equals("time")){
			sql += "dept_t ";
		}else if(order.equals("price")){
			sql += "foreigner ";
		}else if(order.equals("name")){
			sql += "company_name ";
		}
		
		sql += "ASC ;";
		Cursor cursor = db.rawQuery(sql, null);
		
		ArrayList<Time> objects = new ArrayList<Time>();
		
		if(cursor != null && cursor.moveToFirst()){
			// 관련된 회사 목록들
			ArrayList<Terminal> terminals = getTerminalListInCity(dept_id);
			Time object;
			do{
				object = new Time();
				object.setTimeId(cursor.getInt(cursor.getColumnIndex("time_id")));
				object.setDeptId(cursor.getInt(cursor.getColumnIndex("dept")));
				object.setDeptName(cursor.getString(cursor.getColumnIndex("dept_name")));
				object.setDestId(cursor.getInt(cursor.getColumnIndex("dest")));
				object.setDestName(cursor.getString(cursor.getColumnIndex("dest_name")));
				object.setCompanyId(cursor.getInt(cursor.getColumnIndex("company_id")));
				object.setCompanyName(cursor.getString(cursor.getColumnIndex("company_name")));
				object.setTypeId(cursor.getInt(cursor.getColumnIndex("type_id")));
				object.setTypeName(cursor.getString(cursor.getColumnIndex("type_name")));
				object.setMidId(cursor.getInt(cursor.getColumnIndex("mid_id")));
				object.setMidName(cursor.getString(cursor.getColumnIndex("mid_name")));
				object.setDeptTime(cursor.getString(cursor.getColumnIndex("dept_t")));
				object.setArrivalTime(cursor.getString(cursor.getColumnIndex("dest_t")));
				object.setDistance(cursor.getInt(cursor.getColumnIndex("distance")));
				object.setDuration(cursor.getDouble(cursor.getColumnIndex("duration")));
				object.setNative(cursor.getDouble(cursor.getColumnIndex("native")));
				object.setForeign(cursor.getDouble(cursor.getColumnIndex("foreigner")));
				object.setVisa(cursor.getDouble(cursor.getColumnIndex("visa")));
				
				for(Terminal terminal : terminals){
					if(terminal.getCityId() == object.getDeptId() && terminal.getCompanyId() == object.getCompanyId()){
						object.addTerminal(terminal);
					}
				}
				
				objects.add(object);
			}while(cursor.moveToNext());
		}
		return objects;
	}
	
	public ArrayList<Terminal> getTerminalListInCity(int city_id){
		String sql =
				"SELECT * " +
				"FROM TerminalView " +
				"WHERE city_id=? " +
				";";
		
		Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(city_id)});
		
		ArrayList<Terminal> objects = new ArrayList<Terminal>();
		
		if(cursor != null && cursor.moveToFirst()){
			Terminal object;
			do{
				object = new Terminal();
				
				object.setId(cursor.getInt(cursor.getColumnIndex("terminal_id")));
				object.setName(cursor.getString(cursor.getColumnIndex("name")));
				object.setCompanyId(cursor.getInt(cursor.getColumnIndex("company_id")));
				object.setCompanyName(cursor.getString(cursor.getColumnIndex("company_name")));
				object.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
				object.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				object.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
				//object.setLink(cursor.getString(cursor.getColumnIndex("link")));
				object.setAddress(cursor.getString(cursor.getColumnIndex("address")));
				object.setPurchase(cursor.getInt(cursor.getColumnIndex("purchase"))>0?true:false);
				object.setGetIn(cursor.getInt(cursor.getColumnIndex("getin"))>0?true:false);
				object.setGetOff(cursor.getInt(cursor.getColumnIndex("getoff"))>0?true:false);
				//object.setMiscEn(cursor.getString(cursor.getColumnIndex("misc_en")));
				//object.setMiscKo(cursor.getString(cursor.getColumnIndex("misc_ko")));
				object.setMisc(cursor.getString(cursor.getColumnIndex("misc")));
				object.setLatLng(cursor.getString(cursor.getColumnIndex("latlng")));
				
				objects.add(object);
			}while(cursor.moveToNext());
		}
		
		if(cursor!=null)
			cursor.close();
		return objects;
	}
	/*/
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
				object.setLatLng(cursor.getString(cursor.getColumnIndex("latlng")));
							
				objects.add(object);
			}while(cursor.moveToNext());
		}
		
		if(cursor!=null)
			cursor.close();
		return objects;
	}
	
	public Cursor getTerminalListCursor(int city_no, int company_no){
		if(company_no<0){
			throw new NullPointerException("Null Company:"+company_no);
		}
		
		String sql =
				"SELECT * " +
				"FROM CamBUS_TerminalView " +
				"WHERE city_no=? AND company_no=? " +
				";";
		
		Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(city_no), String.valueOf(company_no)});
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
	/**/
	
}
