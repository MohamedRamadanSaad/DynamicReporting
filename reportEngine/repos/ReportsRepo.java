package com.giza.adaay.performance.reportEngine.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import com.giza.adaay.performance.reportEngine.entities.Report;

public interface ReportsRepo extends JpaRepository<Report, Integer> ,CrudRepository<Report, Integer>, JpaSpecificationExecutor<Report> {

	List<Report> findByOrderById();
}
