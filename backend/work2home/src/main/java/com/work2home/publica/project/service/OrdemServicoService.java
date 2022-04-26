package com.work2home.publica.project.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.dto.main_service.OrcamentoAcceptRequest;
import com.work2home.publica.project.dto.main_service.OrdemServicoResponse;
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

	public OrdemServicoResponse buscarDtoPorId(Integer id) {
		
		OrdemServico os = repository
				.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		return new OrdemServicoResponse(os);
	}
	

	
	public OrdemServico criarSolicitacao(SolicitacaoRequest or) {
		
		return repository.save(or.converter(categoriaRepository, prestadorRepository, enderecoRepository));
	}
	
	public OrdemServico aceitarSolicitacao(SolicitacaoAcceptRequest acceptRequest, Integer id) {
		

		OrdemServico os = repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	
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

	public OrdemServico aceitarOrcamento(OrcamentoAcceptRequest orcamentoAcceptRequest, Integer id) {
		
		OrdemServico os = repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		if(os.getStatus() != StatusOrcamento.EM_ORCAMENTO) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		else if(LocalDate.now().isAfter(os.getDataInicio()) || LocalDate.now().isEqual(os.getDataInicio())) {
			throw new ResponseStatusException(HttpStatus.GONE);
		}
		
		if(orcamentoAcceptRequest.isAceitar()) {
			os.setStatus(StatusOrcamento.EM_ANDAMENTO);
		}else {
			os.cancelar();
		}
		return repository.save(os);
	}

	public OrdemServico finalizarOrdemServico(Integer id) {
		
		OrdemServico os = repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		if(os.getStatus() != StatusOrcamento.EM_ANDAMENTO) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			
		}else if(LocalDate.now().isBefore(os.getDataInicio()) || LocalDate.now().isEqual(os.getDataInicio())) {
			throw new ResponseStatusException(HttpStatus.TOO_EARLY);
		}
		os.setStatus(StatusOrcamento.FINALIZADO);
		os.setDataFim(LocalDate.now());
		return repository.save(os);
	}
	
}
