package com.spring.rpc.http.provider;

import com.spring.rpc.api.AccountService;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Created by kl on 2018/9/29.
 * Content : http服务提供者配置
 */
@Configuration
public class HttpConfig {

    @Bean("/AccountService")
    public HttpInvokerServiceExporter rmiServiceExporter(AccountServiceImpl accountService){
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(accountService);
        exporter.setServiceInterface(AccountService.class);
        return exporter;
    }
    @Bean
    public ServletRegistrationBean servletRegistrationBean(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean servlet = new ServletRegistrationBean();
        servlet.setServlet(dispatcherServlet);
        servlet.setName("remoting");
        servlet.setLoadOnStartup(1);
        servlet.addUrlMappings("/remoting/*");
        return servlet;
    }
}
