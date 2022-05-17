package com.work2home.publica.project.rest.controller.exceptions_response;

import lombok.Data;
import java.util.List;

@Data
public class ApiErros {

    private List<String> erros;

    public ApiErros(List<String> erros) {
        this.erros = erros;
    }
}