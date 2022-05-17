package com.work2home.publica.project.rest.controller;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.rest.dto.denuncia.DenunciaRequest;
import com.work2home.publica.project.rest.dto.denuncia.DenunciaResponse;
import com.work2home.publica.project.rest.dto.usuario.UsuarioDenunciasResponse;
import com.work2home.publica.project.service.DenunciaService;

@RestController
@RequestMapping("/denuncia")
public class DenunciaController {
	
	@Autowired
	private DenunciaService denunciaService;

	@ApiOperation(value = "Denuncia um usuário")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void denunciar(@RequestBody  DenunciaRequest denunciaRequest) {
		denunciaService.denunciar(denunciaRequest);
	}

	@ApiOperation(value = "Lista a quantidade de denúncia por usuário")
	@GetMapping
	public List<DenunciaResponse> listaQtdDenuncia(){
		return denunciaService.buscarDenunciaPorQtd();
	}

	@ApiOperation(value = "Lista as denúncias pelo id do usuário")
	@GetMapping("/{denunciadoId}")
	public UsuarioDenunciasResponse listarDenuncias(@PathVariable Integer denunciadoId){
		return denunciaService.buscarPorDenunciado(denunciadoId);
	}
}
