package com.work2home.publica.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.work2home.publica.project.dto.EnderecoDto;
import com.work2home.publica.project.model.Endereco;
import com.work2home.publica.project.repositores.EnderecoRepository;

public class EnderecoService {

	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	
	CidadeService cidadeService;

	public List<Endereco> buscarEndereco() {
		return enderecoRepository.findAll();
	}

	public Endereco buscarClienteId(Integer id) {
		return enderecoRepository.findById(id).orElseThrow(() -> new RuntimeException());
	}

	public Endereco cadastrar(EnderecoDto enderecoDto) {
		Endereco endereco = new Endereco();
		
		endereco.setCidade(cidadeService.converter(enderecoDto));
		endereco.setBairro(enderecoDto.getBairro());
		endereco.setComplemento(enderecoDto.getComplemento());
		endereco.setNumero(enderecoDto.getNumero());
		endereco.setLogradouro(enderecoDto.getEndereco());

		return enderecoRepository.save(endereco);
	}
}
