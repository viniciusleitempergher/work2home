package com.work2home.publica.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work2home.publica.project.dto.LoginRequest;
import com.work2home.publica.project.dto.LoginResponse;
import com.work2home.publica.project.repositores.UsuarioRepository;

@Service
public class LoginService {

	@Autowired
	UsuarioRepository usuarioRepository;

	public LoginResponse logar(LoginRequest request) {
		
		//request.g
		
		LoginResponse response = LoginResponse.builder().accessToken(null).refreshToken(null).build();
		
		return response;
	}
}
