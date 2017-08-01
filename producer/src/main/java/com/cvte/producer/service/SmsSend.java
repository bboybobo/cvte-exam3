package com.cvte.producer.service;

import java.util.LinkedList;
import java.util.Map;

public interface SmsSend {
    void smsAsySend(String sender,
                    int sendNums,
                    LinkedList<String> revicers,
                    String templete,
                    LinkedList<Map<String, String>> params,
                    boolean needReturn
                    );
}
