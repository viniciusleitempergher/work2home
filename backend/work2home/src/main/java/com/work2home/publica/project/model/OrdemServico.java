package com.work2home.publica.project.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.work2home.publica.project.enums.StatusOrcamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	@Enumerated(value = EnumType.ORDINAL)
	private StatusOrcamento status;
	
	@Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
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
	
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoriaServico; 
	
	@ManyToOne
	@JoinColumn(name = "prestador_id")
	private Prestador prestador;

	@ManyToOne
	@JoinColumn(name = "endereco_id")
	private Endereco endereco;
	
	public void cancelar() {
		this.status = StatusOrcamento.NEGADO;
	}
}
