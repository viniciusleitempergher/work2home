package com.work2home.publica.project.rest.dto.avaliacao;

import lombok.Data;

@Data
public class VerificarAvaliacaoRequest {
    private Integer avaliadorId;
    private Integer ordemServicoId;
}
