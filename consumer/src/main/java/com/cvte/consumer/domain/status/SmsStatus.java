package com.cvte.consumer.domain.status;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SmsStatus {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer smsInitDetailId;      //外键
    private Integer code;
    private String status;
    private String detailMsg;

    public Integer getId() {
        return id;
    }

    public Integer getSmsInitDetailId() {
        return smsInitDetailId;
    }

    public Integer getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getDetailMsg() {
        return detailMsg;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSmsInitDetailId(Integer smsInitDetailId) {
        this.smsInitDetailId = smsInitDetailId;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }

    @Override
    public String toString() {
        return "SmsStatus{" +
                "id=" + id +
                ", smsInitDetailId=" + smsInitDetailId +
                ", code=" + code +
                ", status='" + status + '\'' +
                ", detailMsg='" + detailMsg + '\'' +
                '}';
    }
}
