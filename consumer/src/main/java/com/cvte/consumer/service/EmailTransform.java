package com.cvte.consumer.service;

import com.cvte.consumer.domain.*;
import com.cvte.consumer.exception.*;
import com.cvte.consumer.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class EmailTransform {
    @Autowired
    private  EmailRepository emailRepository;

    @Autowired
    ReplyMessageRepository replyMessageRepository;

    private Integer emailInitDetailId;

    //根据发送来的数据拼接成完整的邮件
    @Transactional
    public ArrayList<Email> getEmails(EmailInitDetail detail) throws Exception{
        this.emailInitDetailId = detail.getId();
        ArrayList<Email> emails = new ArrayList<Email>();
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
                //设置每一封邮件
                Email email = new Email(detail.getSender(), detail.getRevicers().get(i), detail.getNick(),
                        detail.getTheme(), contents[i].toString(), date, url);

                //如果需要回复信息，则保存初始信息到数据库
                if(detail.isNeedReturn()){
                    ReplyMessage replyMessage = new ReplyMessage(url, detail.getSender(), detail.getRevicers().get(i),
                            contents[i].toString(), date );
                    replyMessageRepository.save(replyMessage);
                }

                emails.add(email);
            }
        }catch (Exception ex){
            throw new ConsumerException(ErrorStatusEnum.PARA_ERROR.getCode(), detail.getId(),
                    ErrorStatusEnum.PARA_ERROR.getMsg() ,ex.toString());
        }

        return emails;
    }

    //save to database
    public void saveEmailtoDatabase(ArrayList<Email> emails) throws Exception{
        try {
            for (Email email : emails){
                emailRepository.save(email);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ConsumerException(ErrorStatusEnum.DATABASE_SAVE_ERROR.getCode(),emailInitDetailId,
                    ErrorStatusEnum.DATABASE_SAVE_ERROR.getMsg() , ex.toString());
        }
    }

    //根据接口真正发送邮件
    public void sendEmails(ArrayList<Email> emails) throws Exception{
        try {
            for (Email email : emails){
                System.out.println(email);
            }
        }catch (Exception ex){
            throw new ConsumerException(ErrorStatusEnum.KAFKA_ERROR.getCode(),emailInitDetailId,
                    ErrorStatusEnum.KAFKA_ERROR.getMsg() ,ex.toString());
        }
    }



    @Transactional   //保证事务性
    public void saveAndSend(ArrayList<Email> emails) throws Exception{
        saveEmailtoDatabase(emails);
        sendEmails(emails);
    }
}
