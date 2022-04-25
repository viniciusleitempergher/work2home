package com.work2home.publica.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.model.Avaliacao;
import com.work2home.publica.project.service.AvaliacaoService;
import com.work2home.publica.project.service.OrdemServicoService;



@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

	@Autowired
	private AvaliacaoService service;
	
	@PostMapping("/{ordemServicoId}/prestador")
	public void avaliar() {
		
	}



}
