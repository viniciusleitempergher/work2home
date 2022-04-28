package com.work2home.publica.project.dto.cliente;

import com.work2home.publica.project.dto.usuario.UsuarioRequest;
import com.work2home.publica.project.model.Cliente;
import lombok.Data;

@Data
public class ClienteRequest {
	private UsuarioRequest usuarioDto;
	private String cpf;

	public Cliente converter() {
			
		return Cliente.builder()
				.usuario(usuarioDto.converter())
				.cpf(cpf)
				.build();
	}
}
