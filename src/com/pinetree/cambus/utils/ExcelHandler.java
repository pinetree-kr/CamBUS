package com.pinetree.cambus.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.pinetree.cambus.models.BusInfoModel;
import com.pinetree.cambus.models.OfficeInfoModel;

import android.content.Context;
import android.util.Log;

public class ExcelHandler {
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
					HSSFSheet sheet = workbook.getSheet("버스Data");
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
						for(int i = rowStartIndex; i<rowEndIndex; i++){
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
									//object.setPreference(cell.getStringCellValue().trim());
									break;
								case 1:
									object.setDeparture(cell.getStringCellValue().trim());
									break;
								case 2:
									object.setDestination(cell.getStringCellValue().trim());
									break;
								case 3:
									object.setCompany(cell.getStringCellValue().trim());
									break;
								case 4:
									object.setMiddleCity(cell.getStringCellValue().trim());
									break;
								case 5:
									object.setDepartureTime(cell.getDateCellValue());
									break;
								case 6:
									object.setArrivalTime(cell.getDateCellValue());
									break;
								case 7:
									object.setDurationTime(cell.getNumericCellValue());
									break;
								case 8:
									//object.setRemarks(cell.getStringCellValue().trim());
									break;
								case 9:
									//object.setQuality(cell.getStringCellValue().trim());
									break;
								case 10:
									//object.setOperation(cell.getStringCellValue().trim());
									break;
								case 11:
									object.setType(cell.getStringCellValue().trim());
									break;
								case 12:
									object.setNativePrice(cell.getNumericCellValue());
									break;
								case 13:
									object.setForeignerPrice(cell.getNumericCellValue());
									break;
								case 14:
									object.setVisa(cell.getNumericCellValue());
									break;
								case 15:
									object.setDN(cell.getStringCellValue().trim());
									break;
								case 16:
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
		/*/ catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/**/
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
					HSSFSheet sheet = workbook.getSheet("회사Data");
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
						for(int i = rowStartIndex; i<rowEndIndex; i++){
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
								case 0:
									object.setOffice(cell.getStringCellValue().trim());
									break;
								case 1:
									object.setPhoneNo(cell.getStringCellValue().trim());
									break;
								case 2:
									object.setPurchase(cell.getStringCellValue().trim().equals("O")?true:false);
									break;
								case 3:
									object.setGetIn(cell.getStringCellValue().trim().equals("O")?true:false);
									break;
								case 4:
									object.setGetOut(cell.getStringCellValue().trim().equals("O")?true:false);
									break;
								case 5:
									object.setLink(cell.getStringCellValue().trim());
									break;
								case 6:
									object.setAddress(cell.getStringCellValue().trim());
									break;
								case 7:
									object.setMiscEn(cell.getStringCellValue().trim());
									break;
								case 8:
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
		/*/ catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/**/
		return objects;
	}
}
