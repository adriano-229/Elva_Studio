package com.example.mycar.services;

import org.springframework.web.multipart.MultipartFile;

import com.example.mycar.dto.DocumentacionDTO;

public interface DocumentacionService extends BaseService<DocumentacionDTO, Long>{
	
	DocumentacionDTO update(Long id, DocumentacionDTO documentacion, MultipartFile archivo) throws Exception;
	String almacenarPdf(MultipartFile archivo) throws Exception;

}
