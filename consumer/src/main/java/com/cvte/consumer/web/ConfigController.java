package com.cvte.consumer.web;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {
    @PutMapping(value = "/config")
    public void refreshConfig(){

    }
}
