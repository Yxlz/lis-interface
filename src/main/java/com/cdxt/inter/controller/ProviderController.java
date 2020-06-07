package com.cdxt.inter.controller;

import com.cdxt.inter.config.jms.ActivemqMessagingTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: MQ消息提供者
 * @Author: tangxiaohui
 * @CreateDate: 2020/6/7 0007 13:09
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@RestController
public class ProviderController {
    @Resource
    private ActivemqMessagingTemplate activemqMessagingTemplate;

    /*自定义队列名称*/
    @Value("${spring.activemq.queueName}")
    private String queueName;

    @GetMapping("/value")
    public String value() {
        Map<Object, Object> map = new HashMap<>();
        map.put("name", "小明");
        map.put("age", 11);
        activemqMessagingTemplate.sendMapMessage(queueName, map);   //发送到MQ
        return "消息已经发送";
    }

}
