package com.work2home.publica.project.rest.dto.prestador;

import com.work2home.publica.project.model.Prestador;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestadorFiltroResponse {
	
	private Integer id;
	private String nome;	
	private String imagemUrl;
	private Double mediaAvaliacao; 

	public PrestadorFiltroResponse(Prestador prestador) {
		this.id = prestador.getId();
		this.nome = prestador.getUsuario().getNome();
		this.mediaAvaliacao = prestador.getUsuario().getMediaAvaliacao();
		this.imagemUrl = prestador.getUsuario().getImagemUrl();
	}
}
