package com.cvte.consumer.exception;

public class ConsumerException extends RuntimeException {
    private Integer code;
    private Integer initDetailId;
    private String status;
    public ConsumerException(Integer code,Integer initDetailId, String status,String msg) {
        super(msg);
        this.code = code;
        this.initDetailId = initDetailId;
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getInitDetailId() {
        return initDetailId;
    }

    public void setInitDetailId(Integer initDetailId) {
        this.initDetailId = initDetailId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
