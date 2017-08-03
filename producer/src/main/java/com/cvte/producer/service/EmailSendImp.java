package com.cvte.producer.service;

import com.cvte.producer.domain.EmailInitDetail;
import com.cvte.producer.domain.EmailInitDetailRepository;
import com.cvte.producer.domain.ReturnData;
import com.cvte.producer.exception.ResultStatusEnum;
import com.cvte.producer.util.CheckNull;
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

    @Autowired
    private EmailInitDetailRepository emailInitDetailRepository;

    private Gson gson = new GsonBuilder().create();

    @Override
    public ReturnData emailAsySend(String sender, String nick, int sendNums, LinkedList<String> revicers, String theme, String templete, LinkedList<Map<String, String>> params, boolean needReturn) {
        EmailInitDetail emailInitDetail = null;
        ReturnData returnData = null;


        emailInitDetail = new EmailInitDetail( sender,  nick,  sendNums, revicers,
                            theme,  templete,  params, needReturn);
        if(CheckNull.checkEmail(emailInitDetail) != null){
            returnData = new ReturnData(ResultStatusEnum.PARA_ERROR,emailInitDetail);
            System.out.println(returnData);
            return returnData;
        }

        try {
            //save to database
            emailInitDetailRepository.save(emailInitDetail);
        }catch (Exception ex){
            returnData = new ReturnData(ResultStatusEnum.DATABASE_SAVE_ERROR,ex);
            System.out.println(returnData);
            ex.printStackTrace();
            return returnData;
        }
        try {
            //send to queue
            kafkaTemplate.send("email", gson.toJson(emailInitDetail));
        }catch (Exception ex){
            returnData = new ReturnData(ResultStatusEnum.KAFKA_ERROR,ex);
            System.out.println(returnData);
            ex.printStackTrace();
            return returnData;
        }
        returnData = new ReturnData(ResultStatusEnum.ASY_SEND_SUCCESS, null);
        System.out.println(returnData);
        return returnData;
    }
}
