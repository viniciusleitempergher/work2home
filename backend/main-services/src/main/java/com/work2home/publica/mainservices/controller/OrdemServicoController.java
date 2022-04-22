package com.work2home.publica.mainservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.work2home.publica.mainservices.models.OrdemServico;
import com.work2home.publica.mainservices.services.OrdemServicoService;

@RestController("/main-services")
public class OrdemServicoController {

	@Autowired
	private OrdemServicoService service;
	
	@GetMapping("/")
	public String teste2() {
		return "oi";
	}
	
}
