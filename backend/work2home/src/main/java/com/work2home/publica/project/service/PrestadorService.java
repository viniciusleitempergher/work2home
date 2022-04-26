package com.work2home.publica.project.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.dto.CategoriaPrestadorDto;
import com.work2home.publica.project.dto.PrestadorDto;
import com.work2home.publica.project.enums.Roles;
import com.work2home.publica.project.model.Categoria;
import com.work2home.publica.project.model.Cidade;
import com.work2home.publica.project.model.Prestador;
import com.work2home.publica.project.repositores.CategoriaRepository;
import com.work2home.publica.project.repositores.PrestadorRepository;
import com.work2home.publica.project.repositores.UsuarioRepository;

@Service
public class PrestadorService {

	@Autowired
	PrestadorRepository prestadorRepository;
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;

	public List<Prestador> buscarPrestador() {
		return prestadorRepository.findAll();
	}

	public Prestador buscarPrestadorId(Integer id) {
		return prestadorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public Prestador cadastrarPrestador(@Valid PrestadorDto prestadorDto) {
		usuarioRepository.findAll().forEach(usuario -> {
			if (usuario.getEmail().equalsIgnoreCase(prestadorDto.getUsuarioDto().getEmail())) 
				throw new ResponseStatusException(HttpStatus.CONFLICT);
		});
		
		Prestador prestador = prestadorDto.converter();
		prestador.getUsuario().setRole(Roles.PRESTADOR);
		
		usuarioRepository.save(prestadorDto.getUsuarioDto().converter());
		prestador.setCnpj(prestadorDto.getCnpj());
		prestador.setNomeFantasia(prestadorDto.getNomeFantasia());
		
		return prestadorRepository.save(prestador);
	}

	public void adicionarCidades(Integer prestadorId, Cidade cidade) {
		boolean contemNaLista=false;
		Prestador prestador = prestadorRepository.findById(prestadorId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		List<Cidade> cidades = prestador.getCidades();
		for(Cidade c :cidades) {
			if(c.getId()==cidade.getId()) {
				contemNaLista=true;
			}
		}
		if(!contemNaLista) {
			if (prestador.getCidades() == null) prestador.setCidades(new ArrayList<Cidade>());
			prestador.getCidades().add(cidade);
			prestadorRepository.save(prestador);
			
		}else {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
	}

	public Prestador adicionarCategoria(@Valid CategoriaPrestadorDto categoriaPrestadorDto) {
		Prestador prestador = prestadorRepository.findById(categoriaPrestadorDto.getPrestadorId()).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
		Categoria categoria = categoriaRepository.findById(categoriaPrestadorDto.getCategoriaId()).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
		if (prestador.getCategorias() == null) prestador.setCategorias(new HashSet<Categoria>());
		for(Categoria c : prestador.getCategorias()) {
			if(c.getId()==categoriaPrestadorDto.getCategoriaId()) {
				throw new ResponseStatusException(HttpStatus.CONFLICT);
			}
		}		
		prestador.getCategorias().add(categoria);
		return prestadorRepository.save(prestador);
		
	}
}