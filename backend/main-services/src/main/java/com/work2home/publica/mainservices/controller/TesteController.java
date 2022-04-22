package com.work2home.publica.mainservices.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/main-services")
public class TesteController {
	
	@GetMapping("/")
	public String teste2() {
		return "oi";
	}
	
	@GetMapping("/teste")
	public String teste() {
		return "user utilities service working!";
	}
	
}
