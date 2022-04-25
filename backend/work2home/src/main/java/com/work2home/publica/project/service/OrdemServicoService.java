package com.work2home.publica.project.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	
	
	public void aceitarSolicitacao(SolicitacaoAcceptRequest acceptRequest, OrdemServico os) {
	
	os.setStatus(StatusOrcamento.EM_ORCAMENTO);
	os.setValor(acceptRequest.getValor());
	os.setDataInicio(LocalDate.parse(acceptRequest.getDataInicio() , Formatador.getFormatter()));
	os.setTempoEstimado(acceptRequest.getTempoEstimado());
	repository.save(os);
	}
	
}
