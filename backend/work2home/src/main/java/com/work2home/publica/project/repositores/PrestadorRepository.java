package com.work2home.publica.project.repositores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.work2home.publica.project.model.Prestador;

@Repository
public interface PrestadorRepository extends JpaRepository<Prestador, Integer>, JpaSpecificationExecutor<Prestador> {
	
	List<Prestador> findByCategorias_IdAndCidades_Id(Integer categoriaId, Integer cidadeId);

}
