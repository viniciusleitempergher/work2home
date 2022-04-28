package com.work2home.publica.project.rest.dto.cliente;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.work2home.publica.project.rest.dto.usuario.UsuarioRequest;
import org.hibernate.validator.constraints.br.CPF;

import com.work2home.publica.project.model.Cliente;
import lombok.Data;

@Data
public class ClienteRequest {

	@Valid
	private UsuarioRequest usuarioDto;
	
	@CPF(message = "{campo.cpf.invalido}")
	@NotBlank(message = "{campo.cpf.obrigatorio}")
	private String cpf;

	public Cliente converter() {
			
		return Cliente.builder()
				.usuario(usuarioDto.converter())
				.cpf(cpf)
				.build();
	}
}
