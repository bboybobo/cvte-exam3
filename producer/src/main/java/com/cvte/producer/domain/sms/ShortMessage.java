package com.cvte.producer.domain.sms;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class ShortMessage {
    @Id
    @GeneratedValue
    Integer id;
    String sender;
    String receiver;
    String content;
    Date sendTime;
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public ShortMessage() {
    }

    public ShortMessage(String sender, String receiver, String content, Date sendTime, String url) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.sendTime = sendTime;
        this.url = url;
    }

    @Override
    public String toString() {
        return "ShortMessage{" +
                "id=" + id +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", content='" + content + '\'' +
                ", sendTime=" + sendTime +
                ", url='" + url + '\'' +
                '}';
    }
}
