package com.cvte.producer.exception;


public class ProducerException extends RuntimeException{
    private Integer statusCode;

    public ProducerException(ResultStatusEnum resultStatusEnum){
        super(resultStatusEnum.getMsg());
        this.statusCode = resultStatusEnum.getStatusCode();
    }
    public ProducerException(Integer statusCode,String msg){
        super(msg);
        this.statusCode = statusCode;
    }


}
