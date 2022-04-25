package com.work2home.publica.project.repositores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.work2home.publica.project.model.Avaliacao;
import com.work2home.publica.project.model.AvaliacaoId;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, AvaliacaoId>{

}
