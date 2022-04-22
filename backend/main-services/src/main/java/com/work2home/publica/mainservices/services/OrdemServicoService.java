package com.work2home.publica.mainservices.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.work2home.publica.mainservices.models.OrdemServico;
import com.work2home.publica.mainservices.repositories.OrdemServicoRepository;

@Service
public class OrdemServicoService {

	@Autowired
	private OrdemServicoRepository repository;
	
	public List<OrdemServico> findAll(){
		return repository.findAll();
	}
}
