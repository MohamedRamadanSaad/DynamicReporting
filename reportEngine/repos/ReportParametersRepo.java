package com.giza.adaay.performance.reportEngine.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.giza.adaay.performance.reportEngine.entities.Report;
import com.giza.adaay.performance.reportEngine.entities.ReportParameter;

public interface ReportParametersRepo extends JpaRepository<ReportParameter, Integer> ,CrudRepository<ReportParameter, Integer>, JpaSpecificationExecutor<ReportParameter> {

	@Query(nativeQuery=true,value ="select LABEL_AR,LABEL_EN ,COMPENENT,SELECT_STAT from REPORTS_PARAMETERS where REPORT_ID = :reportID order by sort asc")
	List<ReportParameter> findByReportIDOrderByOrder(int reportID);
	
	List<ReportParameter> findByReportOrderByOrder(Report report);
}
