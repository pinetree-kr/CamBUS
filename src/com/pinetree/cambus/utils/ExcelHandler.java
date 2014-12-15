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
			handler.initTable();
			
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
						colEnd = sheet.getRow(1).getLastCellNum();
						
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
									city.setName(cell.getStringCellValue().trim());
									break;
								case 2:
									city.setPref(cell.getStringCellValue().trim().equals("High")?true:false);
									break;
								case 3:
									city.setIndex((int)cell.getNumericCellValue());
									break;
								}
							}
							city.setId((int)handler.insertCity(city));
							//Log.i("DebugPrint", "city:"+city.getId());
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
						colEnd = sheet.getRow(1).getLastCellNum();
						
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
									company.setName(cell.getStringCellValue().trim());
									break;
								}
							}
							company.setId((int)handler.insertCompany(company));
							//Log.i("DebugPrint", "company:"+company.getId());
							company_list.add(company);
						}
					}
					
					/*
					 * Type Table
					 */
					ArrayList<Type> type_list = new ArrayList<Type>();
					sheet = workbook.getSheet("TypeData");
					if(sheet != null){
						rowEnd = sheet.getLastRowNum();
						colEnd = sheet.getRow(1).getLastCellNum();
						
						Type type;
						for(int i = rowStart; i<=rowEnd; i++){
							row = sheet.getRow(i);
							
							if(row == null)
								continue;
							
							type = new Type();
							for(int col = colStart; col<=colEnd; col++){
								cell = row.getCell(col);
								if(cell == null){
									continue;
								}
								
								switch(col){
								case 1:
									type.setName(cell.getStringCellValue().trim());
									break;
								}
							}
							type.setId((int)handler.insertType(type));
							//Log.i("DebugPrint", "type:"+type.getId());
							type_list.add(type);
						}
					}
					/*
					 * City Route Table
					 */
					ArrayList<CityRoute> route_list = new ArrayList<CityRoute>();
					sheet = workbook.getSheet("CityRoute");
					if(sheet != null){
						rowEnd = sheet.getLastRowNum();
						colEnd = sheet.getRow(1).getLastCellNum();
						
						CityRoute route;
						for(int i = rowStart; i<=rowEnd; i++){
							row = sheet.getRow(i);
							
							if(row == null)
								continue;
							route = new CityRoute();
							for(int col = colStart; col<=colEnd; col++){
								cell = row.getCell(col);
								if(cell == null){
									continue;
								}
								
								switch(col){
								case 0:
									route.setCityName(cell.getStringCellValue().trim());
									for(City object : city_list){
										if(object.getName().equals(route.getCityName())){
											route.setCityId(object.getId());
											break;
										}
									}
									break;
								case 1:
									route.setLineNo((int)cell.getNumericCellValue());
									break;
								case 2:
									route.setLineOrder((int)cell.getNumericCellValue());
									break;
								case 3:
									route.setName("eng",cell.getStringCellValue().trim());
									break;
								case 4:
									route.setName("khm",cell.getStringCellValue().trim());
									break;
								case 5:
									route.setName("kor",cell.getStringCellValue().trim());
									break;
								}
							}
							handler.insertRoute(route);
							
							//route.setId((int)handler.insertTerminal(terminal));
							//terminal_list.add(terminal);
						}
					}
					/*
					 * Terminal Table
					 */
					ArrayList<Terminal> terminal_list = new ArrayList<Terminal>();
					sheet = workbook.getSheet("TerminalData");
					if(sheet != null){
						rowEnd = sheet.getLastRowNum();
						colEnd = sheet.getRow(1).getLastCellNum();
						
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
								//String[] misc = new String[2];
								switch(col){
								case 1:
									terminal.setCityName(cell.getStringCellValue().trim());
									for(City object : city_list){
										if(object.getName().equals(terminal.getCityName())){
											terminal.setCityId(object.getId());
											break;
										}
									}
									break;
								case 2:
									terminal.setCompanyName(cell.getStringCellValue().trim());
									for(Company object : company_list){
										if(object.getName().equals(terminal.getCompanyName())){
											terminal.setCompanyId(object.getId());
											break;
										}
									}
									break;
								case 3:
									terminal.setName(cell.getStringCellValue().trim());
									break;
								case 4:
									terminal.setPhone(cell.getStringCellValue().trim());
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
									//terminal.setLink(cell.getStringCellValue().trim());
									break;
								case 9:
									terminal.setAddress(cell.getStringCellValue().trim());
									break;
								case 10:
									//terminal.setMiscEn(cell.getStringCellValue().trim());
									//misc[0] = cell.getStringCellValue().trim();
									terminal.setMisc("eng", cell.getStringCellValue().trim());
									break;
								case 11:
									//terminal.setMiscKo(cell.getStringCellValue().trim());
									//misc[1] = cell.getStringCellValue().trim();
									terminal.setMisc("kor", cell.getStringCellValue().trim());
									break;
								case 12:
									terminal.setLatLng(cell.getStringCellValue().trim());
									break;
								}
							}
							terminal.setId((int)handler.insertTerminal(terminal));
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
						colEnd = sheet.getRow(1).getLastCellNum();
						
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
										if(object.getName().equals(line.getDeptName())){
											line.setDeptId(object.getId());
											break;
										}
									}
									break;
								case 2:
									line.setDestName(cell.getStringCellValue().trim());
									for(City object : city_list){
										if(object.getName().equals(line.getDestName())){
											line.setDestId(object.getId());
											break;
										}
									}
									break;
								case 3:
									line.setDistance((int) cell.getNumericCellValue());
									break;
								}
							}
							line.setLineId((int)handler.insertLine(line));
							line_list.add(line);
						}
					}
					/*
					 * Bus Table
					 */
					ArrayList<Bus> bus_list = new ArrayList<Bus>();
					sheet = workbook.getSheet("BusData");
					if(sheet != null){
						rowEnd = sheet.getLastRowNum();
						//Log.i("DebugPrint","linebus-row:"+rowEnd);
						colEnd = sheet.getRow(1).getLastCellNum();
						//Log.i("DebugPrint","linebus-end:"+colEnd);
						
						Bus bus;
						for(int i = rowStart; i<=rowEnd; i++){
							row = sheet.getRow(i);
							
							if(row == null)
								continue;
							
							bus = new Bus();
							for(int col = colStart; col<=colEnd; col++){
								cell = row.getCell(col);
								if(cell == null){
									continue;
								}
								
								switch(col){
								case 1:
									bus.setDeptName(cell.getStringCellValue().trim());
									//Log.i("DebugPrint","deptname:"+bus.getDeptName());
									break;
								case 2:
									bus.setDestName(cell.getStringCellValue().trim());
									//Log.i("DebugPrint","destname:"+bus.getDestName());
									for(Line object : line_list){
										if(object.getDeptName().equals(bus.getDeptName())
												&& object.getDestName().equals(bus.getDestName())){
											bus.setLineId(object.getLineId());
											//Log.i("DebugPrint","lineid:"+bus.getLineId());
											break;
										}
									}
									break;
								case 3:
									bus.setCompanyName(cell.getStringCellValue().trim());
									//Log.i("DebugPrint","company:"+bus.getCompanyName());
									for(Company object : company_list){
										if(object.getName().equals(bus.getCompanyName())){
											bus.setCompanyId(object.getId());
											//Log.i("DebugPrint","companyId:"+bus.getCompanyId());
											break;
										}
									}
									break;
								case 4:
									bus.setTypeName(cell.getStringCellValue().trim());
									for(Type object : type_list){
										if(object.getName().equals(bus.getTypeName())){
											bus.setTypeId(object.getId());
											break;
										}
									}
									break;
								case 5:
									bus.setMidName(cell.getStringCellValue().trim());
									for(City object : city_list){
										if(object.getName().equals(bus.getMidName())){
											bus.setMidId(object.getId());
											break;
										}
									}
									break;
								case 6:
									bus.setDuration(cell.getNumericCellValue());
									break;
								case 7:
									bus.setNative(cell.getNumericCellValue());
									break;
								case 8:
									bus.setForeign(cell.getNumericCellValue());
									break;
								case 9:
									bus.setVisa(cell.getNumericCellValue());
									break;
								case 10:
									bus.setAbroad(cell.getStringCellValue().trim().equals("Abroad")?true:false);
									break;
								}
							}
							bus.setBusId((int)handler.insertBus(bus));
							//Log.i("DebugPrint", "bus:"+bus.getBusId());
							bus_list.add(bus);
						}
					}
					
					/*
					 * Time Table
					 */
					ArrayList<Time> time_list = new ArrayList<Time>();
					sheet = workbook.getSheet("TimeData");
					if(sheet != null){
						rowEnd = sheet.getLastRowNum();
						//Log.i("DebugPrint","linebustime-row:"+rowEnd);
						colEnd = sheet.getRow(1).getLastCellNum();
						//Log.i("DebugPrint","linebustime-end:"+colEnd);
						
						Time time;
						for(int i = rowStart; i<=rowEnd; i++){
							row = sheet.getRow(i);
							
							if(row == null)
								continue;
							
							time = new Time();
							for(int col = colStart; col<=colEnd; col++){
								cell = row.getCell(col);
								if(cell == null){
									continue;
								}
								
								switch(col){
								case 1:
									time.setDeptName(cell.getStringCellValue().trim());
									break;
								case 2:
									time.setDestName(cell.getStringCellValue().trim());
									break;
								case 3:
									time.setCompanyName(cell.getStringCellValue().trim());
									break;
								case 4:
									time.setTypeName(cell.getStringCellValue().trim());
									for(Bus object : bus_list){
										if(object.getDeptName().equals(time.getDeptName())
											&& object.getDestName().equals(time.getDestName())
											&& object.getCompanyName().equals(time.getCompanyName())
											&& object.getTypeName().equals(time.getTypeName())){
											time.setBusId(object.getBusId());
											break;
										}
									}
									break;
									/*/
								case 5:
									time.setMidName(cell.getStringCellValue().trim());
									for(City object : city_list){
										if(object.getName().equals(time.getMidName())){
											time.setMidId(object.getId());
											break;
										}
									}
									break;
									/**/
								case 5:
									time.setDeptTime(cell.getStringCellValue().trim());
									break;
								case 6:
									time.setArrivalTime(cell.getStringCellValue().trim());
									break;
								}
							}
							time.setTimeId((int)handler.insertTime(time));
							//Log.i("DebugPrint", "time:"+time.getTimeId());
							time_list.add(time);
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
}
