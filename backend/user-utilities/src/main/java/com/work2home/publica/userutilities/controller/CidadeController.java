package com.work2home.publica.userutilities.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController("/user-utilities/cidade")
public class CidadeController {
	
	
	@GetMapping("/error")
	public String retornaErro() {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
	}
}
