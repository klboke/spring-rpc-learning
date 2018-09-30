package com.spring.rpc.http.provider;

import com.spring.rpc.api.Account;
import com.spring.rpc.api.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Account getAccount(String name) {
        logger.info("{} 请求获取账号！", name);
        Account account = new Account();
        account.setName(name + "的账号");
        return account;
    }
}