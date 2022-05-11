package com.work2home.publica.project.rest.controller;

import javax.validation.Valid;

import com.work2home.publica.project.rest.dto.endereco.EnderecoRequest;
import com.work2home.publica.project.rest.dto.endereco.EnderecoResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.service.EnderecoService;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

	@Autowired
	private EnderecoService enderecoService;

	@ApiOperation(value = "Busca o endereço pelo id do cliente")
 	@GetMapping("{clienteId}")
	public EnderecoResponse buscarEndreco(@PathVariable Integer clienteId ) {
		return enderecoService.buscarEndereco(clienteId);
	}

	@ApiOperation(value = "Cadastra o endereço do cliente logado")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void cadastrarEndereco(@RequestBody @Valid EnderecoRequest enderecoDto) {
		enderecoService.cadastrar(enderecoDto);
	}

	@ApiOperation(value = "Altera o endereço do cliente logado")
	@PutMapping
	public void alterarEndereco(@RequestBody @Valid EnderecoRequest enderecoDto) {
		enderecoService.cadastrar(enderecoDto);
	}
}
