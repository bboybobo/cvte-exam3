package com.cvte.consumer.domain.status;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmailStatusRepository extends JpaRepository<EmailStatus, Integer> {
}
