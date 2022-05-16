package com.work2home.publica.project.rest.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.work2home.publica.project.rest.dto.ImagemDto;
import com.work2home.publica.project.rest.dto.usuario.AlterarSenha;
import com.work2home.publica.project.rest.dto.usuario.RoleUsuarioResponse;
import com.work2home.publica.project.service.UsuarioService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

	@ApiOperation(value = "Lista todos os usuários")
	@GetMapping
	public List<UsuarioResponse> buscaListaUsuario() {
		return usuarioService.buscarUsuarios();
	}

	@ApiOperation(value = "Retorna o usuário logado")
	@GetMapping("/me")
	public UsuarioResponse getMe() throws IllegalAccessException, InvocationTargetException {
		Usuario user = jwt.getUserFromHeaderToken();
		UsuarioResponse ur = new UsuarioResponse();
		BeanUtils.copyProperties(ur, user);
		return ur;
	}

	@ApiOperation(value = "Busca a role de usuário por id")
	@GetMapping("/{id}/get-role")
	public RoleUsuarioResponse getRole(@PathVariable Integer id){
		return usuarioService.getRole(id);
	}

	@ApiOperation(value = "Adiciona uma imagem no usuário logado")
	@PostMapping("/imagem")
	@ResponseStatus(HttpStatus.CREATED)
	public ImagemDto cadastrarImagem(@RequestParam("image") MultipartFile multipartFile) {
		return usuarioService.cadastrarImagem(multipartFile);
	}

	@ApiOperation(value = "Altera a senha do usuário logado")
	@PatchMapping("/alterar-senha")
	public void alterarSenha(@RequestBody @Valid AlterarSenha alterarSenha){
		usuarioService.alterarSenha(alterarSenha.getNovaSenha());
	}

	@ApiOperation(value = "Bane ou desbane um usuário")
	@PatchMapping("/banimento/{id}")
	public void banirUsuario(@PathVariable Integer id){
		usuarioService.banimentoUsuario(id);
	}
}
