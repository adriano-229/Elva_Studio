package com.projects.gym.gym_app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Direccion {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String calle;
    private String numeracion;
    private String barrio;
    private String manzanaPiso;
    private String casaDepartamento;
    private String referencia;
    @ManyToOne
    private Localidad localidad;
    private boolean eliminado;
    
    public String getDireccionCompleta() {
        StringBuilder sb = new StringBuilder();

        if (calle != null && !calle.isEmpty()) {
            sb.append(calle);
        }

        if (numeracion != null && !numeracion.isEmpty()) {
            sb.append(" ").append(numeracion);
        }

        if (barrio != null && !barrio.isEmpty()) {
            sb.append(", ").append(barrio);
        }

        if (manzanaPiso != null && !manzanaPiso.isEmpty()) {
            sb.append(", ").append(manzanaPiso);
        }

        if (casaDepartamento != null && !casaDepartamento.isEmpty()) {
            sb.append(", ").append(casaDepartamento);
        }

        if (localidad != null) {
            if (localidad.getNombre() != null && !localidad.getNombre().isEmpty()) {
                sb.append(", ").append(localidad.getNombre());
            }
            
            if (localidad.getDepartamento() != null) {
                if (localidad.getDepartamento().getNombre() != null && !localidad.getDepartamento().getNombre().isEmpty()) {
                    sb.append(", ").append(localidad.getDepartamento().getNombre());
                }

	            if (localidad.getDepartamento().getProvincia() != null) {
	                if (localidad.getDepartamento().getProvincia().getNombre() != null && !localidad.getDepartamento().getProvincia().getNombre().isEmpty()) {
	                    sb.append(", ").append(localidad.getDepartamento().getProvincia().getNombre());
	                }
	
	                if (localidad.getDepartamento().getProvincia().getPais() != null) {
	                    if (localidad.getDepartamento().getProvincia().getPais().getNombre() != null && !localidad.getDepartamento().getProvincia().getPais().getNombre().isEmpty()) {
	                        sb.append(", ").append(localidad.getDepartamento().getProvincia().getPais().getNombre());
	                    }
	                }
	            }
            }
        }

        return sb.toString();
    }

}
