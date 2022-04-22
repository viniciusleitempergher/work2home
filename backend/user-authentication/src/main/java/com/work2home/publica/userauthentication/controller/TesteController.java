package com.work2home.publica.userauthentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user-authentication")
public class TesteController {
	
	@GetMapping("/")
	public String teste2() {
		return "oi";
	}
	
	@GetMapping("/teste")
	public String teste() {
		return "user authentication service working!";
	}
	
}
