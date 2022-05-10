package com.work2home.publica.project.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

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

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "denuncia_tb")
@IdClass(DenunciaId.class)
public class Denuncia implements Serializable {

	@Serial
	private static final long serialVersionUID = 6691799932343781245L;

	@Column(name = "descricao", nullable = false)
	private String descricao;
	
	@Column(name = "data_denuncia")
	private LocalDateTime dataDenuncia;
	
	@Id
	@JoinColumn(name = "denunciador_id", nullable = false)
	@ManyToOne
	private Usuario denunciador;
	
	@Id
	@JoinColumn(name = "denunciado_id", nullable = false)
	@ManyToOne
	private Usuario denunciado;
}