package com.work2home.publica.userauthentication.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.userauthentication.dto.PrestadorDto;
import com.work2home.publica.userauthentication.model.Prestador;
import com.work2home.publica.userauthentication.model.Usuario;
import com.work2home.publica.userauthentication.repositores.LoginRepository;
import com.work2home.publica.userauthentication.repositores.PrestadorRepository;

@Service
public class PrestadorService {

	@Autowired
	PrestadorRepository prestadorRepository;
	
	@Autowired
	LoginRepository loginRepository;

	public List<Prestador> buscarPrestador() {
		return prestadorRepository.findAll();
	}

	public Prestador buscarPrestadorId(Integer id) {

		return prestadorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

	}

	public Prestador cadastrarPrestador(@Valid PrestadorDto prestadorDto) {

		Usuario usuario = new Usuario();
		usuario= prestadorDto.converter(prestadorDto);
		Prestador prestador = new Prestador();
		prestador.setUsuario(loginRepository.save(usuario));
		prestador.setCnpj(prestadorDto.getCnpj());
		prestador.setNomeFantasia(prestadorDto.getNomeFantasia());
		System.out.println(prestador);
		
		return prestadorRepository.save(prestador);
	}
	



}