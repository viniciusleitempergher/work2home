package com.work2home.publica.project.rest.dto.endereco;

import lombok.Data;

@Data
public class EnderecoRequest {
	private String logradouro;
	private String estado;
	private String cidade;
	private Integer numero;
	private String complemento;
	private String bairro;

}
