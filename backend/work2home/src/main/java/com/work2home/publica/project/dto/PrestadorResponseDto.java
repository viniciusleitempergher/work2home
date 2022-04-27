package com.work2home.publica.project.dto;

import java.util.List;
import java.util.Set;
import com.work2home.publica.project.dto.main_service.AvaliacaoResponse;
import com.work2home.publica.project.model.Categoria;
import com.work2home.publica.project.model.Cidade;
import com.work2home.publica.project.model.Prestador;
import lombok.Data;

@Data
public class PrestadorResponseDto {

	private String nome;
	private String email;
	private String telefone;
	private Double mediaAvaliacao;
	private Set<Categoria> categorias;
	private Set<Cidade> cidades;
	private List<AvaliacaoResponse> avaliacoes;
	
	public PrestadorResponseDto(Prestador prestador) {
		this.nome = prestador.getUsuario().getNome();
		this.email = prestador.getUsuario().getEmail();
		this.telefone = prestador.getUsuario().getTelefone();
		this.mediaAvaliacao = prestador.getUsuario().getMediaAvaliacao();
		this.categorias = prestador.getCategorias();
		this.cidades = prestador.getCidades();
		this.avaliacoes = prestador.getUsuario()
				.getAvaliacoesRecebidas()
				.stream()
				.map(a -> new AvaliacaoResponse(a))
				.toList();
		
		
	}
}
