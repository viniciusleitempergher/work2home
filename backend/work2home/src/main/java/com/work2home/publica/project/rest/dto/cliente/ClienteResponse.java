package com.work2home.publica.project.rest.dto.cliente;

import com.work2home.publica.project.model.Cliente;
import lombok.Data;

@Data
public class ClienteResponse {

    private String nome;
    private String email;
    private String telefone;
    private Double mediaAvaliacao;
    private String estado;
    private String cidade;

    public ClienteResponse(Cliente cliente) {
        this.nome = cliente.getUsuario().getNome();
        this.email = cliente.getUsuario().getEmail();
        this.telefone = cliente.getUsuario().getTelefone();
        this.mediaAvaliacao = cliente.getUsuario().getMediaAvaliacao();
        this.estado = cliente.getEndereco().getCidade().getEstado();
        this.cidade = cliente.getEndereco().getCidade().getNome();
    }
}
