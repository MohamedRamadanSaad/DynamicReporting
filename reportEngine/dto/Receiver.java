package com.giza.adaay.performance.reportEngine.dto;

import java.util.HashMap;
import java.util.Map;

public class Receiver {
	private int id;
	private Map<String,Object> params ;
	private Map<String,Object> parameterString;
	private int type;
	private String printedBy;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	public Map<String, Object> getParameterString() {
		return parameterString;
	}
	public void setParameterString(Map<String, Object> parameterString) {
		this.parameterString = parameterString;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPrintedBy() {
		return printedBy;
	}
	public void setPrintedBy(String printedBy) {
		this.printedBy = printedBy;
	}
	
}
