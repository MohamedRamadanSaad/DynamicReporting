package com.giza.adaay.performance.reportEngine.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.giza.adaay.performance.reportEngine.entities.ReportCategory;

public interface ReportCategoriesRepo extends JpaRepository<ReportCategory, Integer> ,CrudRepository<ReportCategory, Integer>, JpaSpecificationExecutor<ReportCategory> {

}
