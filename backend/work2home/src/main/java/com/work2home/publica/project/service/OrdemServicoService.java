package com.work2home.publica.project.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.dto.ordem_servico.OrcamentoAcceptRequest;
import com.work2home.publica.project.dto.ordem_servico.OrdemServicoResponse;
import com.work2home.publica.project.dto.ordem_servico.SolicitacaoAcceptRequest;
import com.work2home.publica.project.dto.ordem_servico.SolicitacaoRequest;
import com.work2home.publica.project.enums.StatusOrcamento;
import com.work2home.publica.project.model.Cliente;
import com.work2home.publica.project.model.OrdemServico;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.CategoriaRepository;
import com.work2home.publica.project.repositores.ClienteRepository;
import com.work2home.publica.project.repositores.EnderecoRepository;
import com.work2home.publica.project.repositores.OrdemServicoRepository;
import com.work2home.publica.project.repositores.PrestadorRepository;
import com.work2home.publica.project.utils.Formatador;
import com.work2home.publica.project.utils.JwtUtil;

@Service
public class OrdemServicoService {

	@Autowired
	private OrdemServicoRepository repository;

	@Autowired
	private PrestadorRepository prestadorRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private JwtUtil jwt;

	public List<OrdemServico> findAll() {
		return repository.findAll();
	}

	public OrdemServicoResponse buscarDtoPorId(Integer id) {

		OrdemServico os = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return new OrdemServicoResponse(os);
	}

	public OrdemServico criarSolicitacao(SolicitacaoRequest or) {

		Usuario usuario = jwt.getUserFromHeaderToken();

		Cliente cliente = clienteRepository.findById(usuario.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

		return repository.save(or.converter(categoriaRepository, prestadorRepository, cliente.getEndereco()));
	}

	public OrdemServico aceitarSolicitacao(SolicitacaoAcceptRequest acceptRequest, Integer id) {

		OrdemServico os = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		Usuario usuario = jwt.getUserFromHeaderToken();

		if (os.getPrestador().getId() != usuario.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		LocalDate data = LocalDate.parse(acceptRequest.getDataInicio(), Formatador.getFormatter());
		LocalDate agora = LocalDate.now();

		if (os.getStatus() != StatusOrcamento.SOLICITADO) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else if (data.isBefore(agora)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		os.setStatus(StatusOrcamento.EM_ORCAMENTO);
		os.setValor(acceptRequest.getValor());
		os.setDataInicio(data);
		os.setTempoEstimado(acceptRequest.getTempoEstimado());
		return repository.save(os);
	}

	public OrdemServico aceitarOrcamento(OrcamentoAcceptRequest orcamentoAcceptRequest, Integer id) {

		OrdemServico os = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		Usuario usuario = jwt.getUserFromHeaderToken();

		if (os.getEndereco().getCliente().getId() != usuario.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		if (os.getStatus() != StatusOrcamento.EM_ORCAMENTO) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else if (LocalDate.now().isAfter(os.getDataInicio()) || LocalDate.now().isEqual(os.getDataInicio())) {
			throw new ResponseStatusException(HttpStatus.GONE);
		}

		if (orcamentoAcceptRequest.isAceitar()) {
			os.setStatus(StatusOrcamento.EM_ANDAMENTO);
		} else {
			os.cancelar();
		}
		return repository.save(os);
	}

	public OrdemServico finalizarOrdemServico(Integer id) {

		OrdemServico os = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		Usuario usuario = jwt.getUserFromHeaderToken();

		if (os.getPrestador().getId() != usuario.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		if (os.getStatus() != StatusOrcamento.EM_ANDAMENTO) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

		} else if (LocalDate.now().isBefore(os.getDataInicio()) || LocalDate.now().isEqual(os.getDataInicio())) {
			throw new ResponseStatusException(HttpStatus.TOO_EARLY);
		}
		os.setStatus(StatusOrcamento.FINALIZADO);
		os.setDataFim(LocalDate.now());
		return repository.save(os);
	}

	public void negarSolicitacao(Integer id) {
		OrdemServico os = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		Usuario usuario = jwt.getUserFromHeaderToken();

		if (os.getPrestador().getId() != usuario.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		os.cancelar();	
		repository.delete(os);
	}

}
