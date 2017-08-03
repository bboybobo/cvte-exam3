package com.cvte.producer.domain;

import com.cvte.producer.exception.ResultStatusEnum;

//返回类型的类
public class ReturnData {
    //状态码
    private Integer statusCode;
    //提示信息
    private String msg;
    //具体内容
    private Object object;

    public ReturnData(Integer statusCode, String msg, Object object) {
        this.statusCode = statusCode;
        this.msg = msg;
        this.object = object;
    }

    public ReturnData(ResultStatusEnum rs,Object object ){
        this.statusCode = rs.getStatusCode();
        this.msg = rs.getMsg();
        this.object = object;
    }
    public Integer getStatusCode() {
        return statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public Object getObject() {
        return object;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "ReturnData{" +
                "statusCode=" + statusCode +
                ", msg='" + msg + '\'' +
                ", object=" + object +
                '}';
    }
}
