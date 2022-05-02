package com.work2home.publica.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.model.Denuncia;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.DenunciaRepository;
import com.work2home.publica.project.repositores.UsuarioRepository;
import com.work2home.publica.project.rest.dto.denuncia.DenunciaRequest;
import com.work2home.publica.project.utils.JwtUtil;

@Service
public class DenunciaService {

	@Autowired
	private DenunciaRepository denunciaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private JwtUtil jwt;
	
	public void denunciar(DenunciaRequest denunciaRequest) {
		
		Usuario denunciador = jwt.getUserFromHeaderToken(); 
		
		Denuncia denuncia = denunciaRequest.converter(usuarioRepository);
		denuncia.setDenunciador(denunciador);
		denunciaRepository.save(denuncia);
	}
	
	public List<Denuncia> buscarPorDenunciado(Integer denunciadoId){
		return denunciaRepository.findByDenunciadoId(denunciadoId);
	}
}
