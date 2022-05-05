package com.work2home.publica.project.repositores.specs;

import com.work2home.publica.project.enums.StatusOrcamento;
import com.work2home.publica.project.model.OrdemServico;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PrestadorSpecs {

	public static Specification<OrdemServico> prestadorEqual(Integer prestadorId){

		return (root, query, cb) -> cb.equal(root.get("prestador"), prestadorId);
	}

	public static Specification<OrdemServico> enderecoEqual(Integer enderecoId){

		return (root, query, cb) -> cb.equal(root.get("endereco"), enderecoId);
	}

	public static Specification<OrdemServico> statusEqual(Integer status){

		return (root, query, cb) -> cb.equal(root.get("statusOrcamento"), status);
	}
}
