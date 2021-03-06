package com.cvte.producer.util;

import com.cvte.producer.domain.email.EmailInitDetail;
import com.cvte.producer.domain.sms.SmsInitDetail;
import com.cvte.producer.exception.ProducerException;
import com.cvte.producer.exception.ResultStatusEnum;

public class CheckNull {
    public static Exception checkEmail(EmailInitDetail emailInitDetail){
        if (emailInitDetail.getRevicers() == null
                || emailInitDetail.getSender() == null
                || emailInitDetail.getSendNums() == null
                || emailInitDetail.getTemplete() == null
                || emailInitDetail.getTheme() == null){
            return new ProducerException(ResultStatusEnum.PARA_ERROR);
        }else {
            return null;
        }
    }

    public static Exception checkSms(SmsInitDetail smsInitDetail){
        if (smsInitDetail.getRevicers() == null
                || smsInitDetail.getSender() == null
                || smsInitDetail.getSendNums() == null
                || smsInitDetail.getTemplete() == null
        ){
            return new ProducerException(ResultStatusEnum.PARA_ERROR);
        }else {
            return null;
        }
    }
}
