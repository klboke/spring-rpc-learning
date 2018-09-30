package com.spring.rpc.hessian.consumer;

import com.spring.rpc.api.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

/**
 * Created by kl on 2018/9/29.
 * Content :hessian 消费者（服务调用者）配置
 */
@Configuration
public class HessianConfig {

    @Bean("accountService")
    public HessianProxyFactoryBean hessianProxyFactoryBean(){
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceInterface(AccountService.class);
        factoryBean.setServiceUrl("http://127.0.0.1:8080/remoting/AccountService");
        return factoryBean;
    }
}
