package com.work2home.publica.userauthentication.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.userauthentication.dto.LoginDto;
import com.work2home.publica.userauthentication.model.Usuario;
import com.work2home.publica.userauthentication.repositores.LoginRepository;
import com.work2home.publica.userauthentication.repositores.UsuarioRepository;

@Service
public class LoginService {

	@Autowired
	UsuarioRepository usuarioRepository;

	public List<Usuario> buscarLogin() {
		return usuarioRepository.findAll();
	}

	public Usuario buscarLoginId(Integer id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

	}

	public Usuario verificaLogin(@Valid LoginDto loginDto) {

		return null;
	}

}
