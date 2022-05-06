package com.work2home.publica.project.rest.dto.endereco;

import lombok.Data;

@Data
public class EnderecoResponse {
	private String logradouro;
	private Integer numero;
	private String complemento;
	private String bairro;

}
