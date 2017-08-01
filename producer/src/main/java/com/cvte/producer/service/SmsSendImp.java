package com.cvte.producer.service;

import com.cvte.producer.domain.SmsInitDetail;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Map;

@Component
public class SmsSendImp implements SmsSend {


    @Autowired
    private KafkaTemplate kafkaTemplate;
    private Gson gson = new GsonBuilder().create();

    @Override
    public void smsAsySend(String sender, int sendNums, LinkedList<String> revicers, String templete,
                           LinkedList<Map<String, String>> params, boolean needReturn) {
        SmsInitDetail smsInitDetail = new SmsInitDetail(sender, sendNums, revicers,
                 templete,  params, needReturn);

        kafkaTemplate.send("SMS",gson.toJson(smsInitDetail));
    }
}
