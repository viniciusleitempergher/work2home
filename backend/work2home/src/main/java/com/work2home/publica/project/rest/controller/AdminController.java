package com.work2home.publica.project.rest.controller;

import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.rest.dto.usuario.UsuarioRequest;
import com.work2home.publica.project.service.AdminService;

@RestController("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createAdmin(@RequestBody @Valid UsuarioRequest requestBody) {
		adminService.cadastrar(requestBody);
	}
	
}
