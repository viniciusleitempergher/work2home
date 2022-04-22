package com.work2home.publica.userutilities.repositores;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work2home.publica.userutilities.model.Cidade;


public interface CidadeRepository extends JpaRepository<Cidade, Integer>{
	
	public Cidade findByNomeAndEstado(String nome, String estado);

}
