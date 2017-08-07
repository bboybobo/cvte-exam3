package com.cvte.producer.web;

import com.cvte.producer.domain.ReturnData;
import com.cvte.producer.domain.sms.SmsInitDetail;
import com.cvte.producer.service.SmsSendImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {
    @Autowired
    private SmsSendImp smsSendImp;

    //同步发送邮件
    @PostMapping(value = "/asySms")
    public ReturnData smsAsySend(SmsInitDetail detail){
        return smsSendImp.smsAsySend(detail.getSender(),
                detail.getSendNums(),
                detail.getRevicers(),
                detail.getTemplete(),
                detail.getParams(),
                detail.isNeedReturn()
                );
    }

    //同步发送短信
    @PostMapping(value = "/synSms")
    public ReturnData smsSynSend(SmsInitDetail detail){
        return smsSendImp.smsSynSend(detail.getSender(),
                detail.getSendNums(),
                detail.getRevicers(),
                detail.getTemplete(),
                detail.getParams(),
                detail.isNeedReturn()
                );
    }
}
