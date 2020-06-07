package com.cdxt.inter.config.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description: 队列消费者
 * @Author: tangxiaohui
 * @CreateDate: 2020/6/7 0007 13:07
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Component
@Slf4j
public class QueueReceiver {

    @JmsListener(destination = "${spring.activemq.queueName}", containerFactory = "MyjmsQueueListener")
    public void receiveMap(Map<Object, Object> map) {
        log.info("receiveMap:{}", map);
    }
}
