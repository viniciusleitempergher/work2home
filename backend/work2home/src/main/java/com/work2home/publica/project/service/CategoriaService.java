package com.work2home.publica.project.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work2home.publica.project.model.Categoria;
import com.work2home.publica.project.model.Prestador;
import com.work2home.publica.project.repositores.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Autowired
	PrestadorService prestadorService;

	public Categoria cadastrarCategoria(@Valid Categoria categoria) {
		
		return categoriaRepository.save(categoria);
	}

	public List<Categoria> buscarCategorias() {
		return categoriaRepository.findAll();
	}

	public Prestador cadastrarCategoriaPrestador(Integer categoriaId) {
		return prestadorService.adicionarCategoria(categoriaId);
		
	}
	
}
