package com.work2home.publica.project.model;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoId implements Serializable{

	@Serial
	private static final long serialVersionUID = -7730069734989414339L;

	private Integer avaliador;
	private Integer avaliado;
	private Integer ordemServico;
}
