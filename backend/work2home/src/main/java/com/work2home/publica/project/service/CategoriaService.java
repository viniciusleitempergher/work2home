package com.work2home.publica.project.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.model.Categoria;
import com.work2home.publica.project.model.Prestador;
import com.work2home.publica.project.repositores.CategoriaRepository;
import com.work2home.publica.project.utils.FileUploadUtil;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private PrestadorService prestadorService;

	public Categoria cadastrarCategoria(@Valid Categoria categoria) {
		
		return categoriaRepository.save(categoria);
	}

	public List<Categoria> buscarCategorias() {
		return categoriaRepository.findAll();
	}

	public Prestador cadastrarCategoriaPrestador(Integer categoriaId) {
		return prestadorService.adicionarCategoria(categoriaId);
		
	}

	public void cadastrarImagem(Integer id, MultipartFile multipartFile) {
		
		Categoria categoria = categoriaRepository
				.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		String uuid = UUID.randomUUID().toString();
		String dir = "../images/categoria";
		
		categoria.setImagemUrl(dir + uuid);
		categoriaRepository.save(categoria);
		
		try {
			FileUploadUtil.saveFile(dir, uuid , multipartFile);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
