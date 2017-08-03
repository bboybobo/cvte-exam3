package com.cvte.producer.service;

import com.cvte.producer.domain.ReturnData;
import com.cvte.producer.domain.SmsInitDetail;
import com.cvte.producer.domain.SmsInitDetailRepository;
import com.cvte.producer.exception.ResultStatusEnum;
import com.cvte.producer.util.CheckNull;
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

    @Autowired
    private SmsInitDetailRepository smsInitDetailRepository;

    private Gson gson = new GsonBuilder().create();

    @Override
    public ReturnData smsAsySend(String sender, int sendNums, LinkedList<String> revicers, String templete,
                    LinkedList<Map<String, String>> params, boolean needReturn) {
        ReturnData returnData = null;
        SmsInitDetail smsInitDetail = new SmsInitDetail(sender, sendNums, revicers,
                 templete,  params, needReturn);

        if(CheckNull.checkSms(smsInitDetail) != null){
            returnData = new ReturnData(ResultStatusEnum.PARA_ERROR,smsInitDetail);
            System.out.println(returnData);
            return returnData;
        }
        try{
            //save to database
            smsInitDetailRepository.save(smsInitDetail);
        }catch (Exception ex){
            returnData = new ReturnData(ResultStatusEnum.DATABASE_SAVE_ERROR,ex);
            System.out.println(returnData);
            ex.printStackTrace();
            return returnData;
        }


        try {
            //send to queue
            kafkaTemplate.send("SMS",gson.toJson(smsInitDetail));
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
