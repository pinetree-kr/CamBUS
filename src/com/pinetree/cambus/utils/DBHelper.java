package com.pinetree.cambus.utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{

	protected final static String DB_NAME = "CamBUS_DB";
	protected final static int DB_VERSION = 1;
	
	public DBHelper(Context context){
		this(context, DB_NAME, null, DB_VERSION);
	}
	public DBHelper(Context context, int db_version){
		this(context, DB_NAME, null, db_version);
	}
	
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("DebugPrint","DB Create");
		// 동기화를 위한 테이블
		String createCambusInfoTableSql =
				"CREATE TABLE Cambus_VersionTable (" +
					"app_ver TEXT NOT NULL, " +
					"last_updated TEXT NOT NULL);";
		
		// 도시목록 
		String createCambusCityTableSql =
				"CREATE TABLE Cambus_CityTable (" +
					"city_no INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
					"city_name TEXT NOT NULL, " +
					"preference INTEGER NOT NULL);";
		
		// 회사목록 
		String createCambusCompanyTableSql =
				"CREATE TABLE Cambus_CompanyTable (" +
					"company_no INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
					"company_name TEXT NOT NULL);";

		// 도시별회사목록 
		String createCambusTerminalTableSql =
				"CREATE TABLE Cambus_TerminalTable (" +
					"terminal_no INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
					"terminal_name TEXT NOT NULL, " +
					"city_no INTEGER NOT NULL, " + // FK - CityTable
					"company_no INTEGER NOT NULL, " + // FK - CityTable
					"phone_no TEXT NULL, " +
					"purchase INTEGER NOT NULL, " +
					"get_in INTEGER NOT NULL, " +
					"get_off INTEGER NOT NULL, " +
					"link TEXT NULL, " +
					"address TEXT NULL, " +
					"misc_en TEXT NULL, " +
					"misc_ko TEXT NULL " +
					");";
		
		// 타입목록 
		String createCambusTypeTableSql =
				"CREATE TABLE Cambus_TypeTable (" +
					"type_no INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
					"type_name TEXT NOT NULL);";
		
		// 노선목록 
		String createCambusLineTableSql = 
				"CREATE TABLE Cambus_LineTable (" +
					"line_no INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
					"dept_no INTEGER NOT NULL, " + // FK - CityTable
					"dest_no INTEGER NOT NULL, " + // FK - CityTable
					"distance INTEGER NULL, " + 
					"UNIQUE(dept_no, dest_no) ON CONFLICT IGNORE );";
		
		// 노선별버스목록 
		String createCambusLineBusTableSql = 
				"CREATE TABLE Cambus_LineBusTable (" +
					"linebus_no INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
					"line_no INTEGER NOT NULL, " + // FK - LineTable
					"company_no INTEGER NOT NULL, " + // FK - CompanyTable
					"type_no INTEGER NOT NULL, " + // FK - TypeTable
					"duration_time REAL NULL, " +
					"native_price REAL NOT NULL, " +
					"foreigner_price REAL NOT NULL, " +
					"visa REAL NULL, " +
					"dn TEXT NULL, " +
					//"last_update TEXT NULL, " +
					"UNIQUE(line_no, company_no, type_no) ON CONFLICT IGNORE );";

		// 노선별버스시간목록 
		String createCambusLineBusTimeTableSql = 
				"CREATE TABLE Cambus_LineBusTimeTable (" +
					"linebustime_no INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
					"linebus_no INTEGER NOT NULL, " + // FK - LineBusTable
					"mid_no INTEGER NULL, " + // FK - CityTable 
					"departure_time TEXT NOT NULL, " +
					"arrival_time TEXT NULL);";
		
		// 노선뷰 
		String createCambusLineView =
				"CREATE VIEW Cambus_LineView AS " +
					"SELECT line_no, dept_no, b.city_name as dept_name, dest_no, c.city_name as dest_name, distance " +
					"FROM Cambus_LineTable a " +
					"INNER JOIN Cambus_CityTable b " +
					"ON a.dept_no = b.city_no " +
					"INNER JOIN Cambus_CityTable c " +
					"ON a.dest_no = c.city_no "+
					";";
		
		// 노선별버스뷰 
		String createCambusLineBusView =
				"CREATE VIEW Cambus_LineBusView AS " +
					"SELECT linebus_no, dept_no, dept_name, " +
						"a.line_no, dest_no, dest_name, a.company_no, company_name, " +
						"a.type_no, type_name, duration_time, native_price, " +
						"foreigner_price, visa " +
					"FROM Cambus_LineBusTable a " +
					"INNER JOIN Cambus_LineView b " +
					"ON a.line_no = b.line_no " +
					"INNER JOIN Cambus_CompanyTable c " +
					"ON a.company_no = c.company_no " +
					"INNER JOIN Cambus_TypeTable d " +
					"ON a.type_no = d.type_no " +
					";";
		
		// 노선별버스시간뷰 
		String createCambusLineBusTimeView =
				"CREATE VIEW Cambus_LineBusTimeView AS " +
					"SELECT linebustime_no, b.dept_no, dept_name, " +
						"b.dest_no, dest_name, company_no, company_name, " +
						"type_no, type_name, a.mid_no, c.city_name as middle_city, " +
						"departure_time, arrival_time, distance, duration_time, native_price, " +
						"foreigner_price, visa " +
					"FROM Cambus_LineBusTimeTable a " +
					"INNER JOIN Cambus_LineBusView b " +
					"ON a.linebus_no = b.linebus_no " +
					"LEFT JOIN Cambus_CityTable c " +
					"ON a.mid_no = c.city_no " +
					"INNER JOIN Cambus_LineTable d " +
					"ON b.line_no = d.line_no " +
					";";
		
		String createCambusTerminalView = 
				"CREATE VIEW Cambus_TerminalView AS " +
					"SELECT terminal_no, a.city_no, city_name, a.company_no, company_name, terminal_name, " +
						"phone_no, purchase, get_in, get_off, link, address, misc_en, misc_ko " +
					"FROM Cambus_TerminalTable a " +
					"INNER JOIN Cambus_CityTable b " +
					"ON a.city_no = b.city_no " +
					"INNER JOIN Cambus_CompanyTable c " +
					"ON a.company_no = c.company_no " +
					";";
		
		try{
			db.beginTransaction();
			
			// Create Table
			db.execSQL(createCambusInfoTableSql);

			db.execSQL(createCambusCityTableSql);
			db.execSQL(createCambusCompanyTableSql);
			db.execSQL(createCambusTerminalTableSql);
			db.execSQL(createCambusTypeTableSql);
			db.execSQL(createCambusLineTableSql);
			db.execSQL(createCambusLineBusTableSql);
			db.execSQL(createCambusLineBusTimeTableSql);
			
			// Create View
			
			db.execSQL(createCambusLineView);
			db.execSQL(createCambusLineBusView);
			db.execSQL(createCambusLineBusTimeView);
			db.execSQL(createCambusTerminalView);
			
			// Set Transaction Successful
			db.setTransactionSuccessful();
		}catch(SQLiteException e){
			Log.i("DebugPrint", e.getMessage());
			e.printStackTrace();
		}catch(SQLException e){
			Log.i("DebugPrint", e.getMessage());
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i("DebugPrint","DB Upgrade");
		
		db.execSQL("DROP TABLE IF EXISTS Cambus_VersionTable");
		db.execSQL("DROP TABLE IF EXISTS Cambus_CityTable");
		db.execSQL("DROP TABLE IF EXISTS Cambus_CompanyTable");
		db.execSQL("DROP TABLE IF EXISTS Cambus_TerminalTable");
		db.execSQL("DROP TABLE IF EXISTS Cambus_TypeTable");
		db.execSQL("DROP TABLE IF EXISTS Cambus_LineTable");
		db.execSQL("DROP TABLE IF EXISTS Cambus_LineBusTable");
		db.execSQL("DROP TABLE IF EXISTS Cambus_LineBusTimeTable");
		
		db.execSQL("DROP VIEW IF EXISTS Cambus_LineView");
		db.execSQL("DROP VIEW IF EXISTS Cambus_LineBusView");
		db.execSQL("DROP VIEW IF EXISTS Cambus_LineBusTimeView");
		db.execSQL("DROP VIEW IF EXISTS Cambus_TerminalView");
		
		onCreate(db);
	}
	
	@Override
	public void onOpen(SQLiteDatabase db){
		super.onOpen(db);
		//Log.i("DebugPrint","DB Open");
		// TODO 버전체크를 업데이트를 여기에다가 
		
	}

}
