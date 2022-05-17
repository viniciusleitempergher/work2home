package com.work2home.publica.project.rest.dto.usuario;

import java.time.LocalDate;
import com.work2home.publica.project.enums.Roles;
import com.work2home.publica.project.model.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
	private Integer id;
	private String email;
	private String nome;
	private String telefone;
	private LocalDate dtNascimento;
	private Roles role;
	private String imagemUrl;
	
	public UsuarioResponse(Usuario usuario) {
		this.id = usuario.getId();
		this.email = usuario.getEmail();
		this.nome = usuario.getNome();
		this.telefone = usuario.getTelefone();
		this.dtNascimento = usuario.getDtNascimento();
		this.role = usuario.getRole();
		this.imagemUrl = usuario.getImagemUrl();
    }
}
