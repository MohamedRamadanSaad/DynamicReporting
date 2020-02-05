package com.giza.adaay.performance.reportEngine.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "REPORTS_APPLICATIONS")
public class STCApplication {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="APPLICATION_ID")
	private int id;
	
	@Column(name = "APPLICATION_NAME")
	private String name;
	
	@Column(name = "APLICATION_DESC")
	private String description;
	
	@Column(name = "DISPLAY_NAME_AR")
	private String displayNameAR;
	
	@Column (name = "DISPLAY_NAME_EN")
	private String displayNameEN;
	
	@OneToOne(mappedBy="stcApplication")
	private ReportCategory category;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
}
