package com.work2home.publica.project.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.dto.PrestadorDto;
import com.work2home.publica.project.dto.PrestadorResponseDto;
import com.work2home.publica.project.model.Prestador;
import com.work2home.publica.project.repositores.PrestadorRepository;
import com.work2home.publica.project.service.PrestadorService;

import lombok.Delegate;

@RestController
@RequestMapping(value = "/prestador")
public class PrestadorController {

	@Autowired
	private PrestadorRepository prestadorRepository;

	@Autowired
	private PrestadorService prestadorService;

	@GetMapping
	public List<Prestador> buscaListaPrestador() {
		return prestadorService.buscarPrestador();
	}
	
	@GetMapping("/{id}")
	public PrestadorResponseDto buscaConta(@PathVariable Integer id) {
		return prestadorService.buscarPrestadorId(id);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public PrestadorResponseDto cadastrarPrestador(@RequestBody @Valid PrestadorDto prestadorDto) {
		return prestadorService.cadastrarPrestador(prestadorDto);
	}
	
	@PutMapping("/{id}")
	public void alterarPrestador(@PathVariable Integer id, @RequestBody @Valid PrestadorDto prestadorDto) {
		
		prestadorService.alterarPrestador(id, prestadorDto);
	}
	
	@DeleteMapping("/{prestadorId}/cidade/{cidadeId}")
	public void removerCidadePrestador(@PathVariable Integer prestadorId, @PathVariable Integer cidadeId) {
		
		prestadorService.removerCidadePrestador(prestadorId, cidadeId);
	}
	
	@DeleteMapping("/{prestadorId}/categoria/{categoriaId}")
	public void removerCategoriaPrestador(@PathVariable Integer prestadorId, @PathVariable Integer categoriaId) {
		
		prestadorService.removerCidadePrestador(prestadorId, prestadorId);
	}

}
