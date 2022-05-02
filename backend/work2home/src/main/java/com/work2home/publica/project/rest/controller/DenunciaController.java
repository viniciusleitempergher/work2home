package com.work2home.publica.project.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.model.Denuncia;
import com.work2home.publica.project.rest.dto.denuncia.DenunciaRequest;
import com.work2home.publica.project.service.DenunciaService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/denucia")
public class DenunciaController {
	
	@Autowired
	private DenunciaService denunciaService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void denunciar(@RequestBody DenunciaRequest denunciaRequest) {
		denunciaService.denunciar(denunciaRequest);
	}
	
	@GetMapping("/{denunciadoId}")
	public List<Denuncia> listarDenuncias(@PathVariable Integer denunciadoId){
		return denunciaService.buscarPorDenunciado(denunciadoId);
	}
}
