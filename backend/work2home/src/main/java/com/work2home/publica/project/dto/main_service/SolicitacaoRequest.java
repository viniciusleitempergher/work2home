package com.work2home.publica.project.dto.main_service;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.work2home.publica.project.enums.StatusOrcamento;
import com.work2home.publica.project.model.Categoria;
import com.work2home.publica.project.model.Endereco;
import com.work2home.publica.project.model.OrdemServico;
import com.work2home.publica.project.model.Prestador;
import com.work2home.publica.project.repositores.CategoriaRepository;
import com.work2home.publica.project.repositores.EnderecoRepository;
import com.work2home.publica.project.repositores.PrestadorRepository;
import lombok.Data;

@Data
public class SolicitacaoRequest {

	@NotBlank
	@Length(min = 20)
	private String descricao; 
	private String imagemUrl;
	@NotNull
	private Integer categoriaServicoId;
	@NotNull
	private Integer prestadorId;
	@NotNull
	private Integer enderecoId;
	
	public OrdemServico converter(CategoriaRepository categoriaRepository,
			PrestadorRepository prestadorRepository, EnderecoRepository enderecoRepository) {
		
		Categoria categoria = categoriaRepository
				.findById(categoriaServicoId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		Prestador prestador = prestadorRepository
				.findById(prestadorId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		Endereco endereco = enderecoRepository
				.findById(enderecoId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		return OrdemServico.builder()
		.dataSolicitada(LocalDate.now())
		.status(StatusOrcamento.SOLICITADO)
		.imagemUrl(imagemUrl)
		.categoriaServico(categoria)
		.prestador(prestador)
		.endereco(endereco)
		.build();		
	}
}
