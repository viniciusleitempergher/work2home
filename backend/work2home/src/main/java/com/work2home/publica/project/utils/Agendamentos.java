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
import com.work2home.publica.project.service.OrdemServicoService;

@EnableScheduling
@Component
public class Agendamentos {

	@Autowired
	private PrestadorRepository prestadorRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private OrdemServicoRepository osRepository;

	private static final long SEGUNDO = 1000;
	private static final long MINUTO = 60 * SEGUNDO;
	private static final long HORA = 60 * MINUTO;
	private static final long DIA = 24 * HORA;

	@Scheduled(fixedDelay = HORA)
	public void desativarPrestador() {
		List<Prestador> prestadors = prestadorRepository.findAll();

		for (Prestador p : prestadors) {

			List<OrdemServico> servicos = p.getServicos();

			if (servicos.isEmpty() && plusThirdyBefore(p.getUsuario().getDataCriacao())) {
				desativarPrestador(p);
			} else {
				OrdemServico os = servicos.get(servicos.size() - 1);

				if ((os.getStatus() == StatusOrcamento.FINALIZADO || os.getStatus() == StatusOrcamento.PAGO)
						&& plusThirdyBefore(os.getDataFim())) {
					desativarPrestador(p);
				}
			}
		}
	}

	private void desativarPrestador(Prestador p) {
		p.getUsuario().setRole(Roles.INATIVO);
		prestadorRepository.save(p);
		usuarioRepository.save(p.getUsuario());
	}

	@Scheduled(fixedDelay = HORA)
	public void cancelarOrcamento() {

		List<OrdemServico> servicos = osRepository.findAll();

		for (OrdemServico os : servicos) {

			if (os.getStatus() == StatusOrcamento.SOLICITADO && plusThirdyBefore(os.getDataSolicitada())) {
				os.setStatus(StatusOrcamento.NEGADO);
				osRepository.save(os);

			} else if (os.getStatus() == StatusOrcamento.FINALIZADO && plusThirdyBefore(os.getDataFim())) {
				Usuario usuario = os.getEndereco().getCliente().getUsuario();
				usuario.setRole(Roles.BANIDO);
				usuarioRepository.save(usuario);
			}

		}
	}

	public boolean plusThirdyBefore(LocalDate dia) {
		return dia.isBefore(LocalDate.now());
  }
}
