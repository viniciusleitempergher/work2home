package com.work2home.publica.project.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work2home.publica.project.dto.main_service.AvaliarPrestadorDto;
import com.work2home.publica.project.dto.main_service.AvaliarClienteDto;
import com.work2home.publica.project.repositores.AvaliacaoRepository;
import com.work2home.publica.project.repositores.OrdemServicoRepository;
import com.work2home.publica.project.repositores.UsuarioRepository;

@Service
public class AvaliacaoService {

	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	public void avaliarCliente(@Valid AvaliarClienteDto avaliacaoDto) {
		avaliacaoRepository.save(avaliacaoDto.converter(usuarioRepository, ordemServicoRepository));

	}

	public void avaliarPrestador(@Valid AvaliarPrestadorDto avaliacaoDto) {
		avaliacaoRepository.save(avaliacaoDto.converter(usuarioRepository, ordemServicoRepository));
	}
	
	
}
