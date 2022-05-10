package com.work2home.publica.project.model;

import java.io.Serial;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "avaliacao_tb")
@IdClass(AvaliacaoId.class)
public class Avaliacao implements Serializable{
	
	@Serial
	private static final long serialVersionUID = -3259431626478556888L;

	@Column(name = "nota", nullable = false)
	private Integer nota;
	
	@Column(name = "comentario")
	private String comentario;
	
	@Id
	@JoinColumn(name = "avaliador_id", nullable = false)
	@ManyToOne
	private Usuario avaliador;
	
	@Id
	@JoinColumn(name = "avaliado_id", nullable = false)
	@ManyToOne
	private Usuario avaliado;
	
	@Id
	@JoinColumn(name = "ordem_servico_id", nullable = false)
	@ManyToOne
	private OrdemServico ordemServico;	
}
