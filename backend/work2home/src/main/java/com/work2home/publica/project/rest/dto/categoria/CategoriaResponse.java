package com.work2home.publica.project.rest.dto.categoria;

import com.work2home.publica.project.model.Categoria;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoriaResponse {

	private Integer id;
	private String nome;
	private String imagemUrl;

	public CategoriaResponse(Categoria categoria){
		this.id = categoria.getId();
		this.nome = categoria.getNome();
		this.imagemUrl = categoria.getImagemUrl();
	}

}
