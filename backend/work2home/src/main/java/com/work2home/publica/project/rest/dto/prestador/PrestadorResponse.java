package com.work2home.publica.project.rest.dto.prestador;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.work2home.publica.project.rest.dto.avaliacao.AvaliacaoResponse;
import com.work2home.publica.project.model.Categoria;
import com.work2home.publica.project.model.Cidade;
import com.work2home.publica.project.model.Prestador;
import lombok.Data;

@Data
public class PrestadorResponse {

	private String nome;
	private String email;
	private String telefone;
	private Double mediaAvaliacao;
	private String imagemUrl;
	private Set<Categoria> categorias;
	private Set<Cidade> cidades;
	private List<AvaliacaoResponse> avaliacoes;
	
	public PrestadorResponse(Prestador prestador) {
		this.nome = prestador.getUsuario().getNome();
		this.imagemUrl = prestador.getUsuario().getImagemUrl();
		this.email = prestador.getUsuario().getEmail();
		this.telefone = prestador.getUsuario().getTelefone();
		this.mediaAvaliacao = prestador.getUsuario().getMediaAvaliacao();
		this.categorias = prestador.getCategorias();
		this.cidades = prestador.getCidades();
		
		if(prestador.getUsuario().getAvaliacoesRecebidas() != null) {
			this.avaliacoes = prestador.getUsuario()
					.getAvaliacoesRecebidas()
					.stream()
					.map(a -> new AvaliacaoResponse(a))
					.toList();
		}else {
			this.avaliacoes = new ArrayList<>();
		}
	}
}
