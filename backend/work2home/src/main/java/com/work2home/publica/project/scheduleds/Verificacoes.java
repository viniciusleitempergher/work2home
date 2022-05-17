package com.work2home.publica.project.scheduleds;

import java.time.LocalDate;
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
public class Verificacoes {

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
	
	@Scheduled(fixedDelay = DIA)
	public void tarefasPorDia() {
		verificarPrestadores();
		verificarOrcamentos();
	}
	
	private void verificarPrestadores() {
		List<Prestador> prestadors = prestadorRepository.findAll();

		for (Prestador p : prestadors) {

			List<OrdemServico> servicos = p.getServicos();

			if (servicos.isEmpty() && plusThirdyBefore(p.getUsuario().getDataCriacao())) {
				desativarPrestador(p);
			} else {
				if (servicos.isEmpty()) continue;
				
				OrdemServico os = servicos.get(servicos.size() - 1);

				if ((os.getStatus() == StatusOrcamento.FINALIZADO)
						&& plusThirdyBefore(os.getDataFim())) {
					desativarPrestador(p);
				}
			}
		}
	}

	private void verificarOrcamentos() {

		List<OrdemServico> servicos = osRepository.findAll();

		for (OrdemServico os : servicos) {

			if (os.getStatus() == StatusOrcamento.SOLICITADO && plusThirdyBefore(os.getDataSolicitada())) {
				os.cancelar();
				osRepository.save(os);

			} else if(os.getStatus() == StatusOrcamento.EM_ORCAMENTO && os.getDataInicio().isBefore(LocalDate.now())) {
				os.cancelar();
				osRepository.save(os);
			}
		}
	}

	private boolean plusThirdyBefore(LocalDate dia) {
		return dia.plusDays(30).isBefore(LocalDate.now());
	}
	
	private  void desativarPrestador(Prestador p) {
		p.getUsuario().setRole(Roles.INATIVO);
		prestadorRepository.save(p);
		usuarioRepository.save(p.getUsuario());
	}
}
