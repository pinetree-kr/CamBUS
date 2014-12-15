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
	public DBHelper(Context context, String dbName, int db_version){
		this(context, dbName, null, db_version);
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
		String createVersionTableSql =
				"CREATE TABLE Version (" +
					"ver_no INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
					"db_lastupdate REAL NOT NULL);";
		String initVersionTableSql =
				"INSERT INTO Version " +
					"(db_lastupdate) " +
					"VALUES (0) ;";
		// 도시목록 
		String createCityTableSql =
				"CREATE TABLE City (" +
					"_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
					"name TEXT NOT NULL, " +
					"pref INTEGER NOT NULL, " +
					"pref_order INTEGER NULL );";
		
		// 회사목록 
		String createCompanyTableSql =
				"CREATE TABLE Company (" +
					"_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
					"name TEXT NOT NULL);";

		// 도시별회사목록 
		String createTerminalTableSql =
				"CREATE TABLE Terminal (" +
					"_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
					"name TEXT NOT NULL, " +
					"city_id INTEGER NOT NULL, " + // FK - CityTable
					"company_id INTEGER NOT NULL, " + // FK - CityTable
					"phone TEXT NULL, " +
					"purchase INTEGER NULL, " +
					"getin INTEGER NULL, " +
					"getoff INTEGER NULL, " +
					//"link TEXT NULL, " +
					"address TEXT NULL, " +
					"misc TEXT NULL, " +
					//"misc_en TEXT NULL, " +
					//"misc_ko TEXT NULL, " +
					"latlng TEXT NULL " +
					");";
		
		// 타입목록 
		String createTypeTableSql =
				"CREATE TABLE Type (" +
					"_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
					"name TEXT NOT NULL);";
		
		// 노선목록 
		String createLineTableSql = 
				"CREATE TABLE Line (" +
					"_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
					"dept INTEGER NOT NULL, " + // FK - CityTable
					"dest INTEGER NOT NULL, " + // FK - CityTable
					"distance INTEGER NULL, " + 
					"UNIQUE(dept, dest) ON CONFLICT IGNORE );";
		
		// 노선별버스목록 
		String createBusTableSql = 
				"CREATE TABLE Bus (" +
					"_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
					"line_id INTEGER NOT NULL, " + // FK - LineTable
					"company_id INTEGER NOT NULL, " + // FK - CompanyTable
					"type_id INTEGER NOT NULL, " + // FK - TypeTable
					"mid_id INTEGER NULL, " + // FK - CityTable
					"duration REAL NULL, " +
					"native REAL NOT NULL, " +
					"foreigner REAL NOT NULL, " +
					"visa REAL NULL, " +
					"abroad INTEGER NULL, " +
					"seat INTEGER NULL, " +					
					//"last_update TEXT NULL, " +
					"UNIQUE(line_id, company_id, type_id, mid_id) ON CONFLICT IGNORE );";

		// 노선별버스시간목록 
		String createTimeTableSql = 
				"CREATE TABLE Time (" +
					"_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
					"bus_id INTEGER NOT NULL, " + // FK - BusTable
					//"mid_no INTEGER NULL, " + // FK - CityTable 
					"dept_t TEXT NOT NULL, " +
					"dest_t TEXT NULL);";

		// 노선별버스시간목록 
		String createCityRouteTableSql = 
				"CREATE TABLE CityRoute (" +
						//"_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
						"city_id INTEGER NOT NULL, " + //FK
						"line_no INTEGER NOT NULL, " + 
						"line_order INTEGER NOT NULL, " + // 
						"name TEXT NOT NULL, " +
						"PRIMARY KEY(city_id, line_no, line_order) ON CONFLICT IGNORE ); ";
						//"dest_t TEXT NULL);";
		
		// 노선뷰 
		String createLineView =
				"CREATE VIEW LineView AS " +
					"SELECT a._id as line_id, dept, b.name as dept_name, dest, c.name as dest_name, distance " +
					"FROM Line a " +
					"INNER JOIN City b " +
					"ON a.dept = b._id " +
					"INNER JOIN City c " +
					"ON a.dest = c._id "+
					";";
		String createDepartureListView =
				"CREATE VIEW DepartureListView AS " +
					"SELECT distinct b.* " +
					"FROM LineView a " +
					"INNER JOIN City b " +
					"ON a.dept = b._id " +
					";";
					
		// 노선별버스뷰 
		String createBusView =
				"CREATE VIEW BusView AS " +
					"SELECT a._id as bus_id, company_id, c.name as company_name, type_id, d.name as type_name, " + //dept_no, dept_name, " +
						"mid_id, e.name as mid_name, duration, native, foreigner, visa, abroad, seat, b.* " +
					"FROM Bus a " +
					"INNER JOIN LineView b " +
					"ON a.line_id = b.line_id " +
					"INNER JOIN Company c " +
					"ON a.company_id = c._id " +
					"INNER JOIN Type d " +
					"ON a.type_id = d._id " +
					"LEFT JOIN City e " +
					"ON a.mid_id = e._id " +
					";";
		
		// 노선별버스시간뷰 
		String createTimeView =
				"CREATE VIEW TimeView AS " +
					"SELECT a._id as time_id, b.*,dept_t, dest_t " +
					"FROM Time a " +
					"INNER JOIN BusView b " +
					"ON a.bus_id = b.bus_id " +
					";";
		
		String createTerminalView = 
				"CREATE VIEW TerminalView AS " +
					"SELECT a._id as terminal_id, a.name, city_id, b.name as city_name, company_id, c.name as company_name, " +
						"phone, purchase, getin, getoff, address, misc, latlng " +
					"FROM Terminal a " +
					"INNER JOIN City b " +
					"ON a.city_id = b._id " +
					"INNER JOIN Company c " +
					"ON a.company_id = c._id " +
					";";
		
		try{
			db.beginTransaction();
			
			// Create Table
			db.execSQL(createVersionTableSql);
			db.execSQL(initVersionTableSql);
			
			db.execSQL(createCityTableSql);
			db.execSQL(createCompanyTableSql);
			db.execSQL(createTerminalTableSql);
			db.execSQL(createTypeTableSql);
			db.execSQL(createLineTableSql);
			db.execSQL(createBusTableSql);
			db.execSQL(createTimeTableSql);
			
			db.execSQL(createCityRouteTableSql);
			
			// Create View
			
			db.execSQL(createLineView);
			db.execSQL(createDepartureListView);
			
			db.execSQL(createBusView);
			db.execSQL(createTimeView);
			db.execSQL(createTerminalView);
			
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
		
		db.execSQL("DROP TABLE IF EXISTS Version");
		
		db.execSQL("DROP TABLE IF EXISTS City");
		db.execSQL("DROP TABLE IF EXISTS Company");
		db.execSQL("DROP TABLE IF EXISTS Terminal");
		db.execSQL("DROP TABLE IF EXISTS Type");
		db.execSQL("DROP TABLE IF EXISTS Line");
		db.execSQL("DROP TABLE IF EXISTS Bus");
		db.execSQL("DROP TABLE IF EXISTS Time");
		
		db.execSQL("DROP TABLE IF EXISTS CityRoute");
		
		db.execSQL("DROP VIEW IF EXISTS LineView");
		db.execSQL("DROP VIEW IF EXISTS DepartureListView");
		db.execSQL("DROP VIEW IF EXISTS BusView");
		db.execSQL("DROP VIEW IF EXISTS TimeView");
		db.execSQL("DROP VIEW IF EXISTS TerminalView");
		
		onCreate(db);
	}
	
	@Override
	public void onOpen(SQLiteDatabase db){
		super.onOpen(db);
		//Log.i("DebugPrint","DB Open");
		// TODO 버전체크를 업데이트를 여기에다가 
		
	}

}
