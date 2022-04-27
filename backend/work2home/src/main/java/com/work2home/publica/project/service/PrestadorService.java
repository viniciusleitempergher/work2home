package com.work2home.publica.project.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.dto.CategoriaPrestadorDto;
import com.work2home.publica.project.dto.PrestadorDto;
import com.work2home.publica.project.enums.Roles;
import com.work2home.publica.project.dto.PrestadorResponseDto;
import com.work2home.publica.project.model.Categoria;
import com.work2home.publica.project.model.Cidade;
import com.work2home.publica.project.model.Prestador;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.CategoriaRepository;
import com.work2home.publica.project.repositores.CidadeRepository;
import com.work2home.publica.project.repositores.PrestadorRepository;
import com.work2home.publica.project.repositores.UsuarioRepository;
import com.work2home.publica.project.utils.Formatador;

@Service
public class PrestadorService {

	@Autowired
	private PrestadorRepository prestadorRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<Prestador> buscarPrestador() {
		return prestadorRepository.findAll();
	}

	public PrestadorResponseDto buscarPrestadorId(Integer id) {
		
		Prestador prestador = prestadorRepository
				.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		return new PrestadorResponseDto(prestador);
	}

	public PrestadorResponseDto cadastrarPrestador(@Valid PrestadorDto prestadorDto) {
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
		return new PrestadorResponseDto(prestador);
	}

	public void adicionarCidades(Integer prestadorId, Cidade cidade) {
		boolean contemNaLista=false;
		Prestador prestador = prestadorRepository
				.findById(prestadorId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		List<Cidade> cidades = prestador.getCidades();
		for(Cidade c :cidades) {
			if(c.getId()==cidade.getId()) {
				contemNaLista=true;
			}
		}
		if(!contemNaLista) {
			if (prestador.getCidades() == null) prestador.setCidades(new ArrayList<Cidade>());
			prestador.getCidades().add(cidade);
			prestadorRepository.save(prestador);
			
		}else {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
	}

	public Prestador adicionarCategoria(@Valid CategoriaPrestadorDto categoriaPrestadorDto) {
		Prestador prestador = prestadorRepository.findById(categoriaPrestadorDto.getPrestadorId()).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
		Categoria categoria = categoriaRepository.findById(categoriaPrestadorDto.getCategoriaId()).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST));
		if (prestador.getCategorias() == null) prestador.setCategorias(new HashSet<Categoria>());
		for(Categoria c : prestador.getCategorias()) {
			if(c.getId()==categoriaPrestadorDto.getCategoriaId()) {
				throw new ResponseStatusException(HttpStatus.CONFLICT);
			}
		}		
		prestador.getCategorias().add(categoria);
		return prestadorRepository.save(prestador);
		
	}

	public void alterarPrestador(Integer id, @Valid PrestadorDto dto) {
		
		Prestador prestador = prestadorRepository
				.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		
        Usuario usuario = prestador.getUsuario();
		
		usuario.setRole(Roles.PRESTADOR);
		
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

	public void removerCidadePrestador(Integer prestadorId, Integer cidadeId) {
		
		Prestador prestador = prestadorRepository
				.findById(prestadorId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		for(Cidade c : prestador.getCidades()) {
			if(c.getId() == cidadeId) {
				prestador.getCidades().remove(c);
				break;
			}
		}
		
		prestadorRepository.save(prestador);
	}
}