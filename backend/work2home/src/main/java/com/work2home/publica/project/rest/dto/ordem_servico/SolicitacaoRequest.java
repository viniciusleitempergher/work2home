package com.work2home.publica.project.rest.dto.ordem_servico;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.work2home.publica.project.enums.StatusOrcamento;
import com.work2home.publica.project.model.Categoria;
import com.work2home.publica.project.model.Endereco;
import com.work2home.publica.project.model.OrdemServico;
import com.work2home.publica.project.model.Prestador;
import com.work2home.publica.project.repositores.CategoriaRepository;
import com.work2home.publica.project.repositores.PrestadorRepository;
import lombok.Data;

@Data
public class SolicitacaoRequest {

	@NotBlank(message = "{campo.descricao.obrigatorio}")
	private String descricao;
	
	private String imagemUrl;
	
	@NotNull(message = "{campo.categoria_id.obrigatorio}")
	private Integer categoriaServicoId;
	
	@NotNull(message = "{campo.prestador_id.obrigatorio}")
	private Integer prestadorId;
	
	public OrdemServico converter(CategoriaRepository categoriaRepository,
			PrestadorRepository prestadorRepository, Endereco endereco) {
		
		Categoria categoria = categoriaRepository
				.findById(categoriaServicoId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		Prestador prestador = prestadorRepository
				.findById(prestadorId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		return OrdemServico.builder()
		.dataSolicitada(LocalDate.now())
		.status(StatusOrcamento.SOLICITADO)
		.descricao(descricao)
		.imagemUrl(imagemUrl)
		.categoriaServico(categoria)
		.prestador(prestador)
		.endereco(endereco)
		.build();		
	}
}
