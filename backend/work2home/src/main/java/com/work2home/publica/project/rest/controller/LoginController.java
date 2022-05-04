package com.work2home.publica.project.rest.controller;

import com.work2home.publica.project.rest.dto.login.LoginRequest;
import com.work2home.publica.project.rest.dto.login.LoginResponse;
import com.work2home.publica.project.rest.dto.refresh.RefreshRequest;
import com.work2home.publica.project.rest.dto.refresh.RefreshResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.service.LoginService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@PostMapping("/login")
	public LoginResponse logar(@RequestBody @Valid LoginRequest request) {
		LoginResponse response = loginService.logar(request);
		System.out.println(response);
		return response;
	}

	@PostMapping("/refresh")
	public RefreshResponse refresh(@RequestBody @Valid RefreshRequest request) {
		return loginService.refresh(request);
	}
	
	@PostMapping("/google-login")
	public void logarGoogle(RequestEntity<Object> request) {
		System.out.println(request.getBody());
		System.out.println(request.getHeaders());
		
	}
}
