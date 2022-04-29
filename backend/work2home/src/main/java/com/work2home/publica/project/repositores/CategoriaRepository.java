package com.work2home.publica.project.repositores;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.work2home.publica.project.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

	Optional<Categoria> findByNome(String categoriaNome);

}

