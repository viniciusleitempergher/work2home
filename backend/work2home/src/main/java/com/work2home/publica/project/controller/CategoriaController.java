package com.work2home.publica.project.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.dto.CategoriaPrestadorDto;
import com.work2home.publica.project.model.Categoria;
import com.work2home.publica.project.model.Prestador;
import com.work2home.publica.project.service.CategoriaService;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
	
	@Autowired
	CategoriaService categoriaService;
	
	@GetMapping
	public List<Categoria> listaCategorias(){
		return categoriaService.buscarCategorias();
	}
	
	@RolesAllowed("ROLES_ADMIN")
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Categoria cadastrarCategoria(@RequestBody @Valid Categoria categoria) {
		return categoriaService.cadastrarCategoria(categoria);
	}
	
	@RolesAllowed("ROLES_PRESTADOR")
	@PostMapping("/{id}/prestador")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Prestador cadastrarCategoriaPrestador(@PathVariable Integer id) {
		
		return categoriaService.cadastrarCategoriaPrestador(id);
	}
}	
