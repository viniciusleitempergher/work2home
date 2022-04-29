package com.work2home.publica.project.rest.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import com.work2home.publica.project.rest.dto.endereco.CidadesPretadorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.model.Cidade;
import com.work2home.publica.project.service.CidadeService;

@RestController
@RequestMapping("/cidade")
public class CidadeController {
	
	@Autowired
	private CidadeService cidadeService;
	
	@GetMapping
	public List<Cidade> buscaListaCidade() {
		return cidadeService.buscarCidades();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade cadastrarCidade(@RequestBody @Valid CidadesPretadorRequest cidadesPretadorDto) {
		return cidadeService.cadastrarCidadePrestador(cidadesPretadorDto);
	}

}
