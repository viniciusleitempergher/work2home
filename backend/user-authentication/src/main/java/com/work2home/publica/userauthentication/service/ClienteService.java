package com.work2home.publica.userauthentication.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work2home.publica.userauthentication.dto.ClienteDto;
import com.work2home.publica.userauthentication.model.Cliente;
import com.work2home.publica.userauthentication.model.Usuario;
import com.work2home.publica.userauthentication.repositores.ClienteRepository;
import com.work2home.publica.userauthentication.repositores.LoginRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	LoginRepository loginRepository;
	
	public List<Cliente> buscarContas() {
		return clienteRepository.findAll();
	}

	public Cliente buscarClienteId(Integer id) {
		return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException());
	}

	public Cliente cadastrarCliente(ClienteDto clienteDto) {

		Usuario usuario = new Usuario();
		usuario= clienteDto.converter(clienteDto);
		Cliente cliente = new Cliente();
		cliente.setUsuario(loginRepository.save(usuario));
		cliente.setCpf(clienteDto.getCpf());
		System.out.println(cliente);
		
		return clienteRepository.save(cliente);
	}
	
}
