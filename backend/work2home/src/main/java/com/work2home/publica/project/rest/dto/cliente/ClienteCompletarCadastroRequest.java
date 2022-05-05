package com.work2home.publica.project.rest.dto.cliente;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

import com.work2home.publica.project.model.Cliente;
import com.work2home.publica.project.rest.dto.usuario.UsuarioCompletarCadastroRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteCompletarCadastroRequest {
	@Valid
	private UsuarioCompletarCadastroRequest usuarioDto;
	
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
