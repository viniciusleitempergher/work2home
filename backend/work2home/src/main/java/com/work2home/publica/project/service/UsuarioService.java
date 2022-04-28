package com.work2home.publica.project.service;

import com.work2home.publica.project.model.OrdemServico;
import com.work2home.publica.project.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.work2home.publica.project.enums.Roles;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.UsuarioRepository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

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

    public void cadastrarImagem(Integer id, MultipartFile multipartFile) {
		Usuario usuario = usuarioRepository
				.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

		String uuid = UUID.randomUUID().toString();
		String dir = "../images/usuario";

		usuario.setImagemUrl(dir + uuid);
		usuarioRepository.save(usuario);

		try {
			FileUploadUtil.saveFile(dir, uuid , multipartFile);

		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
