package com.cvte.consumer.service;

import com.cvte.consumer.domain.status.EmailStatus;
import com.cvte.consumer.domain.status.EmailStatusRepository;
import com.cvte.consumer.exception.ErrorStatusEnum;
import com.cvte.consumer.exception.GlobalExceptionHandler;
import com.cvte.consumer.util.CommonUtil;
import com.cvte.consumer.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

@Component
public class EmailConsumer {

    @Autowired
    EmailTransform emailTransform;

    @Autowired
    EmailStatusRepository emailStatusRepository;

    private Gson gson = new GsonBuilder().create();

    @KafkaListener(topics = "email")
    public void handleEmail(String data){
        EmailInitDetail detail = gson.fromJson(data, EmailInitDetail.class);
        EmailStatus emailStatus = new EmailStatus();
        try {

            ArrayList<Email> emails = emailTransform.getEmails(detail);
            emailTransform.saveAndSend(emails);
            //save status
            emailStatus.setEmailInitDetailId(detail.getId());
            emailStatus.setCode(ErrorStatusEnum.SEND_SUCCESS.getCode());
            emailStatus.setStatus(ErrorStatusEnum.SEND_SUCCESS.getMsg());
            emailStatus.setDetailMsg(null);
            emailStatusRepository.save(emailStatus);
            //System.out.println(emailStatus);
        }catch (Exception e){
            GlobalExceptionHandler handler = new GlobalExceptionHandler();
            emailStatus = handler.handleEmailException(e);
            emailStatus.setEmailInitDetailId(detail.getId());
            emailStatusRepository.save(emailStatus);
        }


    }
}
