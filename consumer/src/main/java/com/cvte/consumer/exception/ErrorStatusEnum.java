package com.cvte.consumer.exception;

public enum ErrorStatusEnum {
    UNKNOW_ERROE(-1, "其他错误"),
    SEND_SUCCESS(0, "发送成功"),
    PARA_ERROR(1, "信息格式或内容有错误"),
    DATABASE_SAVE_ERROR(2, "数据库保存异常"),
    KAFKA_ERROR(3, "发送信息接口有误"),
    ;

    private Integer code;
    private String msg;

    ErrorStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
