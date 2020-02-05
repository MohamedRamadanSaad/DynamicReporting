package com.giza.adaay.performance.reportEngine.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import com.giza.adaay.performance.reportEngine.entities.STCApplication;

public interface STCApplicationRepo extends JpaRepository<STCApplication, Integer> ,CrudRepository<STCApplication, Integer>, JpaSpecificationExecutor<STCApplication> {

}
