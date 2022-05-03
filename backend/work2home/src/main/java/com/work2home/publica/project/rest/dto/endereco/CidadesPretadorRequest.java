package com.work2home.publica.project.rest.dto.endereco;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CidadesPretadorRequest {
	@NotBlank(message = "{campo.estado.obrigatorio}")
	private String estado;
	@NotBlank(message = "{campo.cidade.obrigatorio}")
	private String nome;
}
