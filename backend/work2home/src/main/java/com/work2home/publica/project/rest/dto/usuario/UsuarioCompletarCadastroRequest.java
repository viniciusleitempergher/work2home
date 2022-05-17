package com.work2home.publica.project.rest.dto.usuario;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.utils.Formatador;
import com.work2home.publica.project.validation.BrazilDate;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsuarioCompletarCadastroRequest {
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
				.dtNascimento(LocalDate.parse(dtNascimento, Formatador.getFormatter()))
				.telefone(telefone)
				.dataCriacao(LocalDate.now())
				.build();
	}
}
