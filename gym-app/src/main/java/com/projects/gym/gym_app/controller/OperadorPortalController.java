package com.projects.gym.gym_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/operador")
@RequiredArgsConstructor
public class OperadorPortalController {

    @GetMapping("/portal")
    public String portal(Model model) {
        model.addAttribute("titulo", "Panel del Operador");
        model.addAttribute("seccion", "Operadores");
        model.addAttribute("active", "operador-portal");

        model.addAttribute("resumenes", List.of(
                new ResumenCaja("Cobrado hoy", "$ 0", "bi-currency-dollar", "primary"),
                new ResumenCaja("Pagos pendientes", "0", "bi-hourglass-split", "warning"),
                new ResumenCaja("Rechazados", "0", "bi-x-circle", "danger")
        ));

        model.addAttribute("pagosRecientes", List.of(
                new PagoReciente("---", LocalDate.now(), "Sin datos", true)
        ));

        model.addAttribute("fechaActual", LocalDateTime.now());
        return "operador/portal";
    }
    private static class ResumenCaja {
        private final String titulo;
        private final String valor;
        private final String icono;
        private final String estilo;

        private ResumenCaja(String titulo, String valor, String icono, String estilo) {
            this.titulo = titulo;
            this.valor = valor;
            this.icono = icono;
            this.estilo = estilo;
        }

        public String getTitulo() {
            return titulo;
        }

        public String getValor() {
            return valor;
        }

        public String getIcono() {
            return icono;
        }

        public String getEstilo() {
            return estilo;
        }
    }

    private static class PagoReciente {
        private final String socio;
        private final LocalDate fecha;
        private final String estado;
        private final boolean aprobado;

        private PagoReciente(String socio, LocalDate fecha, String estado, boolean aprobado) {
            this.socio = socio;
            this.fecha = fecha;
            this.estado = estado;
            this.aprobado = aprobado;
        }

        public String getSocio() {
            return socio;
        }

        public LocalDate getFecha() {
            return fecha;
        }

        public String getEstado() {
            return estado;
        }

        public boolean isAprobado() {
            return aprobado;
        }
    }
}
