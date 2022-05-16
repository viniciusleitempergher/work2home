package com.work2home.publica.project.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work2home.publica.project.rest.dto.endereco.CidadesPretadorRequest;
import com.work2home.publica.project.rest.dto.endereco.EnderecoRequest;
import com.work2home.publica.project.model.Cidade;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.CidadeRepository;
import com.work2home.publica.project.utils.JwtUtil;

@Service
public class CidadeService {
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private PrestadorService prestadorService;
	
	@Autowired
	private JwtUtil jwt;
	
	public Cidade converter(EnderecoRequest enderecoDto) {
		return verificaECadastra(enderecoDto.getEstado(), enderecoDto.getCidade());
	}

	public void cadastrarCidadePrestador(@Valid CidadesPretadorRequest cidadesPretadorDto) {
		
		Usuario usuario = jwt.getUserFromHeaderToken();
		
		Cidade cidade = verificaECadastra(cidadesPretadorDto.getEstado(), cidadesPretadorDto.getNome());
		prestadorService.adicionarCidades(usuario.getId() ,cidade);
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
