package com.adaay.reporting.reportingmodule.dynamicReport.example.dto;

import com.adaay.reporting.reportingmodule.dto.Pagination;

import java.util.ArrayList;
import java.util.List;

public class PublicVacationResultSetDTO {
    List<PublicVacationDTO> data = new ArrayList<PublicVacationDTO>();
    Pagination pagination = new Pagination();

    public List<PublicVacationDTO> getData() {
        return data;
    }

    public void setData(List<PublicVacationDTO> data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
