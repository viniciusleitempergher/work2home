package com.work2home.publica.project.rest.controller;

import java.lang.reflect.InvocationTargetException;

import com.work2home.publica.project.service.UsuarioService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.work2home.publica.project.rest.dto.usuario.UsuarioResponse;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.utils.JwtUtil;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	private JwtUtil jwt;

	@Autowired
	private UsuarioService service;
	
	@GetMapping("/me")
	public UsuarioResponse getMe() throws IllegalAccessException, InvocationTargetException {

		Usuario user = jwt.getUserFromHeaderToken();
		UsuarioResponse ur = new UsuarioResponse();
		BeanUtils.copyProperties(ur, user);
		return ur;
	}

	@PostMapping("/imagem")
	@ResponseStatus(HttpStatus.CREATED)
	public void cadastrarImagem(@RequestParam("image") MultipartFile multipartFile) {
		service.cadastrarImagem(multipartFile);

	}
}
