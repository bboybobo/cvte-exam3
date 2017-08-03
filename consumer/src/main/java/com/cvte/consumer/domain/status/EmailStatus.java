package com.cvte.consumer.domain.status;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class EmailStatus {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer emailInitDetailId;      //外键
    private Integer code;
    private String status;
    private String detailMsg;

    public Integer getId() {
        return id;
    }

    public Integer getEmailInitDetailId() {
        return emailInitDetailId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public String getDetailMsg() {
        return detailMsg;
    }

    public void setEmailInitDetailId(Integer emailInitDetailId) {
        this.emailInitDetailId = emailInitDetailId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }

    @Override
    public String toString() {
        return "EmailStatus{" +
                "id=" + id +
                ", emailInitDetailId=" + emailInitDetailId +
                ", code=" + code +
                ", status='" + status + '\'' +
                ", detailMsg='" + detailMsg + '\'' +
                '}';
    }
}
