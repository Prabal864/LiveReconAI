package com.micronauticals.accountservice.repository;

import com.micronauticals.accountservice.entity.Consent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsentRepository extends JpaRepository<Consent, String> {

}
