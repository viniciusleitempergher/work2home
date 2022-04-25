package com.work2home.publica.project.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.dto.CidadesPretadorDto;
import com.work2home.publica.project.model.Cidade;
import com.work2home.publica.project.service.CidadeService;

@RestController
@RequestMapping("/cidade")
public class CidadeController {
	
	@Autowired
	CidadeService cidadeService;
	
	@GetMapping("/error")
	public String retornaErro() {
		return "oi";
	}
	

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cidade cadastrarPrestador(@RequestBody @Valid CidadesPretadorDto cidadesPretadorDto) {
		return cidadeService.cadastrarCidadePrestador(cidadesPretadorDto);
	}


}
