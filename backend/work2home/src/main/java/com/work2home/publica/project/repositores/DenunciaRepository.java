package com.work2home.publica.project.repositores;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.work2home.publica.project.model.Denuncia;
import com.work2home.publica.project.model.DenunciaId;

@Repository
public interface DenunciaRepository extends JpaRepository<Denuncia, DenunciaId> {

	List<Denuncia> findByDenunciadoId(Integer denunciadoId);

}
