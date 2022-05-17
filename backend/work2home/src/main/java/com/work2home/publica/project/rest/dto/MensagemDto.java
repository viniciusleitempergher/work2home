package com.work2home.publica.project.rest.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MensagemDto {
	private Integer userFrom;
	private Integer userTo;
	private String text;
	private LocalDateTime sentDate;
}
