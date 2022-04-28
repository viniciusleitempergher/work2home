package com.work2home.publica.project.dto.endereco;

import lombok.Data;

@Data
public class EnderecoRequest {
	private String endereco;
	private String estado;
	private String cidade;
	private Integer numero;
	private String complemento;
	private String bairro;

}
