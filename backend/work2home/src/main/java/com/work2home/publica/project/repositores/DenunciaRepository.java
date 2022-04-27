package com.work2home.publica.project.repositores;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work2home.publica.project.model.Denuncia;
import com.work2home.publica.project.model.DenunciaId;

public interface DenunciaRepository extends JpaRepository<Denuncia, DenunciaId> {

}
