package com.work2home.publica.project.rest.dto.avaliacao;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class VerificarAvaliacaoRequest {

    @NotNull(message = "{campo.avaliador_id.obrigatorio}")
    private Integer avaliadorId;
    @NotNull(message = "{campo.ordem_servico_id.obrigatorio}")
    private Integer ordemServicoId;
}
