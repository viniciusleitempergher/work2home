package com.work2home.publica.project.rest.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.rest.dto.usuario.UsuarioRequest;
import com.work2home.publica.project.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createAdmin(@RequestBody @Valid UsuarioRequest requestBody) {
		System.out.println("teste"+requestBody.getNome());
		adminService.cadastrar(requestBody);
	}
	
	@PatchMapping("/banir/{usuarioId}")
	public void banirUsuario(@PathVariable Integer usuarioId) {
		adminService.banirUsuario(usuarioId);
	}
}
