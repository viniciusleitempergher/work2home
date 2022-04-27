package com.work2home.publica.project.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.UsuarioRepository;
import com.work2home.publica.project.service.UsuarioService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
@RequestMapping("/relatorio")
public class RelatorioServico {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	UsuarioService usuarioService;


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
		parameters.put("mediaAvaliacao",Long.toString(qtdAdm));

		JasperPrint jasperPrint = JasperFillManager.fillReport("src/main/resources/relatorioServico/relatorioUsuario.jasper", parameters, beanCollectionDataSource);

		byte data[] = JasperExportManager.exportReportToPdf(jasperPrint);

		System.err.println(data);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=relatoriousuario.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
	}
	
	@GetMapping("/servico")
	public ResponseEntity<byte[]> relatorioServico() throws JRException, IOException {
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
		parameters.put("mediaAvaliacao",Long.toString(qtdAdm));

		JasperPrint jasperPrint = JasperFillManager.fillReport("src/main/resources/relatorioServico/relatorioServico.jasper", parameters, beanCollectionDataSource);

		byte data[] = JasperExportManager.exportReportToPdf(jasperPrint);

		System.err.println(data);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=relatorioservico.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
	}

}
