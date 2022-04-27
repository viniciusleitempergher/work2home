package com.work2home.publica.project.dto.endereco;

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
public class EnderecoDto {
	private String endereco;
	private String estado;
	private String cidade;
	private Integer numero;
	private String complemento;
	private String bairro;

}
