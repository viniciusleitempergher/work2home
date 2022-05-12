package com.work2home.publica.project.rest.dto.denuncia;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.model.Denuncia;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.UsuarioRepository;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class DenunciaRequest {

	@NotNull(message = "{campo.denunciado_id.obrigatorio}")
	private Integer denunciadoId;
	@NotBlank(message = "{campo.descricao.obrigatorio}")
	private String descricao;
	
	public Denuncia converter(UsuarioRepository usuarioRepository) {
		
		Usuario denunciado = usuarioRepository
				.findById(denunciadoId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		return Denuncia.builder()
				.dataDenuncia(LocalDateTime.now())
				.descricao(descricao)
				.denunciado(denunciado)
				.build();
	}
}
