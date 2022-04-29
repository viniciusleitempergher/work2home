package com.work2home.publica.project.rest.dto.prestador;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.work2home.publica.project.rest.dto.usuario.UsuarioRequest;
import org.hibernate.validator.constraints.br.CNPJ;

import com.work2home.publica.project.model.Prestador;

import lombok.Data;

@Data
public class PrestadorRequest {

	@Valid
	private UsuarioRequest usuarioDto;
	
	@CNPJ(message = "{campo.cnpj.invalido}")
	@NotBlank(message = "{campo.cnpj.obrigatorio}")
	private String cnpj;
	
	@NotBlank(message = "{campo.nome_fantasia.obrigatorio}")
	private String nomeFantasia;
	
	public Prestador converter() {
		
		
		return Prestador.builder()
				.usuario(usuarioDto.converter())
				.cnpj(cnpj)
				.nomeFantasia(nomeFantasia)
				.build();
	}
}
