package com.work2home.publica.mainservices.models;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.work2home.publica.mainservices.enums.StatusOrcamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "ordem_servico_tb")
public class OrdemServico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "status_orcamento", nullable = false)
	private StatusOrcamento status;
	
	@Column(name = "descricao", nullable = false)
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
	
	@Column(name = "data_solicitada", nullable = false)
	private LocalDate dataSolicitada;
	
	@JoinColumn(table = "categoria_servico_tb", name = "categoria_id", nullable = false)
	private Integer categoriaServicoId; 
	
	@JoinColumn(referencedColumnName = "id", table = "prestador_tb", name = "prestador_id", nullable = false)
	private Integer prestadorId;

	@JoinColumn(referencedColumnName = "id", table = "endereco_tb", name = "endereco_id", nullable = false)
	private Integer enderecoId;
	
	public void cancelar() {
		this.status = StatusOrcamento.NEGADO;
	}
}
