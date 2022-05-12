package com.work2home.publica.project.rest.dto.categoria;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoriaRequest {

	@NotBlank(message = "{campo.nome.obrigatorio}")
	private String nome;
}
