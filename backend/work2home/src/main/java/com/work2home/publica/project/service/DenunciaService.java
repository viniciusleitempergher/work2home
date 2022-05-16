package com.work2home.publica.project.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.model.Denuncia;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.DenunciaRepository;
import com.work2home.publica.project.repositores.UsuarioRepository;
import com.work2home.publica.project.rest.dto.denuncia.DenunciaRequest;
import com.work2home.publica.project.rest.dto.denuncia.DenunciaResponse;
import com.work2home.publica.project.rest.dto.usuario.UsuarioDenunciasResponse;
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

		denunciaRepository.findAll().forEach(d -> {
			if(Objects.equals(d.getDenunciador().getId(), denunciador.getId()) && Objects.equals(d.getDenunciado().getId(),
					denunciaRequest.getDenunciadoId())) throw new ResponseStatusException(HttpStatus.CONFLICT);
		});
		
		Denuncia denuncia = denunciaRequest.converter(usuarioRepository);
		denuncia.setDenunciador(denunciador);
		denunciaRepository.save(denuncia);
	}
	
	public UsuarioDenunciasResponse buscarPorDenunciado(Integer denunciadoId){		
		return new UsuarioDenunciasResponse
				(usuarioRepository.findById(denunciadoId)
						.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)));
	}

	public List<DenunciaResponse> buscarDenunciaPorQtd() {
		return denunciaRepository.findDenunciaPorQtd();
	}
}
