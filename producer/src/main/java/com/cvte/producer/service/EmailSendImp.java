package com.cvte.producer.service;

import com.cvte.producer.domain.EmailInitDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.LinkedList;
import java.util.Map;

@Component
public class EmailSendImp implements EmailSend {

    @Autowired
    private KafkaTemplate kafkaTemplate;
    private Gson gson = new GsonBuilder().create();

    @Override
    public void emailAsySend(String sender, String nick, int sendNums, LinkedList<String> revicers, String theme, String templete, LinkedList<Map<String, String>> params, boolean needReturn) {
        EmailInitDetail emailInitDetail = new EmailInitDetail( sender,  nick,  sendNums, revicers,
                                                                theme,  templete,  params, needReturn);

        kafkaTemplate.send("email", gson.toJson(emailInitDetail));
    }
}
