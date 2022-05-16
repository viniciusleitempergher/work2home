package com.work2home.publica.project.rest.dto.avaliacao;

import com.work2home.publica.project.model.Avaliacao;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvaliacaoResponse {

	private String comentario;
	private Integer nota;
	
	public AvaliacaoResponse(Avaliacao avaliacao) {
		this.comentario = avaliacao.getComentario();
		this.nota = avaliacao.getNota();
	}
}
