package com.work2home.publica.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work2home.publica.project.dto.EnderecoDto;
import com.work2home.publica.project.model.Cidade;
import com.work2home.publica.project.repositores.CidadeRepository;

@Service
public class CidadeService {
	@Autowired
	CidadeRepository cidadeRepository;
	
	public Cidade converter(EnderecoDto enderecoDto) {
		Cidade cidade = cidadeRepository.findByNomeAndEstado(enderecoDto.getCidade(),enderecoDto.getEstado());
		if(cidade == null) {
			cidade = cidadeRepository.save(Cidade.builder().estado(enderecoDto.getEstado()).nome(enderecoDto.getCidade()).build());
		}		
		return cidade;
	}
}
