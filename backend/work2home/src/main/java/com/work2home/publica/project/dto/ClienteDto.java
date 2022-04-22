package com.work2home.publica.project.dto;

import java.time.LocalDate;

import javax.validation.Valid;

import com.work2home.publica.project.model.Cliente;
import com.work2home.publica.project.model.Prestador;
import com.work2home.publica.project.model.Usuario;

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
