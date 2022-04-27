package com.work2home.publica.project.dto;

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
