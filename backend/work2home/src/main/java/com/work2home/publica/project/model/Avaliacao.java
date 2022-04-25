package com.work2home.publica.project.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	
	private static final long serialVersionUID = -3259431626478556888L;

//	@Id
//    @Column(name = "avaliacao_id")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
    

	@Column(name = "nota_id", nullable = false)
	private Integer nota;
	
	@Column(name = "comentario_id")
	private String comentario;
	
	@Id
	@JoinColumn(name = "avaliador_id")
	@ManyToOne
	private Usuario avaliador;
	
	@Id
	@JoinColumn(name = "avaliado_id")
	@ManyToOne
	private Usuario avaliado;
	
	@ManyToOne
	@JoinColumn(name = "ordem_servico_id")
	private OrdemServico ordemServico;	
}
