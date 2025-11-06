-- src/main/resources/db/migration/V5__seed_socios_usuarios.sql
-- Datos base de direcciones, usuarios, personas, socios y cuotas mensuales

merge into direccion (id, calle, numeracion, barrio, manzana_piso, casa_departamento,
                      referencia, eliminado, localidad_id)
key(id) values
('DIR1','Av. San Martín','1234','Centro',null,null,null,false,'DOR'),
('DIR2','Calle Italia','456','Sur',null,null,null,false,'DOR'),
('DIR3', 'Belgrano', '789', 'Norte', NULL, NULL, 'Cerca del parque central', FALSE, 'VHP'),
('DIR4', 'Calle Boulogne Sur Mer', '200', 'Sur', NULL, NULL, 'A dos cuadras del hospital', FALSE, 'VHP');

merge into usuario (id, nombre_usuario, clave, rol, eliminado)
key(id) values
('USR1','juanp','{noop}juan123','SOCIO',false),
('USR2','mariaq','{noop}maria123','SOCIO',false);

merge into persona (id, nombre, apellido, fecha_nacimiento, tipo_documento,
                    numero_documento, telefono, correo_electronico, eliminado)
key(id) values
(1,'Juan','Perez','1990-05-20','DNI','30111222','2611111111','juan@ejemplo.com',false),
(2,'Maria','Quinteros','1988-11-02','DNI','30999999','2612222222','maria@ejemplo.com',false);

merge into socio (persona_id, numero_socio, direccion_id, usuario_id)
key(persona_id) values
(1,1001,'DIR1','USR1'),
(2,1002,'DIR2','USR2');

-- Seed para valor_cuota
merge into valor_cuota (id, mes, anio, valor_cuota, creado_el)
key(id) values
('VAL1', 'ENERO', 2024, 100.00, CURRENT_TIMESTAMP),
('VAL2', 'FEBRERO', 2024, 200.00, CURRENT_TIMESTAMP),
('VAL3', 'MARZO', 2024, 300.00, CURRENT_TIMESTAMP),
('VAL4', 'ENERO', 2025, 400.00, CURRENT_TIMESTAMP),
('VAL5', 'FEBRERO', 2025, 500.00, CURRENT_TIMESTAMP),
('VAL6', 'MARZO', 2025, 600.00, CURRENT_TIMESTAMP);

-- Seed para cuota_mensual con IDs únicos
merge into cuota_mensual (id, mes, anio, estado, fecha_vencimiento, valor_cuota_id, eliminado, socio_id, version)
key(id) values
('CUO1','ENERO',2025,'PAGADA','2025-01-10','VAL1',false,1,0),
('CUO2','FEBRERO',2025,'PAGADA','2025-02-10','VAL2',false,1,0),
('CUO3','MARZO',2025,'ADEUDADA','2025-03-10','VAL3',false,1,0),
('CUO4','ABRIL',2025,'ADEUDADA','2025-04-10','VAL3',false,1,0),
('CUO5','MAYO',2025,'ADEUDADA','2025-05-10','VAL4',false,1,0),
('CUO6','JUNIO',2025,'ADEUDADA','2025-06-10','VAL4',false,1,0),
('CUO7','JULIO',2025,'ADEUDADA','2025-07-10','VAL4',false,1,0),
('CUO8','AGOSTO',2025,'ADEUDADA','2025-08-10','VAL5',false,1,0),
('CUO9','SEPTIEMBRE',2025,'ADEUDADA','2025-09-10','VAL6',false,1,0),
('CUO10','ENERO',2025,'ADEUDADA','2025-01-15','VAL2',false,2,0),
('CUO11','FEBRERO',2025,'ADEUDADA','2025-02-15','VAL2',false,2,0);


--merge into cuota_mensual (id, mes, anio, estado, fecha_vencimiento, valor_cuota_id,
--                          eliminado, socio_id, version)
--key(id) values
--('CUO1','ENERO',2024,'PAGADA','2024-01-10','VAL1',false,1,0),
--('CUO2','FEBRERO',2024,'PAGADA','2024-02-10','VAL2',false,1,0),
--('CUO1','MARZO',2024,'ADEUDADA','2024-01-10','VAL3',false,1,0),
--('CUO2','ABRIL',2024,'ADEUDADA','2024-02-10','VAL3',false,1,0),
--('CUO2','MAYO',2024,'ADEUDADA','2024-02-10','VAL4',false,1,0),
--('CUO1','JUNIO',2024,'ADEUDAD','2024-01-10','VAL5',false,1,0),
--('CUO2','JULIO',2024,'ADEUDADA','2024-02-10','VAL6',false,1,0)
--('CUO3','ENERO',2024,'ADEUDADA','2024-01-15','VAL2',false,2,0);
--('CUO3','FEBRERO',2024,'ADEUDADA','2024-01-15','VAL2',false,2,0);


--merge into valor_cuota (id, mes, anio, valor_cuota, creado_el)
--key(id) values
--('VAL1', 'ENERO', 2024, 100.00, CURRENT_TIMESTAMP),
--('VAL2', 'FEBRERO', 2024, 200.00, CURRENT_TIMESTAMP),
--('VAL3', 'MARZO', 2024, 300.00, CURRENT_TIMESTAMP),
--('VAL4', 'ENERO', 2025, 400.00, CURRENT_TIMESTAMP),
--('VAL5', 'FEBRERO', 2025, 500.00, CURRENT_TIMESTAMP),
--('VAL6', 'MARZO', 2025, 600.00, CURRENT_TIMESTAMP);
