package com.work2home.publica.project.rest.dto.prestador;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CNPJ;

import com.work2home.publica.project.rest.dto.usuario.UsuarioCompletarCadastroRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrestadorCompletarCadastroRequest {
	@Valid
	private UsuarioCompletarCadastroRequest usuarioDto;
	
	@CNPJ(message = "{campo.cnpj.invalido}")
	@NotBlank(message = "{campo.cnpj.obrigatorio}")
	private String cnpj;
	
	@NotBlank(message = "{campo.nome_fantasia.obrigatorio}")
	private String nomeFantasia;
}
