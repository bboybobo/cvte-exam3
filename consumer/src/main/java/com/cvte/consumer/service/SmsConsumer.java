package com.cvte.consumer.service;

import com.cvte.consumer.domain.status.SmsStatus;
import com.cvte.consumer.domain.status.SmsStatusRepository;
import com.cvte.consumer.exception.ErrorStatusEnum;
import com.cvte.consumer.exception.GlobalExceptionHandler;
import com.cvte.consumer.util.CommonUtil;
import com.cvte.consumer.domain.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class SmsConsumer {
    @Autowired
    private SmsTransform smsTransform;

    @Autowired
    SmsStatusRepository smsStatusRepository;

    private Gson gson = new GsonBuilder().create();

    @KafkaListener(topics = "SMS")
    public void handleSms(String data){
        SmsInitDetail detail = gson.fromJson(data,SmsInitDetail.class);
        SmsStatus smsStatus = new SmsStatus();
        try {
            ArrayList<ShortMessage> shortMessages = smsTransform.getSms(detail);
            smsTransform.saveAndSend(shortMessages);

            smsStatus.setSmsInitDetailId(detail.getId());
            smsStatus.setCode(ErrorStatusEnum.SEND_SUCCESS.getCode());
            smsStatus.setStatus(ErrorStatusEnum.SEND_SUCCESS.getMsg());
            smsStatus.setDetailMsg(null);
            smsStatusRepository.save(smsStatus);
        }catch (Exception ex){
            GlobalExceptionHandler handler = new GlobalExceptionHandler();
            smsStatus = handler.handleSmsException(ex);
            smsStatus.setSmsInitDetailId(detail.getId());
            smsStatusRepository.save(smsStatus);
        }



    }
}
