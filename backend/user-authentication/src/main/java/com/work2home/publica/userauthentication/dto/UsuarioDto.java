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
	
	public Usuario converter() {
		Usuario user = new  Usuario();
		
		user.setNome(nome);
		user.setEmail(email);
		user.setSenha(senha);
		user.setDtNascimento(dtNascimento);
		user.setTelefone(telefone);
		
		return user;
	}
}
