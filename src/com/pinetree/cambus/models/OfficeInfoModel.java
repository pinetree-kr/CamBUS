package com.pinetree.cambus.models;


public class OfficeInfoModel extends Model {
	private static final long serialVersionUID = 911L;
	
	protected int office_no = -1;
	protected String office = "";
	protected int company_no = -1;
	protected String company = "";
	
	protected String phone_no = "";
	
	protected boolean purchase = false;
	protected boolean get_in = false;
	protected boolean get_out = false;
	
	protected String link = "";
	protected String address = "";
	
	protected String misc_en = "";
	protected String misc_ko = "";
	
	public void setOfficeNo(int no){
		this.office_no = no;
	}
	public int getOfficeNo(){
		return office_no;
	}
	public void setOffice(String office){
		this.office = office;
	}
	public String getOffice(){
		return office;
	}
	public void setCompanyNo(int no){
		this.company_no = no;
	}
	public void setCompany(String company){
		this.company = company;
	}
	public int getCompanyNo(){
		return company_no;
	}
	public String getCompany(){
		return company;
	}
	
	public void setPhoneNo(String phone_no){
		this.phone_no = phone_no;
	}
	public String getPhoneNo(){
		return phone_no;
	}
	public String[] getPhoneNoList(){
		String[] list = phone_no.split("/");
		for(int i=0; i<list.length; i++){
			list[i] = list[i].trim();
		}
		return list;
	}
	
	public void setPurchase(boolean purchase){
		this.purchase = purchase;
	}
	public boolean isPurchase(){
		return purchase;
	}
	public void setGetIn(boolean get_in){
		this.get_in = get_in;
	}
	public boolean isGetIn(){
		return get_in;
	}
	public void setGetOut(boolean get_out){
		this.get_out = get_out;
	}
	public boolean isGetOut(){
		return get_out;
	}
	public void setLink(String link){
		this.link = link;
	}
	public String getLink(){
		return link;
	}
	public void setAddress(String address){
		this.address = address;
	}
	public String getAddress(){
		return address;
	}
	
	public void setMiscEn(String misc_en){
		this.misc_en = misc_en;
	}
	public String getMiscEn(){
		return misc_en;
	}
	
	public void setMiscKo(String misc_ko){
		this.misc_ko = misc_ko;
	}
	public String getMiscKo(){
		return misc_ko;
	}
}
