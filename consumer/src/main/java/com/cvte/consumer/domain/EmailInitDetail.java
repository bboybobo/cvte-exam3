package com.cvte.consumer.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.LinkedList;
import java.util.Map;

public class EmailInitDetail {
    @Id
    @GeneratedValue
    Integer id;

    String sender;
    String nick;
    int sendNums;
    LinkedList<String> revicers;
    String theme;
    String templete;
    LinkedList<Map<String, String>> params;
    boolean needReturn;

    public Integer getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getNick() {
        return nick;
    }

    public int getSendNums() {
        return sendNums;
    }

    public LinkedList<String> getRevicers() {
        return revicers;
    }

    public String getTheme() {
        return theme;
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

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setSendNums(int sendNums) {
        this.sendNums = sendNums;
    }

    public void setRevicers(LinkedList<String> revicers) {
        this.revicers = revicers;
    }

    public void setTheme(String theme) {
        this.theme = theme;
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

    public EmailInitDetail(){}

    public EmailInitDetail(String sender, String nick, int sendNums, LinkedList<String> revicers,
                           String theme, String templete, LinkedList<Map<String, String>> params,
                           boolean needReturn) {
        this.sender = sender;
        this.nick = nick;
        this.sendNums = sendNums;
        this.revicers = revicers;
        this.theme = theme;
        this.templete = templete;
        this.params = params;
        this.needReturn = needReturn;
    }

}
