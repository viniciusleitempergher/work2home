package com.work2home.publica.project.rest.dto.ordem_servico;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SolicitacaoAcceptRequest {

	@NotNull(message = "{campo.valor.obrigatorio}")
	private Double valor;

	@NotNull(message = "{campo.tempo_estimado.obrigatorio}")
	private Integer tempoEstimado;

	@NotBlank(message = "{campo.data_inicio.obrigatorio}")
	private String dataInicio;
}
