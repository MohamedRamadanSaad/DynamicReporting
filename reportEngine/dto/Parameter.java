package com.giza.adaay.performance.reportEngine.dto;

import java.util.ArrayList;
import java.util.List;

public class Parameter {

	private String name;
	private String type;
	private List<ParameterValue> values;
	private boolean isMandatory;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<ParameterValue> getValues() {
		if (values==null)
			values = new ArrayList<ParameterValue>();
		return values;
	}
	public void setValues(List<ParameterValue> values) {
		this.values = values;
	}
	public boolean isMandatory() {
		return isMandatory;
	}
	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
	
}
