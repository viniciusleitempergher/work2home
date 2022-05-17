package com.work2home.publica.project.rest.controller;

import java.util.List;
import javax.validation.Valid;

import com.work2home.publica.project.rest.dto.prestador.PrestadorCompletarCadastroRequest;
import com.work2home.publica.project.rest.dto.prestador.PrestadorFiltroResponse;
import com.work2home.publica.project.rest.dto.prestador.PrestadorRequest;
import com.work2home.publica.project.rest.dto.prestador.PrestadorResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.work2home.publica.project.service.CategoriaService;
import com.work2home.publica.project.service.PrestadorService;

@RestController
@RequestMapping("/prestador")
public class PrestadorController {

	@Autowired
	private PrestadorService prestadorService;
	
	@Autowired
	private CategoriaService categoriaService;

	@ApiOperation(value = "Lista todos os prestadores")
	@GetMapping
	public List<PrestadorResponse> listarPrestadores() {
		return prestadorService.buscarPrestador();
	}

	@ApiOperation(value = "Busca um prestador por id")
	@GetMapping("/{id}")
	public PrestadorResponse buscaConta(@PathVariable Integer id) {
		return prestadorService.buscarPrestadorId(id);
	}

	@ApiOperation(value = "Cadastra um prestador")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PrestadorResponse cadastrarPrestador(@RequestBody @Valid PrestadorRequest prestadorDto) {
		return prestadorService.cadastrarPrestador(prestadorDto);
	}

	@ApiOperation(value = "Altera o prestador logado")
	@PutMapping
	public void alterarPrestador(@RequestBody @Valid PrestadorRequest prestadorDto) {
		prestadorService.alterarPrestador(prestadorDto);
	}

	@ApiOperation(value = "Completa o cadastro do prestador logado")
	@PutMapping("/completar-cadastro")
	public void completarCadastro(@RequestBody @Valid PrestadorCompletarCadastroRequest prestadorDto) {
		prestadorService.completarCadastro(prestadorDto);
	}

	@ApiOperation(value = "Cadastra um categoria por id no prestador logado")
	@PostMapping("/categoria/{categoriaId}")
	@ResponseStatus(HttpStatus.CREATED)
	public void cadastrarCategoriaPrestador(@PathVariable Integer categoriaId) {
	   categoriaService.cadastrarCategoriaPrestador(categoriaId);
	}

	@ApiOperation(value = "Remove uma cidade do prestador")
	@DeleteMapping("/cidade/{cidadeId}")
	public void removerCidadePrestador(@PathVariable Integer cidadeId) {
		
		prestadorService.removerCidadePrestador(cidadeId);
	}

	@ApiOperation(value = "Remove uma categoria do prestador")
	@DeleteMapping("/categoria/{categoriaId}")
	public void removerCategoriaPrestador(@PathVariable Integer categoriaId) {
		
		prestadorService.removerCategoriaPrestador(categoriaId);
	}

	@ApiOperation(value = "Lista os prestador por categoria e por localidade do cliente logado")
	@GetMapping("/filtro/{categoriaId}")
	public List<PrestadorFiltroResponse> filtrarPrestadores(@PathVariable Integer categoriaId) {
		return prestadorService.filtrarPrestadores(categoriaId);
	}
}
