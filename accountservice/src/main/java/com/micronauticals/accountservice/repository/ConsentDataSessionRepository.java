package com.micronauticals.accountservice.repository;

import com.micronauticals.accountservice.entity.consent.ConsentDataSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsentDataSessionRepository extends JpaRepository<ConsentDataSession,String> {
}
