package com.pinetree.cambus.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	protected final static String DB_NAME = "CamBUS_DB";
	protected final static int DB_VERSION = 2;
	
	public DBHelper(Context context){
		this(context, DB_NAME, null, DB_VERSION);
	}
	
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createCamBUSDeptListViewSql =
				"CREATE VIEW CamBUS_DepartureListView AS " +
				"SELECT DISTINCT city_no, city_name, preference " +
				"FROM CamBUS_CityTable a " +
				"INNER JOIN CamBUS_TimeTable b " +
				"ON a.city_no = b.dept_no " +
				"ORDER BY preference DESC, city_name ASC;";
		
		String createCamBUSDestListViewSql =
				"CREATE VIEW CamBUS_DestinationListView AS " +
				"SELECT DISTINCT city_no, city_name, preference " +
				"FROM CamBUS_CityTable a " +
				"INNER JOIN CamBUS_TimeTable b " +
				"ON a.city_no = b.dest_no " +
				"ORDER BY preference DESC, city_name ASC;";
		
		String createCamBUSBusListViewSql = 
				"CREATE VIEW CamBUS_BusListView " +
				"AS " +
				"SELECT a.*, c.city_name AS dept_name, d.city_name AS dest_name, mid_no, e.city_name AS mid_name, company_name, type_name, duration_time, native_price, foreigner_price, visa, dn, last_updated " +
				"FROM CamBUS_TimeTable a " +
				"INNER JOIN CamBUS_DeptDestTable b " +
				"ON a.dept_no = b.dept_no AND a.dest_no = b.dest_no AND a.company_no = b.company_no AND a.type_no = b.type_no " +
				"INNER JOIN CamBUS_CityTable c " +
				"ON a.dept_no = c.city_no " +
				"INNER JOIN CamBUS_CityTable d " +
				"ON a.dest_no = d.city_no " +
				"LEFT JOIN CamBUS_CityTable e " +
				"ON b.mid_no = e.city_no " +
				"INNER JOIN CamBUS_CompanyTable f " +
				"ON a.company_no = f.company_no " +
				"INNER JOIN CamBUS_TypeTable g " +
				"ON a.type_no = g.type_no " +
				"ORDER BY time_no ASC;";
		
		String createCamBUSCityTableSql =
				"CREATE TABLE CamBUS_CityTable (" +
				"city_no INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
				"city_name TEXT NOT NULL, " +
				"preference INTEGER NOT NULL);";

		String createCamBUSCompanyTableSql =
				"CREATE TABLE CamBUS_CompanyTable (" +
				"company_no INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
				"company_name TEXT NOT NULL);";
		
		String createCamBUSTypeTableSql =
				"CREATE TABLE CamBUS_TypeTable (" +
				"type_no INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
				"type_name TEXT NOT NULL);";
		/*/
		String createCamBUSOperationTableSql =
				"CREATE TABLE CamBUS_OperationTable (" +
						"op_no INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
						"op_name TEXT NOT NULL);";
		/**/
		String createCamBUSDeptDestTableSql = 
				"CREATE TABLE CamBUS_DeptDestTable (" +
						"dept_no INTEGER NOT NULL, " +
						"dest_no INTEGER NOT NULL, " +
						"company_no INTEGER NOT NULL, " +
						"type_no INTEGER NOT NULL, " +
						//"op_no INTEGER NULL, " +
						"mid_no INTEGER NULL, " +
						"duration_time REAL NOT NULL, " +
						//"remarks TEXT NULL, " +
						//"quality TEXT NULL, " +
						"native_price REAL NOT NULL, " +
						"foreigner_price REAL NOT NULL, " +
						"visa REAL NULL, " +
						"dn TEXT NULL, " +
						"last_updated TEXT NULL, " +
						"PRIMARY KEY(dept_no, dest_no, company_no, type_no));";
		
		String createCamBUSTimeTableSql = 
				"CREATE TABLE CamBUS_TimeTable (" +
						"time_no INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
						"dept_no INTEGER NOT NULL, " +
						"dest_no INTEGER NOT NULL, " +
						"company_no INTEGER NOT NULL, " +
						"type_no INTEGER NOT NULL, " +
						"departure_time TEXT NOT NULL, " +
						"arrival_time TEXT NULL" +
						");";
						//"PRIMARY KEY(time_no, dept_no, dest_no, company_no, type_no));";
		/*/
		String createCamBUSTimeTableSql = 
				"CREATE TABLE CamBUS_TimeTable (" +
				"bus_no INTEGER PRIMARY KEY NOT NULL, " +
				"preference TEXT NOT NULL, " +
				"departure_no INTEGER NOT NULL, " +
				"destination_no INTEGER NOT NULL, " +
				"middlecity_no INTEGER NULL, " +
				"company_no INTEGER NOT NULL, " +
				"departure_time TEXT NOT NULL, " +
				"arrival_time TEXT NULL, " +
				"duration_time REAL NOT NULL, " +
				"remarks TEXT NULL, " +
				"quality TEXT NULL, " +
				"operation TEXT NULL, " +
				"type_no INTEGER NOT NULL, " +
				//나중에 REAL로 변경예
				"native_price REAL NOT NULL, " +
				"foreigner_price REAL NOT NULL, " +
				"visa REAL NULL, " +
				
				"dn TEXT NULL, " +
				"last_updated TEXT NULL);";
		String createCamBUSCityTableSql =
				"CREATE TABLE CamBUS_CityTable (" +
				"city_no INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
				"city_name TEXT NOT NULL);";

		String createCamBUSCompanyTableSql =
				"CREATE TABLE CamBUS_CompanyTable (" +
				"company_no INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
				"company_name TEXT NOT NULL);";
		
		String createCamBUSTypeTableSql =
				"CREATE TABLE CamBUS_TypeTable (" +
				"type_no INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
				"type_name TEXT NOT NULL);";
		/**/
		//db.execSQL(createCamBUSOperationTableSql);
		db.execSQL(createCamBUSCityTableSql);
		db.execSQL(createCamBUSCompanyTableSql);
		db.execSQL(createCamBUSTypeTableSql);
		db.execSQL(createCamBUSDeptDestTableSql);
		db.execSQL(createCamBUSTimeTableSql);
		db.execSQL(createCamBUSBusListViewSql);
		db.execSQL(createCamBUSDeptListViewSql);
		db.execSQL(createCamBUSDestListViewSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS CamBUS_TimeTable");
		db.execSQL("DROP TABLE IF EXISTS CamBUS_CityTable");
		db.execSQL("DROP TABLE IF EXISTS CamBUS_CompanyTable");
		db.execSQL("DROP TABLE IF EXISTS CamBUS_DeptDestTable");
		db.execSQL("DROP TABLE IF EXISTS CamBUS_TypeTable");
		db.execSQL("DROP VIEW IF EXISTS CamBUS_BusListView");
		db.execSQL("DROP VIEW IF EXISTS CamBUS_DepartureListView");
		db.execSQL("DROP VIEW IF EXISTS CamBUS_DestinationListView");
		onCreate(db);
	}

}
