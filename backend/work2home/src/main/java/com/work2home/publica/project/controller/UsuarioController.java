package com.work2home.publica.project.controller;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.dto.UsuarioResponse;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.utils.JwtUtil;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	private JwtUtil jwt;
	
	@GetMapping("/me")
	public UsuarioResponse getMe() throws IllegalAccessException, InvocationTargetException {
		String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();

		Usuario user = jwt.getUserFromAccessToken(token);
		
		UsuarioResponse ur = new UsuarioResponse();
		BeanUtils.copyProperties(ur, user);
		
		return ur;
	}
}
