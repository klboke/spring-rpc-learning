package com.spring.rpc.hessian.provider;

import com.spring.rpc.api.AccountService;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import javax.servlet.http.HttpServlet;

/**
 * Created by kl on 2018/9/29.
 * Content :hessian 服务提供者配置
 */
@Configuration
public class HessianConfig {

    @Bean
    public ServletRegistrationBean servletRegistrationBean(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean servlet = new ServletRegistrationBean();
        servlet.setServlet(dispatcherServlet);
        servlet.setName("remoting");
        servlet.setLoadOnStartup(1);
        servlet.addUrlMappings("/remoting/*");
        return servlet;
    }

    @Bean("/AccountService")
    public HessianServiceExporter hessianServiceExporter(AccountServiceImpl accountService) {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setServiceInterface(AccountService.class);
        exporter.setService(accountService);
        return exporter;
    }
}
