package com.pinetree.cambus.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.pinetree.cambus.models.DBModel.*;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;

public class ExcelHandler {
	// 엑셀로 부터 불러온 데이터들을 SQLite로 전환하는 작업
	public static void Data2SQLite(Context context, DBHandler handler, String fileName) throws IOException{
		try{
			// begin transaction
			handler.beginTransaction();

			// FileIO
			InputStream is = new BufferedInputStream(context.getResources().getAssets().open(fileName));
			
			if(fileName.endsWith(".xlsx")){
				throw new IOException("unabled file extension");
			}else if(fileName.endsWith(".xls")){
				
				// load workbook
				HSSFWorkbook workbook = new HSSFWorkbook(is);
				HSSFSheet sheet;
				if(workbook != null){
					int rowStart = 1, rowEnd;
					int colStart = 0, colEnd;
					
					HSSFRow row;
					HSSFCell cell;
					
					/*
					 * City Table
					 */
					ArrayList<City> city_list = new ArrayList<City>();
					sheet = workbook.getSheet("CityData");
					if(sheet != null){
						rowEnd = sheet.getLastRowNum();
						Log.i("DebugPrint","city-row:"+rowEnd);
						colEnd = sheet.getRow(1).getLastCellNum();
						Log.i("DebugPrint","city-end:"+colEnd);
						
						City city;
						for(int i = rowStart; i<=rowEnd; i++){
							row = sheet.getRow(i);
							
							if(row == null)
								continue;
							
							city = new City();
							for(int col = colStart; col<=colEnd; col++){
								cell = row.getCell(col);
								if(cell == null){
									continue;
								}
								
								switch(col){
								case 1:
									city.setCityName(cell.getStringCellValue().trim());
									break;
								case 2:
									city.setHigh(cell.getStringCellValue().trim().equals("High")?true:false);
									break;
								case 3:
									city.setOrder((int)cell.getNumericCellValue());
									break;
								}
							}
							city.setCityNo((int)handler.insertCity(city));
							city_list.add(city);
						}
					}
					
					/*
					 * Company Table
					 */
					ArrayList<Company> company_list = new ArrayList<Company>();
					sheet = workbook.getSheet("CompanyData");
					if(sheet != null){
						rowEnd = sheet.getLastRowNum();
						Log.i("DebugPrint","company-row:"+rowEnd);
						colEnd = sheet.getRow(1).getLastCellNum();
						Log.i("DebugPrint","company-end:"+colEnd);
						
						Company company;
						for(int i = rowStart; i<=rowEnd; i++){
							row = sheet.getRow(i);
							
							if(row == null)
								continue;
							
							company = new Company();
							for(int col = colStart; col<=colEnd; col++){
								cell = row.getCell(col);
								if(cell == null){
									continue;
								}
								
								switch(col){
								case 1:
									company.setCompanyName(cell.getStringCellValue().trim());
									break;
								}
							}
							company.setCompanyNo((int)handler.insertCompany(company));
							company_list.add(company);
						}
					}
					
					/*
					 * Type Table
					 */
					ArrayList<BusType> type_list = new ArrayList<BusType>();
					sheet = workbook.getSheet("BusTypeData");
					if(sheet != null){
						rowEnd = sheet.getLastRowNum();
						Log.i("DebugPrint","type-row:"+rowEnd);
						colEnd = sheet.getRow(1).getLastCellNum();
						Log.i("DebugPrint","type-end:"+colEnd);
						
						BusType type;
						for(int i = rowStart; i<=rowEnd; i++){
							row = sheet.getRow(i);
							
							if(row == null)
								continue;
							
							type = new BusType();
							for(int col = colStart; col<=colEnd; col++){
								cell = row.getCell(col);
								if(cell == null){
									continue;
								}
								
								switch(col){
								case 1:
									type.setTypeName(cell.getStringCellValue().trim());
									break;
								}
							}
							type.setTypeNo((int)handler.insertBusType(type));
							type_list.add(type);
						}
					}
					
					/*
					 * Terminal Table
					 */
					ArrayList<Terminal> terminal_list = new ArrayList<Terminal>();
					sheet = workbook.getSheet("TerminalData");
					if(sheet != null){
						rowEnd = sheet.getLastRowNum();
						Log.i("DebugPrint","Terminal-row:"+rowEnd);
						colEnd = sheet.getRow(1).getLastCellNum();
						Log.i("DebugPrint","Terminal-end:"+colEnd);
						
						Terminal terminal;
						for(int i = rowStart; i<=rowEnd; i++){
							row = sheet.getRow(i);
							
							if(row == null)
								continue;
							
							terminal = new Terminal();
							for(int col = colStart; col<=colEnd; col++){
								cell = row.getCell(col);
								if(cell == null){
									continue;
								}
								
								switch(col){
								case 1:
									terminal.setCityName(cell.getStringCellValue().trim());
									for(City object : city_list){
										if(object.getCityName().equals(terminal.getCityName())){
											terminal.setCityNo(object.getCityNo());
											break;
										}
									}
									break;
								case 2:
									terminal.setCompanyName(cell.getStringCellValue().trim());
									for(Company object : company_list){
										if(object.getCompanyName().equals(terminal.getCompanyName())){
											terminal.setCompanyNo(object.getCompanyNo());
											break;
										}
									}
									break;
								case 3:
									terminal.setTerminalName(cell.getStringCellValue().trim());
									break;
								case 4:
									terminal.setPhoneNo(cell.getStringCellValue().trim());
									break;
								case 5:
									terminal.setPurchase(cell.getStringCellValue().trim().equals("O")?true:false);
									break;
								case 6:
									terminal.setGetIn(cell.getStringCellValue().trim().equals("O")?true:false);
									break;
								case 7:
									terminal.setGetOff(cell.getStringCellValue().trim().equals("O")?true:false);
									break;
								case 8:
									terminal.setLink(cell.getStringCellValue().trim());
									break;
								case 9:
									terminal.setAddress( cell.getStringCellValue().trim());
									break;
								case 10:
									terminal.setMiscEn(cell.getStringCellValue().trim());
									break;
								case 11:
									terminal.setMiscKo(cell.getStringCellValue().trim());
									break;
								case 12:
									terminal.setLatLng(cell.getStringCellValue().trim());
									break;
								}
							}
							terminal.setTerminalNo((int)handler.insertTerminal(terminal));
							terminal_list.add(terminal);
						}
					}
					
					/*
					 * Line Table
					 */
					ArrayList<Line> line_list = new ArrayList<Line>();
					sheet = workbook.getSheet("LineData");
					if(sheet != null){
						rowEnd = sheet.getLastRowNum();
						Log.i("DebugPrint","line-row:"+rowEnd);
						colEnd = sheet.getRow(1).getLastCellNum();
						Log.i("DebugPrint","line-end:"+colEnd);
						
						Line line;
						for(int i = rowStart; i<=rowEnd; i++){
							row = sheet.getRow(i);
							
							if(row == null)
								continue;
							
							line = new Line();
							for(int col = colStart; col<=colEnd; col++){
								cell = row.getCell(col);
								if(cell == null){
									continue;
								}
								
								switch(col){
								case 1:
									line.setDeptName(cell.getStringCellValue().trim());
									for(City object : city_list){
										if(object.getCityName().equals(line.getDeptName())){
											line.setDeptNo(object.getCityNo());
											break;
										}
									}
									break;
								case 2:
									line.setDestName(cell.getStringCellValue().trim());
									for(City object : city_list){
										if(object.getCityName().equals(line.getDestName())){
											line.setDestNo(object.getCityNo());
											break;
										}
									}
									break;
								case 3:
									line.setDistance((int) cell.getNumericCellValue());
									break;
								}
							}
							line.setLineNo((int)handler.insertLine(line));
							line_list.add(line);
						}
					}
					
					/*
					 * LineBus Table
					 */
					ArrayList<LineBus> linebus_list = new ArrayList<LineBus>();
					sheet = workbook.getSheet("LineBusData");
					if(sheet != null){
						rowEnd = sheet.getLastRowNum();
						Log.i("DebugPrint","linebus-row:"+rowEnd);
						colEnd = sheet.getRow(1).getLastCellNum();
						Log.i("DebugPrint","linebus-end:"+colEnd);
						
						LineBus linebus;
						for(int i = rowStart; i<=rowEnd; i++){
							row = sheet.getRow(i);
							
							if(row == null)
								continue;
							
							linebus = new LineBus();
							for(int col = colStart; col<=colEnd; col++){
								cell = row.getCell(col);
								if(cell == null){
									continue;
								}
								
								switch(col){
								case 1:
									linebus.setDeptName(cell.getStringCellValue().trim());
									break;
								case 2:
									linebus.setDestName(cell.getStringCellValue().trim());
									for(Line object : line_list){
										if(object.getDeptName().equals(linebus.getDeptName())
												&& object.getDestName().equals(linebus.getDestName())){
											linebus.setLineNo(object.getLineNo());
											break;
										}
									}
									break;
								case 3:
									linebus.setCompanyName(cell.getStringCellValue().trim());
									for(Company object : company_list){
										if(object.getCompanyName().equals(linebus.getCompanyName())){
											linebus.setCompanyNo(object.getCompanyNo());
											break;
										}
									}
									break;
								case 4:
									linebus.setTypeName(cell.getStringCellValue().trim());
									for(BusType object : type_list){
										if(object.getTypeName().equals(linebus.getTypeName())){
											linebus.setTypeNo(object.getTypeNo());
											break;
										}
									}
									break;
								case 5:
									linebus.setDurationTime(cell.getNumericCellValue());
									break;
								case 6:
									linebus.setNativePrice(cell.getNumericCellValue());
									break;
								case 7:
									linebus.setForeignerPrice(cell.getNumericCellValue());
									break;
								case 8:
									linebus.setVisa(cell.getNumericCellValue());
									break;
								case 9:
									linebus.setDN(cell.getStringCellValue().trim());
									break;
								}
							}
							linebus.setLineBusNo((int)handler.insertLineBus(linebus));
							linebus_list.add(linebus);
						}
					}
					
					/*
					 * LineBus Table
					 */
					ArrayList<LineBusTime> linebustime_list = new ArrayList<LineBusTime>();
					sheet = workbook.getSheet("LineBusTimeData");
					if(sheet != null){
						rowEnd = sheet.getLastRowNum();
						Log.i("DebugPrint","linebustime-row:"+rowEnd);
						colEnd = sheet.getRow(1).getLastCellNum();
						Log.i("DebugPrint","linebustime-end:"+colEnd);
						
						LineBusTime linebustime;
						for(int i = rowStart; i<=rowEnd; i++){
							row = sheet.getRow(i);
							
							if(row == null)
								continue;
							
							linebustime = new LineBusTime();
							for(int col = colStart; col<=colEnd; col++){
								cell = row.getCell(col);
								if(cell == null){
									continue;
								}
								
								switch(col){
								case 1:
									linebustime.setDeptName(cell.getStringCellValue().trim());
									break;
								case 2:
									linebustime.setDestName(cell.getStringCellValue().trim());
									break;
								case 3:
									linebustime.setCompanyName(cell.getStringCellValue().trim());
									break;
								case 4:
									linebustime.setTypeName(cell.getStringCellValue().trim());
									for(LineBus object : linebus_list){
										if(object.getDeptName().equals(linebustime.getDeptName())
											&& object.getDestName().equals(linebustime.getDestName())
											&& object.getCompanyName().equals(linebustime.getCompanyName())
											&& object.getTypeName().equals(linebustime.getTypeName())){
											linebustime.setLineBusNo(object.getLineBusNo());
											break;
										}
									}
									break;
								case 5:
									linebustime.setMiddleCity(cell.getStringCellValue().trim());
									for(City object : city_list){
										if(object.getCityName().equals(linebustime.getMiddleCity())){
											linebustime.setMidNo(object.getCityNo());
											break;
										}
									}
									break;
								case 6:
									linebustime.setDeptTime(cell.getStringCellValue().trim());
									break;
								case 7:
									linebustime.setArrivalTime(cell.getStringCellValue().trim());
									break;
								}
							}
							linebustime.setLineBusNo((int)handler.insertLineBusTime(linebustime));
							linebustime_list.add(linebustime);
						}
					}
				}
			}
			
			// set transaction successful
			handler.setTransactionSuccessful();
		}catch(SQLException e){
			Log.i("DebugPrint",e.getMessage());
		}finally{
			// end transaction
			handler.endTransaction();
		}
	}
	/*
	public static ArrayList<BusInfoModel> getBusFromXLS(Context context, String fileName){
		ArrayList<BusInfoModel> objects = new ArrayList<BusInfoModel>();
		try {
			InputStream is = new BufferedInputStream(context.getResources().getAssets().open(fileName));
			
			if(fileName.endsWith(".xlsx")){
				throw new IOException("unabled file extension");
			}else if(fileName.endsWith(".xls")){
				
				HSSFWorkbook workbook = new HSSFWorkbook(is);
				if(workbook != null){
					//HSSFSheet sheet = workbook.getSheetAt(0);
					HSSFSheet sheet = workbook.getSheet("버스Backup");
					if(sheet != null){
						int rowStartIndex = 1;
						int rowEndIndex = sheet.getLastRowNum();
						int colStartIndex = 0;
						int colEndIndex = sheet.getRow(1).getLastCellNum();
						
						HSSFRow row;
						HSSFCell cell;
						BusInfoModel object;

						Log.i("DebugPrint","bus-row:"+rowEndIndex);
						Log.i("DebugPrint","bus-col:"+colEndIndex);
						for(int i = rowStartIndex; i<=rowEndIndex; i++){
							row = sheet.getRow(i);
							
							if(row == null)
								continue;
							
							object = new BusInfoModel();
							
							for(int col = colStartIndex; col<colEndIndex; col++){
								cell = row.getCell(col);
								if(cell == null){
									//Log.i("DebugPrint",i+","+col);
									continue;
								}
								
								switch(col){
								case 0:
									object.setDeparture(cell.getStringCellValue().trim());
									break;
								case 1:
									object.setDestination(cell.getStringCellValue().trim());
									break;
								case 2:
									object.setCompany(cell.getStringCellValue().trim());
									break;
								case 3:
									object.setMiddleCity(cell.getStringCellValue().trim());
									break;
								case 4:
									object.setDepartureTime(cell.getDateCellValue());
									break;
								case 5:
									object.setArrivalTime(cell.getDateCellValue());
									break;
								case 6:
									object.setDurationTime(cell.getNumericCellValue());
									break;
								case 7:
									//object.setRemarks(cell.getStringCellValue().trim());
									break;
								case 8:
									//object.setQuality(cell.getStringCellValue().trim());
									break;
								case 9:
									//object.setOperation(cell.getStringCellValue().trim());
									break;
								case 10:
									object.setType(cell.getStringCellValue().trim());
									break;
								case 11:
									object.setNativePrice(cell.getNumericCellValue());
									break;
								case 12:
									object.setForeignerPrice(cell.getNumericCellValue());
									break;
								case 13:
									object.setVisa(cell.getNumericCellValue());
									break;
								case 14:
									object.setDN(cell.getStringCellValue().trim());
									break;
								case 15:
									if(cell.getDateCellValue()!=null)
										object.setLastUpdate(cell.getDateCellValue());
									break;
								}
							}
							objects.add(object);
						}
						
					}
				}
				workbook.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return objects;
	}
	
	public static ArrayList<OfficeInfoModel> getOfficeFromXLS(Context context, String fileName){
		ArrayList<OfficeInfoModel> objects = new ArrayList<OfficeInfoModel>();
		try {
			InputStream is = new BufferedInputStream(context.getResources().getAssets().open(fileName));
			
			if(fileName.endsWith(".xlsx")){
				throw new IOException("unabled file extension");
			}else if(fileName.endsWith(".xls")){
				
				HSSFWorkbook workbook = new HSSFWorkbook(is);
				if(workbook != null){
					//HSSFSheet sheet = workbook.getSheetAt(0);
					HSSFSheet sheet = workbook.getSheet("CityCompanyData");
					if(sheet != null){
						int rowStartIndex = 1;
						int rowEndIndex = sheet.getLastRowNum();
						int colStartIndex = 0;
						int colEndIndex = sheet.getRow(1).getLastCellNum();
						
						HSSFRow row;
						HSSFCell cell;
						OfficeInfoModel object;

						Log.i("DebugPrint","office-row:"+rowEndIndex);
						Log.i("DebugPrint","office-col:"+colEndIndex);
						for(int i = rowStartIndex; i<=rowEndIndex; i++){
							row = sheet.getRow(i);
							
							if(row == null)
								continue;
							
							object = new OfficeInfoModel();
							
							for(int col = colStartIndex; col<colEndIndex; col++){
								cell = row.getCell(col);
								if(cell == null){
									//Log.i("DebugPrint",i+","+col);
									continue;
								}
								
								switch(col){
								case 1:
									object.setCity(cell.getStringCellValue().trim());
									break;
								case 2:
									object.setCompany(cell.getStringCellValue().trim());
									break;
								case 3:
									object.setOffice(cell.getStringCellValue().trim());
									break;
								case 4:
									object.setPhoneNo(cell.getStringCellValue().trim());
									break;
								case 5:
									object.setPurchase(cell.getStringCellValue().trim().equals("O")?true:false);
									break;
								case 6:
									object.setGetIn(cell.getStringCellValue().trim().equals("O")?true:false);
									break;
								case 7:
									object.setGetOut(cell.getStringCellValue().trim().equals("O")?true:false);
									break;
								case 8:
									object.setLink(cell.getStringCellValue().trim());
									break;
								case 9:
									object.setAddress(cell.getStringCellValue().trim());
									break;
								case 10:
									object.setMiscEn(cell.getStringCellValue().trim());
									break;
								case 11:
									object.setMiscKo(cell.getStringCellValue().trim());
									break;
								}
							}
							objects.add(object);
						}
						
					}
				}
				workbook.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.i("DebugPrint","officeSize:"+objects.size());
		return objects;
	}
	*/
}
