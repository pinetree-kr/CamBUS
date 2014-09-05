package com.pinetree.cambus.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	protected final static String DB_NAME = "CamBUS_DB";
	protected final static int DB_VERSION = 8;
	
	public DBHelper(Context context){
		this(context, DB_NAME, null, DB_VERSION);
	}
	
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
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
		
		db.execSQL(createCamBUSTimeTableSql);
		db.execSQL(createCamBUSCityTableSql);
		db.execSQL(createCamBUSCompanyTableSql);
		db.execSQL(createCamBUSTypeTableSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS CamBUS_TimeTable");
		db.execSQL("DROP TABLE IF EXISTS CamBUS_CityTable");
		db.execSQL("DROP TABLE IF EXISTS CamBUS_CompanyTable");
		db.execSQL("DROP TABLE IF EXISTS CamBUS_TypeTable");
		onCreate(db);
	}

}
