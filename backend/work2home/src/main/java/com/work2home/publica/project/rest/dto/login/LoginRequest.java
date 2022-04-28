package com.work2home.publica.project.rest.dto.login;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequest {
	@NotBlank(message = "{campo.email.obrigatorio}")
	private String email;
	@NotBlank(message = "{campo.senha.obrigatorio}")
	private String senha;
}
