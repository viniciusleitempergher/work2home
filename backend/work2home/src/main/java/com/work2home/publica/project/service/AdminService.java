package com.work2home.publica.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work2home.publica.project.dto.usuario.UsuarioRequest;
import com.work2home.publica.project.enums.Roles;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.UsuarioRepository;

@Service
public class AdminService {
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public void cadastrar(UsuarioRequest dto) {
		Usuario usuario = dto.converter();
		
		usuario.setRole(Roles.ADMIN);
				
		usuarioRepository.save(usuario);
	}
}
