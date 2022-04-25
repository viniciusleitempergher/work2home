package com.work2home.publica.project.repositores;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work2home.publica.project.model.Prestador;

public interface PrestadorRepository extends JpaRepository<Prestador, Integer> {
	

}
