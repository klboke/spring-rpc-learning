package com.spring.rpc.http.consumer;

import com.spring.rpc.api.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpComponentsHttpInvokerRequestExecutor;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

/**
 * Created by kl on 2018/9/29.
 * Content :http消费者（服务调用者）配置
 */
@Configuration
public class HttpConfig {

    @Bean("accountService")
    public HttpInvokerProxyFactoryBean accountService(){
        HttpInvokerProxyFactoryBean factoryBean = new HttpInvokerProxyFactoryBean();
        factoryBean.setHttpInvokerRequestExecutor(new HttpComponentsHttpInvokerRequestExecutor());
        factoryBean.setServiceUrl("http://127.0.0.1:8081/remoting/AccountService");
        factoryBean.setServiceInterface(AccountService.class);
        return factoryBean;
    }
}
