package com.cvte.producer.service;

import com.cvte.producer.domain.ReplyMessage;
import com.cvte.producer.domain.ReplyMessageRepository;
import com.cvte.producer.domain.ReturnData;
import com.cvte.producer.domain.foreignInterface.InterfaceTable;
import com.cvte.producer.domain.foreignInterface.InterfaceTableRepository;
import com.cvte.producer.domain.sms.ShortMessage;
import com.cvte.producer.domain.sms.SmsInitDetail;
import com.cvte.producer.domain.sms.SmsInitDetailRepository;
import com.cvte.producer.domain.sms.SmsRepository;
import com.cvte.producer.exception.ResultStatusEnum;
import com.cvte.producer.util.CheckNull;
import com.cvte.producer.util.CommonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

@Component
public class SmsSendImp implements SmsSend {


    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private SmsInitDetailRepository smsInitDetailRepository;

    @Autowired
    SmsRepository smsRepository;

    @Autowired
    ReplyMessageRepository replyMessageRepository;

    @Autowired
    InterfaceTableRepository interfaceTableRepository;


    private Gson gson = new GsonBuilder().create();

    @Override
    public ReturnData smsAsySend(String sender, int sendNums, LinkedList<String> revicers, String templete,
                    LinkedList<Map<String, String>> params, boolean needReturn) {
        ReturnData returnData = null;
        SmsInitDetail smsInitDetail = new SmsInitDetail(sender, sendNums, revicers,
                 templete,  params, needReturn);

        //参数检查，保存初始信息
        returnData = checkAndSaveInitSms(smsInitDetail);
        if (returnData != null){
            System.out.println(returnData);
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

    //同步发送短息
    @Override
    public ReturnData smsSynSend(String sender, int sendNums, LinkedList<String> revicers, String templete, LinkedList<Map<String, String>> params, boolean needReturn) {

        SmsInitDetail smsInitDetail = new SmsInitDetail(sender, sendNums, revicers,
                templete,  params, needReturn);

        //参数检查，保存初始信息
        ReturnData returnData = checkAndSaveInitSms(smsInitDetail);
        if (returnData != null){
            System.out.println(returnData);
            return returnData;
        }

        //拼接短信，生成短信列表
        ArrayList<ShortMessage> shortMessages = getSms(smsInitDetail);
        //拼接失败，说明参数有误
        if (shortMessages == null){
            returnData =  new ReturnData(ResultStatusEnum.PARA_ERROR,smsInitDetail);
            System.out.println(returnData);
            return returnData;
        }

        //保存短信至数据库，并发送
        try {
            saveAndSend(shortMessages);
        }catch (Exception ex){
            ex.printStackTrace();
            returnData = new ReturnData(ResultStatusEnum.SYN_SEND_FAIL, smsInitDetail);
            System.out.println(returnData);
            return returnData;

        }
        returnData = new ReturnData(ResultStatusEnum.SYN_SEND_SUCCESS, null);
        System.out.println(returnData);
        return returnData;



    }


    //进行参数检查，保存数据库，有异常则返回包装后的结果，正常返回空。
    private ReturnData checkAndSaveInitSms(SmsInitDetail smsInitDetail){
        ReturnData returnData = null;
        //参数检查是否为空
        if(CheckNull.checkSms(smsInitDetail) != null){
            returnData = new ReturnData(ResultStatusEnum.PARA_ERROR,smsInitDetail);
            //System.out.println(returnData);
            return returnData;
        }

        //保存数据库
        try{
            smsInitDetailRepository.save(smsInitDetail);
        }catch (Exception e){
            returnData = new ReturnData(ResultStatusEnum.DATABASE_SAVE_ERROR,e);
            //System.out.println(returnData);
            e.printStackTrace();
            return returnData;
        }
        return null;
    }

    //根据短信数据拼接成短信列表,返回空则表示参数有误，拼接失败
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
           ex.printStackTrace();
           return null;     //返回null表示失败
        }

        return shortMessages;

    }

    //把要发送出去的短信保存至数据库
    public void saveSmstoDatabase(ArrayList<ShortMessage> shortMessages) throws Exception{
        for (ShortMessage shortMessage : shortMessages){
            smsRepository.save(shortMessage);
        }
    }


    //根据接口真正发送邮件
    public void sendSms(ArrayList<ShortMessage> shortMessages) throws Exception{
        InterfaceTable interfaceTable = interfaceTableRepository.findOne(1);
        String smsInterface = interfaceTable.getSmsInterface();
        for (ShortMessage shortMessage : shortMessages){
            System.out.println(smsInterface + "\n" + shortMessage);
        }
    }

    @Transactional   //保证事务性
    public void saveAndSend(ArrayList<ShortMessage> shortMessages) throws Exception{
        sendSms(shortMessages);
        saveSmstoDatabase(shortMessages);
    }
}

