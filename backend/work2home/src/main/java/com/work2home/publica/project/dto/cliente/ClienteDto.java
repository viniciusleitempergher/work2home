package com.work2home.publica.project.dto.cliente;

import com.work2home.publica.project.dto.usuario.UsuarioDto;
import com.work2home.publica.project.model.Cliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ClienteDto {
	private UsuarioDto usuarioDto;
	private String cpf;

	public Cliente converter() {
		Cliente cliente = new Cliente();
		
		cliente.setUsuario(usuarioDto.converter());
		cliente.setCpf(cpf);
		
		return cliente;
	}
}
