package com.work2home.publica.project.repositores;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work2home.publica.project.model.Cidade;


public interface CidadeRepository extends JpaRepository<Cidade, Integer>{
	
	public Cidade findByNomeAndEstado(String nome, String estado);

}
