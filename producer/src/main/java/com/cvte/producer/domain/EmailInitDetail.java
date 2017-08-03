package com.cvte.producer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.Map;

@Entity
public class EmailInitDetail {
    @Id
    @GeneratedValue
    Integer id;

    @NotNull(message = "发送者不能为空")
    String sender;
    String nick;
    @NotNull(message = "发送数量不能为空")
    Integer sendNums;
    @NotNull(message = "接收者不能为空")
    LinkedList<String> revicers;
    @NotNull(message = "邮件主题不能为空")
    String theme;
    @NotNull(message = "邮件模板不能为空")
    String templete;
    LinkedList<Map<String, String>> params;
    @NotNull(message = "是否需要回信参数不能为空")
    boolean needReturn;

    public String getSender() {
        return sender;
    }

    public String getNick() {
        return nick;
    }

    public Integer getSendNums() {
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

    public void setSendNums(Integer sendNums) {
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
                           boolean needReturn){
        this.sender = sender;
        this.nick = nick;
        this.sendNums = sendNums;
        this.revicers = revicers;
        this.theme = theme;
        this.templete = templete;
        this.params = params;
        this.needReturn = needReturn;
    }

    @Override
    public String toString() {
        return "EmailInitDetail{" +
                "id=" + id +
                ", sender='" + sender + '\'' +
                ", nick='" + nick + '\'' +
                ", sendNums=" + sendNums +
                ", revicers=" + revicers +
                ", theme='" + theme + '\'' +
                ", templete='" + templete + '\'' +
                ", params=" + params +
                ", needReturn=" + needReturn +
                '}';
    }
}
