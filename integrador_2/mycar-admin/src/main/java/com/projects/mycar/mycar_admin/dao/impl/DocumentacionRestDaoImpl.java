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

import com.projects.mycar.mycar_admin.dao.DocumentacionRestDao;
import com.projects.mycar.mycar_admin.domain.DocumentacionDTO;

@Repository
public class DocumentacionRestDaoImpl extends BaseRestDaoImpl<DocumentacionDTO, Long> implements DocumentacionRestDao {

    public DocumentacionRestDaoImpl() {
        super(DocumentacionDTO.class, DocumentacionDTO[].class, "http://localhost:8083/api/v1/documentacion");
    }

    @Override
    public DocumentacionDTO crearDocumentacion(DocumentacionDTO dto) throws Exception {
        try {
            String uri = baseUrl + "/saveConDocumento";

            // Crear archivo temporal para adjuntar el PDF
            File tempFile = File.createTempFile("doc-", ".pdf");
            dto.getPdf().transferTo(tempFile);
            tempFile.deleteOnExit();

            // Parte JSON del DTO
            HttpHeaders jsonHeaders = new HttpHeaders();
            jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<DocumentacionDTO> documentacionPart = new HttpEntity<>(dto, jsonHeaders);

            // Parte archivo PDF
            HttpHeaders fileHeaders = new HttpHeaders();
            fileHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            HttpEntity<FileSystemResource> pdfPart = new HttpEntity<>(new FileSystemResource(tempFile), fileHeaders);

            // Armar cuerpo multipart
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("documentacion", documentacionPart);
            body.add("pdf", pdfPart);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<DocumentacionDTO> documentacion =
                    restTemplate.postForEntity(uri, requestEntity, entityClass);

            return documentacion.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al crear documentacion", e);
        }
    }

    @Override
    public void actualizarDocumentacion(DocumentacionDTO dto) throws Exception {
        try {
            if (dto.getPdf() != null) {
                File tempFile = File.createTempFile("doc-", ".pdf");
                dto.getPdf().transferTo(tempFile);
                tempFile.deleteOnExit();

                HttpHeaders jsonHeaders = new HttpHeaders();
                jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<DocumentacionDTO> docuPart = new HttpEntity<>(dto, jsonHeaders);

                HttpHeaders fileHeaders = new HttpHeaders();
                fileHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                HttpEntity<FileSystemResource> pdfPart =
                        new HttpEntity<>(new FileSystemResource(tempFile), fileHeaders);

                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                body.add("documentacion", docuPart);
                body.add("pdf", pdfPart);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);

                HttpEntity<MultiValueMap<String, Object>> requestEntity =
                        new HttpEntity<>(body, headers);

                String uri = baseUrl + "/updateConDocumento/{id}";
                restTemplate.put(uri, requestEntity, dto.getId());
            } else {
                String uri = baseUrl + "/{id}";
                restTemplate.put(uri, dto, dto.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al actualizar entidad", e);
        }
    }
}
