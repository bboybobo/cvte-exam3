package com.cvte.producer.web;

import com.cvte.producer.domain.ReturnData;
import com.cvte.producer.domain.email.EmailInitDetail;
import com.cvte.producer.service.EmailSend;
import com.cvte.producer.service.EmailSendImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @Autowired
    private EmailSendImp emailSendImp;

    //异步发送邮件
    @PostMapping(value = "/asyEmail")
    public ReturnData emailAsySend(EmailInitDetail detail){
        return  emailSendImp.emailAsySend(detail.getSender(),
                detail.getNick(),
                detail.getSendNums(),
                detail.getRevicers(),
                detail.getTheme(),
                detail.getTemplete(),
                detail.getParams(),
                detail.isNeedReturn()
                );
    }

    //同步发送邮件
    @PostMapping(value = "/synEmail")
    public ReturnData emailSynSend(EmailInitDetail detail){
        return emailSendImp.emailSynSend(detail.getSender(),
                detail.getNick(),
                detail.getSendNums(),
                detail.getRevicers(),
                detail.getTheme(),
                detail.getTemplete(),
                detail.getParams(),
                detail.isNeedReturn()
                );
    }

}
