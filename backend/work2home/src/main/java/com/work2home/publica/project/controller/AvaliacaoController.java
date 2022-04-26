package com.work2home.publica.project.controller;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.dto.main_service.AvaliarPrestadorDto;
import com.work2home.publica.project.dto.main_service.AvaliarClienteDto;
import com.work2home.publica.project.service.AvaliacaoService;


@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

	@Autowired
	private AvaliacaoService service;
	
	@RolesAllowed("ROLES_PRESTADOR")
	@PostMapping("/prestador-avalia-cliente")
	@ResponseStatus(HttpStatus.CREATED)
	public void avaliarCliente(@RequestBody @Valid AvaliarClienteDto avaliacaoDto) {
		service.avaliarCliente(avaliacaoDto);
	}
	
	@RolesAllowed("ROLES_CLIENTE")
	@PostMapping("/cliente-avalia-prestador")
	@ResponseStatus(HttpStatus.CREATED)
	public void avaliarPrestador(@RequestBody @Valid AvaliarPrestadorDto avaliacaoDto) {
		service.avaliarPrestador(avaliacaoDto);
	}
	

}
