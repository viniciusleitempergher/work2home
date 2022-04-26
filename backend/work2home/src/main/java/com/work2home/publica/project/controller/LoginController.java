package com.work2home.publica.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.dto.LoginRequest;
import com.work2home.publica.project.dto.LoginResponse;
import com.work2home.publica.project.dto.refresh.RefreshRequest;
import com.work2home.publica.project.dto.refresh.RefreshResponse;
import com.work2home.publica.project.service.LoginService;

@RestController
@RequestMapping("/auth")
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@PostMapping("/login")
	public LoginResponse logar(@RequestBody LoginRequest request) {
		return loginService.logar(request);
	}

	@PostMapping("/refresh")
	public RefreshResponse refresh(@RequestBody RefreshRequest request) {
		return loginService.refresh(request);
	}
}
