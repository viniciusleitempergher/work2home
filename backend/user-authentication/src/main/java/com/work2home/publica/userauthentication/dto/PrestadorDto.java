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
public class PrestadorDto {
	private String email;
	private String senha;
	private String nome;
	private String telefone;
	private LocalDate dtNascimento;
	private String cnpj;
	private String nomeFantasia;
	
	
	public Usuario converter(@Valid PrestadorDto prestadorDto) {
		Usuario user = new  Usuario();
		user.setNome(prestadorDto.getNome());
		user.setEmail(prestadorDto.getEmail());
		user.setSenha(prestadorDto.getSenha());
		user.setDtNascimento(prestadorDto.getDtNascimento());
		user.setTelefone(prestadorDto.getTelefone());
		return user;
	}
}
