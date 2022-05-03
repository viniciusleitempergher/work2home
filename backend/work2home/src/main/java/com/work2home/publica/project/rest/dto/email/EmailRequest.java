package com.work2home.publica.project.rest.dto.email;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class EmailRequest {

    @Email(message = "{campo.email.invalido}")
    @NotEmpty(message = "{campo.email.obrigatorio}")
    private String email;
}
