package com.work2home.publica.project.rest.controller;

import javax.validation.Valid;
import com.work2home.publica.project.rest.dto.avaliacao.AvaliarClienteDto;
import com.work2home.publica.project.rest.dto.avaliacao.AvaliarPrestadorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.service.AvaliacaoService;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

	@Autowired
	private AvaliacaoService service;

	@PostMapping("/prestador-avalia-cliente/{ordemServicoId}")
	@ResponseStatus(HttpStatus.CREATED)
	public void avaliarCliente(@PathVariable Integer ordemServicoId, @RequestBody @Valid AvaliarClienteDto avaliacaoDto) {
		service.avaliarCliente(ordemServicoId, avaliacaoDto);
	}
	
	@PostMapping("/cliente-avalia-prestador/{ordemServicoId}")
	@ResponseStatus(HttpStatus.CREATED)
	public void avaliarPrestador(@PathVariable Integer ordemServicoId, @RequestBody @Valid AvaliarPrestadorDto avaliacaoDto) {
		service.avaliarPrestador(ordemServicoId, avaliacaoDto);
	}
}
