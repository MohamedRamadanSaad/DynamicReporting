package com.giza.adaay.performance.reportEngine.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.giza.adaay.performance.utilities.BooleanToYNStringConverter;

@Entity
@Table(name = "REPORTS")
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "REPORT_ID")
	private int id;

	@Column(name = "REPORT_NAME", nullable = false)
	private String name;

	@Column(name = "DISPLAY_NAME_AR", nullable = false)
	private String displayNameAR;

	@Column(name = "DISPLAY_NAME_EN", nullable = false)
	private String displayNameEN;

	@Column(name = "PATH", nullable = false)
	private String directory;

	@ManyToOne
	@JoinColumn(name = "REPORTS_CATEGORY_ID", nullable = false)
	private ReportCategory categoryID;

	@Convert(converter = BooleanToYNStringConverter.class)
	@Column(name = "IS_OPERATIONAL")
	private Boolean isOperational;

	@Column(name = "REPORT_DESC")
	private String description;

	@OneToMany(mappedBy = "report")
	@OrderBy("order")
	private List<ReportParameter> parameters = new ArrayList<ReportParameter>();

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

	public String getDisplayNameAR() {
		return displayNameAR;
	}

	public void setDisplayNameAR(String displayNameAR) {
		this.displayNameAR = displayNameAR;
	}

	public String getDisplayNameEN() {
		return displayNameEN;
	}

	public void setDisplayNameEN(String displayNameEN) {
		this.displayNameEN = displayNameEN;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public ReportCategory getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(ReportCategory categoryID) {
		this.categoryID = categoryID;
	}

	public Boolean getIsOperational() {
		return isOperational;
	}

	public void setIsOperational(Boolean isOperational) {
		this.isOperational = isOperational;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ReportParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<ReportParameter> parameters) {
		this.parameters = parameters;
	}
	

}
