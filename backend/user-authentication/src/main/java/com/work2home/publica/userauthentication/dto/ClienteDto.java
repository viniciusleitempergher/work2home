package com.work2home.publica.userauthentication.dto;

import java.time.LocalDate;

import javax.validation.Valid;

import com.work2home.publica.userauthentication.model.Cliente;
import com.work2home.publica.userauthentication.model.Prestador;
import com.work2home.publica.userauthentication.model.Usuario;

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
		
		cliente.setUsuario(usuarioDto.converter(usuarioDto));
		cliente.setCpf(cpf);
		
		return cliente;
	}
}
