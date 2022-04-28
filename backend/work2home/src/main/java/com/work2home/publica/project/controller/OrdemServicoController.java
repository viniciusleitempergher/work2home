package com.work2home.publica.project.controller;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.dto.ordem_servico.OrcamentoAcceptRequest;
import com.work2home.publica.project.dto.ordem_servico.OrdemServicoResponse;
import com.work2home.publica.project.dto.ordem_servico.SolicitacaoAcceptRequest;
import com.work2home.publica.project.dto.ordem_servico.SolicitacaoRequest;
import com.work2home.publica.project.dto.prestador.PrestadorRequest;
import com.work2home.publica.project.model.OrdemServico;
import com.work2home.publica.project.service.OrdemServicoService;

@RestController
@RequestMapping("/ordem-servico")
public class OrdemServicoController {

	@Autowired
	private OrdemServicoService service;

	@GetMapping("/all")
	public List<OrdemServico> findAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public OrdemServicoResponse findById(@PathVariable Integer id) {
		return service.buscarDtoPorId(id);
	}

	@RolesAllowed("ROLES_CLIENTE")
	@PostMapping("/add")
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServico criarSolicitacao(@RequestBody SolicitacaoRequest sr) {
		return service.criarSolicitacao(sr);
	}

	@RolesAllowed("ROLES_PRESTADOR")
	@PatchMapping("/{id}/aceitar-solicitacao")
	public OrdemServico aceitarSolicitacao(@PathVariable Integer id,
			@RequestBody SolicitacaoAcceptRequest acceptRequest) {

		return service.aceitarSolicitacao(acceptRequest, id);
	}

	@RolesAllowed("ROLES_PRESTADOR")
	@PatchMapping("/{id}/negar-solicitacao")
	public void negarSolicitacao(@PathVariable Integer id) {

		service.negarSolicitacao(id);
	}

	@RolesAllowed("ROLES_CLIENTE")
	@PatchMapping("/{id}/aceitar-orcamento")
	public OrdemServico aceitarOrcamento(@PathVariable Integer id,
			@RequestBody OrcamentoAcceptRequest orcamentoAcceptRequest) {

		return service.aceitarOrcamento(orcamentoAcceptRequest, id);
	}

	@RolesAllowed("ROLES_PRESTADOR")
	@PatchMapping("/{id}/finalizar-os")
	public OrdemServico finalizarOrdemServico(@PathVariable Integer id) {

		return service.finalizarOrdemServico(id);
	}

}
