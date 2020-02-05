package com.giza.adaay.performance.reportEngine.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "REPORTS_CATEGORIES")
public class ReportCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="REPORTS_CATEGORY_ID")
	private int id;
	
	@Column(name= "DISPLAY_NAME_EN")
	private String displayNameEN;
	
	@Column(name= "REPORTS_CATEGORY_NAME")   
	private String reportsCategoryNname;
	
	@Column(name= "DISPLAY_NAME_AR")   
	private String displayNameAR;
	
	@OneToOne
	@JoinColumn(name="APPLICATION_ID", unique=true, nullable=false)
	private STCApplication stcApplication;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDisplayNameEN() {
		return displayNameEN;
	}

	public void setDisplayNameEN(String displayNameEN) {
		this.displayNameEN = displayNameEN;
	}

	public String getReportsCategoryNname() {
		return reportsCategoryNname;
	}

	public void setReportsCategoryNname(String reportsCategoryNname) {
		this.reportsCategoryNname = reportsCategoryNname;
	}

	public String getDisplayNameAR() {
		return displayNameAR;
	}

	public void setDisplayNameAR(String displayNameAR) {
		this.displayNameAR = displayNameAR;
	}

	public STCApplication getStcApplication() {
		return stcApplication;
	}

	public void setStcApplication(STCApplication stcApplication) {
		this.stcApplication = stcApplication;
	}

}
