package com.work2home.publica.project.dto;

import com.work2home.publica.project.enums.Roles;
import com.work2home.publica.project.model.Prestador;

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
public class PrestadorDto {
	private UsuarioDto usuarioDto;
	private String cnpj;
	private String nomeFantasia;
	
	public Prestador converter() {
		Prestador prestador = new Prestador();
		
		prestador.setUsuario(usuarioDto.converter());
		prestador.setCnpj(cnpj);
		prestador.setNomeFantasia(nomeFantasia);
		prestador.getUsuario().setRole(Roles.PRESTADOR);
		
		return prestador;
	}
}
