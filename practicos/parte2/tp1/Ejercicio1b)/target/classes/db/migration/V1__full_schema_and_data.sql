-- =================== DDL: CREACIÓN DE TABLAS ===================

CREATE TABLE pais (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      nombre VARCHAR(100) NOT NULL,
                      activo TINYINT(1) NOT NULL
);

CREATE TABLE provincia (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           nombre VARCHAR(100) NOT NULL,
                           activo TINYINT(1) NOT NULL,
                           pais_id INT NOT NULL,
                           FOREIGN KEY (pais_id) REFERENCES pais(id)
);

CREATE TABLE departamento (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              nombre VARCHAR(100) NOT NULL,
                              activo TINYINT(1) NOT NULL,
                              provincia_id INT NOT NULL,
                              FOREIGN KEY (provincia_id) REFERENCES provincia(id)
);

CREATE TABLE localidad (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           nombre VARCHAR(100) NOT NULL,
                           codigo_postal INT NOT NULL,
                           activo TINYINT(1) NOT NULL,
                           departamento_id INT NOT NULL,
                           FOREIGN KEY (departamento_id) REFERENCES departamento(id)
);

CREATE TABLE direccion (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           calle VARCHAR(100) NOT NULL,
                           altura INT NOT NULL,
                           activo TINYINT(1) NOT NULL,
                           localidad_id INT NOT NULL,
                           FOREIGN KEY (localidad_id) REFERENCES localidad(id)
);

CREATE TABLE persona (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         apellido VARCHAR(100) NOT NULL,
                         email VARCHAR(150) NOT NULL,
                         activo TINYINT(1) NOT NULL
);

CREATE TABLE usuario (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         persona_id INT NOT NULL,
                         cuenta VARCHAR(50) NOT NULL,
                         clave VARCHAR(100) NOT NULL,
                         rol VARCHAR(20) NOT NULL,
                         FOREIGN KEY (persona_id) REFERENCES persona(id)
);

CREATE TABLE empresa (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         razon_social VARCHAR(150) NOT NULL,
                         activo TINYINT(1) NOT NULL,
                         direccion_id INT NOT NULL,
                         FOREIGN KEY (direccion_id) REFERENCES direccion(id)
);

CREATE TABLE proveedor (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           persona_id INT NOT NULL,
                           cuit VARCHAR(20) NOT NULL,
                           direccion_id INT NOT NULL,
                           FOREIGN KEY (persona_id) REFERENCES persona(id),
                           FOREIGN KEY (direccion_id) REFERENCES direccion(id)
);

-- =================== DML: INSERCIÓN DE DATOS ===================

-- PAÍSES
INSERT INTO pais (nombre, activo)
VALUES ('Argentina', true),
       ('Brasil', true),
       ('Chile', true),
       ('Uruguay', true),
       ('Paraguay', true);

-- PROVINCIAS
INSERT INTO provincia (nombre, activo, pais_id)
VALUES
    ('Buenos Aires', true, 1),
    ('Córdoba', true, 1),
    ('Mendoza', true, 1),
    ('Santa Fe', true, 1),
    ('Tucumán', true, 1),
    ('São Paulo', true, 2),
    ('Rio de Janeiro', true, 2),
    ('Santiago', true, 3),
    ('Valparaíso', true, 3),
    ('Montevideo', true, 4);

-- DEPARTAMENTOS
INSERT INTO departamento (nombre, activo, provincia_id)
VALUES
    ('Capital Federal', true, 1),
    ('La Plata', true, 1),
    ('Mar del Plata', true, 1),
    ('Capital', true, 2),
    ('Río Cuarto', true, 2),
    ('Capital', true, 3),
    ('Godoy Cruz', true, 3),
    ('Las Heras', true, 3),
    ('Rosario', true, 4),
    ('Santa Fe Capital', true, 4),
    ('São Paulo Capital', true, 6),
    ('Campinas', true, 6),
    ('Santiago Centro', true, 8);

-- LOCALIDADES
INSERT INTO localidad (nombre, codigo_postal, activo, departamento_id)
VALUES
    ('CABA Centro', 1000, true, 1),
    ('Puerto Madero', 1001, true, 1),
    ('Palermo', 1414, true, 1),
    ('La Plata Centro', 1900, true, 2),
    ('Villa Elisa', 1902, true, 2),
    ('Nueva Córdoba', 5000, true, 4),
    ('Centro', 5001, true, 4),
    ('Ciudad de Mendoza', 5500, true, 6),
    ('Pedro Molina', 5501, true, 6),
    ('Godoy Cruz Centro', 5501, true, 7),
    ('Villa Hipódromo', 5502, true, 7),
    ('Las Heras Centro', 5539, true, 8),
    ('Centro', 2000, true, 9),
    ('Barrio Norte', 2001, true, 9),
    ('Centro', 1000, true, 11),
    ('Vila Madalena', 5433, true, 11);

-- DIRECCIONES
INSERT INTO direccion (calle, altura, activo, localidad_id)
VALUES
    ('Corrientes', 1234, true, 1),
    ('Florida', 567, true, 1),
    ('Santa Fe', 890, true, 1),
    ('Pierina Dealessi', 2020, true, 2),
    ('Honduras', 4455, true, 3),
    ('Gurruchaga', 1776, true, 3),
    ('7 y 47', 123, true, 4),
    ('Diagonal 74', 456, true, 4),
    ('27 de Abril', 2020, true, 6),
    ('Independencia', 890, true, 6),
    ('San Martín', 1234, true, 8),
    ('Las Heras', 567, true, 8),
    ('San Martín', 123, true, 10),
    ('Belgrano', 456, true, 10),
    ('Córdoba', 1500, true, 13),
    ('Pellegrini', 800, true, 13);

-- PERSONAS
INSERT INTO persona (nombre, apellido, email, activo)
VALUES ('Administrador', 'Sistema', 'admin@tp1.com', true),
       ('Usuario', 'Sistema', 'user@tp1.com', true);

-- USUARIOS
INSERT INTO usuario (persona_id, cuenta, clave, rol)
VALUES (1, 'admin', '$2b$12$3.TzsKLkZg0hLi/9GZ5wQ.fP.zJuxoWh14NZ7VsqewL0LWHRJUSaW', 'ADMIN'),
       (2, 'user', '$2b$12$zAi968mCZsa3i3IZIyuaYuMSMst1t4mqmV06Gd.pQavKAQ386g9Jy', 'USUARIO');

-- EMPRESAS
INSERT INTO empresa (razon_social, activo, direccion_id)
VALUES ('Mercado Libre S.R.L.', true, 1),
       ('Globant S.A.', true, 2),
       ('Techint S.A.', true, 3),
       ('YPF S.A.', true, 4),
       ('Banco Galicia', true, 5),
       ('Telecom Argentina', true, 7),
       ('Arcor S.A.I.C.', true, 6),
       ('Bodegas Chandon', true, 11),
       ('Bodega Catena Zapata', true, 12),
       ('Cervecería Quilmes', true, 15);

-- PROVEEDORES
-- Carlos Rodríguez
INSERT INTO persona (nombre, apellido, email, activo)
VALUES ('Carlos', 'Rodríguez', 'carlos.rodriguez@proveedor.com', true);
INSERT INTO proveedor (persona_id, cuit, direccion_id)
VALUES (LAST_INSERT_ID(), '20-12345678-9', 8);

-- Ana López
INSERT INTO persona (nombre, apellido, email, activo)
VALUES ('Ana', 'López', 'ana.lopez@proveedor.com', true);
INSERT INTO proveedor (persona_id, cuit, direccion_id)
VALUES (LAST_INSERT_ID(), '27-87654321-0', 9);

-- Roberto Martínez
INSERT INTO persona (nombre, apellido, email, activo)
VALUES ('Roberto', 'Martínez', 'roberto.martinez@proveedor.com', true);
INSERT INTO proveedor (persona_id, cuit, direccion_id)
VALUES (LAST_INSERT_ID(), '20-11111111-1', 10);

-- Laura Fernández
INSERT INTO persona (nombre, apellido, email, activo)
VALUES ('Laura', 'Fernández', 'laura.fernandez@proveedor.com', true);
INSERT INTO proveedor (persona_id, cuit, direccion_id)
VALUES (LAST_INSERT_ID(), '27-22222222-2', 13);

-- Diego Sánchez
INSERT INTO persona (nombre, apellido, email, activo)
VALUES ('Diego', 'Sánchez', 'diego.sanchez@proveedor.com', true);
INSERT INTO proveedor (persona_id, cuit, direccion_id)
VALUES (LAST_INSERT_ID(), '20-33333333-3', 14);

-- =================== USUARIOS DE ACCESO ===================
-- admin/admin123 (ADMIN) - Acceso completo
-- user/admin123 (USUARIO) - Acceso limitado
