package com.work2home.publica.project.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	private Set<Cidade> cidades;
	
	@ManyToMany
	private Set<Categoria> categorias;
	
	@OneToMany(mappedBy = "prestador")
	@JsonIgnore
	private List<OrdemServico> servicos;
	
}
