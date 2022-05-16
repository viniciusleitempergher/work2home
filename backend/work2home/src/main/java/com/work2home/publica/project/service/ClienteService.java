package com.work2home.publica.project.service;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import com.work2home.publica.project.rest.dto.cliente.ClienteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.rest.dto.cliente.ClienteCompletarCadastroRequest;
import com.work2home.publica.project.rest.dto.cliente.ClienteRequest;
import com.work2home.publica.project.enums.Roles;
import com.work2home.publica.project.model.Cliente;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.ClienteRepository;
import com.work2home.publica.project.repositores.UsuarioRepository;
import com.work2home.publica.project.utils.Formatador;
import com.work2home.publica.project.utils.JwtUtil;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private JwtUtil jwt;

	public List<ClienteResponse> buscarCliente() {
		return clienteRepository
				.findAll()
				.stream()
				.map(ClienteResponse::new)
				.toList();
	}

	public ClienteResponse buscarClienteId(Integer id) {
		Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return new ClienteResponse(cliente);
	}
	
	public boolean verificarClienteBanido(Integer id) {
		return clienteRepository.findById(id).isPresent();
	}

	@Transactional
	public void cadastrarCliente(ClienteRequest clienteDto) {

		usuarioRepository.findAll().forEach(usuario -> {
			if (usuario.getEmail().equalsIgnoreCase(clienteDto.getUsuarioDto().getEmail()))
				throw new ResponseStatusException(HttpStatus.CONFLICT);
		});

		Cliente cliente = clienteDto.converter();

		Usuario usuario = cliente.getUsuario();
		usuario.setRole(Roles.CLIENTE);

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		usuario.setSenha(bcrypt.encode(usuario.getSenha()));
		
		usuario.setSenha(usuario.getSenha());

		usuarioRepository.save(usuario);
	    clienteRepository.save(cliente);
	}
	

	@Transactional
	public void completarCadastro(@Valid ClienteCompletarCadastroRequest dto) {
		
		Usuario usuario = jwt.getUserFromHeaderToken();
		
		if (usuario.getRole() == Roles.CADASTRO_INCOMPLETO) {
			usuario.setRole(Roles.CLIENTE);
		}

		Cliente cliente = clienteRepository.findById(usuario.getId())
				.orElse(new Cliente());
		
		if (cliente.getId() != null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}

		cliente.setCpf(dto.getCpf());
		usuario.setNome(dto.getUsuarioDto().getNome());
		usuario.setTelefone(dto.getUsuarioDto().getTelefone());
		usuario.setDtNascimento(LocalDate.parse(dto.getUsuarioDto().getDtNascimento(), Formatador.getFormatter()));
		cliente.setUsuario(usuario);
		
		usuarioRepository.save(usuario);
		clienteRepository.save(cliente);
	}

	@Transactional
	public void alterarCliente(@Valid ClienteRequest dto) {
		
		Usuario usuario = jwt.getUserFromHeaderToken();

		Cliente cliente = clienteRepository.findById(usuario.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		usuarioRepository.findAll().forEach(u -> {
			if (u.getEmail().equalsIgnoreCase(dto.getUsuarioDto().getEmail())
					&& !dto.getUsuarioDto().getEmail().equalsIgnoreCase(usuario.getEmail()))
				throw new ResponseStatusException(HttpStatus.CONFLICT);
		});

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
