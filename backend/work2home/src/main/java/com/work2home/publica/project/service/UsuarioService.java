package com.work2home.publica.project.service;

import com.work2home.publica.project.rest.dto.ImagemDto;
import com.work2home.publica.project.rest.dto.usuario.RoleUsuarioResponse;
import com.work2home.publica.project.rest.dto.usuario.UsuarioResponse;
import com.work2home.publica.project.utils.FileUploadUtil;
import com.work2home.publica.project.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.work2home.publica.project.enums.Roles;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.UsuarioRepository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private PrestadorService prestadorService;
	
	@Autowired
	private JwtUtil jwt;

	public long quantidadeUsuario() {
		
		return usuarioRepository.count();
	}

	public long[] quantidadesCargos() {
		long qtdPrestador=0, qtdBanido=0,qtdCliente=0,qtdAdmin=0;
		for(Usuario us:usuarioRepository.findAll()) {
			if(us.getRole()==Roles.BANIDO) {
				qtdBanido++;
			}else if(us.getRole()==Roles.CLIENTE) {
				qtdCliente++;
			}else if(us.getRole()==Roles.PRESTADOR) {
				qtdPrestador++;
			}else {
				qtdAdmin++;
			}			
			
		}
		return new long[]{qtdBanido,qtdCliente,qtdPrestador,qtdAdmin};
	}

    public ImagemDto cadastrarImagem(MultipartFile multipartFile) {
		Usuario usuario = jwt.getUserFromHeaderToken();
		
		String uuid = UUID.randomUUID().toString();
		String dir = "../images/usuario";
		String imagemUrl = dir + "/" + uuid + ".png";

		usuario.setImagemUrl(imagemUrl);
		usuarioRepository.save(usuario);

		try {
			FileUploadUtil.saveFile(dir, uuid , multipartFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ImagemDto(imagemUrl);
    }

    public void alterarSenha(String novaSenha) {

		Usuario usuario = jwt.getUserFromHeaderToken();
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

		usuario.setSenha(bc.encode(novaSenha));
		usuarioRepository.save(usuario);
    }

    public RoleUsuarioResponse getRole(Integer id) {

		Usuario usuario = usuarioRepository
				.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return new RoleUsuarioResponse(usuario.getRole().toString());
    }

	public List<UsuarioResponse> buscarUsuarios() {
		return usuarioRepository
				.findAll()
				.stream()
				.map(UsuarioResponse::new)
				.toList();
	}

	public UsuarioResponse buscarUsuarioId(Integer id) {
		Usuario usuario = usuarioRepository
				.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return new UsuarioResponse(usuario);
	}

	public void banimentoUsuario(Integer id) {
		Usuario usuario = usuarioRepository
				.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		if(verificaBanido(id)) {
			usuario.setRole(buscarRoleBanido(id));
		}else {			
			usuario.setRole(Roles.BANIDO);						
		}
		usuarioRepository.save(usuario);
	}

	public boolean verificaBanido(Integer id){
		return Objects.equals(getRole(id).getRole(), "BANIDO");
	}
	
	public Roles buscarRoleBanido(Integer id) {
		if(clienteService.verificarClienteBanido(id)) {
			return Roles.CLIENTE;
		}
		if(prestadorService.verificarPrestadorBanido(id)) {
			return Roles.PRESTADOR;
		}
		return Roles.ADMIN;
	}
}
