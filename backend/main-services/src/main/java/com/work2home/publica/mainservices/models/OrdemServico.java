package com.work2home.publica.mainservices.models;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import com.work2home.publica.mainservices.enums.StatusOrcamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "ordem_servico_tb")
public class OrdemServico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "status_orcamento")
	private StatusOrcamento status;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "valor")
	private Double valor;
	
	@Column(name = "imagem_url")
	private String imagemUrl;
	
	@Column(name = "tempo_estimado")
	private Integer tempoEstimado;
	
	@Column(name = "data_inicio")
	private LocalDate dataInicio;
	
	@Column(name = "data_fim")
	private LocalDate dataFim;
	
	@Column(name = "data_solicitada")
	private LocalDate dataSolicitada;
	
	@JoinColumn(table = "categoria_servico_tb", name = "categoria_id")
	private Integer categoriaServicoId; 
	
	@JoinColumn(table = "prestador_tb", name = "prestador_id")
	private Integer prestadorId;
	
	@JoinColumn(table = "endereco_tb", name = "endereco_id")
	private Integer enderecoId;
	
	public void cancelar() {
		this.status = StatusOrcamento.NEGADO;
	}
}
