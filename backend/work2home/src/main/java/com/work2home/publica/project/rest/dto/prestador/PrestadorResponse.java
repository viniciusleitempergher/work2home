package com.work2home.publica.project.rest.dto.prestador;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.work2home.publica.project.rest.dto.avaliacao.AvaliacaoResponse;
import com.work2home.publica.project.model.Prestador;
import com.work2home.publica.project.rest.dto.categoria.CategoriaResponse;
import com.work2home.publica.project.rest.dto.endereco.CidadeResponse;
import lombok.Data;

@Data
public class PrestadorResponse {

	private String nome;
	private String email;
	private String telefone;
	private Double mediaAvaliacao;
	private String imagemUrl;
	private String cnpj;
	private String nomeFantasia;
	private Set<CategoriaResponse> categorias;
	private Set<CidadeResponse> cidades;
	private List<AvaliacaoResponse> avaliacoes;
	
	public PrestadorResponse(Prestador prestador) {
		this.nome = prestador.getUsuario().getNome();
		this.imagemUrl = prestador.getUsuario().getImagemUrl();
		this.email = prestador.getUsuario().getEmail();
		this.telefone = prestador.getUsuario().getTelefone();
		this.mediaAvaliacao = prestador.getUsuario().getMediaAvaliacao();
		this.cnpj = prestador.getCnpj();
		this.nomeFantasia = prestador.getNomeFantasia();		
		
		if(prestador.getUsuario().getAvaliacoesRecebidas() != null) {
			this.avaliacoes = prestador.getUsuario()
					.getAvaliacoesRecebidas()
					.stream()
					.map(AvaliacaoResponse::new)
					.toList();
		}else {
			this.avaliacoes = new ArrayList<>();
		}

		if(prestador.getCidades() != null){
			this.cidades = prestador.getCidades()
					.stream()
					.map(CidadeResponse::new)
					.collect(Collectors.toSet());
		}else{
			this.cidades = new HashSet<>();
		}

		if(prestador.getCategorias() != null){
			this.categorias = prestador.getCategorias()
					.stream()
					.map(CategoriaResponse::new)
					.collect(Collectors.toSet());
		}else{
			this.categorias = new HashSet<>();
		}
	}
}
