package com.work2home.publica.project.rest.dto.usuario;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.utils.Formatador;
import com.work2home.publica.project.validation.BrazilDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioRequest {
	
	@Email(message = "{campo.email.invalido}")
	@NotEmpty(message = "{campo.email.obrigatorio}")
	private String email;
	
	@NotBlank(message = "{campo.senha.obrigatorio}")
	@Length(min = 8, message = "{campo.senha.insuficiente}")
	private String senha;
	
	@NotBlank(message = "{campo.nome.obrigatorio}")
	private String nome;
	
	@NotBlank(message = "{campo.telefone.obrigatorio}")
	private String telefone;
	

	@NotBlank(message = "{campo.data_nascimento.obrigatorio}")
	@BrazilDate
	private String dtNascimento;
	
	public Usuario converter() {
		
		return Usuario.builder()
				.nome(nome)
				.email(email)
				.senha(senha)
				.dtNascimento(LocalDate.parse(dtNascimento, Formatador.getFormatter()))
				.telefone(telefone)
				.dataCriacao(LocalDate.now())
				.build();
	}
}
