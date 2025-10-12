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
       ('Usuario', 'Común', 'user@tp1.com', 0),
       ('Adriano', 'Fabris', 'adrianosfabris@gmail.com', 0);

-- USUARIOS (credenciales del sistema)
-- Nota: Las claves están encriptadas con BCrypt strength 12
-- admin / admin123
-- user / user123
INSERT INTO usuario (id, cuenta, clave, rol)
VALUES (1, 'admin', '$2a$12$3JGZje0HzwqLQUFJYV8FfOMB7iavu/EPOqeMPowdGKXesQAKIHXtS', 'ADMIN'),
       (2, 'user', '$2a$12$OQRazT1mHuP4mYv.LNbAlOPkyMwlebKrHGyKxXcDR4gBXsjGUhKRW', 'USUARIO');

-- DIRECCIONES
INSERT INTO direccion (calle, altura, eliminado, departamento_id)
VALUES
-- Argentina - Buenos Aires
('Av. Corrientes', 1234, 0, 1),
('Calle Florida', 567, 0, 1),
('Av. 9 de Julio', 890, 0, 1),
('Av. Independencia', 3456, 0, 2),
('Calle San Martín', 2345, 0, 2),
('Av. Colón', 1500, 0, 3),
('Calle Chiclana', 678, 0, 3),

-- Argentina - Mendoza
('Av. San Martín', 1050, 0, 4),
('Calle Las Heras', 234, 0, 4),
('Av. Mitre', 890, 0, 5),
('Calle Sarmiento', 445, 0, 6),

-- Brasil - São Paulo
('Av. Paulista', 1578, 0, 7),
('Rua Augusta', 2690, 0, 7),
('Av. Brigadeiro Faria Lima', 3477, 0, 7),
('Rua Dr. Quirino', 1562, 0, 8),
('Av. Francisco Glicério', 935, 0, 8),

-- Brasil - Rio de Janeiro
('Av. Atlântica', 1702, 0, 10),
('Rua Visconde de Pirajá', 550, 0, 10),
('Av. Rio Branco', 156, 0, 10),

-- Chile - Santiago
('Av. Providencia', 2133, 0, 13),
('Av. Apoquindo', 4500, 0, 14),
('Calle Huérfanos', 1178, 0, 13),
('Av. Las Condes', 11.049, 0, 14),

-- Chile - Valparaíso
('Av. Pedro Montt', 2355, 0, 16),
('Calle Arlegui', 579, 0, 17);

-- EMPRESAS
INSERT INTO empresa (razon_social, eliminado, direccion_id)
VALUES
-- Argentina
('Mercado Libre S.R.L.', 0, 1),
('Arcor S.A.I.C.', 0, 2),
('YPF S.A.', 0, 3),
('Techint Ingeniería y Construcción', 0, 4),
('Bodega Trapiche', 0, 8),
('Bodega Norton', 0, 9),

-- Brasil
('Petrobras', 0, 12),
('Vale S.A.', 0, 13),
('Banco Itaú', 0, 14),
('Magazine Luiza', 0, 15),
('Embraer S.A.', 0, 16),

-- Chile
('LATAM Airlines Group', 0, 19),
('Codelco', 0, 20),
('Falabella S.A.', 0, 21),
('Cencosud S.A.', 0, 22);

-- PERSONAS PARA PROVEEDORES
INSERT INTO persona (nombre, apellido, email, eliminado)
VALUES
('Juan', 'González', 'juan.gonzalez@distribuidora.com', 0),
('María', 'Rodríguez', 'maria.rodriguez@logistica.com', 0),
('Carlos', 'Fernández', 'carlos.fernandez@materiales.com', 0),
('Ana', 'Martínez', 'ana.martinez@suministros.com', 0),
('Pedro', 'López', 'pedro.lopez@insumos.com', 0),
('Laura', 'García', 'laura.garcia@packaging.com', 0),
('Roberto', 'Sánchez', 'roberto.sanchez@tecnologia.com', 0),
('Sofía', 'Pérez', 'sofia.perez@alimentos.com', 0),
('Diego', 'Torres', 'diego.torres@transportes.com', 0),
('Valentina', 'Ramírez', 'valentina.ramirez@equipamiento.com', 0),
('Fernando', 'Silva', 'fernando.silva@industrias.com', 0),
('Camila', 'Costa', 'camila.costa@comercial.com', 0),
('Mateo', 'Oliveira', 'mateo.oliveira@soluciones.com', 0),
('Isabella', 'Santos', 'isabella.santos@servicios.com', 0),
('Lucas', 'Almeida', 'lucas.almeida@distribuciones.com', 0);

-- PROVEEDORES
INSERT INTO proveedor (id, cuit, direccion_id)
VALUES
-- Argentina - Buenos Aires
(4, '20-12345678-9', 5),
(5, '20-23456789-0', 6),
(6, '20-34567890-1', 7),

-- Argentina - Mendoza
(7, '20-45678901-2', 10),
(8, '20-56789012-3', 11),

-- Brasil - São Paulo
(9, '20-67890123-4', 12),
(10, '20-78901234-5', 13),
(11, '20-89012345-6', 14),

-- Brasil - Rio de Janeiro
(12, '20-90123456-7', 17),
(13, '20-01234567-8', 18),

-- Chile - Santiago
(14, '20-11234567-9', 19),
(15, '20-22345678-0', 20),
(16, '20-33456789-1', 21),

-- Chile - Valparaíso
(17, '20-44567890-2', 23),
(18, '20-55678901-3', 24);
