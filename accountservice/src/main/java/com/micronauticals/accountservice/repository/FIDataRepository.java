package com.micronauticals.accountservice.repository;


import com.micronauticals.accountservice.entity.financialdata.FiDataBundle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FIDataRepository extends JpaRepository<FiDataBundle,String> {

}
