package com.work2home.publica.userauthentication.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.userauthentication.dto.LoginDto;
import com.work2home.publica.userauthentication.model.Usuario;
import com.work2home.publica.userauthentication.repositores.LoginRepository;
import com.work2home.publica.userauthentication.service.LoginService;

@RestController("/login")
public class LoginController {

	@Autowired
	LoginRepository loginRepository;

	@Autowired
	LoginService loginService;

	@GetMapping
	public List<Usuario> buscaListaContas() {
		return loginService.buscarLogin();
	}
	
	@GetMapping("/{id}")
	public Usuario buscaUsuario(@PathVariable Integer id) {
		return loginService.buscarLoginId(id);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Usuario login(@RequestBody @Valid LoginDto loginDto) {
		return loginService.verificaLogin(loginDto);
	}

}
