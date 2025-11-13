package com.example.biblioteca.dao.impl;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.biblioteca.dao.BaseRestDao;
import com.example.biblioteca.domain.dto.AutorDTO;
import com.example.biblioteca.domain.dto.BaseDTO;
import com.example.biblioteca.domain.dto.LibroDTO;
import com.example.biblioteca.domain.dto.PersonaDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BaseRestDaoImpl<E extends BaseDTO, ID extends Serializable> implements BaseRestDao<E, ID> {
	
	@Autowired
	protected RestTemplate restTemplate;
	
	//para hacerlo generico declaramos este atributo que va a ser inicializado por medio del constructor
	protected Class<E> entityClass;
	
	protected String baseUrl;
	
	protected Class<E[]> entityArrayClass;
	
	public BaseRestDaoImpl(Class<E> entityClass, Class<E[]> entityArrayClass, String baseUrl) {
		this.entityClass = entityClass;
		this.baseUrl = baseUrl;
		this.entityArrayClass = entityArrayClass;
	}
	
	/*@Override
	public void crear(E dto) throws Exception {
		
		try {
			 
			 restTemplate.postForEntity(baseUrl, dto, entityClass);
		     
	      } catch (Exception e){
	          e.printStackTrace();
	          throw new Exception("Error al crear entidad", e);
	      }
		
	}*/
	
	@Override
	public void crear(E dto) throws Exception {
		
		try {
			
			if (dto instanceof LibroDTO libroDTO) {
			    String uri = baseUrl + "/saveLibro";

			    // Crear archivo temporal
			    File tempFile = File.createTempFile("libro-", ".pdf");
			    libroDTO.getPdf().transferTo(tempFile);
			    tempFile.deleteOnExit();
			    
			    // Serializar DTO a JSON
			    ObjectMapper mapper = new ObjectMapper();
			    String libroJson = mapper.writeValueAsString(libroDTO);
			    
			    // Parte JSON como String
			    HttpHeaders jsonHeaders = new HttpHeaders();
			    jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
			    HttpEntity<String> libroPart = new HttpEntity<>(libroJson, jsonHeaders);

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
			    body.add("libro", libroPart);
			    body.add("pdf", pdfPart);

			    HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			    
			    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

			    System.out.println("=== Multipart body ===");
			    body.forEach((key, value) -> {
			        System.out.println(key + " => " + value);
			    });
			    System.out.println("=====================");
			    System.out.println("ESTOY EN BASERESTDAO");
			    System.out.println("TIPO LIBRO: " + libroDTO.getTipo());
			    
			    restTemplate.postForEntity(uri, requestEntity, entityClass);
			    
			} else if(dto instanceof AutorDTO){
				
				String uri = baseUrl + "/saveAutor";
				restTemplate.postForEntity(uri, dto, entityClass);
			    
			}else {
				// Para DTOs que no son libros ni autores
			    restTemplate.postForEntity(baseUrl, dto, entityClass);
			}

		     
	      } catch (Exception e){
	          e.printStackTrace();
	          throw new Exception("Error al crear entidad", e);
	      }
		
	}

	@Override
	public void actualizar(E dto) throws Exception {
		String uri;

		try {
			 
			if (dto instanceof LibroDTO libroDTO) {
			    uri = baseUrl + "/updateLibro/{id}";
			    
			    if (libroDTO.getPdf() != null) {
			    	
			    	// Crear archivo temporal
				    File tempFile = File.createTempFile("libro-", ".pdf");
				    libroDTO.getPdf().transferTo(tempFile);
				    tempFile.deleteOnExit();
				    
				    // Parte JSON como String
	                ObjectMapper mapper = new ObjectMapper();
	                String libroJson = mapper.writeValueAsString(libroDTO);
				    HttpHeaders jsonHeaders = new HttpHeaders();
				    jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
				    HttpEntity<String> libroPart = new HttpEntity<>(libroJson, jsonHeaders);

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
				    body.add("libro", libroPart);
				    body.add("pdf", pdfPart);

				    HttpHeaders headers = new HttpHeaders();
				    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
				    
				    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

				    System.out.println("=== Multipart body ===");
				    body.forEach((key, value) -> {
				        System.out.println(key + " => " + value);
				    });
				    System.out.println("=====================");
				    
				    restTemplate.put(uri, requestEntity, libroDTO.getId());
				}else {
	                
	                uri = baseUrl + "/updateLibro/{id}";
	                restTemplate.put(uri, dto, dto.getId());
	            }
			} else {
				if (dto instanceof PersonaDTO persona) {
					System.out.println("LIBROS PERSONA: " + persona.getLibros());
				}
				uri = baseUrl + "/{id}";
				restTemplate.put(uri, dto, dto.getId());
			}
		     
	      } catch (Exception e){
	          e.printStackTrace();
	          throw new Exception("Error al actualizar entidad", e);
	      }
		
	}
	
	/*@Override
	public void actualizar(E dto) throws Exception {

		try {
			 
			 String uri = baseUrl + "/{id}";
			 
			 restTemplate.put(uri, dto, dto.getId());
		     
	      } catch (Exception e){
	          e.printStackTrace();
	          throw new Exception("Error al actualizar entidad", e);
	      }
		
	}*/

	@Override
	public void eliminar(ID id) throws Exception {
	    try {
	        String uri = baseUrl + "/{id}";
	        restTemplate.delete(uri, id);

	    } catch (HttpClientErrorException e) {
	        // Mostrar respuesta cruda para depurar
	        String respuesta = e.getResponseBodyAsString();
	        System.out.println(">>> RESPUESTA DEL SERVIDOR: " + respuesta);

	        String mensajeError = null;
	        try {
	            ObjectMapper mapper = new ObjectMapper();

	            // Parseamos el JSON, permitiendo valores de cualquier tipo
	            Map<String, Object> map = mapper.readValue(respuesta, new TypeReference<Map<String, Object>>() {});

	            // Buscamos la clave que contiene el mensaje
	            if (map.containsKey("error")) {
	                mensajeError = map.get("error").toString();
	            } else if (map.containsKey("message")) {
	                mensajeError = map.get("message").toString();
	            } else {
	                mensajeError = respuesta;
	            }

	        } catch (Exception parseEx) {
	            // Si falla el parseo, usamos el cuerpo completo
	            mensajeError = respuesta;
	        }

	        // Lanzamos solo el mensaje legible
	        throw new RuntimeException(mensajeError);

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception("Error al eliminar entidad", e);
	    }
	}


	@Override
	public Optional<E> buscarPorId(ID id) throws Exception {
		
		try {
			
			String uri = baseUrl + "/{id}";
			ResponseEntity<E> response = restTemplate.getForEntity(uri, entityClass, id);
			E entity = response.getBody();
			
			return Optional.ofNullable(entity);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar entidad", e);
		}
		
	}
	
	
	@Override
	public boolean existsById(ID id) throws Exception {
		
		try {
			
			String uri = baseUrl + "/{id}";
			ResponseEntity<E> response = restTemplate.getForEntity(uri, entityClass, id);
			
			return response.getStatusCode().is2xxSuccessful();
			
		} catch (HttpClientErrorException.NotFound ex) {
			
			return false;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error de Sistemas");
		}
		
	}
	

	@Override
	public List<E> listarActivo() throws Exception {
		
		try {
			
			ResponseEntity<E[]> response = restTemplate.getForEntity(baseUrl, entityArrayClass);
			E[] array = response.getBody();
			return Arrays.asList(array);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al listar entidades", e);
		}
		
	}

}
