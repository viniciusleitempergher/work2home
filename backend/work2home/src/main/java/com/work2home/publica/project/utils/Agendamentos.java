package com.work2home.publica.project.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.work2home.publica.project.repositores.OrdemServicoRepository;
import com.work2home.publica.project.repositores.PrestadorRepository;

@EnableScheduling
@Component
public class Agendamentos {
	
	@Autowired
	private PrestadorRepository prestadorRepository;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	private static final long SEGUNDO = 1000L;
	private static final long MINUTO = 60L * SEGUNDO;
	private static final long HORA = 60L * MINUTO;
	private static final long DIA = 24L * HORA;
	
	@Scheduled(fixedDelay = SEGUNDO * 10l)
	public void desativarPrestador() {
		System.out.println("Olaa");
	} 

}
