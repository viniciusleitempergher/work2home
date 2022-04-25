package com.work2home.publica.project.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name="prestador_tb")
public class Prestador {
	@Id
	private Integer id;
	
	@MapsId
	@OneToOne
	@PrimaryKeyJoinColumn
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@Column(name = "cnpj", nullable = false)
	private String cnpj;
	
	@Column(name = "nome_fantasia", nullable = false)
	private String nomeFantasia;
	
	@ManyToMany
	@JoinColumn(name = "cidade_id")
	private List<Cidade> cidades;
}
