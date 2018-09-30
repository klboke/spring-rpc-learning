package com.spring.rpc.amqp.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
public class AmqpProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmqpProviderApplication.class, args);
	}
}
