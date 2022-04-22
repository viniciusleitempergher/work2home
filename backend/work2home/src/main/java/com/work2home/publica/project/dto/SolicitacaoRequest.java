package com.work2home.publica.project.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.work2home.publica.project.enums.StatusOrcamento;
import com.work2home.publica.project.model.OrdemServico;

import lombok.Data;

@Data
public class SolicitacaoRequest {

	@NotBlank
	@Length(min = 20)
	private String descricao; 
	private String imagemUrl;
	@NotBlank
	private Integer categoriaServicoId;
	@NotBlank
	private Integer prestadorId;
	@NotBlank
	private Integer enderecoId;
	
	public OrdemServico converter() {
		
		return OrdemServico.builder()
		.dataSolicitada(LocalDate.now())
		.status(StatusOrcamento.SOLICITADO)
		.imagemUrl(imagemUrl)
		.categoriaServicoId(categoriaServicoId)
		.prestadorId(prestadorId)
		.enderecoId(enderecoId)
		.build();		
	}
}
