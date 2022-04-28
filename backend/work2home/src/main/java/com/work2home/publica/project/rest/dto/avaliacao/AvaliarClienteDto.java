package com.work2home.publica.project.rest.dto.avaliacao;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.work2home.publica.project.model.Avaliacao;
import com.work2home.publica.project.model.OrdemServico;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.OrdemServicoRepository;
import com.work2home.publica.project.repositores.UsuarioRepository;
import lombok.Data;

@Data
public class AvaliarClienteDto {

	@NotNull(message = "{campo.nota.obrigatorio}")
	private Integer nota;
	
	public Avaliacao converter(UsuarioRepository usuarioRepository, OrdemServico os) {
				
		return Avaliacao.builder()
				.nota(nota)
				.avaliado(os.getEndereco().getCliente().getUsuario())
				.avaliador(os.getPrestador().getUsuario())
				.ordemServico(os).build();
	}
	
}
