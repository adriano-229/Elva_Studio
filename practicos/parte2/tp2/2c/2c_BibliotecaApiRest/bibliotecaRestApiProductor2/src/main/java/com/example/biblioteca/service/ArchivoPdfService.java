package com.example.biblioteca.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.biblioteca.entities.ArchivoPdf;

public interface ArchivoPdfService extends BaseService<ArchivoPdf, Long> {
	
	String almacenarPdf(MultipartFile pdf) throws Exception;

}
