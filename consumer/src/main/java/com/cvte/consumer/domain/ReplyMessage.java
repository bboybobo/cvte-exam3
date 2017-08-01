package com.cvte.consumer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class ReplyMessage {
    @Id
    @GeneratedValue
    private Integer id;
    private String url;
    private String sender;
    private String reciver;
    private String replyContent;
    private Date replyTime;

    public Integer getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getSender() {
        return sender;
    }

    public String getReciver() {
        return reciver;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public ReplyMessage(String url, String sender, String reciver, String replyContent, Date replyTime) {
        this.url = url;
        this.sender = sender;
        this.reciver = reciver;
        this.replyContent = replyContent;
        this.replyTime = replyTime;
    }
}
