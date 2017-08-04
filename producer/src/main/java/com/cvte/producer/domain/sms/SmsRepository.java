package com.cvte.producer.domain.sms;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SmsRepository extends JpaRepository<ShortMessage, Integer> {
}
