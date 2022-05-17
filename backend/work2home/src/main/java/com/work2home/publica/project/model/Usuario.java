package com.work2home.publica.project.model;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.work2home.publica.project.enums.Roles;

import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "usuario_tb")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@JsonIgnore
	@Column(name = "senha", nullable = false)
	private String senha;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "telefone")
	private String telefone;

	@Column(name = "imagem_url")
	private String imagemUrl;

	@Column(name = "data_nascimento")
	private LocalDate dtNascimento;
	
	@Column(name = "data_criacao")
	private LocalDate dataCriacao;

	@Column(name = "role", nullable = false)
	@Enumerated(value = EnumType.ORDINAL)
	private Roles role;

	@PrimaryKeyJoinColumn
	@OneToOne
	@JoinColumn(name = "refresh_token_id", referencedColumnName = "id")
	private RefreshToken refreshToken;

	@OneToMany(mappedBy = "avaliado")
	private List<Avaliacao> avaliacoesRecebidas = new ArrayList<>();

	@OneToMany(mappedBy = "avaliador")
	private List<Avaliacao> avaliacoesFeitas = new ArrayList<>();
	
	@OneToMany(mappedBy = "denunciado")
	private List<Denuncia> denunciasRecebidas = new ArrayList<>();

	@OneToMany(mappedBy = "sender")
	private List<Mensagem> mensagensEnviadas = new ArrayList<>();

	@OneToMany(mappedBy = "receiver")
	private List<Mensagem> mensagensRecebidas = new ArrayList<>();
	
	public Double getMediaAvaliacao() {
		Locale.setDefault(Locale.US);
		if (avaliacoesRecebidas == null || avaliacoesRecebidas.isEmpty()) {
			return 5.0;
		} else {
			double cont = 0.0;
			for (Avaliacao a : avaliacoesRecebidas) {
				cont += a.getNota();
			}
			return Double.parseDouble(new DecimalFormat("0.0").format(cont/avaliacoesRecebidas.size()));
		}
	}
}
