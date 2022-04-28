package com.work2home.publica.project.rest.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import com.work2home.publica.project.rest.dto.cliente.ClienteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.work2home.publica.project.model.Cliente;
import com.work2home.publica.project.repositores.ClienteRepository;
import com.work2home.publica.project.service.ClienteService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ClienteService clienteService;

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
	public Cliente cadastrarCliente(@RequestBody @Valid ClienteRequest clienteDto) {
		return clienteService.cadastrarCliente(clienteDto);
	}
	
	@RolesAllowed("ROLES_CLIENTE")
	@PutMapping
	public void alterarCliente(@RequestBody @Valid ClienteRequest clienteDto) {
		
		clienteService.alterarCliente(clienteDto);
	}

}
