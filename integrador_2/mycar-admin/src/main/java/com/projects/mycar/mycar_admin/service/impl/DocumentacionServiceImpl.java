package com.projects.mycar.mycar_admin.service.impl;

import com.projects.mycar.mycar_admin.dao.BaseRestDao;
import com.projects.mycar.mycar_admin.dao.impl.DocumentacionRestDaoImpl;
import com.projects.mycar.mycar_admin.domain.DocumentacionDTO;
import com.projects.mycar.mycar_admin.service.DocumentacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentacionServiceImpl extends BaseServiceImpl<DocumentacionDTO, Long> implements DocumentacionService {

    @Autowired
    private DocumentacionRestDaoImpl daoDocumentacion;

    public DocumentacionServiceImpl(BaseRestDao<DocumentacionDTO, Long> dao) {
        super(dao);
    }

    public DocumentacionDTO saveDocumentacion(DocumentacionDTO entity) throws Exception {
        try {
            validar(entity);
            return daoDocumentacion.crearDocumentacion(entity);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    protected void validar(DocumentacionDTO entity) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void beforeSave(DocumentacionDTO entity) throws Exception {
        // TODO Auto-generated method stub

    }

}
