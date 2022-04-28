package com.work2home.publica.project.rest.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import com.work2home.publica.project.rest.dto.endereco.EnderecoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.model.Endereco;
import com.work2home.publica.project.service.EnderecoService;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

	@Autowired
	private EnderecoService enderecoService;

	@GetMapping
	public List<Endereco> buscaListaContas() {
		return enderecoService.buscarEndereco();
	}
	
	@RolesAllowed("ROLES_CLIENTE")
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Endereco cadastrarEndereco(@RequestBody @Valid EnderecoRequest enderecoDto) {
		return enderecoService.cadastrar(enderecoDto);
	}
	
	@RolesAllowed("ROLES_CLIENTE")
	@PutMapping
	public void alterarEndereco(@RequestBody @Valid EnderecoRequest enderecoDto) {
		enderecoService.cadastrar(enderecoDto);
	}
}
