package com.work2home.publica.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.utils.JwtUtil;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	private JwtUtil jwt;
	
	@GetMapping("/me")
	public Usuario getMe() {
		String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();

		Usuario user = jwt.getUserFromAccessToken(token);
		
		return user;
	}
}
