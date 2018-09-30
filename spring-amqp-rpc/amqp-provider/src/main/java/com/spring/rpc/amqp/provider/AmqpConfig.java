package com.spring.rpc.amqp.provider;

import com.spring.rpc.api.AccountService;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.remoting.service.AmqpInvokerServiceExporter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kl on 2018/9/29.
 * Content : amqp服务提供者配置
 */
@Configuration
public class AmqpConfig {

    //RabbitMQ的配置信息
    private String host = "192.168.1.204";
    private Integer port = 5672;
    private String username = "admin";
    private String password = "123456";
    private String virtualHost = "/app/kl";
    private int channelCacheSize = 10;

    @Bean("listener")
    public AmqpInvokerServiceExporter rmiServiceExporter(AccountServiceImpl accountService, AmqpTemplate template) {
        AmqpInvokerServiceExporter exporter = new AmqpInvokerServiceExporter();
        exporter.setService(accountService);
        exporter.setServiceInterface(AccountService.class);
        exporter.setAmqpTemplate(template);
        return exporter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setRoutingKey("remoting.binding");
        rabbitTemplate.setExchange("remoting.exchange");
        return rabbitTemplate;
    }

    @Bean
    public Queue queue(ConnectionFactory connectionFactory) {
        AmqpAdmin admin = new RabbitAdmin(connectionFactory);
        Queue queue = new Queue("kl.remoting.rpc.queue");
        admin.declareQueue(queue);
        DirectExchange requestExchange = new DirectExchange("remoting.exchange");
        admin.declareExchange(requestExchange);
        Binding requestBinding = BindingBuilder.bind(queue).to(requestExchange).with("remoting.binding");
        admin.declareBinding(requestBinding);
        return queue;
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, AmqpInvokerServiceExporter listenerAdapter) {
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueueNames("kl.remoting.rpc.queue");
        listenerContainer.setMessageListener(listenerAdapter);
        listenerContainer.setExposeListenerChannel(true);
        listenerContainer.setMaxConcurrentConsumers(40);
        listenerContainer.setConcurrentConsumers(25);
        listenerContainer.start();
        return listenerContainer;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        connectionFactory.setExecutor(executor);
        connectionFactory.setChannelCacheSize(channelCacheSize);
        return connectionFactory;
    }
}
