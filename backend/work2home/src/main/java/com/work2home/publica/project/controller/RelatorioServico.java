package com.work2home.publica.project.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.repositores.UsuarioRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
@RequestMapping("/relatorioServico")
public class RelatorioServico {
	
	@Autowired
	UsuarioRepository usuarioRepository;


	@GetMapping()
	public ResponseEntity<byte[]> downloadInvoice() throws JRException, IOException {
		
		
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(usuarioRepository.findAll(), false);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("total", "7000");

		JasperReport compileReport = JasperCompileManager
				.compileReport(new FileInputStream("src/main/resources/relatorioServico/relatorioServico2.jrxml"));

		JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, parameters, beanCollectionDataSource);

		// JasperExportManager.exportReportToPdfFile(jasperPrint,
		// System.currentTimeMillis() + ".pdf");

		byte data[] = JasperExportManager.exportReportToPdf(jasperPrint);

		System.err.println(data);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
	}

}
