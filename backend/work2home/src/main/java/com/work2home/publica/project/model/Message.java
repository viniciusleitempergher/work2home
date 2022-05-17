package com.work2home.publica.project.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String text;
	private LocalDateTime dataEnvio;
	
	@JoinColumn(name = "sender_id", nullable = false)
	@ManyToOne
	private Usuario sender;
	
	@JoinColumn(name = "receiver_id", nullable = false)
	@ManyToOne
	private Usuario receiver;
}
