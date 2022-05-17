package com.work2home.publica.project.rest.dto.avaliacao;

import javax.validation.constraints.NotNull;
import com.work2home.publica.project.model.Avaliacao;
import com.work2home.publica.project.model.OrdemServico;
import lombok.Data;

@Data
public class AvaliarClienteDto {

	@NotNull(message = "{campo.nota.obrigatorio}")
	private Integer nota;
	
	public Avaliacao converter(OrdemServico os) {
		return Avaliacao.builder()
				.nota(nota)
				.avaliado(os.getEndereco().getCliente().getUsuario())
				.avaliador(os.getPrestador().getUsuario())
				.ordemServico(os).build();
	}
	
}
