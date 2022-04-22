package com.work2home.publica.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/cidade")
public class CidadeController {
	
	@GetMapping("/error")
	public String retornaErro() {
		return "oi";
	}
	
	@PostMapping
	public String retornaapsihfaoi() {
		return "FOI";
	}
}
