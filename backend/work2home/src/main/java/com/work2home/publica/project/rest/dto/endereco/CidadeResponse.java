package com.work2home.publica.project.rest.dto.endereco;

import com.work2home.publica.project.model.Cidade;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CidadeResponse {

    private Integer id;
    private String nome;
    private String estado;

    public CidadeResponse(Cidade cidade){
        this.id = cidade.getId();
        this.nome = cidade.getNome();
        this.estado = cidade.getEstado();
    }
}
