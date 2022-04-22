package com.work2home.publica.springcloundconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.ComponentScan;

@EnableConfigServer
@SpringBootApplication
@ComponentScan
public class SpringCloundConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloundConfigServerApplication.class, args);
	}

}
