package com.spring.rpc.ws.provider;

import com.spring.rpc.api.Account;
import com.spring.rpc.api.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(serviceName="AccountService",endpointInterface = "com.spring.rpc.api.AccountService")
@Service
public class AccountServiceImpl  extends SpringBeanAutowiringSupport implements  AccountService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @WebMethod
    public Account getAccount(String name) {
        logger.info("{} 请求获取账号！", name);
        Account account = new Account();
        account.setName(name + "的账号");
        return account;
    }
}