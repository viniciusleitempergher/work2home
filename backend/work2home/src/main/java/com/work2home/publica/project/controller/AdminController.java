package com.work2home.publica.project.controller;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.dto.usuario.UsuarioDto;
import com.work2home.publica.project.service.AdminService;

@RolesAllowed("ROLES_ADMIN")
@RestController("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	public void createAdmin(@RequestBody @Valid UsuarioDto requestBody) {
		adminService.cadastrar(requestBody);
	}
}
