package com.example.proyectocarrito;

import com.example.proyectocarrito.domain.*;
import com.example.proyectocarrito.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seed(ArticuloRepo aRepo, ProveedorRepo pRepo) {
        return args -> {
            // Crear proveedor con ubicación
            Proveedor p = new Proveedor();
            p.setNombre("Acme");
            p.setDireccion("Av. San Martín 123, Mendoza, Argentina");
            p.setLatitud(-32.8895);
            p.setLongitud(-68.8458);
            pRepo.save(p);

            // Crear artículos asociados
            for (int i = 1; i <= 12; i++) {
                Articulo a = new Articulo();
                a.setNombre("Producto " + i);
                a.setPrecio(10000 + i * 500);
                a.setProveedor(p);
                aRepo.save(a);
            }

            // Si querés más proveedores, agregá algunos extra:
            Proveedor q = new Proveedor();
            q.setNombre("Distribuidora Norte");
            q.setDireccion("Av. Las Heras 250, Mendoza, Argentina");
            q.setLatitud(-32.8825);
            q.setLongitud(-68.8380);
            pRepo.save(q);

            Proveedor r = new Proveedor();
            r.setNombre("Servicios Andinos");
            r.setDireccion("Godoy Cruz 450, Mendoza, Argentina");
            r.setLatitud(-32.8890);
            r.setLongitud(-68.8423);
            pRepo.save(r);
        };
    }
}