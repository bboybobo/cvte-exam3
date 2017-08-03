package com.cvte.producer.web;

import com.cvte.producer.domain.EmailInitDetail;
import com.cvte.producer.service.EmailSendImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @Autowired
    private EmailSendImp emailSendImp;

    @PostMapping(value = "/asyEmail")
    public void emailAsySend(EmailInitDetail emailInitDetail){
        //emailSendImp.emailAsySend();
    }
}
