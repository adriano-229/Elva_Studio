-- -------------------------------
-- Limpiar tablas (orden inverso FK)
-- -------------------------------
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE detalle_factura;
TRUNCATE TABLE factura;
TRUNCATE TABLE cuota_mensual;
TRUNCATE TABLE socio;
TRUNCATE TABLE persona;
TRUNCATE TABLE usuario;
TRUNCATE TABLE sucursal;
TRUNCATE TABLE empresa;
TRUNCATE TABLE direccion;
TRUNCATE TABLE localidad;
TRUNCATE TABLE departamento;
TRUNCATE TABLE provincia;
TRUNCATE TABLE pais;
TRUNCATE TABLE valor_cuota;
TRUNCATE TABLE forma_de_pago;

SET FOREIGN_KEY_CHECKS = 1;

-- -------------------------------
-- Pais
-- -------------------------------
INSERT INTO pais (eliminado, nombre) VALUES
(false,'Argentina'),
(false,'Brasil'),
(false,'Chile');

-- -------------------------------
-- Provincia
-- -------------------------------
INSERT INTO provincia (eliminado, nombre, fk_pais) VALUES
(false,'Buenos Aires', 1),
(false,'São Paulo', 2),
(false,'Santiago', 3);

-- -------------------------------
-- Departamento
-- -------------------------------
INSERT INTO departamento (eliminado, nombre, fk_provincia) VALUES
(false,'La Plata', 1),
(false,'Campinas', 2),
(false,'Vitacura', 3);

-- -------------------------------
-- Localidad
-- -------------------------------
INSERT INTO localidad (nombre, codigo_postal, fk_departamento, eliminado) VALUES
('Gonnet', '1900', 1, false),
('Barão Geraldo', '13083', 2, false),
('Vitacura', '7630000', 3, false);

-- -------------------------------
-- Direccion
-- -------------------------------
INSERT INTO direccion (calle, numeracion, barrio, manzana, casa_departamento, referencia, fk_localidad, eliminado) VALUES
('Calle 1', '123', 'Barrio Centro', 'Mza A', 'Dpto 1', 'Cerca del parque', 1, false),
('Rua 2', '456', 'Jardim', 'Mza B', 'Casa 2', 'Frente a la plaza', 2, false),
('Avenida 3', '789', 'Sector Norte', 'Mza C', 'Casa 3', 'Al lado del supermercado', 3, false);

-- -------------------------------
-- Empresa
-- -------------------------------
INSERT INTO empresa (nombre, telefono, correo_electronico, eliminado) VALUES
('Empresa A', '5555-1111', 'contacto@empresaA.com', false),
('Empresa B', '5555-2222', 'contacto@empresaB.com', false),
('Empresa C', '5555-3333', 'contacto@empresaC.com', false);

-- -------------------------------
-- Sucursal
-- -------------------------------
INSERT INTO sucursal (nombre, fk_empresa, fk_direccion, eliminado) VALUES
('Sucursal 1', 1, 1, false),
('Sucursal 2', 2, 2, false),
('Sucursal 3', 3, 3, false);

-- -------------------------------
-- Usuario
-- -------------------------------
INSERT INTO usuario (nombre_usuario, clave, rol, eliminado) VALUES
('admin1', 'pass1', 'Admin', false),
('empleado1', 'pass2', 'Empleado', false),
('socio1', 'pass3', 'Socio', false);

-- -------------------------------
-- Persona
-- -------------------------------
INSERT INTO persona (nombre, apellido, fecha_nacimiento, tipo_documento, numero_documento, telefono, correo_electronico, fk_sucursal, fk_usuario, fk_direccion, eliminado) VALUES
('Juan', 'Pérez', '1990-01-01', 'DNI', '12345678', '5555-1111', 'juan@mail.com', 1, 1, 1, false),
('Maria', 'Silva', '1985-02-02', 'Pasaporte', 'BR987654', '5555-2222', 'maria@mail.com', 2, 2, 2, false),
('Pedro', 'Gonzalez', '1992-03-03', 'DNI', '87654321', '5555-3333', 'pedro@mail.com', 3, 3, 3, false);

-- -------------------------------
-- Socio (usar el mismo ID que Persona)
-- -------------------------------
INSERT INTO socio (id, numero_socio, eliminado) VALUES
(1, 1001, false),
(2, 1002, false),
(3, 1003, false);

-- -------------------------------
-- ValorCuota
-- -------------------------------
INSERT INTO valor_cuota (fecha_desde, fecha_hasta, valor_cuota, eliminado) VALUES
('2025-01-01', '2025-01-31', 3000.00, false),
('2025-02-01', '2025-02-28', 3200.00, false),
('2025-03-01', '2025-03-31', 3500.00, false);

-- -------------------------------
-- CuotaMensual
-- -------------------------------
INSERT INTO cuota_mensual (mes, anio, estado, fecha_vencimiento, eliminado, fk_valor_cuota, fk_socio) VALUES
('ENERO', 2025, 'Pagada', '2025-01-31', false, 1, 1),
('FEBRERO', 2025, 'Vencida', '2025-02-28', false, 2, 2),
('MARZO', 2025, 'Vencida', '2025-03-31', false, 3, 3);

-- -------------------------------
-- FormaDePago
-- -------------------------------
INSERT INTO forma_de_pago (eliminado, observacion, tipo_pago) VALUES
(false, 'Pago en efectivo', 'Efectivo'),
(false, 'Pago transferencia', 'Transferencia'),
(false, 'Pago billetera', 'Billetera_virtual');

-- -------------------------------
-- Factura
-- -------------------------------

INSERT INTO factura (eliminado, estado, fecha_factura, numero_factura, total_pagado, fk_forma_de_pago) VALUES
(true, 'Pagada', '2025-01-05', 1, 3000.00, 1),
(false, 'Pagada', '2025-02-05', 2, 3200.00, 2),
(false, 'Pagada', '2025-03-05', 3, 3500.00, 3);

-- -------------------------------
-- DetalleFactura
-- -------------------------------
INSERT INTO detalle_factura  (eliminado, fk_factura, fk_cuota_mensual) VALUES
(false,1,1),
(false,2,2),
(false,3,3);
