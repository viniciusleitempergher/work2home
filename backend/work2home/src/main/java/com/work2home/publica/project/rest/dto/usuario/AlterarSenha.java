package com.work2home.publica.project.rest.dto.usuario;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class AlterarSenha {

    @NotBlank(message = "{campo.senha.obrigatorio}")
    @Length(min = 8, message = "{campo.senha.insuficiente}")
    private String novaSenha;
}
