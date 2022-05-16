package com.work2home.publica.project.model;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DenunciaId implements Serializable {

	@Serial
	private static final long serialVersionUID = 7843864021035775856L;

	private int denunciador;
	private int denunciado;
}
