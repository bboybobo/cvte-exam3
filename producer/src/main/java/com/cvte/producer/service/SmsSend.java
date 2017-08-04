package com.cvte.producer.service;

import com.cvte.producer.domain.ReturnData;

import java.util.LinkedList;
import java.util.Map;

public interface SmsSend {
    //异步发送短信
    ReturnData smsAsySend(String sender,
                          int sendNums,
                          LinkedList<String> revicers,
                          String templete,
                          LinkedList<Map<String, String>> params,
                          boolean needReturn
                    );

    //同步发送短信
    ReturnData smsSynSend(String sender,
                          int sendNums,
                          LinkedList<String> revicers,
                          String templete,
                          LinkedList<Map<String, String>> params,
                          boolean needReturn
    );
}
