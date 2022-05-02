package com.work2home.publica.project.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.work2home.publica.project.model.Categoria;
import com.work2home.publica.project.model.Prestador;
import com.work2home.publica.project.repositores.CategoriaRepository;
import com.work2home.publica.project.rest.dto.categoria.CategoriaRequest;
import com.work2home.publica.project.rest.dto.categoria.CategoriaResponse;
import com.work2home.publica.project.utils.FileUploadUtil;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private PrestadorService prestadorService;

	public CategoriaResponse cadastrarCategoria(@Valid CategoriaRequest categoriaRequest) {
		Categoria categoria = categoriaRepository.save(new Categoria(categoriaRequest.getNome()));
		CategoriaResponse cr = new CategoriaResponse();
		try {
			BeanUtils.copyProperties(cr, categoria);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return cr;
	}

	public List<CategoriaResponse> buscarCategorias() {
		
		return categoriaRepository
				.findAll()
				.stream()
				.map(c -> {
					CategoriaResponse cr = new CategoriaResponse();
					try {
						BeanUtils.copyProperties(cr, c);
					} catch (Exception e) {				
						e.printStackTrace();
					} 
					return cr;
				})
				.toList();
	}

	public void cadastrarCategoriaPrestador(Integer categoriaId) {
		prestadorService.adicionarCategoria(categoriaId);
	}

	public Categoria cadastrarImagem(Integer id, MultipartFile multipartFile) {
		
		Categoria categoria = categoriaRepository
				.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		String uuid = UUID.randomUUID().toString();
		String dir = "../images/categoria";
		String url = dir + "/" + uuid + ".png";
		
		categoria.setImagemUrl(url);
		
		try {
			FileUploadUtil.saveFile(dir, uuid , multipartFile);		
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		return categoriaRepository.save(categoria);
	}

	public void deletarCategoria(Integer id) {
		Categoria categoria = categoriaRepository
				.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
		categoriaRepository.delete(categoria);
	}
}
