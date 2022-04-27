package com.work2home.publica.project.dto.main_service;

import com.work2home.publica.project.model.OrdemServico;
import com.work2home.publica.project.utils.Formatador;
import lombok.Data;

@Data
public class OrdemServicoResponse {

	private String nomeCliente;
	private String nomePrestador;
	private String area;
	private String descricao;
	private String dataSolicitada;
	private String dataInicio;
	private String dataFim;
	private Integer tempoEstimado;
	private Double valor;
	private String status;
	
	public OrdemServicoResponse(OrdemServico os) {
		this.nomeCliente = os.getEndereco().getCliente().getUsuario().getNome();
		this.nomePrestador = os.getPrestador().getUsuario().getNome();
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
