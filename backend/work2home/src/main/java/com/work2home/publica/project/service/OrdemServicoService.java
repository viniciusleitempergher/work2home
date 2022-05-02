package com.work2home.publica.project.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.work2home.publica.project.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.rest.dto.ordem_servico.OrcamentoAcceptRequest;
import com.work2home.publica.project.rest.dto.ordem_servico.OrdemServicoResponse;
import com.work2home.publica.project.rest.dto.ordem_servico.SolicitacaoAcceptRequest;
import com.work2home.publica.project.rest.dto.ordem_servico.SolicitacaoRequest;
import com.work2home.publica.project.enums.Roles;
import com.work2home.publica.project.enums.StatusOrcamento;
import com.work2home.publica.project.model.Cliente;
import com.work2home.publica.project.model.OrdemServico;
import com.work2home.publica.project.model.Prestador;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.CategoriaRepository;
import com.work2home.publica.project.repositores.ClienteRepository;
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

	public List<OrdemServicoResponse> findAll(Integer status) {	
		
		Usuario usuario = jwt.getUserFromHeaderToken();
		
		if(usuario.getRole() == Roles.CLIENTE) {
			
			Cliente cliente = clienteRepository
					.findById(usuario.getId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
			
			return repository
					.findByStatusOrcamentoAndEnderecoId(status, cliente.getEndereco().getId())
					.stream()
					.map(OrdemServicoResponse::new)
					.toList();
			
		}else if(usuario.getRole() == Roles.PRESTADOR) {
			
			return repository
					.findByStatusOrcamentoAndPrestadorId(status, usuario.getId())
					.stream()
					.map(OrdemServicoResponse::new)
					.toList();
		}else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}

	public OrdemServicoResponse buscarDtoPorId(Integer id) {

		Usuario usuario = jwt.getUserFromHeaderToken();
		
		OrdemServico os = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		if(os.getPrestador().getId() != usuario.getId() && os.getEndereco().getCliente().getId() != usuario.getId() 
				&& usuario.getRole() != Roles.ADMIN ) {
		  throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		return new OrdemServicoResponse(os);
	}
	
	public OrdemServicoResponse criarSolicitacao(SolicitacaoRequest sr) {

		Usuario usuario = jwt.getUserFromHeaderToken();

		Cliente cliente = clienteRepository.findById(usuario.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

		OrdemServico os = repository.save(sr.converter(categoriaRepository, prestadorRepository, cliente.getEndereco()));
		return new OrdemServicoResponse(os);
	}

	public OrdemServicoResponse aceitarSolicitacao(SolicitacaoAcceptRequest acceptRequest, Integer id) {

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
		return new OrdemServicoResponse(repository.save(os));
	}

	public OrdemServicoResponse aceitarOrcamento(OrcamentoAcceptRequest orcamentoAcceptRequest, Integer id) {

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
		return new OrdemServicoResponse(repository.save(os));
	}

	public OrdemServicoResponse finalizarOrdemServico(Integer id) {

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
		return new OrdemServicoResponse(repository.save(os));
	}

	public OrdemServicoResponse negarSolicitacao(Integer id) {
		OrdemServico os = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		Usuario usuario = jwt.getUserFromHeaderToken();

		if (os.getPrestador().getId() != usuario.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		os.cancelar();	
		return new OrdemServicoResponse(repository.save(os));
	}

	public void cadastrarImagem(Integer id, MultipartFile multipartFile) {

		OrdemServico os = repository
				.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

		if(os.getEndereco().getCliente().getId() != jwt.getUserFromHeaderToken().getId()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		
		String uuid = UUID.randomUUID().toString();
		String dir = "../images/ordem_servico";

		os.setImagemUrl(dir + uuid);
		repository.save(os);

		try {
			FileUploadUtil.saveFile(dir, uuid , multipartFile);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
