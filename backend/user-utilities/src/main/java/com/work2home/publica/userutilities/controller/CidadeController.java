package com.work2home.publica.userutilities.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cidade")
public class CidadeController {
	
	@GetMapping("/error")
	public void retornaErro() {

	}
}
