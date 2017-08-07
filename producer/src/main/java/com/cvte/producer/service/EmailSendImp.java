package com.cvte.producer.service;

import com.cvte.producer.domain.ReplyMessage;
import com.cvte.producer.domain.ReplyMessageRepository;
import com.cvte.producer.domain.ReturnData;
import com.cvte.producer.domain.email.Email;
import com.cvte.producer.domain.email.EmailInitDetail;
import com.cvte.producer.domain.email.EmailInitDetailRepository;
import com.cvte.producer.domain.email.EmailRepository;
import com.cvte.producer.domain.foreignInterface.InterfaceTable;
import com.cvte.producer.domain.foreignInterface.InterfaceTableRepository;
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
public class EmailSendImp implements EmailSend {

    @Autowired
    ReplyMessageRepository replyMessageRepository;
    @Autowired
    EmailRepository emailRepository;
    @Autowired
    InterfaceTableRepository interfaceTableRepository;
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private EmailInitDetailRepository emailInitDetailRepository;
    private Gson gson = new GsonBuilder().create();

    @Override
    public ReturnData emailAsySend(String sender, String nick, int sendNums, LinkedList<String> revicers, String theme, String templete, LinkedList<Map<String, String>> params, boolean needReturn) {
        EmailInitDetail emailInitDetail = null;
        ReturnData returnData = null;


        emailInitDetail = new EmailInitDetail(sender, nick, sendNums, revicers,
                theme, templete, params, needReturn);

        //检查参数是否为空，并保存初始信息
        returnData = checkAndSaveInitEmail(emailInitDetail);
        if (returnData != null) {
            System.out.println(returnData);
            return returnData;
        }

        try {
            //send to queue
            kafkaTemplate.send("email", gson.toJson(emailInitDetail));
        } catch (Exception ex) {
            returnData = new ReturnData(ResultStatusEnum.KAFKA_ERROR, ex);
            System.out.println(returnData);
            ex.printStackTrace();
            return returnData;
        }
        returnData = new ReturnData(ResultStatusEnum.ASY_SEND_SUCCESS, null);
        System.out.println(returnData);
        return returnData;
    }

    @Override
    public ReturnData emailSynSend(String sender, String nick, int sendNums, LinkedList<String> revicers, String theme, String templete, LinkedList<Map<String, String>> params, boolean needReturn) {
        EmailInitDetail emailInitDetail = null;
        ReturnData returnData = null;


        emailInitDetail = new EmailInitDetail(sender, nick, sendNums, revicers,
                theme, templete, params, needReturn);

        //检查参数是否为空，并保存初始信息
        returnData = checkAndSaveInitEmail(emailInitDetail);
        if (returnData != null) {
            System.out.println(returnData);
            return returnData;
        }

        //得到具体发送的短信列表
        ArrayList<Email> emails = getEmails(emailInitDetail);
        if (emails == null) {
            returnData = new ReturnData(ResultStatusEnum.PARA_ERROR, emailInitDetail);
            System.out.println(returnData);
            return returnData;
        }

        //保存并发送
        try {
            saveAndSend(emails);
        } catch (Exception ex) {
            ex.printStackTrace();
            returnData = new ReturnData(ResultStatusEnum.SYN_SEND_FAIL, emailInitDetail);
            System.out.println(returnData);
            return returnData;
        }


        //发送成功，返回
        returnData = new ReturnData(ResultStatusEnum.SYN_SEND_SUCCESS, null);
        System.out.println(returnData);
        return returnData;
    }


    //进行参数检查，保存数据库，有异常则返回包装后的结果，正常返回空。
    private ReturnData checkAndSaveInitEmail(EmailInitDetail emailInitDetail) {
        ReturnData returnData = null;
        //参数检查是否为空
        if (CheckNull.checkEmail(emailInitDetail) != null) {
            returnData = new ReturnData(ResultStatusEnum.PARA_ERROR, emailInitDetail);
            return returnData;
        }

        //保存数据库
        try {
            emailInitDetailRepository.save(emailInitDetail);
        } catch (Exception e) {
            returnData = new ReturnData(ResultStatusEnum.DATABASE_SAVE_ERROR, e);
            e.printStackTrace();
            return returnData;
        }
        return null;
    }

    //根据发送来的数据拼接成完整的邮件
    @Transactional
    public ArrayList<Email> getEmails(EmailInitDetail detail) {

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
                if (detail.isNeedReturn()) {
                    ReplyMessage replyMessage = new ReplyMessage(url, detail.getSender(), detail.getRevicers().get(i),
                            contents[i].toString(), date);
                    replyMessageRepository.save(replyMessage);
                }

                emails.add(email);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;        //null表示失败
        }

        return emails;
    }



//    public void saveEmailtoDatabase(ArrayList<Email> emails) throws Exception{
//
//    }
//
//
//    public void sendEmails(ArrayList<Email> emails) throws Exception {
//
//    }


    @Transactional   //保证事务性
    public void saveAndSend(ArrayList<Email> emails) throws Exception {
        //根据接口真正发送邮件
        InterfaceTable interfaceTable = interfaceTableRepository.findOne(1);
        String emailInterface = interfaceTable.getEmailInterface();
        for (Email email : emails) {
            System.out.println(emailInterface + "\n" + email);
        }
        //保存发送出去的邮件
        for (Email email : emails) {
            emailRepository.save(email);
        }

    }
}
