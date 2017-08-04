package com.cvte.producer.domain.foreignInterface;

import org.springframework.context.annotation.Scope;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Scope("prototype")
public class InterfaceTable {
    @Id
    @GeneratedValue
    private Integer id;

    private String emailInterface;
    private String smsInterface;

    public Integer getId() {
        return id;
    }

    public String getEmailInterface() {
        return emailInterface;
    }

    public String getSmsInterface() {
        return smsInterface;
    }

    public void setEmailInterface(String emailInterface) {
        this.emailInterface = emailInterface;
    }

    public void setSmsInterface(String smsInterface) {
        this.smsInterface = smsInterface;
    }
}
