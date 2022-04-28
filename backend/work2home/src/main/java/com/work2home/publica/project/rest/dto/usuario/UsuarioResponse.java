package com.work2home.publica.project.rest.dto.usuario;

import java.time.LocalDate;
import com.work2home.publica.project.enums.Roles;
import lombok.Data;

@Data
public class UsuarioResponse {
	private Integer id;
	private String email;
	private String nome;
	private String telefone;
	private LocalDate dtNascimento;
	private Roles role;
}
