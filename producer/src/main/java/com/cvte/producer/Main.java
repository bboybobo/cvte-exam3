package com.cvte.producer;

import com.cvte.producer.service.EmailSend;
import com.cvte.producer.service.EmailSendImp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Main {
    public static void main(String[] args){
        EmailSend emailSend = new EmailSendImp();
        LinkedList<String> receivers = new LinkedList();
        receivers.add("a@qq.com");
        receivers.add("b@cvte.com");
        String template = "你好{$name},你的验证码为{$value}";

        LinkedList<Map<String, String>> params = new LinkedList();
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("name", "xubobo");
        map1.put("value","0123");
        params.add(map1);

        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("name", "zhangyan");
        map2.put("value","2222");
        params.add(map2);
        emailSend.emailAsySend("a@abc","tony",2,receivers,
                "topic",template, params,true);

    }
}
