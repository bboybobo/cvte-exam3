package com.cvte.consumer.service;

import com.cvte.consumer.config.CommonUtil;
import com.cvte.consumer.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class EmailConsumer {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    ReplyMessageRepository replyMessageRepository;

    private Gson gson = new GsonBuilder().create();

    @KafkaListener(topics = "email")
    public void handleEmail(String data){
        EmailInitDetail detail = gson.fromJson(data, EmailInitDetail.class);

        StringBuffer[] contents = new StringBuffer[detail.getSendNums()];

        for (int i=0; i<detail.getSendNums(); i++){
            //获取模板内容
            contents[i] = new StringBuffer(detail.getTemplete());
            Map<String, String> map = detail.getParams().get(i);
            //将模板中的参数替换为指定内容
            for (Map.Entry<String, String> entry : map.entrySet()){
                String key = entry.getKey();
                String value = entry.getValue();
                String replaceKey = "{$" + key + "}";
                int replaceStart = contents[i].indexOf(replaceKey);
                int replaceEnd = contents[i].indexOf(replaceKey) + replaceKey.length();
                contents[i].replace(replaceStart,replaceEnd,value);
            }

            //设置发送时间
            Date nowDate = new Date();
            long timeFrom1970 = nowDate.getTime();
            java.sql.Date date = new java.sql.Date(timeFrom1970);

            //设置url
            String url = "";
            if(detail.isNeedReturn()){
                url = CommonUtil.URL + "/" + UUID.randomUUID().toString().replaceAll("-","");
            }
            //设置每一封邮件
            Email email = new Email(detail.getSender(), detail.getRevicers().get(i), detail.getNick(),
                    detail.getTheme(), contents[i].toString(), date, url);

            System.out.println(email);
            //保存已发送邮件到数据库
            emailRepository.save(email);

            //如果需要回复信息，则保存初始信息到数据库
            if(detail.isNeedReturn()){
                ReplyMessage replyMessage = new ReplyMessage(url, detail.getSender(), detail.getRevicers().get(i),
                        contents[i].toString(), date );
                replyMessageRepository.save(replyMessage);
            }
        }

    }
}
