package com.giza.adaay.performance.reportEngine.dto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class JasperReportReadyDTO {

	private int id;
//	private Map<String,String> params = new LinkedHashMap<String,String>();
	private List<Parameter> params;
	private int type;
	private String printedBy;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
//	public Map<String, String> getParams() {
//		return params;
//	}
//	public void setParams(Map<String, String> params) {
//		this.params = params;
//	}
	
	public int getType() {
		return type;
	}
	public List<Parameter> getParams() {
		if(params==null)
			params = new ArrayList<Parameter>();
		return params;
	}
	public void setParams(List<Parameter> params) {
		this.params = params;
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
