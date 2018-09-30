package com.spring.rpc.jms.consumer;

import com.spring.rpc.api.AccountService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.remoting.JmsInvokerProxyFactoryBean;

/**
 * Created by kl on 2018/9/29.
 * Content :jms消费者（服务调用者）配置
 */
@Configuration
public class JmsConfig {

    @Bean("connectionFactory")
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setTrustAllPackages(true);
        connectionFactory.setBrokerURL("tcp://127.0.0.1:61616");
        return connectionFactory;
    }

    @Bean("accountService")
    public JmsInvokerProxyFactoryBean accountService(ActiveMQConnectionFactory connectionFactory,ActiveMQQueue queue){
        JmsInvokerProxyFactoryBean factoryBean = new JmsInvokerProxyFactoryBean();
        factoryBean.setConnectionFactory(connectionFactory);
        factoryBean.setServiceInterface(AccountService.class);
        factoryBean.setQueue(queue);
        return factoryBean;
    }

    @Bean("queue")
    public ActiveMQQueue activeMQQueue() {
        return new ActiveMQQueue("mmm");
    }
}
