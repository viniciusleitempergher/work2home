package com.work2home.publica.project.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.utils.Formatador;

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
	private String dtNascimento;
	
	public Usuario converter() {
		Usuario user = new  Usuario();
		
		user.setNome(nome);
		user.setEmail(email);
		user.setSenha(senha);
		user.setDtNascimento(LocalDate.parse(dtNascimento, Formatador.getFormatter()));
		user.setTelefone(telefone);
		
		return user;
	}
}
