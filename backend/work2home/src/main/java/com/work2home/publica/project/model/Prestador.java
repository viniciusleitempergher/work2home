package com.work2home.publica.project.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.Valid;

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
}
