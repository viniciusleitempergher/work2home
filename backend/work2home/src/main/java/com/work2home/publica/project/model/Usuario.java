package com.work2home.publica.project.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name="usuario_tb")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "senha", nullable = false)
	private String senha;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "telefone", nullable = false)
	private String telefone;
	
	@Column(name = "data_nascimento", nullable = true)
	private LocalDate dtNascimento;

	@OneToOne
	@JoinColumn(name = "refresh_token_id", referencedColumnName = "id")
	private RefreshToken refreshToken;

	@OneToMany(mappedBy = "avaliado")
	private List<Avaliacao> avaliacoesRecebidas = new ArrayList<>();
	
	@OneToMany(mappedBy = "avaliador")
	private List<Avaliacao> avaliacoesFeitas = new ArrayList<>();
	
	public Double getMediaAvaliacao() {
		
		double cont = 0.0;
		for(Avaliacao a : avaliacoesRecebidas) {
			cont += a.getNota();
		}
		return cont/avaliacoesRecebidas.size();
	}
}
