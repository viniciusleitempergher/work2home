package com.work2home.publica.project.rest.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.UUID;

import com.work2home.publica.project.model.Email;
import com.work2home.publica.project.rest.dto.email.EmailRequest;
import com.work2home.publica.project.rest.dto.usuario.AlterarSenha;
import com.work2home.publica.project.service.UsuarioService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.work2home.publica.project.rest.dto.usuario.UsuarioResponse;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.utils.JwtUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	private JwtUtil jwt;

	@Autowired
	private UsuarioService usuarioService;
	
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
		usuarioService.cadastrarImagem(multipartFile);

	}

	@PatchMapping("/alterar-senha")
	public void alterarSenha(@RequestBody @Valid AlterarSenha alterarSenha){

		usuarioService.alterarSenha(alterarSenha.getNovaSenha());
	}

}
