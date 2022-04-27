package com.work2home.publica.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work2home.publica.project.enums.Roles;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;

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
	
}
