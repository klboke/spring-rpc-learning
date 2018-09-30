package com.spring.rpc.amqp.consumer;

import com.spring.rpc.api.AccountService;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.remoting.client.AmqpProxyFactoryBean;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kl on 2018/9/29.
 * Content :amqp消费者（服务调用者）配置
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

    @Bean("accountService")
    public AmqpProxyFactoryBean accountService(AmqpTemplate template){
        AmqpProxyFactoryBean factoryBean = new AmqpProxyFactoryBean();
        factoryBean.setAmqpTemplate(template);
        factoryBean.setServiceInterface(AccountService.class);
        return factoryBean;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setReplyTimeout(5000);
        rabbitTemplate.setRoutingKey("remoting.binding");
        rabbitTemplate.setExchange("remoting.exchange");
        return rabbitTemplate;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        ExecutorService executor = Executors.newFixedThreadPool(11);
        connectionFactory.setExecutor(executor);
        connectionFactory.setChannelCacheSize(channelCacheSize);
        return connectionFactory;
    }
}
