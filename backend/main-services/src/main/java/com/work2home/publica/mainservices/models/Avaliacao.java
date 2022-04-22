package com.work2home.publica.mainservices.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
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
	
	@JoinColumn(table = "usuario_tb", name = "usuario_id")
	private Integer avaliador;
	
	@JoinColumn(table = "usuario_tb", name = "usuario_id")
	private Integer avaliado;
	
	@ManyToOne
	@JoinColumn(name = "ordem_servico_id")
	private OrdemServico ordemServico;
	
}
