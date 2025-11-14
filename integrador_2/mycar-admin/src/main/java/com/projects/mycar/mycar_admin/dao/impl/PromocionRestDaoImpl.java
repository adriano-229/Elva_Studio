package com.projects.mycar.mycar_admin.dao.impl;

import com.projects.mycar.mycar_admin.dao.PromocionRestDao;
import com.projects.mycar.mycar_admin.domain.ConfiguracionPromocionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class PromocionRestDaoImpl extends BaseRestDaoImpl<ConfiguracionPromocionDTO, Long> implements PromocionRestDao {

    public PromocionRestDaoImpl() {
        super(ConfiguracionPromocionDTO.class, ConfiguracionPromocionDTO[].class, "http://168.181.186.171:8083/api/promociones");
    }

    @Override
    public ConfiguracionPromocionDTO configurarPromocion(ConfiguracionPromocionDTO configuracion) throws Exception {
        try {

            String uri = baseUrl + "/configurar";

            ResponseEntity<ConfiguracionPromocionDTO> response = restTemplate.postForEntity(
                    uri,
                    configuracion,
                    ConfiguracionPromocionDTO.class
            );

            return response.getBody();

        } catch (Exception e) {
            throw new Exception("Error al configurar promocion, e");
        }
    }

    @Override
    public ConfiguracionPromocionDTO obtenerConfiguracionActiva() throws Exception {

        try {

            String uri = baseUrl + "/configuracion-activa";
            ResponseEntity<ConfiguracionPromocionDTO> response = restTemplate.getForEntity(
                    uri,
                    ConfiguracionPromocionDTO.class
            );

            return response.getBody();


        } catch (Exception e) {
            throw new Exception("Error al obtener configuracion activa", e);
        }


    }

    @Override
    public String enviarPromocionesManual() throws Exception {
        try {

            ResponseEntity<String> response = restTemplate.postForEntity(
                    baseUrl + "/enviar-manual",
                    null,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new RuntimeException("Error en la respuesta del servicio de promociones: "
                        + response.getStatusCode());
            }

        } catch (Exception e) {
            throw new RuntimeException("No se pudo conectar con el servicio de promociones: " + e.getMessage(), e);
        }
    }


}
