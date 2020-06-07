package com.cdxt.inter.config.jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.support.destination.DestinationResolver;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

/**
 * @Description: 自定义配置JMS
 * @Author: tangxiaohui
 * @CreateDate: 2020/6/7 0007 12:58
 * @Copyright: Copyright (c) 2020
 * @Company: 成都信通网易医疗科技发展有限公司
 * @Version: 1.0
 */
@Configuration
@EnableJms
public class JmsConfig {
    @Value("${spring.activemq.broker-url}")
    private String activeMQURL;
    @Value("${spring.activemq.user}")
    private String userName;
    @Value("${spring.activemq.password}")
    private String password;

    /**
     * @return:  org.springframework.jms.config.DefaultJmsListenerContainerFactory
     * @description: JMS 队列的监听容器工厂
     * @Param jmsConnectionFactory:
     * @date: 2020/6/7 0007 14:18
     */
    @Bean(name = "MyjmsQueueListener")
    public DefaultJmsListenerContainerFactory jmsQueueListenerContainerFactory(@Qualifier("MyConnectionFactory") ConnectionFactory jmsConnectionFactory) {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();

        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(jmsConnectionFactory);

        factory.setConnectionFactory(cachingConnectionFactory);

        factory.setSessionTransacted(true);
        factory.setConcurrency("5");
        DestinationResolver destinationResolver = (session, destinationName, pubSubDomain) -> {
            assert session != null;
            return (Destination) session.createQueue(destinationName);
        };

        factory.setDestinationResolver(destinationResolver);
        return factory;
    }

    /**
     * @return:  org.springframework.jms.config.DefaultJmsListenerContainerFactory
     * @description: JMS 主题的监听容器工厂
     * @Param jmsConnectionFactory:
     * @date: 2020/6/7 0007 14:19
     */
    @Bean(name = "MyjmsTopicListener")
    public DefaultJmsListenerContainerFactory jmsTopicListenerContainerFactory(@Qualifier("MyConnectionFactory") ConnectionFactory jmsConnectionFactory) {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();

        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(jmsConnectionFactory);
        factory.setConnectionFactory(cachingConnectionFactory);

        factory.setPubSubDomain(true);
        factory.setSessionTransacted(true);
        factory.setConcurrency("6");

        return factory;
    }

    /**
     * @return:  javax.jms.ConnectionFactory
     * @description: JMS个人连接工厂，这里可以配置kfka,activeMQ,rabbitMQ...
     * @date: 2020/6/7 0007 14:20
     */
    @Bean(name = "MyConnectionFactory")
    public ConnectionFactory jmsConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

        connectionFactory.setBrokerURL(activeMQURL);
        connectionFactory.setUserName(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setTrustAllPackages(true);
        connectionFactory.setMaxThreadPoolSize(ActiveMQConnection.DEFAULT_THREAD_POOL_SIZE);
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();

        //定义ReDelivery(重发机制)机制 ，重发时间间隔是100毫秒，最大重发次数是3次
        //是否在每次尝试重新发送失败后,增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        //重发次数,默认为6次   这里设置为1次
        redeliveryPolicy.setMaximumRedeliveries(1);
        //重发时间间隔,默认为1秒
        redeliveryPolicy.setInitialRedeliveryDelay(1000);
        //第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
        redeliveryPolicy.setBackOffMultiplier(2);
        //最大传送延迟，只在useExponentialBackOff为true时有效（V5.5），假设首次重连间隔为10ms，倍数为2，那么第
        //二次重连时间间隔为 20ms，第三次重连时间间隔为40ms，当重连时间间隔大的最大重连时间间隔时，以后每次重连时间间隔都为最大重连时间间隔。
        redeliveryPolicy.setMaximumRedeliveryDelay(1000);
        connectionFactory.setRedeliveryPolicy(redeliveryPolicy);

        return connectionFactory;
    }
}
