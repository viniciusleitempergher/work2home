package com.work2home.publica.project.rest.controller;

import java.util.List;

import javax.validation.Valid;

import com.work2home.publica.project.rest.dto.ordem_servico.OrcamentoAcceptRequest;
import com.work2home.publica.project.rest.dto.ordem_servico.OrdemServicoResponse;
import com.work2home.publica.project.rest.dto.ordem_servico.SolicitacaoAcceptRequest;
import com.work2home.publica.project.rest.dto.ordem_servico.SolicitacaoRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.work2home.publica.project.service.OrdemServicoService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ordem-servico")
public class OrdemServicoController {

	@Autowired
	private OrdemServicoService service;

	@ApiOperation(value = "Lista as ordens de serviço por status")
	@GetMapping("/filtro/{status}")
	public List<OrdemServicoResponse> findAllByFilter(@PathVariable Integer status) {
		return service.findAllByFilter(status);
	}

	@ApiOperation(value = "Busca uma ordem de serviço por id")
	@GetMapping("/{id}")
	public OrdemServicoResponse findById(@PathVariable Integer id) {
		return service.buscarDtoPorId(id);
	}

	@ApiOperation(value = "Cria uma ordem de serviço solicitada")
	@PostMapping("/solicitar")
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoResponse criarSolicitacao(@RequestBody @Valid SolicitacaoRequest sr) {
		return service.criarSolicitacao(sr);
	}

	@ApiOperation(value = "Adiciona uma imagem na ordem de serviço")
	@PostMapping("/{id}/imagem")
	@ResponseStatus(HttpStatus.CREATED)
	public void cadastrarImagem(@PathVariable Integer id, @RequestParam("image") MultipartFile multipartFile) {
		service.cadastrarImagem(id, multipartFile);
	}

	@ApiOperation(value = "Aceita a solicitação e modifica para um orçamento")
	@PatchMapping("/{id}/aceitar-solicitacao")
	public OrdemServicoResponse aceitarSolicitacao(@PathVariable Integer id,
			@RequestBody @Valid SolicitacaoAcceptRequest acceptRequest) {
		return service.aceitarSolicitacao(acceptRequest, id);
	}

	@ApiOperation(value = "Nega a solicitação")
	@PatchMapping("/{id}/negar-solicitacao")
	public OrdemServicoResponse negarSolicitacao(@PathVariable Integer id) {
		return service.negarSolicitacao(id);
	}

	@ApiOperation(value = "Aceita ou nega o orçamento")
	@PatchMapping("/{id}/aceitar-orcamento")
	public OrdemServicoResponse aceitarOrcamento(@PathVariable Integer id,
			@RequestBody @Valid OrcamentoAcceptRequest orcamentoAcceptRequest) {
		return service.aceitarOrcamento(orcamentoAcceptRequest, id);
	}

	@ApiOperation(value = "Finaliza a ordem de serviço")
	@PatchMapping("/{id}/finalizar-os")
	public OrdemServicoResponse finalizarOrdemServico(@PathVariable Integer id) {
		return service.finalizarOrdemServico(id);
	}

	@ApiOperation(value = "Busca as quantidades de ordens de serviço por status")
	@GetMapping("/quantidades-servicos")
	public List<List<Long>> buscarQuantidadesDeOs() {
		return service.buscarQuantidadesDeOs();
	}
}
