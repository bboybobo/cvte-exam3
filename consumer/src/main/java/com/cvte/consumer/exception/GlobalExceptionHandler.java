package com.cvte.consumer.exception;

import com.cvte.consumer.domain.status.EmailStatus;
import com.cvte.consumer.domain.status.EmailStatusRepository;
import com.cvte.consumer.domain.status.SmsStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
public class GlobalExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public void handle(Exception e){
//       handleException(e);
//    }

    public  EmailStatus handleEmailException(Exception e){
        EmailStatus emailStatus = new EmailStatus();
        if(e instanceof ConsumerException){
            ConsumerException consumerExeption = (ConsumerException)e;
            emailStatus.setCode(consumerExeption.getCode());
            emailStatus.setDetailMsg(consumerExeption.getMessage());
            emailStatus.setStatus(consumerExeption.getStatus());
            emailStatus.setEmailInitDetailId(consumerExeption.getInitDetailId());
        }else {
            emailStatus.setCode(ErrorStatusEnum.UNKNOW_ERROE.getCode());
            emailStatus.setDetailMsg(e.getMessage());
            emailStatus.setStatus(ErrorStatusEnum.UNKNOW_ERROE.getMsg());
        }
        logger.error("出现异常：{}",emailStatus);
        return emailStatus;
    }

    public  SmsStatus handleSmsException(Exception e){
        SmsStatus smsStatus = new SmsStatus();
        if(e instanceof ConsumerException){
            ConsumerException consumerExeption = (ConsumerException)e;
            smsStatus.setCode(consumerExeption.getCode());
            smsStatus.setDetailMsg(consumerExeption.getMessage());
            smsStatus.setStatus(consumerExeption.getStatus());
            smsStatus.setSmsInitDetailId(consumerExeption.getInitDetailId());
        }else {
            smsStatus.setCode(ErrorStatusEnum.UNKNOW_ERROE.getCode());
            smsStatus.setDetailMsg(e.getMessage());
            smsStatus.setStatus(ErrorStatusEnum.UNKNOW_ERROE.getMsg());
        }
        logger.error("出现异常：{}",smsStatus);
        return smsStatus;
    }
}
