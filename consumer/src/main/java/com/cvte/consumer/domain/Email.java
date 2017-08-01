package com.cvte.consumer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Email {
    @Id
    @GeneratedValue
    Integer id;
    String sender;
    String receiver;
    String nick;
    String theme;
    String content;
    Date sendTime;

    public Integer getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getNick() {
        return nick;
    }

    public String getTheme() {
        return theme;
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
        this.receiver = sender;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Email() {
    }

    public Email(String sender, String receiver, String nick, String theme, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.nick = nick;
        this.theme = theme;
        this.content = content;
    }
    public Email(String sender, String receiver, String nick, String theme, String content, Date sendTime) {
        this.sender = sender;
        this.receiver = receiver;
        this.nick = nick;
        this.theme = theme;
        this.content = content;
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return "from: " + sender + "\n to: " + receiver + "\n nick: " + nick + "\n theme: " + theme + "\n content: " + content + "\n";
    }
}
