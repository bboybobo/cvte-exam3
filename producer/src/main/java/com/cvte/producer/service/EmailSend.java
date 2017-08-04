package com.cvte.producer.service;

import com.cvte.producer.domain.ReturnData;

import java.util.LinkedList;
import java.util.Map;


public interface EmailSend {
    //异步发送邮件
    ReturnData emailAsySend(String sender,
                            String nick,
                            int sendNums,
                            LinkedList<String> revicers,
                            String theme,
                            String templete,
                            LinkedList<Map<String, String>> params,
                            boolean needReturn
    );


    //同步发送邮件
    ReturnData emailSynSend(String sender,
                            String nick,
                            int sendNums,
                            LinkedList<String> revicers,
                            String theme,
                            String templete,
                            LinkedList<Map<String, String>> params,
                            boolean needReturn
    );


}
