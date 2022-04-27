package com.work2home.publica.project.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

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

import com.work2home.publica.project.dto.cliente.ClienteDto;
import com.work2home.publica.project.dto.prestador.PrestadorDto;
import com.work2home.publica.project.model.Cliente;
import com.work2home.publica.project.repositores.ClienteRepository;
import com.work2home.publica.project.service.ClienteService;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {
	
	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	ClienteService clienteService;

	@GetMapping
	public List<Cliente> buscaListaCliente() {
		return clienteService.buscarCliente();
	}
	
	@GetMapping("/{id}")
	public Cliente buscaCliente(@PathVariable Integer id) {
		return clienteService.buscarClienteId(id);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cliente cadastrarCliente(@RequestBody @Valid ClienteDto clienteDto) {
		return clienteService.cadastrarCliente(clienteDto);
	}
	
	@RolesAllowed("ROLES_CLIENTE")
	@PutMapping
	public void alterarCliente(@RequestBody @Valid ClienteDto clienteDto) {
		
		clienteService.alterarCliente(clienteDto);
	}

}
