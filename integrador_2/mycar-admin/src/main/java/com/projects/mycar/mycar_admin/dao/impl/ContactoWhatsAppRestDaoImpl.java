package com.projects.mycar.mycar_admin.dao.impl;

import com.projects.mycar.mycar_admin.domain.contacto.ContactoWhatsappResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class ContactoWhatsAppRestDaoImpl {

    @Autowired
    private RestTemplate restTemplate;

    private final String baseUrl = "http://localhost:8083/contactos";


    public ContactoWhatsappResponse obtenerWhatsapp(Long clienteId) throws Exception {
        try {

            String uri = baseUrl + "/{clienteId}";
            ResponseEntity<ContactoWhatsappResponse> response = this.restTemplate.getForEntity(uri, ContactoWhatsappResponse.class);
            ContactoWhatsappResponse contactoWhatsappResponse = response.getBody();

            return contactoWhatsappResponse;

        } catch (Exception e) {
            throw new Exception("Error al obtener whatsapp", e);
        }
    }


}
