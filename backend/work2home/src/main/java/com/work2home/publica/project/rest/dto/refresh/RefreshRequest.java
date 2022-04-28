package com.work2home.publica.project.rest.dto.refresh;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshRequest {
	
	@NotBlank(message = "{campo.refresh_token.obrigatorio}")
	String refreshToken;
}
