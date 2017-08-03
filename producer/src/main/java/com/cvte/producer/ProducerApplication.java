package com.cvte.producer;

import com.cvte.producer.service.EmailSend;
import com.cvte.producer.service.EmailSendImp;
import com.cvte.producer.service.SmsSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@SpringBootApplication
public class ProducerApplication {


	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ProducerApplication.class, args);

		EmailSend emailSend = context.getBean(EmailSend.class);
		LinkedList<String> receivers = new LinkedList();
		receivers.add("a@qq.com");
		receivers.add("b@cvte.com");
		String template = "你好{$name},你的验证码为{$value}";

		LinkedList<Map<String, String>> params = new LinkedList();
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("name", "xubobo");
		map1.put("value","0123");
		params.add(map1);

		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("name", "zhangyan");
		map2.put("value","2222");
		params.add(map2);
		emailSend.emailAsySend("a@bbc.com","tony",2,receivers,
				"topic",template, params,true);


		SmsSend smsSend = context.getBean(SmsSend.class);
		LinkedList<String> receivers2 = new LinkedList();
		receivers2.add("15116350011");
		receivers2.add("15116350000");
		String template2 = "你好{$name},你的验证码为{$value}";

		LinkedList<Map<String, String>> params2 = new LinkedList();
		Map<String, String> map21 = new HashMap<String, String>();
		map21.put("name", "许先生");
		map21.put("value","0123");
		params2.add(map21);

		Map<String, String> map22 = new HashMap<String, String>();
		map22.put("name", "张先生");
		map22.put("value","2222");
		params2.add(map22);
		smsSend.smsAsySend("15116350264",2,receivers2,
				template2,params2,true);
	}
}
