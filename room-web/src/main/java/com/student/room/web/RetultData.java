package com.student.room.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RetultData {
	public RetultData(){
		//System.out.println("调用了A的无参构造函数");  
	     this.setResult("ok");
	     this.setCode(1);
	     this.setVer("1.0.0");
	   }  
	private int code;
	private String result;		
	private String ver;	
	private Map data;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}	
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public Map getData() {
		return data;
	}
	public void setData(Map data) {
		this.data = data;
	}
	
	public void addData(String key,Object o) {		
		if (this.data==null)
		{
			this.data=new HashMap();
		}
		this.data.put(key, o);
		
	}
	
}
