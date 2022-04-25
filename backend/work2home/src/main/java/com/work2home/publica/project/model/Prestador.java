package com.work2home.publica.project.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

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
@Table(name="prestador_tb")
public class Prestador {
	@Id
	private Integer id;
	
	@MapsId
	@OneToOne
	@PrimaryKeyJoinColumn
	private Usuario usuario;
	private String cnpj;
	private String nomeFantasia;
	
	@ManyToMany
	private List<Cidade> cidades;
}
