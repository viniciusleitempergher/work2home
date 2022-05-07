package com.work2home.publica.project.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.rest.dto.endereco.EnderecoRequest;
import com.work2home.publica.project.rest.dto.endereco.EnderecoResponse;
import com.work2home.publica.project.model.Cliente;
import com.work2home.publica.project.model.Endereco;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.repositores.ClienteRepository;
import com.work2home.publica.project.repositores.EnderecoRepository;
import com.work2home.publica.project.utils.JwtUtil;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired	
	private CidadeService cidadeService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private JwtUtil jwt;

	public void cadastrar(EnderecoRequest enderecoDto) {
		Endereco endereco = new Endereco();	
		Usuario user = jwt.getUserFromHeaderToken();
		

		 Cliente cliente = clienteRepository
				 .findById(user.getId())
				 .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));

		if( cliente.getEndereco() != null) {
			endereco = cliente.getEndereco();
		}
		
		endereco.setCliente(cliente);
		endereco.setCidade(cidadeService.converter(enderecoDto));
		endereco.setBairro(enderecoDto.getBairro());
		endereco.setComplemento(enderecoDto.getComplemento());
		endereco.setNumero(enderecoDto.getNumero());
		endereco.setLogradouro(enderecoDto.getLogradouro());

		endereco = enderecoRepository.save(endereco);
		
		cliente.setEndereco(endereco);
		clienteRepository.save(cliente);
	}

	public EnderecoResponse buscarEndereco(Integer clienteId) {
		EnderecoResponse enderecoResponse = new EnderecoResponse();
		Endereco endereco = enderecoRepository.findByClienteUsuarioId(clienteId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		BeanUtils.copyProperties(endereco, enderecoResponse);
		return enderecoResponse;
	}
}
