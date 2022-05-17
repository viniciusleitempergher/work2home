package com.work2home.publica.project.rest.controller;

import java.util.List;

import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.work2home.publica.project.rest.dto.categoria.CategoriaRequest;
import com.work2home.publica.project.rest.dto.categoria.CategoriaResponse;
import com.work2home.publica.project.service.CategoriaService;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;

	@ApiOperation(value = "Lista todas as categorias")
	@GetMapping
	public List<CategoriaResponse> listaCategorias(){
		return categoriaService.buscarCategorias();
	}

	@ApiOperation(value = "Canastra uma categoria")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CategoriaResponse cadastrarCategoria(@RequestBody @Valid CategoriaRequest categoria) {
		return categoriaService.cadastrarCategoria(categoria);
	}

	@ApiOperation(value = "Salva e adiciona uma imagem na categoria")
	@PostMapping("/{id}/imagem")
	@ResponseStatus(HttpStatus.CREATED)
	public CategoriaResponse cadastrarImagem(@PathVariable Integer id, @RequestParam("image") MultipartFile multipartFile) {
		return categoriaService.cadastrarImagem(id, multipartFile);
	}

	@ApiOperation(value = "Remove uma categoria pelo id")
	@DeleteMapping("/{id}")
	public void removerCategoria(@PathVariable Integer id) {
		categoriaService.deletarCategoria(id);
	}
}	
