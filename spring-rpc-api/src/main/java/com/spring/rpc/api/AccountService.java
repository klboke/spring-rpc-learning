package com.spring.rpc.api;

import javax.jws.WebService;

/**
 * @WebService 注解只用于ws 提供的RPC服务
 */
@WebService
public interface AccountService {
     Account getAccount(String name);
}