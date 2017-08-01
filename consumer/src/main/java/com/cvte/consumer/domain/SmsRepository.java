package com.cvte.consumer.domain;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SmsRepository extends JpaRepository<ShortMessage, Integer> {
}
