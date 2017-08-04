package com.cvte.consumer.service;

import com.cvte.consumer.domain.*;
import com.cvte.consumer.domain.foreignInterface.InterfaceTable;
import com.cvte.consumer.domain.foreignInterface.InterfaceTableRepository;
import com.cvte.consumer.exception.ConsumerException;
import com.cvte.consumer.exception.ErrorStatusEnum;
import com.cvte.consumer.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class SmsTransform {
    @Autowired
    SmsRepository smsRepository;

    @Autowired
    ReplyMessageRepository replyMessageRepository;

    @Autowired
    InterfaceTableRepository interfaceTableRepository;

    @Transactional
    public ArrayList<ShortMessage> getSms(SmsInitDetail detail) {
        ArrayList<ShortMessage> shortMessages = new ArrayList();

        StringBuffer[] contents = new StringBuffer[detail.getSendNums()];

        try {
            for (int i = 0; i < detail.getSendNums(); i++) {
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

                //设置url
                String url = "";
                if (detail.isNeedReturn()) {
                    url = CommonUtil.URL + "/" + UUID.randomUUID().toString().replaceAll("-", "");
                }

                ShortMessage shortMessage = new ShortMessage(detail.getSender(), detail.getRevicers().get(i),
                        contents[i].toString(), date, url);

                //如果需要回复信息，则保存初始信息到数据库
                if(detail.isNeedReturn()){
                    ReplyMessage replyMessage = new ReplyMessage(url, detail.getSender(), detail.getRevicers().get(i),
                            contents[i].toString(), date );
                    replyMessageRepository.save(replyMessage);
                }

                shortMessages.add(shortMessage);
            }
        }catch (Exception ex){
            throw new ConsumerException(ErrorStatusEnum.PARA_ERROR.getCode(), detail.getId(),
                    ErrorStatusEnum.PARA_ERROR.getMsg() ,ex.toString());
        }

        return shortMessages;

    }

    //save to database
    public void saveSmstoDatabase(ArrayList<ShortMessage> shortMessages) throws Exception{
        try {
            for (ShortMessage shortMessage : shortMessages){
                smsRepository.save(shortMessage);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ConsumerException(ErrorStatusEnum.DATABASE_SAVE_ERROR.getCode(),0,
                    ErrorStatusEnum.DATABASE_SAVE_ERROR.getMsg() , ex.toString());
        }
    }

    //根据接口真正发送邮件
    public void sendSms(ArrayList<ShortMessage> shortMessages) throws Exception{
        try {
            InterfaceTable interfaceTable = interfaceTableRepository.findOne(1);
            String smsInterface = interfaceTable.getSmsInterface();
            for (ShortMessage shortMessage : shortMessages){
                System.out.println(smsInterface + "\n" + shortMessage);
            }
        }catch (Exception ex){
            throw new ConsumerException(ErrorStatusEnum.KAFKA_ERROR.getCode(),0,
                    ErrorStatusEnum.KAFKA_ERROR.getMsg() ,ex.toString());
        }
    }

    @Transactional   //保证事务性
    public void saveAndSend(ArrayList<ShortMessage> shortMessages)throws Exception{
        saveSmstoDatabase(shortMessages);
        sendSms(shortMessages);
    }
}
