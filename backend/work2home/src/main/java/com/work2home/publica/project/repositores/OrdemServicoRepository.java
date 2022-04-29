package com.work2home.publica.project.repositores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.work2home.publica.project.model.OrdemServico;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Integer>{



	@Query(nativeQuery = true, 
			value = "select * from ordem_servico_tb where status_orcamento = ?1 and prestador_id = ?2")
	List<OrdemServico> findByStatusOrcamentoAndPrestadorId(Integer statusOrcamento, Integer prestadorId);

	@Query(nativeQuery = true, 
			value = "select * from ordem_servico_tb where status_orcamento = ?1 and endereco_id = ?2")
	List<OrdemServico> findByStatusOrcamentoAndEnderecoId(Integer statusOrcamento, Integer enderecoId);

}
