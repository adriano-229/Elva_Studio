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
       ('Chile', 0);

-- PROVINCIAS
INSERT INTO provincia (nombre, eliminado, pais_id)
VALUES ('Buenos Aires', 0, 1),
       ('Mendoza', 0, 1),
       ('São Paulo', 0, 2),
       ('Rio de Janeiro', 0, 2),
       ('Santiago', 0, 3),
       ('Valparaíso', 0, 3);

-- DEPARTAMENTOS
INSERT INTO departamento (nombre, codigo_postal, eliminado, provincia_id)
VALUES
-- Buenos Aires
('La Plata', 1900, 0, 1),
('Mar del Plata', 7600, 0, 1),
('Bahía Blanca', 8000, 0, 1),

-- Mendoza
('Capital', 5500, 0, 2),
('Godoy Cruz', 5501, 0, 2),
('Las Heras', 5539, 0, 2),

-- São Paulo
('São Paulo Capital', 1000, 0, 3),
('Campinas', 13000, 0, 3),
('Santos', 11000, 0, 3),

-- Rio de Janeiro
('Rio de Janeiro Capital', 20000, 0, 4),
('Niterói', 24000, 0, 4),
('Petrópolis', 25600, 0, 4),

-- Santiago
('Santiago Centro', 8320000, 0, 5),
('Las Condes', 7550000, 0, 5),
('Maipú', 9250000, 0, 5),

-- Valparaíso
('Valparaíso Ciudad', 2340000, 0, 6),
('Viña del Mar', 2520000, 0, 6),
('Quilpué', 2430000, 0, 6);


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
