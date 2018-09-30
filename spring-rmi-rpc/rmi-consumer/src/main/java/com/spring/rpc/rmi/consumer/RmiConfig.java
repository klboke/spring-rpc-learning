package com.spring.rpc.rmi.consumer;

import com.spring.rpc.api.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

/**
 * Created by kl on 2018/9/29.
 * Content :rmi消费者（服务调用者）配置
 */
@Configuration
public class RmiConfig {

    @Bean("accountService")
    public RmiProxyFactoryBean accountService(){
        RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();
        factoryBean.setServiceUrl("rmi://127.0.0.1:1199/AccountService");
        factoryBean.setServiceInterface(AccountService.class);
        return factoryBean;
    }
}
