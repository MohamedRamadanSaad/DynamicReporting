package com.adaay.reporting.reportingmodule.dynamicReport.example.dto;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import  com.adaay.reporting.reportingmodule.dynamicReport.customAnnotation.*;

public class PublicVacationDTO {
	@CrossTableID
    private int id;
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@ColumnName(labelEN = "1 Start Date",labelAR = "ا")
    private Date startDate;
	
	
	@ColumnName(labelEN = "2 To Date",labelAR = "ت")
    private Date toDate;

	@ColumnName(labelEN = "3 Vacation Description",labelAR = "غ")
    private String vacationDescription;
	
	@ColumnName(labelEN = "4 Created By",labelAR = "ف")
    private String createdBy;
	
	@ColumnName(labelEN = "5 Created On",labelAR = "ق")
    private Timestamp createdOn;
	
	@ColumnName(labelEN = "6 Modified By",labelAR = "ث")
    private String modifiedBy;
	
	@ColumnName(labelEN = "7 Modified On",labelAR = "ص")
    private Timestamp modifiedOn;
	
	@ColumnName(labelEN = "8 Version Number",labelAR = "ر")
    private String versionNumber;
	
	@ColumnName(labelEN = "9 Is Posted",labelAR = "ي")
    private String isPosted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getVacationDescription() {
        return vacationDescription;
    }

    public void setVacationDescription(String vacationDescription) {
        this.vacationDescription = vacationDescription;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Timestamp getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Timestamp modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getIsPosted() {
        return isPosted;
    }

    public void setIsPosted(String isPosted) {
        this.isPosted = isPosted;
    }
}
