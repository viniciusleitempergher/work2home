package com.work2home.publica.userauthentication.controller;

import java.util.List;

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

import com.work2home.publica.userauthentication.dto.PrestadorDto;
import com.work2home.publica.userauthentication.model.Prestador;
import com.work2home.publica.userauthentication.repositores.PrestadorRepository;
import com.work2home.publica.userauthentication.service.PrestadorService;

@RestController
@RequestMapping(value = "/prestador")
public class PrestadorController {

	@Autowired
	PrestadorRepository prestadorRepository;

	@Autowired
	PrestadorService prestadorService;

	@GetMapping
	public List<Prestador> buscaListaPrestador() {
		return prestadorService.buscarPrestador();
	}
	
	@GetMapping("/{id}")
	public Prestador buscaConta(@PathVariable Integer id) {
		return prestadorService.buscarPrestadorId(id);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Prestador cadastrarPrestador(@RequestBody @Valid PrestadorDto prestadorDto) {
		return prestadorService.cadastrarPrestador(prestadorDto);
	}

}
