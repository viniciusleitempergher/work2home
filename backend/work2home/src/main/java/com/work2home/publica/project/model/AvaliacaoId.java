package com.work2home.publica.project.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoId implements Serializable{

	private Integer avaliador;
	
	private Integer avaliado;
}
