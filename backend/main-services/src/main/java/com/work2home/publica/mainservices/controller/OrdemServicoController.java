package com.work2home.publica.mainservices.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.work2home.publica.mainservices.dtos.SolicitacaoAcceptRequest;
import com.work2home.publica.mainservices.dtos.SolicitacaoRequest;
import com.work2home.publica.mainservices.models.OrdemServico;
import com.work2home.publica.mainservices.services.OrdemServicoService;

@RestController("/main-services/ordem-servico")
public class OrdemServicoController {

	@Autowired
	private OrdemServicoService service;

	@GetMapping("ordem-servico/all")
	public List<OrdemServico> findAll() {
		System.out.print("olaa");
		return service.findAll();
	}

	@GetMapping("ordem-servico/orcamentos-solicitados/{prestadorId}")
	public List<OrdemServico> findSolicitadosByPrestadorId() {

		return service.findSolicitadosByPrestadorId();
	}

	@GetMapping("ordem-servico/{id}")
	public OrdemServico findById(@PathVariable Integer id) {

		return service.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@PostMapping("ordem-servico/{clienteId}/add")
	public OrdemServico criarSolicitacao(@RequestBody @PathVariable Integer clienteId, SolicitacaoRequest sr) {
		return service.criarSolicitacao(sr);
	}

	@PatchMapping("ordem-servico/{id}/aceitar")
	public void createOrdemServico(@RequestBody @PathVariable Integer id, SolicitacaoAcceptRequest acceptRequest) {

		OrdemServico os = service.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		service.aceitarSolicitacao(acceptRequest, os);
	}

}
