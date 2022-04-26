package com.work2home.publica.project.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.dto.main_service.OrcamentoAcceptRequest;
import com.work2home.publica.project.dto.main_service.SolicitacaoAcceptRequest;
import com.work2home.publica.project.dto.main_service.SolicitacaoRequest;
import com.work2home.publica.project.enums.StatusOrcamento;
import com.work2home.publica.project.model.OrdemServico;
import com.work2home.publica.project.repositores.CategoriaRepository;
import com.work2home.publica.project.repositores.EnderecoRepository;
import com.work2home.publica.project.repositores.OrdemServicoRepository;
import com.work2home.publica.project.repositores.PrestadorRepository;
import com.work2home.publica.project.utils.Formatador;

@Service
public class OrdemServicoService {

	@Autowired
	private OrdemServicoRepository repository;
	
	@Autowired 
	private PrestadorRepository prestadorRepository;
	
	@Autowired 
	private EnderecoRepository enderecoRepository;
	
	@Autowired 
	private CategoriaRepository categoriaRepository;
	
	public List<OrdemServico> findAll(){
		return repository.findAll();
	}

	public List<OrdemServico> findSolicitadosByPrestadorId() {
		return null;
	}

	public Optional<OrdemServico> findById(Integer id) {
		
		return repository.findById(id);
	}
	
	public OrdemServico criarSolicitacao(SolicitacaoRequest or) {
		
		return repository.save(or.converter(categoriaRepository, prestadorRepository, enderecoRepository));
	}
	
	public OrdemServico aceitarSolicitacao(SolicitacaoAcceptRequest acceptRequest, OrdemServico os) {
	
	LocalDate data = LocalDate.parse(acceptRequest.getDataInicio(), Formatador.getFormatter());
	LocalDate agora = LocalDate.now();
	
	if(os.getStatus() != StatusOrcamento.SOLICITADO) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
	}else if(data.isBefore(agora) || data.isEqual(agora)) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
	}
			
	os.setStatus(StatusOrcamento.EM_ORCAMENTO);
	os.setValor(acceptRequest.getValor());
	os.setDataInicio(data);
	os.setTempoEstimado(acceptRequest.getTempoEstimado());
	return repository.save(os);
	}

	public OrdemServico aceitarOrcamento(OrcamentoAcceptRequest orcamentoAcceptRequest, OrdemServico os) {
		
		if(os.getStatus() != StatusOrcamento.EM_ORCAMENTO) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		else if(LocalDate.now().isAfter(os.getDataInicio()) || LocalDate.now().isEqual(os.getDataInicio())) {
			throw new ResponseStatusException(HttpStatus.GONE);
		}
		
		if(orcamentoAcceptRequest.isAceitar()) {
			os.setStatus(StatusOrcamento.EM_ANDAMENTO);
		}else {
			os.setStatus(StatusOrcamento.NEGADO);
		}
		return repository.save(os);
	}

	public OrdemServico finalizarOrdemServico(OrdemServico os) {
		if(os.getStatus() != StatusOrcamento.EM_ANDAMENTO) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		os.setStatus(StatusOrcamento.FINALIZADO);
		os.setDataFim(LocalDate.now());
		return repository.save(os);
	}
	
}
