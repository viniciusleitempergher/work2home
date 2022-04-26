package com.work2home.publica.project.dto.main_service;

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
public class AvaliarPrestadorDto {
	
	@NotNull
	private Integer nota;
	private String comentario;
	@NotNull
	private Integer ordemServicoId = -1;
	
	public Avaliacao converter(UsuarioRepository usuarioRepository, OrdemServicoRepository ordemServicoRepository) {
		
		
		OrdemServico ordemServico = ordemServicoRepository
				.findById(ordemServicoId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		System.out.println("OLaaaaa");
		System.out.println(ordemServico.getImagemUrl());
		
		return Avaliacao.builder()
				.nota(nota)
				.comentario(comentario)
				.avaliado(ordemServico.getPrestador().getUsuario())
				.avaliador(ordemServico.getEndereco().getCliente().getUsuario())
				.ordemServico(ordemServico).build();
	}

}
