package com.work2home.publica.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Avaliacao {
	
	@Id
    @Column(name = "avaliacao_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@Column(name = "nota_id")
	private Integer nota;
	
	@Column(name = "comentario_id")
	private String comentario;
	
	@JoinColumn(name = "avaliador_id")
	@ManyToOne
	private Usuario avaliador;
	
	@JoinColumn(name = "avaliado_id")
	@ManyToOne
	private Usuario avaliado;
	
	@ManyToOne
	@JoinColumn(name = "ordem_servico_id")
	private OrdemServico ordemServico;	
}
