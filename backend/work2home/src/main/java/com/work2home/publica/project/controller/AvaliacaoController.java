package com.work2home.publica.project.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.dto.main_service.AvaliarPrestadorDto;
import com.work2home.publica.project.dto.main_service.AvaliarClienteDto;
import com.work2home.publica.project.model.Avaliacao;
import com.work2home.publica.project.service.AvaliacaoService;
import com.work2home.publica.project.service.OrdemServicoService;



@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

	@Autowired
	private AvaliacaoService service;
	
	@PostMapping("/prestador-avalia-cliente")
	public void avaliarCliente(@RequestBody @Valid AvaliarClienteDto avaliacaoDto) {
		service.avaliarCliente(avaliacaoDto);
	}
	
	@PostMapping("/cliente-avalia-prestador")
	public void avaliarPrestador(@RequestBody @Valid AvaliarPrestadorDto avaliacaoDto) {
		service.avaliarPrestador(avaliacaoDto);
	}
}
