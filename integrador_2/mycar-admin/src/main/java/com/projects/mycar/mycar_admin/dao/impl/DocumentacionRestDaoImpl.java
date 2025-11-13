package com.projects.mycar.mycar_admin.dao.impl;

import java.io.File;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.mycar.mycar_admin.domain.DocumentacionDTO;
import com.projects.mycar.mycar_admin.dao.DocumentacionRestDao;

@Repository
public class DocumentacionRestDaoImpl extends BaseRestDaoImpl<DocumentacionDTO, Long> implements DocumentacionRestDao{

	public DocumentacionRestDaoImpl() {
		super(DocumentacionDTO.class, DocumentacionDTO[].class, "http://localhost:9000/api/v1/documentacion");
	}
	
	
	public DocumentacionDTO crearDocumentacion(DocumentacionDTO dto) throws Exception {
		try {
			String uri = baseUrl + "/saveConDocumento";

		    // Crear archivo temporal
		    File tempFile = File.createTempFile("doc-", ".pdf");
		    dto.getPdf().transferTo(tempFile);
		    tempFile.deleteOnExit();
		    
		    // Parte JSON del DTO
		    HttpHeaders jsonHeaders = new HttpHeaders();
		    jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
		    HttpEntity<DocumentacionDTO> documentacionPart = new HttpEntity<>(dto, jsonHeaders);

		    // Parte archivo PDF
		    HttpHeaders fileHeaders = new HttpHeaders();
		    // APPLICATION_OCTET_STREAM indica que es contenido binario genérico, no JSON ni texto
		    fileHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		    //FileSystemResource es un wrapper de Spring que toma un archivo en disco (tempFile) 
		    // y lo convierte en un recurso que Spring puede usar para enviar por HTTP.
		    HttpEntity<FileSystemResource> pdfPart = new HttpEntity<>(new FileSystemResource(tempFile), fileHeaders);
		    // HttpEntity<FileSystemResource> combina ese archivo con sus headers (tipo de contenido) para que RestTemplate sepa cómo enviarlo.

		    // Armar cuerpo multipart
		    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		    body.add("documentacion", documentacionPart);
		    body.add("pdf", pdfPart);

		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		    
		    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

		    System.out.println("=== Multipart body ===");
		    body.forEach((key, value) -> {
		        System.out.println(key + " => " + value);
		    });
		    System.out.println("=====================");

		    
		    ResponseEntity<DocumentacionDTO> documentacion = restTemplate.postForEntity(uri, requestEntity, entityClass);
		    
		    return documentacion.getBody();
		    
		} catch (Exception e) {
			e.printStackTrace();
	          throw new Exception("Error al crear documentacion", e);
		}
	}
	
	

}
