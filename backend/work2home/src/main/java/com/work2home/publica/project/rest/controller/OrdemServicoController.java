package com.work2home.publica.project.rest.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import com.work2home.publica.project.rest.dto.ordem_servico.OrcamentoAcceptRequest;
import com.work2home.publica.project.rest.dto.ordem_servico.OrdemServicoResponse;
import com.work2home.publica.project.rest.dto.ordem_servico.SolicitacaoAcceptRequest;
import com.work2home.publica.project.rest.dto.ordem_servico.SolicitacaoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.work2home.publica.project.enums.StatusOrcamento;
import com.work2home.publica.project.model.OrdemServico;
import com.work2home.publica.project.service.OrdemServicoService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ordem-servico")
public class OrdemServicoController {

	@Autowired
	private OrdemServicoService service;

	@GetMapping("/filtro/{status}")
	public List<OrdemServicoResponse> findAll(@PathVariable Integer status) {
		return service.findAll(status);
	}

	@GetMapping("/{id}")
	public OrdemServicoResponse findById(@PathVariable Integer id) {
		return service.buscarDtoPorId(id);
	}

	@PostMapping("/solicitar")
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoResponse criarSolicitacao(@RequestBody @Valid SolicitacaoRequest sr) {
		return service.criarSolicitacao(sr);
	}

	@PostMapping("/{id}/imagem")
	@ResponseStatus(HttpStatus.CREATED)
	public void cadastrarImagem(@PathVariable Integer id, @RequestParam("image") MultipartFile multipartFile) {
		service.cadastrarImagem(id, multipartFile);

	}

	@PatchMapping("/{id}/aceitar-solicitacao")
	public OrdemServicoResponse aceitarSolicitacao(@PathVariable Integer id,
			@RequestBody @Valid SolicitacaoAcceptRequest acceptRequest) {

		return service.aceitarSolicitacao(acceptRequest, id);
	}

	@PatchMapping("/{id}/negar-solicitacao")
	public OrdemServicoResponse negarSolicitacao(@PathVariable Integer id) {

		return service.negarSolicitacao(id);
	}

	@PatchMapping("/{id}/aceitar-orcamento")
	public OrdemServicoResponse aceitarOrcamento(@PathVariable Integer id,
			@RequestBody @Valid OrcamentoAcceptRequest orcamentoAcceptRequest) {

		return service.aceitarOrcamento(orcamentoAcceptRequest, id);
	}
	
	@PatchMapping("/{id}/finalizar-os")
	public OrdemServicoResponse finalizarOrdemServico(@PathVariable Integer id) {

		return service.finalizarOrdemServico(id);
	}
}
