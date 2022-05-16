package com.work2home.publica.project.repositores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.work2home.publica.project.model.Denuncia;
import com.work2home.publica.project.model.DenunciaId;
import com.work2home.publica.project.rest.dto.denuncia.DenunciaResponse;

@Repository
public interface DenunciaRepository extends JpaRepository<Denuncia, DenunciaId> {

	@Query(value = "SELECT NEW com.work2home.publica.project.rest.dto.denuncia.DenunciaResponse"
			+ "(de.id, COUNT(d) AS qtdDenuncias, de.nome, de.role)"
			+ " FROM Denuncia d INNER JOIN d.denunciado de "
			+ "GROUP BY de.id ORDER BY qtdDenuncias DESC")
	List<DenunciaResponse> findDenunciaPorQtd();
}
