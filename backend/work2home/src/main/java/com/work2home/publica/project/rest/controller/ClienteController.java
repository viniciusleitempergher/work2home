package com.work2home.publica.project.rest.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import com.work2home.publica.project.rest.dto.cliente.ClienteRequest;
import com.work2home.publica.project.rest.dto.cliente.ClienteResponse;
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
	private ClienteService clienteService;

	@GetMapping
	public List<ClienteResponse> buscaListaCliente() {
		return clienteService.buscarCliente();
	}
	
	@GetMapping("/{id}")
	public ClienteResponse buscaCliente(@PathVariable Integer id) {
		return clienteService.buscarClienteId(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente cadastrarCliente(@RequestBody @Valid ClienteRequest clienteDto) {
		System.out.println(clienteDto);
		return clienteService.cadastrarCliente(clienteDto);
	}
	
	@PutMapping
	public void alterarCliente(@RequestBody @Valid ClienteRequest clienteDto) {
		
		clienteService.alterarCliente(clienteDto);
	}

}
