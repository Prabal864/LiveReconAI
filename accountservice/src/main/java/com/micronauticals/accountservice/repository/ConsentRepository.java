package com.micronauticals.accountservice.repository;

import com.micronauticals.accountservice.entity.consent.Consent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsentRepository extends JpaRepository<Consent, String> {

    List<Consent> findAllByIdIn(List<String> ids);
}
