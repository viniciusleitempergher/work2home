package com.work2home.publica.project.rest.dto.denuncia;

import java.time.LocalDateTime;

import com.work2home.publica.project.model.Denuncia;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DenunciaDto {
	private String descricao;
	private LocalDateTime dataDenuncia;
	
	public DenunciaDto(Denuncia d) {
		this.descricao = d.getDescricao();
		this.dataDenuncia = d.getDataDenuncia();
	}
}
