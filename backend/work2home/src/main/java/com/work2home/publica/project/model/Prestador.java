package com.work2home.publica.project.model;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

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
	
	@OneToMany(mappedBy = "prestador", fetch = FetchType.EAGER)
	private List<OrdemServico> servicos;
}
