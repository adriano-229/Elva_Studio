package com.projects.mycar.mycar_server.services;

import com.projects.mycar.mycar_server.dto.DocumentacionDTO;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentacionService extends BaseService<DocumentacionDTO, Long> {

    DocumentacionDTO update(Long id, DocumentacionDTO documentacion, MultipartFile archivo) throws Exception;

    String almacenarPdf(MultipartFile archivo) throws Exception;

}
