package com.spring.rpc.hessian.consumer;

import com.spring.rpc.api.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class HessianConsumerApplication {

	@Autowired
	private AccountService accountService;

	@PostConstruct
	public void callRpcService(){
		System.out.println("RPC远程访问开始！");
		System.err.println(accountService.getAccount("kl").getName());
		System.out.println("RPC远程访问结束！");
	}

	public static void main(String[] args) {
		SpringApplication.run(HessianConsumerApplication.class, args);
	}
}
