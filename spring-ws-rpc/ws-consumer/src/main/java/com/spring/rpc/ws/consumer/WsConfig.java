package com.spring.rpc.ws.consumer;

import com.spring.rpc.api.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpComponentsHttpInvokerRequestExecutor;
import org.springframework.remoting.jaxws.JaxWsPortProxyFactoryBean;

import java.net.URL;


/**
 * Created by kl on 2018/9/29.
 * Content :ws消费者（服务调用者）配置
 */
@Configuration
public class WsConfig {

    @Bean("accountService")
    public JaxWsPortProxyFactoryBean accountService()throws Exception{
        JaxWsPortProxyFactoryBean factoryBean = new JaxWsPortProxyFactoryBean();
        factoryBean.setServiceName("AccountService");
        factoryBean.setPortName("AccountServiceImplPort");
        factoryBean.setNamespaceUri("http://provider.ws.rpc.spring.com/");
        URL wsdlDocumentUrl = new URL("http://127.0.0.1:8083/AccountServiceImpl?WSDL");
        factoryBean.setWsdlDocumentUrl(wsdlDocumentUrl);
        factoryBean.setServiceInterface(AccountService.class);
        return factoryBean;
    }
}
