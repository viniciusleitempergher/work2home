package com.work2home.publica.project.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.rest.dto.avaliacao.AvaliarClienteDto;
import com.work2home.publica.project.rest.dto.avaliacao.AvaliarPrestadorDto;
import com.work2home.publica.project.model.OrdemServico;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.AvaliacaoRepository;
import com.work2home.publica.project.repositores.OrdemServicoRepository;
import com.work2home.publica.project.repositores.UsuarioRepository;
import com.work2home.publica.project.utils.JwtUtil;

@Service
public class AvaliacaoService {

	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private JwtUtil jwt;

	public void avaliarCliente(Integer OrdemServicoId, @Valid AvaliarClienteDto avaliacaoDto) {
		
		OrdemServico os = ordemServicoRepository
				.findById(OrdemServicoId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		Usuario usuario = jwt.getUserFromHeaderToken(); 
		
		if(usuario.getId() != os.getPrestador().getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		avaliacaoRepository.save(avaliacaoDto.converter(usuarioRepository, os));

	}

	public void avaliarPrestador(Integer OrdemServicoId, @Valid AvaliarPrestadorDto avaliacaoDto) {
		
		OrdemServico os = ordemServicoRepository
				.findById(OrdemServicoId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		Usuario usuario = jwt.getUserFromHeaderToken(); 
		
		if(usuario.getId() != os.getEndereco().getCliente().getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		avaliacaoRepository.save(avaliacaoDto.converter(usuarioRepository, os));
	}
	
	
}
