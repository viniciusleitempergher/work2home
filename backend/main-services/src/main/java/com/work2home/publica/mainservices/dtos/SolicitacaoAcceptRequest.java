package com.work2home.publica.mainservices.dtos;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SolicitacaoAcceptRequest {

	@NotBlank
	private Double valor;

	@NotBlank
	private Integer tempoEstimado;

	@NotBlank
	private String dataInicio;
}
