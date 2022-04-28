package com.work2home.publica.project.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "denuncia_tb")
@IdClass(DenunciaId.class)
public class Denuncia implements Serializable {

	private static final long serialVersionUID = 6691799932343781245L;

	private String descricao;
	
	@Id
	@JoinColumn(name = "denunciador_id", nullable = false)
	@ManyToOne
	private Usuario denunciador;
	
	@Id
	@JoinColumn(name = "denunciado_id", nullable = false)
	@ManyToOne
	private Usuario denunciado;
}