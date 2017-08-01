package com.cvte.producer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.LinkedList;
import java.util.Map;

@Entity
public class SmsInitDetail {
    @Id
    @GeneratedValue
    Integer id;
    String sender;
    int sendNums;
    LinkedList<String> revicers;
    String templete;
    LinkedList<Map<String, String>> params;
    boolean needReturn;

    public String getSender() {
        return sender;
    }


    public int getSendNums() {
        return sendNums;
    }

    public LinkedList<String> getRevicers() {
        return revicers;
    }

    public String getTemplete() {
        return templete;
    }

    public LinkedList<Map<String, String>> getParams() {
        return params;
    }

    public boolean isNeedReturn() {
        return needReturn;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }


    public void setSendNums(int sendNums) {
        this.sendNums = sendNums;
    }

    public void setRevicers(LinkedList<String> revicers) {
        this.revicers = revicers;
    }

    public void setTemplete(String templete) {
        this.templete = templete;
    }

    public void setParams(LinkedList<Map<String, String>> params) {
        this.params = params;
    }

    public void setNeedReturn(boolean needReturn) {
        this.needReturn = needReturn;
    }

    public SmsInitDetail(String sender, int sendNums, LinkedList<String> revicers, String templete,
                         LinkedList<Map<String, String>> params, boolean needReturn) {
        this.sender = sender;
        this.sendNums = sendNums;
        this.revicers = revicers;
        this.templete = templete;
        this.params = params;
        this.needReturn = needReturn;
    }
}
