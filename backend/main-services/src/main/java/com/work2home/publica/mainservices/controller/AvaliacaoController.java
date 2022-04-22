package com.work2home.publica.mainservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.mainservices.models.Avaliacao;
import com.work2home.publica.mainservices.services.OrdemServicoService;

@RestController("main-services/avaliacao")
public class AvaliacaoController {

	@Autowired
	private OrdemServicoService service;

	@GetMapping("avaliacao/teste")
	public String avaliation() {
		
		Avaliacao a = new Avaliacao();
		
		
		return "testee";
	}

}
