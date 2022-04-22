package com.work2home.publica.mainservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan
public class MainServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainServicesApplication.class, args);
	}

}
