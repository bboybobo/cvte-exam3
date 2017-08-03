package com.cvte.producer.exception;

//结果状态枚举
public enum ResultStatusEnum {
    UNKNOW_ERROE(-1,"未知错误"),
    ASY_SEND_SUCCESS(0,"正在发送，请稍后查询"),
    PARA_ERROR(1,"您传入的参数有误,注意有些参数不能为空值，注意参数类型"),
    DATABASE_SAVE_ERROR(2,"数据库保存异常，请检查您的参数是否有误"),
    KAFKA_ERROR(3,"加入消息队列失败"),
    ;

    private Integer statusCode;
    private String msg;

    ResultStatusEnum(Integer statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getMsg() {
        return msg;
    }
}
