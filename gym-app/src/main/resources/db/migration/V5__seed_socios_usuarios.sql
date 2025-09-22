-- src/main/resources/db/migration/V5__seed_socios_usuarios.sql
-- Datos base de direcciones, usuarios, personas, socios y cuotas mensuales

merge into direccion (id, calle, numeracion, barrio, manzana_piso, casa_departamento,
                      referencia, eliminado, localidad_id)
key(id) values
('DIR1','Av. San Martín','1234','Centro',null,null,null,false,'DOR'),
('DIR2','Calle Italia','456','Sur',null,null,null,false,'DOR');

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

merge into cuota_mensual (id, mes, anio, estado, fecha_vencimiento, valor,
                          eliminado, socio_id, version)
key(id) values
('CUO1','ENERO',2024,'PENDIENTE','2024-01-10',4000.00,false,1,0),
('CUO2','FEBRERO',2024,'PENDIENTE','2024-02-10',4000.00,false,1,0),
('CUO3','ENERO',2024,'PENDIENTE','2024-01-15',4000.00,false,2,0);
