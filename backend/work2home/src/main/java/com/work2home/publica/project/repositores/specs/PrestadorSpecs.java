package com.work2home.publica.project.repositores.specs;


import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.model.Categoria;
import com.work2home.publica.project.model.Prestador;
import com.work2home.publica.project.repositores.CategoriaRepository;
import com.work2home.publica.project.repositores.PrestadorRepository;

@Component
public class PrestadorSpecs {

	public static Specification<Prestador> categoriaEqual(String nomeCategoria){
		return (root, query, cb) -> {
			  // These next lines don't work! What do I put here?
			
			  query.distinct(true);
	            Root<Prestador> prestadorRoot = root;
	            Subquery<Categoria> categoriaSubQuery = query.subquery(Categoria.class);
	            Root<Categoria> categoriaRoot = categoriaSubQuery.from(Categoria.class);
	            Expression<Set<Prestador>> categoriaPrestadores = categoriaRoot.get("prestadores");
	            categoriaSubQuery.select(categoriaRoot);
	            categoriaSubQuery.where(cb.equal(categoriaRoot.get("nome"), nomeCategoria), cb.isMember(prestadorRoot, categoriaPrestadores));
	            return cb.exists(categoriaSubQuery);	
	            };
	}
	
	
}
