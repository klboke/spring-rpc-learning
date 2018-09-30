package com.spring.rpc.ws.provider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.jaxws.SimpleHttpServerJaxWsServiceExporter;

/**
 * Created by kl on 2018/9/29.
 * Content : ws服务提供者配置
 */
@Configuration
public class WsConfig {

    private String ipList = "127.0.0.1";
    private String userName = "admin";
    private String passWord = "sasa";

    @Bean
    public SimpleHttpServerJaxWsServiceExporter rmiServiceExporter(Authenticator authenticator) {
        SimpleHttpServerJaxWsServiceExporter exporter = new SimpleHttpServerJaxWsServiceExporter();
        exporter.setHostname("127.0.0.1");
        exporter.setPort(8083);
        exporter.setAuthenticator(authenticator);
        return exporter;
    }

    @Bean
    public Authenticator authenticator(){
        Authenticator authenticator = new Authenticator();
        authenticator.setIpList(ipList);
        authenticator.setUserName(userName);
        authenticator.setPassWord(passWord);
        return authenticator;
    }
}
