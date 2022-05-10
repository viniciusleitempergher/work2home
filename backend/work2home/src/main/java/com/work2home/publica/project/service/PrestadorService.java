package com.work2home.publica.project.service;

import java.time.LocalDate;
import java.util.*;

import javax.validation.Valid;

import com.work2home.publica.project.model.*;
import com.work2home.publica.project.repositores.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.rest.dto.prestador.PrestadorCompletarCadastroRequest;
import com.work2home.publica.project.rest.dto.prestador.PrestadorFiltroResponse;
import com.work2home.publica.project.rest.dto.prestador.PrestadorRequest;
import com.work2home.publica.project.rest.dto.prestador.PrestadorResponse;
import com.work2home.publica.project.enums.Roles;
import com.work2home.publica.project.repositores.CategoriaRepository;
import com.work2home.publica.project.repositores.PrestadorRepository;
import com.work2home.publica.project.repositores.UsuarioRepository;
import com.work2home.publica.project.utils.Formatador;
import com.work2home.publica.project.utils.JwtUtil;

@Service
public class PrestadorService {

	@Autowired
	private PrestadorRepository prestadorRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private JwtUtil jwt;

	public List<PrestadorResponse> buscarPrestador() {
		return prestadorRepository
				.findAll()
				.stream()
				.map(PrestadorResponse::new)
				.toList();
	}

	public PrestadorResponse buscarPrestadorId(Integer id) {

		Prestador prestador = prestadorRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return new PrestadorResponse(prestador);
	}
	
	public boolean verificarPrestadorBanido(Integer id) {
		return prestadorRepository.findById(id).isPresent();
	}

	@Transactional
	public PrestadorResponse cadastrarPrestador(@Valid PrestadorRequest prestadorDto) {
		usuarioRepository.findAll().forEach(usuario -> {
			if (usuario.getEmail().equalsIgnoreCase(prestadorDto.getUsuarioDto().getEmail()))
				throw new ResponseStatusException(HttpStatus.CONFLICT);
		});

		Prestador prestador = prestadorDto.converter();

		Usuario usuario = prestador.getUsuario();
		usuario.setRole(Roles.PRESTADOR);

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		usuario.setSenha(bcrypt.encode(usuario.getSenha()));

		usuarioRepository.save(usuario);
		prestador.setCnpj(prestadorDto.getCnpj());
		prestador.setNomeFantasia(prestadorDto.getNomeFantasia());

		prestadorRepository.save(prestador);
		return new PrestadorResponse(prestador);
	}

	public void adicionarCidades(Integer prestadorId, Cidade cidade) {

		Usuario usuario = jwt.getUserFromHeaderToken();

		boolean contemNaLista = false;
		Prestador prestador = prestadorRepository.findById(usuario.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		Set<Cidade> cidades = prestador.getCidades();
		for (Cidade c : cidades) {
			if (Objects.equals(c.getId(), cidade.getId())) {
				contemNaLista = true;
			}
		}
		if (!contemNaLista) {
			if (prestador.getCidades() == null)
				prestador.setCidades(new HashSet<Cidade>());
			prestador.getCidades().add(cidade);
			prestadorRepository.save(prestador);

		} else {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
	}

	public void adicionarCategoria(Integer categoriaId) {

		Usuario usuario = jwt.getUserFromHeaderToken();

		Prestador prestador = prestadorRepository.findById(usuario.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
		Categoria categoria = categoriaRepository.findById(categoriaId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

		if (prestador.getCategorias() == null)
			prestador.setCategorias(new HashSet<Categoria>());
		for (Categoria c : prestador.getCategorias()) {
			if (Objects.equals(c.getId(), categoriaId)) {
				throw new ResponseStatusException(HttpStatus.CONFLICT);
			}
		}
		prestador.getCategorias().add(categoria);
		prestadorRepository.save(prestador);
	}
	
	@Transactional
	public void completarCadastro(@Valid PrestadorCompletarCadastroRequest dto) {
		Usuario usuario = jwt.getUserFromHeaderToken();
		
		if (usuario.getRole() == Roles.CADASTRO_INCOMPLETO) {
			usuario.setRole(Roles.PRESTADOR);
		}

		Prestador prestador = prestadorRepository.findById(usuario.getId())
				.orElse(new Prestador());
		
		if (prestador.getId() != null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}

		prestador.setCnpj(dto.getCnpj());
		prestador.setNomeFantasia(dto.getNomeFantasia());
		usuario.setNome(dto.getUsuarioDto().getNome());
		usuario.setTelefone(dto.getUsuarioDto().getTelefone());
		usuario.setDtNascimento(LocalDate.parse(dto.getUsuarioDto().getDtNascimento(), Formatador.getFormatter()));
		prestador.setUsuario(usuario);
		
		usuarioRepository.save(usuario);
		prestadorRepository.save(prestador);
	}

	@Transactional
	public void alterarPrestador(@Valid PrestadorRequest dto) {
		Usuario usuario = jwt.getUserFromHeaderToken();

		Prestador prestador = prestadorRepository.findById(usuario.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		usuarioRepository.findAll().forEach(u -> {
			if (u.getEmail().equalsIgnoreCase(dto.getUsuarioDto().getEmail())
					&& !dto.getUsuarioDto().getEmail().equalsIgnoreCase(usuario.getEmail()))
				throw new ResponseStatusException(HttpStatus.CONFLICT);
		});

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		usuario.setSenha(bcrypt.encode(dto.getUsuarioDto().getSenha()));

		prestador.setCnpj(dto.getCnpj());
		prestador.setNomeFantasia(dto.getNomeFantasia());
		usuario.setNome(dto.getUsuarioDto().getNome());
		usuario.setTelefone(dto.getUsuarioDto().getTelefone());
		usuario.setDtNascimento(LocalDate.parse(dto.getUsuarioDto().getDtNascimento(), Formatador.getFormatter()));

		usuarioRepository.save(usuario);
		prestadorRepository.save(prestador);
	}

	public void removerCidadePrestador(Integer cidadeId) {

		Usuario usuario = jwt.getUserFromHeaderToken();

		Prestador prestador = prestadorRepository.findById(usuario.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

		for (Cidade c : prestador.getCidades()) {
			if (Objects.equals(c.getId(), cidadeId)) {
				prestador.getCidades().remove(c);
				break;
			}
		}

		prestadorRepository.save(prestador);
	}

	public void removerCategoriaPrestador(Integer categoriaId) {
		System.out.println(categoriaId);
		Usuario usuario = jwt.getUserFromHeaderToken();

		Prestador prestador = prestadorRepository.findById(usuario.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

		for (Categoria c : prestador.getCategorias()) {
			if (Objects.equals(c.getId(), categoriaId)) {
				prestador.getCategorias().remove(c);
				break;
			}
		}
		prestadorRepository.save(prestador);
	}

	public List<PrestadorFiltroResponse> filtrarPrestadores(Integer categoriaId) {

		Usuario usuario = jwt.getUserFromHeaderToken();

		Cliente cliente = clienteRepository
				.findById(usuario.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

		List<PrestadorFiltroResponse> prestadorResponses = prestadorRepository
				.findByCategorias_IdAndCidades_Id(categoriaId, cliente.getEndereco().getCidade().getId())
				.stream()
				.filter(p -> p.getUsuario().getRole() == Roles.PRESTADOR)
				.map(PrestadorFiltroResponse::new)
				.toList();

		List<PrestadorFiltroResponse> list = new LinkedList<PrestadorFiltroResponse>(prestadorResponses);
		ordenaPorMediaAvaliacao(list);
		return list;
	}

	private static void ordenaPorMediaAvaliacao(List<PrestadorFiltroResponse> listaPessoas) {
		listaPessoas.sort(
				 (PrestadorFiltroResponse p1, PrestadorFiltroResponse p2) ->
						p2.getMediaAvaliacao().compareTo(p1.getMediaAvaliacao()));
	}
}