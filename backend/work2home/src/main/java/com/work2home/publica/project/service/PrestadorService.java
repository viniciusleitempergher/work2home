package com.work2home.publica.project.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.dto.PrestadorDto;
import com.work2home.publica.project.model.Cidade;
import com.work2home.publica.project.model.Prestador;
import com.work2home.publica.project.repositores.PrestadorRepository;
import com.work2home.publica.project.repositores.UsuarioRepository;

@Service
public class PrestadorService {

	@Autowired
	PrestadorRepository prestadorRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;

	public List<Prestador> buscarPrestador() {
		return prestadorRepository.findAll();
	}

	public Prestador buscarPrestadorId(Integer id) {
		return prestadorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public Prestador cadastrarPrestador(@Valid PrestadorDto prestadorDto) {
		Prestador prestador = prestadorDto.converter();
		
		usuarioRepository.save(prestadorDto.getUsuarioDto().converter());
		prestador.setCnpj(prestadorDto.getCnpj());
		prestador.setNomeFantasia(prestadorDto.getNomeFantasia());
		
		return prestadorRepository.save(prestador);
	}

	public void adicionarCidades(Integer prestador_id, Cidade cidade) {
		boolean contemNaLista=false;
		Prestador prestador = prestadorRepository.getById(prestador_id);
		List<Cidade> cidades = prestador.getCidades();
		for(Cidade c :cidades) {
			if(c.getId()==cidade.getId()) {
				contemNaLista=true;
			}
		}
		if(!contemNaLista) {
			cidades.add(cidade);
			prestador.setCidades(cidades);
			prestadorRepository.save(prestador);
		}else {
			System.out.println("Essa cidade já existe na lista");
		}
	}
}