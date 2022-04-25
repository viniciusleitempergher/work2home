package com.work2home.publica.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.model.Avaliacao;
import com.work2home.publica.project.service.OrdemServicoService;



@RestController("main-services/avaliacao")
public class AvaliacaoController {

	@Autowired
	private OrdemServicoService service;



}
