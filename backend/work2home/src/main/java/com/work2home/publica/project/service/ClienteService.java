package com.work2home.publica.project.service;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.dto.ClienteDto;
import com.work2home.publica.project.dto.PrestadorDto;
import com.work2home.publica.project.enums.Roles;
import com.work2home.publica.project.model.Cliente;
import com.work2home.publica.project.model.Prestador;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.ClienteRepository;
import com.work2home.publica.project.repositores.UsuarioRepository;
import com.work2home.publica.project.utils.Formatador;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	UsuarioRepository usuarioRepository;

	public List<Cliente> buscarCliente() {
		return clienteRepository.findAll();
	}

	public Cliente buscarClienteId(Integer id) {
		return clienteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@Transactional
	public Cliente cadastrarCliente(ClienteDto clienteDto) {

		usuarioRepository.findAll().forEach(usuario -> {
			if (usuario.getEmail().equalsIgnoreCase(clienteDto.getUsuarioDto().getEmail()))
				throw new ResponseStatusException(HttpStatus.CONFLICT);
		});

		Cliente cliente = clienteDto.converter();

		Usuario usuario = cliente.getUsuario();
		usuario.setRole(Roles.CLIENTE);

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		usuario.setSenha(bcrypt.encode(usuario.getSenha()));

		usuarioRepository.save(usuario);

		return clienteRepository.save(cliente);
	}

	@Transactional
	public void alterarCliente(Integer id, @Valid ClienteDto dto) {

		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		Usuario usuario = cliente.getUsuario();

		usuario.setRole(Roles.PRESTADOR);

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		usuario.setSenha(bcrypt.encode(dto.getUsuarioDto().getSenha()));

		cliente.setCpf(dto.getCpf());
		usuario.setNome(dto.getUsuarioDto().getNome());
		usuario.setTelefone(dto.getUsuarioDto().getTelefone());
		usuario.setDtNascimento(LocalDate.parse(dto.getUsuarioDto().getDtNascimento(), Formatador.getFormatter()));

		usuarioRepository.save(usuario);
		clienteRepository.save(cliente);

	}

}
