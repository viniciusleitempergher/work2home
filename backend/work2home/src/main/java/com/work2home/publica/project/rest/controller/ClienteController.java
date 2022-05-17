package com.work2home.publica.project.rest.controller;

import java.util.List;

import javax.validation.Valid;

import com.work2home.publica.project.rest.dto.cliente.ClienteCompletarCadastroRequest;
import com.work2home.publica.project.rest.dto.cliente.ClienteRequest;
import com.work2home.publica.project.rest.dto.cliente.ClienteResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.work2home.publica.project.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@ApiOperation(value = "Lista todos os clientes")
	@GetMapping
	public List<ClienteResponse> listarClientes() {
		return clienteService.buscarCliente();
	}

	@ApiOperation(value = "Busca um cliente por id")
	@GetMapping("/{id}")
	public ClienteResponse buscaCliente(@PathVariable Integer id) {
		return clienteService.buscarClienteId(id);
	}

	@ApiOperation(value = "Cadastra um cliente")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void cadastrarCliente(@RequestBody @Valid ClienteRequest clienteDto) {
		 clienteService.cadastrarCliente(clienteDto);
	}

	@ApiOperation(value = "Altera o cliente logado")
	@PutMapping
	public void alterarCliente(@RequestBody @Valid ClienteRequest clienteDto) {
		clienteService.alterarCliente(clienteDto);
	}

	@ApiOperation(value = "Completa o cadastro do cliente logado")
	@PutMapping("/completar-cadastro")
	public void completarCadastro(@RequestBody @Valid ClienteCompletarCadastroRequest clienteDto) {
		clienteService.completarCadastro(clienteDto);
	}
}
