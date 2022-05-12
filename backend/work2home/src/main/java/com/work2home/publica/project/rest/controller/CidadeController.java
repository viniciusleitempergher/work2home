package com.work2home.publica.project.rest.controller;

import javax.validation.Valid;

import com.work2home.publica.project.rest.dto.endereco.CidadesPretadorRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.service.CidadeService;

@RestController
@RequestMapping("/cidade")
public class CidadeController {
	
	@Autowired
	private CidadeService cidadeService;

	@ApiOperation(value = "Cadastra uma cidade")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void cadastrarCidade(@RequestBody @Valid CidadesPretadorRequest cidadesPretadorDto) {
		cidadeService.cadastrarCidadePrestador(cidadesPretadorDto);
	}
}
