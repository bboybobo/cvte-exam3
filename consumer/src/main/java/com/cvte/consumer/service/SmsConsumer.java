package com.cvte.consumer.service;

import com.cvte.consumer.domain.ShortMessage;
import com.cvte.consumer.domain.SmsInitDetail;
import com.cvte.consumer.domain.SmsRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class SmsConsumer {
    @Autowired
    private SmsRepository smsRepository;

    private Gson gson = new GsonBuilder().create();

    @KafkaListener(topics = "SMS")
    public void handleSms(String data){
        SmsInitDetail detail = gson.fromJson(data,SmsInitDetail.class);
        StringBuffer[] contents = new StringBuffer[detail.getSendNums()];

        for (int i=0; i<detail.getSendNums(); i++) {
            //获取模板内容
            contents[i] = new StringBuffer(detail.getTemplete());
            Map<String, String> map = detail.getParams().get(i);
            //将模板中的参数替换为指定内容
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                String replaceKey = "{$" + key + "}";
                int replaceStart = contents[i].indexOf(replaceKey);
                int replaceEnd = contents[i].indexOf(replaceKey) + replaceKey.length();
                contents[i].replace(replaceStart, replaceEnd, value);
            }

            //设置发送时间
            Date nowDate = new Date();
            long timeFrom1970 = nowDate.getTime();
            java.sql.Date date = new java.sql.Date(timeFrom1970);

            ShortMessage shortMessage = new ShortMessage(detail.getSender(), detail.getRevicers().get(i),
                    contents[i].toString(), date);
            smsRepository.save(shortMessage);
            System.out.println(i);
        }
    }
}
