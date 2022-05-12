package com.work2home.publica.project.rest.controller;

import com.work2home.publica.project.rest.dto.login.GoogleLoginRequest;
import com.work2home.publica.project.rest.dto.login.LoginRequest;
import com.work2home.publica.project.rest.dto.login.LoginResponse;
import com.work2home.publica.project.rest.dto.refresh.RefreshRequest;
import com.work2home.publica.project.rest.dto.refresh.RefreshResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.service.LoginService;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@ApiOperation(value = "Loga um usuário")
	@PostMapping("/login")
	public LoginResponse logar(@RequestBody @Valid LoginRequest request) {
		LoginResponse response = loginService.logar(request);
		System.out.println(response);
		return response;
	}

	@ApiOperation(value = "Faz um refresh no token")
	@PostMapping("/refresh")
	public RefreshResponse refresh(@RequestBody @Valid RefreshRequest request) {
		return loginService.refresh(request);
	}

	@ApiOperation(value = "Faz a autenticação com uma conta do Google")
	@PostMapping("/google-login")
	public LoginResponse googleSignIn(@RequestBody GoogleLoginRequest request) {
		try {
			return loginService.googleSignIn(request.getGoogleToken());
		} catch (GeneralSecurityException | IOException ignored) {
		}
		return null;
	}
}
