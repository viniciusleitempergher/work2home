package com.work2home.publica.project.dto.prestador;

import com.work2home.publica.project.dto.usuario.UsuarioRequest;
import com.work2home.publica.project.enums.Roles;
import com.work2home.publica.project.model.Prestador;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
public class PrestadorRequest {
	private UsuarioRequest usuarioDto;
	private String cnpj;
	private String nomeFantasia;
	
	public Prestador converter() {
		
		
		return Prestador.builder()
				.usuario(usuarioDto.converter())
				.cnpj(cnpj)
				.nomeFantasia(nomeFantasia)
				.build();
	}
}
