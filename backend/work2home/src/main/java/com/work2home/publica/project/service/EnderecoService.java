package com.work2home.publica.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.dto.EnderecoDto;
import com.work2home.publica.project.model.Cliente;
import com.work2home.publica.project.model.Endereco;
import com.work2home.publica.project.repositores.ClienteRepository;
import com.work2home.publica.project.repositores.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired	
	CidadeService cidadeService;
	
	@Autowired
	ClienteRepository clienteRepository;

	public List<Endereco> buscarEndereco() {
		return enderecoRepository.findAll();
	}

	public Endereco buscarClienteId(Integer id) {
		return enderecoRepository.findById(id).orElseThrow(() -> new RuntimeException());
	}

	public Endereco cadastrar(EnderecoDto enderecoDto) {
		Endereco endereco = new Endereco();
		Cliente cliente = new Cliente();
		cliente=clienteRepository.findById(enderecoDto.getIdCliente()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		if( cliente.getEndereco() != null) {
			endereco = cliente.getEndereco();
		}
		
		endereco.setCliente(cliente);
		endereco.setCidade(cidadeService.converter(enderecoDto));
		endereco.setBairro(enderecoDto.getBairro());
		endereco.setComplemento(enderecoDto.getComplemento());
		endereco.setNumero(enderecoDto.getNumero());
		endereco.setLogradouro(enderecoDto.getEndereco());

		endereco = enderecoRepository.save(endereco);
		
		cliente.setEndereco(endereco);
		clienteRepository.save(cliente);
		return endereco;
	}
}
