package com.work2home.publica.project.repositores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.work2home.publica.project.model.OrdemServico;

import java.util.List;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Integer>, JpaSpecificationExecutor<OrdemServico> {

    @Query(value = """
            SELECT os.status_orcamento, COUNT(*)
            FROM ordem_servico_tb os
            WHERE os.endereco_id = ?1
            GROUP BY os.status_orcamento;""", nativeQuery = true)
    List<List<Long>> findQtdsServicosByClienteId(Integer enderecoId);

    @Query(value = """
            SELECT os.status_orcamento, COUNT(*)
            FROM ordem_servico_tb os
            WHERE os.prestador_id = ?1
            GROUP BY os.status_orcamento;""", nativeQuery = true)
    List<List<Long>> findQtdsServicosByPrestadorId(Integer clienteId);
}
