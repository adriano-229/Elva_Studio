package com.projects.mycar.mycar_admin.dao;

import com.example.mycar.mycar_admin.domain.CodigoDescuentoDTO;

public interface CodigoDescuentoRestDao extends BaseRestDao<CodigoDescuentoDTO, Long> {

    CodigoDescuentoDTO buscarPorCodigo(String codigo) throws Exception;

    boolean existePorCodigo(String codigo) throws Exception;

}
