package com.micronauticals.transactionservice.repository;


import com.micronauticals.transactionservice.entity.financialdata.FiDataBundle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FIDataRepository extends JpaRepository<FiDataBundle,String> {

}
