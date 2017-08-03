package com.cvte.producer.service;

import com.cvte.producer.domain.ReturnData;

import java.util.LinkedList;
import java.util.Map;

public interface SmsSend {
    ReturnData smsAsySend(String sender,
                          int sendNums,
                          LinkedList<String> revicers,
                          String templete,
                          LinkedList<Map<String, String>> params,
                          boolean needReturn
                    );
}
