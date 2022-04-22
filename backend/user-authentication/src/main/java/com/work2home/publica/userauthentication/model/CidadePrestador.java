package com.work2home.publica.userauthentication.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "prestador_has_cidade_tb")
public class CidadePrestador implements Serializable {

	private static final long serialVersionUID = -2502089677392561113L;
	
	@Id
	@JoinColumn(table = "usuario_tb", name = "usuario_id")
	private Integer prestadorUsuarioId;
	
	@Id
	@JoinColumn(table = "cidade_tb", name = "cidade_id")
	private Integer cidadeId;
}
