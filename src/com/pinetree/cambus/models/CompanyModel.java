package com.pinetree.cambus.models;

public class CompanyModel extends Model {
	protected String company_name = "";
	protected int company_no = -1;
	
	public void setCompanyName(String companyName){
		company_name = companyName;
	}
	public void setCompanyNo(int no){
		company_no = no;
	}
	public String getCompanyName(){
		return company_name;
	}
	public int getCompanyNo(){
		return company_no;
	}
}
