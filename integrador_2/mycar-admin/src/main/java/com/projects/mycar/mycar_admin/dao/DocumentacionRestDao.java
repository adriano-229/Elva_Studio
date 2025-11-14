package com.projects.mycar.mycar_admin.dao;

import com.projects.mycar.mycar_admin.domain.DocumentacionDTO;

public interface DocumentacionRestDao extends BaseRestDao<DocumentacionDTO, Long> {

    DocumentacionDTO crearDocumentacion(DocumentacionDTO dto) throws Exception;

    void actualizarDocumentacion(DocumentacionDTO dto) throws Exception;

}
