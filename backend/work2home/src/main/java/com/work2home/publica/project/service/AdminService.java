package com.work2home.publica.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.rest.dto.usuario.UsuarioRequest;
import com.work2home.publica.project.enums.Roles;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.UsuarioRepository;

@Service
public class AdminService implements CommandLineRunner {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public void cadastrar(UsuarioRequest dto) {
		Usuario usuario = dto.converter();
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		usuario.setSenha(bcrypt.encode(dto.getSenha()));
		usuario.setRole(Roles.ADMIN);
		usuarioRepository.save(usuario);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Usuario> usuariosList = usuarioRepository.findAll();
		
		boolean hasAnAdmin = false;
		
		for (Usuario u : usuariosList) {
			if (u.getRole() == Roles.ADMIN) {
				hasAnAdmin = true;
				break;
			}
		}
		
		if (!hasAnAdmin) {
			UsuarioRequest usuarioDto = UsuarioRequest.builder().nome("admin")
					.email("admin").senha("admin").dtNascimento("07/01/2004")
					.telefone("4712341234").build();
			cadastrar(usuarioDto);
		}
	}

	public void banirUsuario(Integer usuarioId) {
		Usuario usuario = usuarioRepository
				.findById(usuarioId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
		if(usuario.getRole() == Roles.ADMIN) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		usuario.setRole(Roles.BANIDO);
		usuarioRepository.save(usuario);
	}
}
