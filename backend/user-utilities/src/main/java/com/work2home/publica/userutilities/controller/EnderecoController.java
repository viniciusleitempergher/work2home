package com.work2home.publica.userutilities.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.userutilities.dto.EnderecoDto;
import com.work2home.publica.userutilities.model.Endereco;
import com.work2home.publica.userutilities.repositores.EnderecoRepository;
import com.work2home.publica.userutilities.services.EnderecoService;


@RestController
@RequestMapping(value="/endereco")
public class EnderecoController {
	
	@Autowired
	EnderecoRepository enderecoRepository;

	@Autowired
	EnderecoService enderecoService;

	@GetMapping
	public List<Endereco> buscaListaContas() {

		return enderecoService.buscarEndereco();
	}
	@GetMapping("/{id}")
	public Endereco buscaCliente(@PathVariable Integer id) {
		return enderecoService.buscarClienteId(id);
	}
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Endereco cadastrarCliente(@RequestBody @Valid EnderecoDto enderecoDto) {
		return enderecoService.cadastrar(enderecoDto);
	}
}
