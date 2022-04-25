package com.work2home.publica.project.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.dto.ClienteDto;
import com.work2home.publica.project.model.Cliente;
import com.work2home.publica.project.repositores.ClienteRepository;
import com.work2home.publica.project.repositores.UsuarioRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public List<Cliente> buscarCliente() {
		return clienteRepository.findAll();
	}

	public Cliente buscarClienteId(Integer id) {
		return clienteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public Cliente cadastrarCliente(ClienteDto clienteDto) {
		
		usuarioRepository.findAll().forEach(usuario -> {
			if (usuario.getEmail().equalsIgnoreCase(clienteDto.getUsuarioDto().getEmail())) 
				throw new ResponseStatusException(HttpStatus.CONFLICT);
		});
		
		Cliente cliente = clienteDto.converter();
		
		usuarioRepository.save(cliente.getUsuario());
		
		return clienteRepository.save(cliente);
	}
	
}
