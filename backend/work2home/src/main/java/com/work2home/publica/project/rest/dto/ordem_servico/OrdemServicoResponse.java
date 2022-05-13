package com.work2home.publica.project.rest.dto.ordem_servico;

import com.work2home.publica.project.model.OrdemServico;
import com.work2home.publica.project.utils.Formatador;
import lombok.Data;

@Data
public class OrdemServicoResponse {

	private Integer id;
	private String nomeCliente;
	private String nomePrestador;
	private Integer clienteId;
	private Integer prestadorId;
	private String area;
	private String descricao;
	private String dataSolicitada;
	private String dataInicio;
	private String dataFim;
	private Integer tempoEstimado;
	private Double valor;
	private String status;
	private String imagemUrl;
	
	public OrdemServicoResponse(OrdemServico os) {
		this.id = os.getId();
		this.imagemUrl = os.getImagemUrl();
		this.nomeCliente = os.getEndereco().getCliente().getUsuario().getNome();
		this.nomePrestador = os.getPrestador().getUsuario().getNome();
		this.clienteId = os.getEndereco().getCliente().getId();
		this.prestadorId = os.getPrestador().getId();
		this.area = os.getCategoriaServico().getNome();
		this.descricao = os.getDescricao();
		
		if(os.getDataSolicitada() != null) {
			this.dataSolicitada = os.getDataSolicitada().format(Formatador.getFormatter());
		}
		if(os.getDataInicio() != null) {
			this.dataInicio = os.getDataInicio().format(Formatador.getFormatter());
		}
		if(os.getDataFim() != null) {
			this.dataFim = os.getDataFim().format(Formatador.getFormatter());
		}
		this.tempoEstimado = os.getTempoEstimado();
		this.valor = os.getValor();
		this.status = os.getStatus().toString();
	}
}
