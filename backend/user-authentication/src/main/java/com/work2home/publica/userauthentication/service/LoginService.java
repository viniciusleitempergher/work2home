package com.work2home.publica.userauthentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work2home.publica.userauthentication.dto.LoginRequest;
import com.work2home.publica.userauthentication.dto.LoginResponse;
import com.work2home.publica.userauthentication.repositores.UsuarioRepository;

@Service
public class LoginService {

	@Autowired
	UsuarioRepository usuarioRepository;

	public LoginResponse logar(LoginRequest request) {
		
	}
}
