package com.work2home.publica.project.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work2home.publica.project.dto.CidadesPretadorDto;
import com.work2home.publica.project.dto.EnderecoDto;
import com.work2home.publica.project.model.Cidade;
import com.work2home.publica.project.repositores.CidadeRepository;

@Service
public class CidadeService {
	@Autowired
	CidadeRepository cidadeRepository;
	
	@Autowired
	PrestadorService prestadorService;
	
	public Cidade converter(EnderecoDto enderecoDto) {
	
		return verificaECadastra(enderecoDto.getEstado(), enderecoDto.getCidade());
	}

	public Cidade cadastrarCidadePrestador(@Valid CidadesPretadorDto cidadesPretadorDto) {
		Cidade cidade = verificaECadastra(cidadesPretadorDto.getEstado(), cidadesPretadorDto.getCidade());
		prestadorService.adicionarCidades(cidadesPretadorDto.getPrestador_id(),cidade);
		return cidade;
	}
	
	public Cidade verificaECadastra(String estado, String cdd) {
		Cidade cidade = cidadeRepository.findByNomeAndEstado(cdd,estado);
		if(cidade == null) {
			cidade = cidadeRepository.save(Cidade.builder().estado(estado).nome(cdd).build());
		}
		return cidade;
	}

	public List<Cidade> buscarCidades() {
		
		return cidadeRepository.findAll();
	}
	
}
