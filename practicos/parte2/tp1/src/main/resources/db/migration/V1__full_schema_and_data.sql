-- =================== DDL: CREACIÓN DE TABLAS ===================

-- Tabla base: Pais
CREATE TABLE pais
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre    VARCHAR(255) NOT NULL UNIQUE,
    eliminado TINYINT(1)   NOT NULL DEFAULT 0
);

-- Tabla: Provincia (depende de Pais)
CREATE TABLE provincia
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre    VARCHAR(255) NOT NULL,
    eliminado TINYINT(1)   NOT NULL DEFAULT 0,
    pais_id   BIGINT       NOT NULL,
    FOREIGN KEY (pais_id) REFERENCES pais (id)
);

-- Tabla: Departamento (depende de Provincia)
CREATE TABLE departamento
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(255) NOT NULL,
    codigo_postal INT          NOT NULL,
    eliminado     TINYINT(1)   NOT NULL DEFAULT 0,
    provincia_id  BIGINT       NOT NULL,
    FOREIGN KEY (provincia_id) REFERENCES provincia (id)
);

-- Tabla: Direccion (depende de Departamento)
CREATE TABLE direccion
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    calle           VARCHAR(255) NOT NULL,
    altura          INT          NOT NULL,
    eliminado       TINYINT(1)   NOT NULL DEFAULT 0,
    departamento_id BIGINT       NOT NULL,
    FOREIGN KEY (departamento_id) REFERENCES departamento (id)
);

-- Tabla base: Persona (herencia JOINED)
CREATE TABLE persona
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre    VARCHAR(255) NOT NULL,
    apellido  VARCHAR(255) NOT NULL,
    email     VARCHAR(255) NOT NULL UNIQUE,
    eliminado TINYINT(1)   NOT NULL DEFAULT 0
);

-- Tabla: Usuario (hereda de Persona con JOINED)
CREATE TABLE usuario
(
    id     BIGINT PRIMARY KEY,
    cuenta VARCHAR(50)  NOT NULL UNIQUE,
    clave  VARCHAR(100) NOT NULL,
    rol    VARCHAR(20)  NOT NULL,
    FOREIGN KEY (id) REFERENCES persona (id)
);

-- Tabla: Proveedor (hereda de Persona con JOINED)
CREATE TABLE proveedor
(
    id           BIGINT PRIMARY KEY,
    cuit         VARCHAR(255) NOT NULL UNIQUE,
    direccion_id BIGINT       NULL,
    FOREIGN KEY (id) REFERENCES persona (id),
    FOREIGN KEY (direccion_id) REFERENCES direccion (id)
);

-- Tabla: Empresa (depende de Direccion)
CREATE TABLE empresa
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    razon_social VARCHAR(255) NOT NULL,
    eliminado    TINYINT(1)   NOT NULL DEFAULT 0,
    direccion_id BIGINT       NULL,
    FOREIGN KEY (direccion_id) REFERENCES direccion (id)
);

-- =================== DML: INSERCIÓN DE DATOS ===================

-- PAÍSES
INSERT INTO pais (nombre, eliminado)
VALUES ('Argentina', 0),
       ('Brasil', 0),
       ('Chile', 0),
       ('Uruguay', 0),
       ('Paraguay', 0);

-- PROVINCIAS
INSERT INTO provincia (nombre, eliminado, pais_id)
VALUES ('Buenos Aires', 0, 1),
       ('Córdoba', 0, 1),
       ('Mendoza', 0, 1),
       ('Santa Fe', 0, 1),
       ('Tucumán', 0, 1),
       ('São Paulo', 0, 2),
       ('Rio de Janeiro', 0, 2),
       ('Santiago', 0, 3),
       ('Valparaíso', 0, 3),
       ('Montevideo', 0, 4);

-- DEPARTAMENTOS
INSERT INTO departamento (nombre, codigo_postal, eliminado, provincia_id)
VALUES ('Capital Federal', 1000, 0, 1),
       ('La Plata', 1900, 0, 1),
       ('Mar del Plata', 7600, 0, 1),
       ('Capital', 5000, 0, 2),
       ('Río Cuarto', 5800, 0, 2),
       ('Capital', 5500, 0, 3),
       ('Godoy Cruz', 5501, 0, 3),
       ('Las Heras', 5539, 0, 3),
       ('Rosario', 2000, 0, 4),
       ('Santa Fe Capital', 3000, 0, 4),
       ('São Paulo Capital', 1000, 0, 6),
       ('Campinas', 13000, 0, 6),
       ('Santiago Centro', 8320000, 0, 8);

-- DIRECCIONES
INSERT INTO direccion (calle, altura, eliminado, departamento_id)
VALUES ('Corrientes', 1234, 0, 1),
       ('Florida', 567, 0, 1),
       ('Santa Fe', 890, 0, 1),
       ('Pierina Dealessi', 2020, 0, 1),
       ('Honduras', 4455, 0, 1),
       ('Gurruchaga', 1776, 0, 1),
       ('7 y 47', 123, 0, 2),
       ('Diagonal 74', 456, 0, 2),
       ('27 de Abril', 2020, 0, 4),
       ('Independencia', 890, 0, 4),
       ('San Martín', 1234, 0, 6),
       ('Las Heras', 567, 0, 6),
       ('San Martín', 123, 0, 7),
       ('Belgrano', 456, 0, 7),
       ('Córdoba', 1500, 0, 9),
       ('Pellegrini', 800, 0, 9);

-- PERSONAS (para usuarios del sistema)
INSERT INTO persona (nombre, apellido, email, eliminado)
VALUES ('Administrador', 'Sistema', 'admin@tp1.com', 0),
       ('Usuario', 'Común', 'user@tp1.com', 0);

-- USUARIOS (credenciales del sistema)
-- Nota: Las claves están encriptadas con BCrypt strength 12
-- admin / admin123
-- user / user123
INSERT INTO usuario (id, cuenta, clave, rol)
VALUES (1, 'admin', '$2a$12$3JGZje0HzwqLQUFJYV8FfOMB7iavu/EPOqeMPowdGKXesQAKIHXtS', 'ADMIN'),
       (2, 'user', '$2a$12$OQRazT1mHuP4mYv.LNbAlOPkyMwlebKrHGyKxXcDR4gBXsjGUhKRW', 'USUARIO');


-- EMPRESAS
INSERT INTO empresa (razon_social, eliminado, direccion_id)
VALUES ('Mercado Libre S.R.L.', 0, 1),
       ('Globant S.A.', 0, 2),
       ('Techint S.A.', 0, 3),
       ('YPF S.A.', 0, 4),
       ('Banco Galicia', 0, 5),
       ('Telecom Argentina', 0, 7),
       ('Arcor S.A.I.C.', 0, 6),
       ('Bodegas Chandon', 0, 11),
       ('Bodega Catena Zapata', 0, 12),
       ('Cervecería Quilmes', 0, 15);

-- PERSONAS (para proveedores)
INSERT INTO persona (nombre, apellido, email, eliminado)
VALUES ('Carlos', 'Rodríguez', 'carlos.rodriguez@proveedor.com', 0),
       ('Ana', 'López', 'ana.lopez@proveedor.com', 0),
       ('Roberto', 'Martínez', 'roberto.martinez@proveedor.com', 0),
       ('Laura', 'Fernández', 'laura.fernandez@proveedor.com', 0),
       ('Diego', 'Sánchez', 'diego.sanchez@proveedor.com', 0),
       ('María', 'González', 'maria.gonzalez@proveedor.com', 0),
       ('Jorge', 'Pérez', 'jorge.perez@proveedor.com', 0),
       ('Sofía', 'Ramírez', 'sofia.ramirez@proveedor.com', 0);

-- PROVEEDORES
INSERT INTO proveedor (id, cuit, direccion_id)
VALUES (3, '20-12345678-9', 8),
       (4, '27-87654321-0', 9),
       (5, '20-11111111-1', 10),
       (6, '27-22222222-2', 13),
       (7, '20-33333333-3', 14),
       (8, '27-44444444-4', 15),
       (9, '20-55555555-5', 16),
       (10, '27-66666666-6', 11);

-- =================== COMENTARIOS FINALES ===================
-- Este script mantiene la integridad referencial en el siguiente orden:
-- 1. Pais (sin dependencias)
-- 2. Provincia (depende de Pais)
-- 3. Departamento (depende de Provincia)
-- 4. Direccion (depende de Departamento)
-- 5. Persona (sin dependencias, tabla base para herencia JOINED)
-- 6. Usuario (hereda de Persona)
-- 7. Proveedor (hereda de Persona, depende de Direccion)
-- 8. Empresa (depende de Direccion)
--
-- Notas importantes:
-- - Usuario y Proveedor usan herencia JOINED, por lo que sus IDs deben existir en la tabla persona
-- - Los IDs 1 y 2 de persona son para usuarios del sistema (admin y user)
-- - Los IDs 3-10 de persona son para proveedores
-- - Todas las claves de usuario están encriptadas con BCrypt strength 12
