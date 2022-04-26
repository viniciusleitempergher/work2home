package com.work2home.publica.project.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
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
@Table(name="refresh_token_tb")
public class RefreshToken {
	@Id
	private Integer id;
	
	@Column(name = "token", nullable = false, unique = true)
	private String token;

	@OneToOne
	@JoinColumn(name = "usuario_id", referencedColumnName = "id")
	@MapsId
	private Usuario usuario;
	
}
