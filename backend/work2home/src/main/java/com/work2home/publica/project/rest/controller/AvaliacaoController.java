package com.work2home.publica.project.rest.controller;

import javax.validation.Valid;
import com.work2home.publica.project.rest.dto.avaliacao.AvaliarClienteDto;
import com.work2home.publica.project.rest.dto.avaliacao.AvaliarPrestadorDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.work2home.publica.project.service.AvaliacaoService;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

	@Autowired
	private AvaliacaoService service;

	@ApiOperation(value = "Avalia um cliente")
	@PostMapping("/prestador-avalia-cliente/{ordemServicoId}")
	@ResponseStatus(HttpStatus.CREATED)
	public void avaliarCliente(@PathVariable Integer ordemServicoId, @RequestBody @Valid AvaliarClienteDto avaliacaoDto) {
		service.avaliarCliente(ordemServicoId, avaliacaoDto);
	}

	@ApiOperation(value = "Avalia um prestador")
	@PostMapping("/cliente-avalia-prestador/{ordemServicoId}")
	@ResponseStatus(HttpStatus.CREATED)
	public void avaliarPrestador(@PathVariable Integer ordemServicoId, @RequestBody @Valid AvaliarPrestadorDto avaliacaoDto) {
		service.avaliarPrestador(ordemServicoId, avaliacaoDto);
	}

	@ApiOperation(value = "Confere se uma avaliação da ordem de um serviço existe")
	@GetMapping("/avaliacao-existe/{osId}")
	public Boolean avaliacaoExiste(@PathVariable Integer osId){
		return service.avaliacaoExiste(osId);
	}
}
