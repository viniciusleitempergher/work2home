package com.work2home.publica.project.rest.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.model.OrdemServico;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.OrdemServicoRepository;
import com.work2home.publica.project.repositores.UsuarioRepository;
import com.work2home.publica.project.service.UsuarioService;
import com.work2home.publica.project.utils.Formatador;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
@RequestMapping("/relatorio")
public class RelatorioServicoController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;


	@ApiOperation(value = "Gera um relatório de usuários em PDF")
	@GetMapping("/usuario")
	public ResponseEntity<byte[]> relatorioUsuario() throws JRException, IOException {

		long qdtUsuario = usuarioService.quantidadeUsuario();
		long vetor[] = usuarioService.quantidadesCargos();
		
		long qtdUsuarioBanido = vetor[0];
		long qtdCliente = vetor[1];
		long qtdPrestador = vetor[2];
		long qtdAdm= vetor[3];
		List<Usuario> us= usuarioRepository.findAll();
		
		us.forEach(u -> System.out.println(u.getMediaAvaliacao()));
		
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(us, false);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("totalUsuarios",Long.toString(qdtUsuario));
		parameters.put("totalUsuariosBanido",Long.toString(qtdUsuarioBanido));
		parameters.put("totalCliente",Long.toString(qtdCliente));
		parameters.put("totalPrestador",Long.toString(qtdPrestador));
		parameters.put("totalAdm",Long.toString(qtdAdm));

		JasperPrint jasperPrint = JasperFillManager.fillReport("src/main/resources/relatorioServico/relatorioUsuario.jasper", parameters, beanCollectionDataSource);

		byte data[] = JasperExportManager.exportReportToPdf(jasperPrint);


		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=relatoriousuario.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
	}

	@ApiOperation(value = "Gera um relatório de serviço por id em PDF")
	@GetMapping("/servico/{id}")
	public ResponseEntity<byte[]> relatorioServico(@PathVariable Integer id) throws JRException, IOException {

		OrdemServico ordemServico= ordemServicoRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("descricao",ordemServico.getDescricao());
		parameters.put("nomeCliente",ordemServico.getEndereco().getCliente().getUsuario().getNome());
		parameters.put("emailCliente",ordemServico.getEndereco().getCliente().getUsuario().getEmail());
		parameters.put("telefoneCliente",ordemServico.getEndereco().getCliente().getUsuario().getTelefone());
		parameters.put("cpfCliente",ordemServico.getEndereco().getCliente().getCpf());
		parameters.put("nomePrestador",ordemServico.getPrestador().getUsuario().getNome());
		parameters.put("emailPrestador",ordemServico.getPrestador().getUsuario().getEmail());
		parameters.put("telefonePrestador",ordemServico.getPrestador().getUsuario().getTelefone());
		parameters.put("cnpj",ordemServico.getPrestador().getCnpj());
		parameters.put("nomeEmpresa",ordemServico.getPrestador().getNomeFantasia());
		parameters.put("dataInicio",ordemServico.getDataInicio().format(Formatador.getFormatter()));
		parameters.put("dataFinal",ordemServico.getDataFim().format(Formatador.getFormatter()));
		parameters.put("categoriaServico",ordemServico.getCategoriaServico().getNome());
		parameters.put("valorServico","R$ "+ordemServico.getValor().toString());
		parameters.put("bairro",ordemServico.getEndereco().getBairro());
		parameters.put("cidade",ordemServico.getEndereco().getCidade().getNome());
		parameters.put("estado",ordemServico.getEndereco().getCidade().getEstado());
		parameters.put("rua",ordemServico.getEndereco().getLogradouro());
		parameters.put("complemento",ordemServico.getEndereco().getComplemento());
		parameters.put("numero",ordemServico.getEndereco().getNumero());

		JasperPrint jasperPrint = JasperFillManager.fillReport("src/main/resources/relatorioServico/relatorioServico.jasper", parameters, new JREmptyDataSource());

		byte data[] = JasperExportManager.exportReportToPdf(jasperPrint);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=relatorioservico.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
	}
}
