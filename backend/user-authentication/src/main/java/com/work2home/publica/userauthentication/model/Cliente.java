package com.work2home.publica.userauthentication.model;

import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name="cliente_tb")
public class Cliente{
	@Id
	private Integer id;
	@MapsId
	@OneToOne
	@PrimaryKeyJoinColumn
	private Usuario usuario;
	private String cpf;

}
