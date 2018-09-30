package com.spring.rpc.rmi.provider;

import com.spring.rpc.api.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

/**
 * Created by kl on 2018/9/29.
 * Content : rmi服务提供者配置
 */
@Configuration
public class RmiConfig {

    @Bean
    public RmiServiceExporter rmiServiceExporter(AccountServiceImpl accountService){
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName("AccountService");
        exporter.setService(accountService);
        exporter.setServiceInterface(AccountService.class);
        exporter.setRegistryPort(1199);
        return exporter;
    }
}
