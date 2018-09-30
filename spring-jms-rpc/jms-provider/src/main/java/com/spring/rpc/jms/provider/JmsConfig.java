package com.spring.rpc.jms.provider;

import com.spring.rpc.api.AccountService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.remoting.JmsInvokerServiceExporter;

/**
 * Created by kl on 2018/9/29.
 * Content : jms服务提供者配置
 */
@Configuration
public class JmsConfig {

    @Bean("jmsInvokerServiceExporter")
    public JmsInvokerServiceExporter jmsInvokerServiceExporter(AccountService accountService) {
        JmsInvokerServiceExporter exporter = new JmsInvokerServiceExporter();
        exporter.setServiceInterface(AccountService.class);
        exporter.setService(accountService);
        return exporter;
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(ActiveMQConnectionFactory connectionFactory,
                                                                         ActiveMQQueue queue,
                                                                         JmsInvokerServiceExporter jmsInvokerServiceExporter){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setDestination(queue);
        container.setConcurrentConsumers(3);
        container.setMessageListener(jmsInvokerServiceExporter);
        return container;
    }

    @Bean("connectionFactory")
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setTrustAllPackages(true);
        connectionFactory.setBrokerURL("tcp://127.0.0.1:61616");
        return connectionFactory;
    }

    @Bean("queue")
    public ActiveMQQueue activeMQQueue() {
        return new ActiveMQQueue("mmm");
    }
}
