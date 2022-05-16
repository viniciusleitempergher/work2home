package com.work2home.publica.project.rest.dto.usuario;

import java.util.List;

import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.rest.dto.denuncia.DenunciaDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDenunciasResponse {
	private String nome;
	private String email;
	private String telefone;
	private String cargo;
	private List<DenunciaDto> denuncias;
	
	public UsuarioDenunciasResponse(Usuario usuario) {
		this.nome= usuario.getNome();
		this.email = usuario.getEmail();
		this.telefone = usuario.getTelefone();
		this.cargo = usuario.getRole().toString();
		this.denuncias = usuario.getDenunciasRecebidas()
				.stream()
				.map(DenunciaDto::new)
				.toList();
		
	}

}
