package com.work2home.publica.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageDto {
	private Integer userFrom;
	private Integer userTo;
	private String text;
}
