package com.work2home.publica.project.rest.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.work2home.publica.project.model.Email;
import com.work2home.publica.project.rest.dto.ImagemDto;
import com.work2home.publica.project.rest.dto.cliente.ClienteResponse;
import com.work2home.publica.project.rest.dto.email.EmailRequest;
import com.work2home.publica.project.rest.dto.usuario.AlterarSenha;
import com.work2home.publica.project.rest.dto.usuario.BanirUsuario;
import com.work2home.publica.project.rest.dto.usuario.RoleUsuarioResponse;
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
	
	@GetMapping
	public List<UsuarioResponse> buscaListaUsuario() {
		return usuarioService.buscarUsuarios();
	}
	
	@GetMapping("/denunciado/{id}")
	public UsuarioResponse buscaUsuarioId(@PathVariable Integer id) {
		return usuarioService.buscarUsuarioId(id);
	}
	
	@GetMapping("/me")
	public UsuarioResponse getMe() throws IllegalAccessException, InvocationTargetException {
		Usuario user = jwt.getUserFromHeaderToken();
		UsuarioResponse ur = new UsuarioResponse();
		BeanUtils.copyProperties(ur, user);
		return ur;
	}

	@GetMapping("/{id}/get-role")
	public RoleUsuarioResponse getRole(@PathVariable Integer id){
		return usuarioService.getRole(id);
	}



	@PostMapping("/imagem")
	@ResponseStatus(HttpStatus.CREATED)
	public ImagemDto cadastrarImagem(@RequestParam("image") MultipartFile multipartFile) {
		return usuarioService.cadastrarImagem(multipartFile);

	}

	@PatchMapping("/alterar-senha")
	public void alterarSenha(@RequestBody @Valid AlterarSenha alterarSenha){

		usuarioService.alterarSenha(alterarSenha.getNovaSenha());
	}
	
	@PatchMapping("/banimento")
	public void banirUsuario(@RequestBody @Valid BanirUsuario banirUsuario){
		usuarioService.banimentoUsuario(banirUsuario.getId());
	}

}
