package com.work2home.publica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan
@EnableScheduling
public class Work2HomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(Work2HomeApplication.class, args);
	}
}
