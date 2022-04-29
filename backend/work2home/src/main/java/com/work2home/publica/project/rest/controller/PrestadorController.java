package com.work2home.publica.project.rest.controller;

import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import com.work2home.publica.project.rest.dto.prestador.PrestadorFiltroRequest;
import com.work2home.publica.project.rest.dto.prestador.PrestadorFiltroResponse;
import com.work2home.publica.project.rest.dto.prestador.PrestadorRequest;
import com.work2home.publica.project.rest.dto.prestador.PrestadorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.model.Prestador;
import com.work2home.publica.project.service.CategoriaService;
import com.work2home.publica.project.service.PrestadorService;

@RestController
@RequestMapping("/prestador")
public class PrestadorController {

	@Autowired
	private PrestadorService prestadorService;
	
	@Autowired
	private CategoriaService categoriaService;

	@GetMapping
	public List<PrestadorResponse> buscaListaPrestador() {
		return prestadorService.buscarPrestador();
	}
	
	@GetMapping("/{id}")
	public PrestadorResponse buscaConta(@PathVariable Integer id) {
		
		return prestadorService.buscarPrestadorId(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PrestadorResponse cadastrarPrestador(@RequestBody @Valid PrestadorRequest prestadorDto) {
		return prestadorService.cadastrarPrestador(prestadorDto);
	}
	
	@PutMapping
	public void alterarPrestador(@RequestBody @Valid PrestadorRequest prestadorDto) {
		prestadorService.alterarPrestador(prestadorDto);
	}

	@PostMapping("/{id}/categoria")
	@ResponseStatus(HttpStatus.CREATED)
	public Prestador cadastrarCategoriaPrestador(@PathVariable Integer id) {
		
		return categoriaService.cadastrarCategoriaPrestador(id);
	}

	@DeleteMapping("/cidade/{cidadeId}")
	public void removerCidadePrestador(@PathVariable Integer cidadeId) {
		
		prestadorService.removerCidadePrestador(cidadeId);
	}
	
	@DeleteMapping("/categoria/{categoriaId}")
	public void removerCategoriaPrestador(@PathVariable Integer categoriaId) {
		
		prestadorService.removerCategoriaPrestador(categoriaId);
	}
	
	@GetMapping("/filtro")
	public List<PrestadorFiltroResponse> filtrarPrestadores(@Valid @RequestBody PrestadorFiltroRequest pfr) {
		return prestadorService.filtrarPrestadores(pfr);
	}
}
