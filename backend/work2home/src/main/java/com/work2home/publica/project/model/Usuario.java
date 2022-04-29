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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.work2home.publica.project.enums.Roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "usuario_tb")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "email", nullable = false)
	private String email;

	@JsonIgnore
	@Column(name = "senha", nullable = false)
	private String senha;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "telefone", nullable = false)
	private String telefone;

	@Column(name = "imagem_url")
	private String imagemUrl;

	@Column(name = "data_nascimento", nullable = true)
	private LocalDate dtNascimento;

	@Column(name = "role", nullable = false)
	private Roles role;

	@JsonIgnore
	@PrimaryKeyJoinColumn
	@OneToOne
	@JoinColumn(name = "refresh_token_id", referencedColumnName = "id")
	private RefreshToken refreshToken;

	@OneToMany(mappedBy = "avaliado")
	private List<Avaliacao> avaliacoesRecebidas = new ArrayList<>();

	@OneToMany(mappedBy = "avaliador")
	private List<Avaliacao> avaliacoesFeitas = new ArrayList<>();

	public Double getMediaAvaliacao() {

		if (avaliacoesRecebidas == null || avaliacoesRecebidas.isEmpty()) {
			return 2.0;
		} else {
			double cont = 0.0;
			for (Avaliacao a : avaliacoesRecebidas) {
				cont += a.getNota();
			}
			return cont / avaliacoesRecebidas.size();
		}
	}
}
