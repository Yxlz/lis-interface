package com.cdxt.inter.config.jms;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.io.Serializable;
import java.util.Map;

/**
 * @Description: MQ消息发送模板
 * @Author: tangxiaohui
 * @CreateDate: 2020/6/7 0007 13:11
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Component
@Slf4j
public class ActivemqMessagingTemplate {
    /**
     * MQ jms实例
     **/
    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;
    /**
     * 线程池
     **/
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * @return:  void
     * @description: 向队列发送map信息
     * @Param queueName: 队列名称
     * @Param messageMap:  map
     * @date: 2020/6/7 0007 14:40
     */
    public void sendMapMessage(String queueName, Map<Object, Object> messageMap) {
        threadPoolTaskExecutor.submit(() -> {
            try {
                Destination destination = new ActiveMQQueue(queueName);
                ActiveMQMapMessage mqMapMessage = new ActiveMQMapMessage();
                mqMapMessage.setJMSDestination(destination);
                mqMapMessage.setObject("result", messageMap);
                this.jmsMessagingTemplate.convertAndSend(destination, mqMapMessage);
            } catch (Throwable e) {
                log.error("{}", e);
            }
        });
    }

    /**
     * @return:  void
     * @description: 向队列发送对象信息
     * @Param queueName: 队列名称
     * @Param message:  消息体（须实现序列化接口Serializable）
     * @date: 2020/6/7 0007 14:38
     */
    public void sendObjectMessage(String queueName, Object message) {
        threadPoolTaskExecutor.submit(() -> {
            try {
                log.info("【queue-->send】:activeCount={},queueCount={},completedTaskCount={},taskCount={}", threadPoolTaskExecutor.getThreadPoolExecutor().getActiveCount(), threadPoolTaskExecutor.getThreadPoolExecutor().getQueue().size(), threadPoolTaskExecutor.getThreadPoolExecutor().getCompletedTaskCount(), threadPoolTaskExecutor.getThreadPoolExecutor().getTaskCount());
                Destination destination = new ActiveMQQueue(queueName);
                ActiveMQObjectMessage mqObjectMessage = new ActiveMQObjectMessage();
                mqObjectMessage.setJMSDestination(destination);
                mqObjectMessage.setObject((Serializable) message);
                this.jmsMessagingTemplate.convertAndSend(destination, mqObjectMessage);
            } catch (Throwable e) {
                log.error("{}", e);
            }
        });
    }

    /**
     * @return:  void
     * @description: 向队列发送对象信息
     * @Param destination: 队列
     * @Param message:  消息体（须实现序列化接口Serializable）
     * @date: 2020/6/7 0007 14:39
     */
    public void sendObjectMessage(Destination destination, Object message) {
        threadPoolTaskExecutor.submit(() -> {
            try {
                log.info("【queue-->send】:activeCount={},queueCount={},completedTaskCount={},taskCount={}", threadPoolTaskExecutor.getThreadPoolExecutor().getActiveCount(), threadPoolTaskExecutor.getThreadPoolExecutor().getQueue().size(), threadPoolTaskExecutor.getThreadPoolExecutor().getCompletedTaskCount(), threadPoolTaskExecutor.getThreadPoolExecutor().getTaskCount());
                ActiveMQObjectMessage mqObjectMessage = new ActiveMQObjectMessage();
                mqObjectMessage.setJMSDestination(destination);
                mqObjectMessage.setObject((Serializable) message);
                this.jmsMessagingTemplate.convertAndSend(destination, mqObjectMessage);
            } catch (Throwable e) {
                log.error("{}", e);
            }
        });
    }
}