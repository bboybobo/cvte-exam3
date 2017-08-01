package com.cvte.producer.service;

import java.util.LinkedList;
import java.util.Map;


public interface EmailSend {
    //异步发送邮件
    void emailAsySend(String sender,
                      String nick,
                      int sendNums,
                      LinkedList<String> revicers,
                      String theme,
                      String templete,
                      LinkedList<Map<String, String>> params,
                      boolean needReturn
    );
}
