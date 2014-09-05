package com.pinetree.cambus.models;

public class TypeModel extends Model {
	protected String type_name = "";
	protected int type_no = -1;
	
	public void setTypeName(String typeName){
		type_name = typeName;
	}
	public void setTypeNo(int no){
		type_no = no;
	}
	public String getTypeName(){
		return type_name;
	}
	public int getTypeNo(){
		return type_no;
	}
}
