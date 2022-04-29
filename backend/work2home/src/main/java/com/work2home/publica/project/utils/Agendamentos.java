package com.work2home.publica.project.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.work2home.publica.project.enums.Roles;
import com.work2home.publica.project.enums.StatusOrcamento;
import com.work2home.publica.project.model.OrdemServico;
import com.work2home.publica.project.model.Prestador;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.OrdemServicoRepository;
import com.work2home.publica.project.repositores.PrestadorRepository;
import com.work2home.publica.project.repositores.UsuarioRepository;

@EnableScheduling
@Component
public class Agendamentos {

	@Autowired
	private PrestadorRepository prestadorRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	private static final long SEGUNDO = 1000L;
	private static final long MINUTO = 60L * SEGUNDO;
	private static final long HORA = 60L * MINUTO;
	private static final long DIA = 24L * HORA;

//	@Scheduled(fixedDelay = MINUTO)
//	public void desativarPrestador() {
//		List<Prestador> prestadors = prestadorRepository.findAll();
//		
//	
//		for (Prestador p : prestadors) {
//
//			List<OrdemServico> servicos = p.getServicos();
//
//			if (servicos.isEmpty()) {
//				if (p.getUsuario().getDataCriacao().plusDays(30).isBefore(LocalDate.now())) {
//					desativarPrestador(p);
//				}
//			} else {
//
//				OrdemServico os = servicos.get(servicos.size() - 1);
//
//				if (os.getStatus() == StatusOrcamento.FINALIZADO) {
//
//					if (os.getDataFim().plusDays(30).isBefore(LocalDate.now())) {
//						desativarPrestador(p);
//					}
//				}
//			}
//		}
//	}
//	
//	private void desativarPrestador(Prestador p) {
//		p.getUsuario().setRole(Roles.INATIVO);
//		prestadorRepository.save(p);
//		usuarioRepository.save(p.getUsuario());
//	}
}
