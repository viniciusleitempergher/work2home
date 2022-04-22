package com.work2home.publica.mainservices.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.work2home.publica.mainservices.models.OrdemServico;
import com.work2home.publica.mainservices.services.OrdemServicoService;

@RestController("/main-services")
public class OrdemServicoController {

	@Autowired
	private OrdemServicoService service;
	
	@GetMapping("/all")
	public List<OrdemServico>findAll(){
		System.out.print("olaa");
		return service.findAll();
	}
	
	
	@GetMapping("/oi")
	public String teste2() {
		return "oi";
	}
	
}
