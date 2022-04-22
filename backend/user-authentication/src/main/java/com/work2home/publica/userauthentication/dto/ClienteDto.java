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
public class ClienteDto {
	private String email;
	private String senha;
	private String nome;
	private String telefone;
	private LocalDate dtNascimento;
	private String cpf;
	
	
	public Usuario converter(@Valid ClienteDto clienteDto) {
		Usuario user = new  Usuario();
		user.setNome(clienteDto.getNome());
		user.setEmail(clienteDto.getEmail());
		user.setSenha(clienteDto.getSenha());
		user.setDtNascimento(clienteDto.getDtNascimento());
		user.setTelefone(clienteDto.getTelefone());
		return user;
	}


}
