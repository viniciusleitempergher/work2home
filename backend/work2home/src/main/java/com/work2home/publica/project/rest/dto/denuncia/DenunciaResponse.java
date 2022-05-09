package com.work2home.publica.project.rest.dto.denuncia;



import com.work2home.publica.project.enums.Roles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DenunciaResponse {
	private Integer denunciado_id;
	private long qtdDenuncias;
	private String nome;
	private Roles role;
}
