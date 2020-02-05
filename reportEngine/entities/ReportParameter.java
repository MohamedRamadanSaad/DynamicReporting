package com.giza.adaay.performance.reportEngine.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.giza.adaay.performance.utilities.BooleanToYNStringConverter;

@Entity
@Table(name = "REPORTS_PARAMETERS")
public class ReportParameter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PARAMETER_ID")
	private int id;
	
	@Column(name="NAME" , nullable = false)
	private String name;
	
	@Column(name="LABEL_AR" , nullable = false)
	private String labelAR;
	
	@Column(name="LABEL_EN")
	private String labelEN;
	
	@Column(name="TYPE")
	private String type;
	
	@Column(name="COMPENENT")
	private String compenent;
	
	@Column(name="SELECT_STAT")
	private String selectStatment;
	
	@Column(name="SORT")
	private Integer order;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORT_ID")
	private Report report;
	
	// if this parameter depend on another parameters
	@Column(name="REF_PARAMETER_ID")
	private Integer referenceParameter;
	
	@Column(name="IS_REQUIRED")
	@Convert(converter = BooleanToYNStringConverter.class)
	private Boolean isRequiered;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabelAR() {
		return labelAR;
	}

	public void setLabelAR(String labelAR) {
		this.labelAR = labelAR;
	}

	public String getLabelEN() {
		return labelEN;
	}

	public void setLabelEN(String labelEN) {
		this.labelEN = labelEN;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCompenent() {
		return compenent;
	}

	public void setCompenent(String compenent) {
		this.compenent = compenent;
	}

	public String getSelectStatment() {
		return selectStatment;
	}

	public void setSelectStatment(String selectStatment) {
		this.selectStatment = selectStatment;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public Integer getReferenceParameter() {
		return referenceParameter;
	}

	public void setReferenceParameter(Integer referenceParameter) {
		this.referenceParameter = referenceParameter;
	}

	public Boolean getIsRequiered() {
		return isRequiered;
	}

	public void setIsRequiered(Boolean isRequiered) {
		this.isRequiered = isRequiered;
	}
	
}
