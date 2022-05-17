package com.work2home.publica.project.service;

import javax.validation.Valid;

import com.work2home.publica.project.enums.Roles;
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

import java.util.Objects;

@Service
public class AvaliacaoService {

	@Autowired
	private AvaliacaoRepository avaliacaoRepository;

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@Autowired
	private JwtUtil jwt;

	public void avaliarCliente(Integer OrdemServicoId, @Valid AvaliarClienteDto avaliacaoDto) {

		OrdemServico os = ordemServicoRepository.findById(OrdemServicoId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

		Usuario usuario = jwt.getUserFromHeaderToken();

		if(avaliacaoJaExiste(usuario.getId(), os.getId(), os.getPrestador().getId())){
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}

		avaliacaoRepository.save(avaliacaoDto.converter(os));
	}

	public void avaliarPrestador(Integer OrdemServicoId, @Valid AvaliarPrestadorDto avaliacaoDto) {

		OrdemServico os = ordemServicoRepository.findById(OrdemServicoId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

		Usuario usuario = jwt.getUserFromHeaderToken();

		if(avaliacaoJaExiste(usuario.getId(), os.getId(), os.getEndereco().getCliente().getId())){
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}

		avaliacaoRepository.save(avaliacaoDto.converter(os));
	}
	
	private boolean avaliacaoJaExiste(Integer avaliadorId, Integer osId, Integer avaliadorOsId) {

		if (!Objects.equals(avaliadorId, avaliadorOsId)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return avaliacaoRepository.findByAvaliadorIdAndOrdemServicoId(avaliadorId, osId).isPresent();
	}

    public Boolean avaliacaoExiste(Integer osId) {

		Usuario usuario = jwt.getUserFromHeaderToken();
		System.out.println(osId);
		OrdemServico os = ordemServicoRepository.findById(osId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

		if(usuario.getRole() == Roles.CLIENTE){
			return avaliacaoJaExiste(usuario.getId(), osId, os.getEndereco().getCliente().getId());
		}else if(usuario.getRole() == Roles.PRESTADOR){
			return avaliacaoJaExiste(usuario.getId(), osId, os.getPrestador().getId());
		}else{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
    }
}
