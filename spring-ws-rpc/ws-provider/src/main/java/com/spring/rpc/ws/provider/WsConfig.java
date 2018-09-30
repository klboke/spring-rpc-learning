package com.spring.rpc.ws.provider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.jaxws.SimpleJaxWsServiceExporter;

/**
 * Created by kl on 2018/9/29.
 * Content : ws服务提供者配置
 */
@Configuration
public class WsConfig {

    @Bean
    public SimpleJaxWsServiceExporter rmiServiceExporter() {
        SimpleJaxWsServiceExporter exporter = new SimpleJaxWsServiceExporter();
        exporter.setBaseAddress("http://127.0.0.1:8083/");
        return exporter;
    }
}
