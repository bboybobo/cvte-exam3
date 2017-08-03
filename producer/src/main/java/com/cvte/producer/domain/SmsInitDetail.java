package com.cvte.producer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.Map;

@Entity
public class SmsInitDetail {
    @Id
    @GeneratedValue
    Integer id;
    @NotNull(message = "发送者不能为空")
    String sender;
    @NotNull(message = "发送数量不能为空")
    Integer sendNums;
    @NotNull(message = "接收者不能为空")
    LinkedList<String> revicers;
    @NotNull(message = "短信模板不能为空")
    String templete;
    LinkedList<Map<String, String>> params;
    @NotNull(message = "是否需要回信参数不能为空")
    boolean needReturn;

    public String getSender() {
        return sender;
    }


    public Integer getSendNums() {
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


    public void setSendNums(Integer sendNums) {
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

    @Override
    public String toString() {
        return "SmsInitDetail{" +
                "id=" + id +
                ", sender='" + sender + '\'' +
                ", sendNums=" + sendNums +
                ", revicers=" + revicers +
                ", templete='" + templete + '\'' +
                ", params=" + params +
                ", needReturn=" + needReturn +
                '}';
    }
}
