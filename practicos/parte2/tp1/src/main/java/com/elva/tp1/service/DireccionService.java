package com.elva.tp1.service;

import com.elva.tp1.domain.Departamento;
import com.elva.tp1.domain.Direccion;
import com.elva.tp1.domain.Pais;
import com.elva.tp1.domain.Provincia;
import com.elva.tp1.repository.DireccionRepository;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class DireccionService extends BaseService<Direccion, Long> {

    private final DireccionRepository direccionRepository;

    public DireccionService(DireccionRepository repository, DireccionRepository direccionRepository) {
        super(repository);
        this.direccionRepository = direccionRepository;
    }

    public List<Direccion> findAllByOrderByCalleAsc() {
        return direccionRepository.findAllByOrderByCalleAsc();
    }

    public List<Direccion> findAllByDepartamento_NombreOrderByCalleAsc(String departamentoNombre) {
        return direccionRepository.findAllByDepartamento_NombreOrderByCalleAsc(departamentoNombre);
    }

    public String getDireccionCompleta(Direccion direccion) {
        Departamento departamento = direccion.getDepartamento();
        Provincia provincia = departamento.getProvincia();
        Pais pais = provincia.getPais();

        return direccion.getCalle() + " " + direccion.getAltura() + ", " +
                departamento.getNombre() + ", " + provincia.getNombre() + ", " + pais.getNombre();
    }

    public String getGoogleMapsUrl(Direccion direccion) {
        String direccionCompleta = getDireccionCompleta(direccion);
        try {
            String encoded = URLEncoder.encode(direccionCompleta, StandardCharsets.UTF_8.toString());
            return "https://www.google.com/maps/search/?api=1&query=" + encoded;
        } catch (UnsupportedEncodingException e) {
            // UTF-8 siempre está soportado
            return "https://www.google.com/maps";
        }

    }
}