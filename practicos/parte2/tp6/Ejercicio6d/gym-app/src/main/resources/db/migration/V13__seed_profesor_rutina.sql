-- Datos de prueba para profesor y rutina asignada a un socio existente

insert into persona (id, nombre, apellido, fecha_nacimiento, tipo_documento,
                    numero_documento, telefono, correo_electronico, eliminado, sucursal_id) values
(1001, 'Laura', 'Diaz', '1985-03-15', 'DNI', '30999888', '+54 261 4000010', 'laura.diaz@sport.com', false, 'SUC-001');

insert into empleado (persona_id, activo, tipo) values
(1001, true, 'PROFESOR');

insert into usuario (id, nombre_usuario, clave, rol, eliminado) values
('USR-PROF1', 'profesor1', '{noop}prof123', 'OPERADOR', false);

insert into rutina (id, fecha_inicia, fecha_finaliza, objetivo, activo, estado_rutina, socio_id, profesor_id) values
(100, '2025-01-02', '2025-01-30', 'Plan de fuerza tren inferior', true, 'EN_PROCESO', 1, 1001);

insert into detalle_diario (id, numero_dia, activo, rutina_id) values
(1000, 1, true, 100),
(1001, 3, true, 100);

insert into detalle_ejercicio (id, actividad, series, repeticiones, activo, grupo_muscular, detalle_diario_id) values
(10000, 'Sentadillas con barra', 4, 12, true, 'PIERNAS', 1000),
(10001, 'Prensa inclinada', 3, 15, true, 'PIERNAS', 1000),
(10002, 'Remo con barra', 4, 10, true, 'ESPALDA', 1001);
