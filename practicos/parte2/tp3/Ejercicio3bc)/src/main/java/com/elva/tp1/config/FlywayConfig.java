package com.elva.tp1.config;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración para recrear la base de datos desde cero en cada ejecución. Esto eliminará todos los datos cada vez
 * se inicie la aplicación.
 * ADVERTENCIA: Solo usar en desarrollo.
 */
@Configuration
public class FlywayConfig {

    @Bean
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        return flyway -> {
            // Limpiar la base de datos (elimina todas las tablas)
            flyway.clean();
            // Ejecutar las migraciones desde cero
            flyway.migrate();
        };
    }
}
