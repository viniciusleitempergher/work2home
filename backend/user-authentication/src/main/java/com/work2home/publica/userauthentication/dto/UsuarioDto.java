package com.work2home.publica.userauthentication.dto;

import java.time.LocalDate;

import javax.validation.Valid;

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
public class UsuarioDto {
	private String email;
	private String senha;
	private String nome;
	private String telefone;
	private LocalDate dtNascimento;
	
	public Usuario converter(@Valid UsuarioDto usuarioDto) {
		Usuario user = new  Usuario();
		
		user.setNome(usuarioDto.getNome());
		user.setEmail(usuarioDto.getEmail());
		user.setSenha(usuarioDto.getSenha());
		user.setDtNascimento(usuarioDto.getDtNascimento());
		user.setTelefone(usuarioDto.getTelefone());
		
		return user;
	}
}
