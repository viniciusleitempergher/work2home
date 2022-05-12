package com.work2home.publica.project.rest.dto.endereco;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class EnderecoRequest {

	@NotBlank(message = "{campo.logradouro.obrigatorio}")
	private String logradouro;

	@NotBlank(message = "{campo.estado.obrigatorio}")
	private String estado;

	@NotBlank(message = "{campo.cidade.obrigatorio}")
	private String cidade;

	@NotNull(message = "{campo.numero.obrigatorio}")
	private Integer numero;

	@NotBlank(message = "{campo.complemento.obrigatorio}")
	private String complemento;

	@NotBlank(message = "{campo.bairro.obrigatorio}")
	private String bairro;
}
