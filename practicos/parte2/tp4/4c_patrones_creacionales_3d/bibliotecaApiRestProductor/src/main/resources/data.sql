-- Desactivar temporalmente las claves foráneas
SET FOREIGN_KEY_CHECKS = 0;

-- ===============================
-- 1️⃣ Limpiar tablas (hijas primero)
-- ===============================
DELETE FROM persona_libro;
DELETE FROM libro_autor;
DELETE FROM persona;
DELETE FROM libro;
DELETE FROM autor;
DELETE FROM domicilio;
DELETE FROM localidad;

-- ===============================
-- 2️⃣ Insertar tablas padre primero
-- ===============================
INSERT INTO localidad (denominacion) VALUES ('Mendoza');
INSERT INTO localidad (denominacion) VALUES ('Godoy Cruz');
INSERT INTO localidad (denominacion) VALUES ('Luján de Cuyo');

-- ===============================
-- 3️⃣ Insertar tablas dependientes
-- ===============================
-- Domicilios
INSERT INTO domicilio (calle, numero, fk_localidad) VALUES ('San Martín', 123, 1);
INSERT INTO domicilio (calle, numero, fk_localidad) VALUES ('Belgrano', 456, 2);
INSERT INTO domicilio (calle, numero, fk_localidad) VALUES ('Sarmiento', 789, 3);

-- Autores
INSERT INTO autor (nombre, apellido, biografia) VALUES ('Julio', 'Cortázar', 'Escritor argentino conocido por su estilo innovador y su obra "Rayuela".');
INSERT INTO autor (nombre, apellido, biografia) VALUES ('Jorge Luis', 'Borges', 'Uno de los escritores más destacados de la literatura universal.');
INSERT INTO autor (nombre, apellido, biografia) VALUES ('Ernesto', 'Sábato', 'Autor argentino, conocido por "El túnel".');
INSERT INTO autor (nombre, apellido, biografia) VALUES ('Gabriel', 'García Márquez', 'Escritor colombiano, ganador del Premio Nobel de Literatura.');

-- Libros
-- 📘 Libros físicos
INSERT INTO libro (fecha, genero, paginas, titulo, tipo, ejemplares_disponibles, ruta_pdf, estado)
VALUES (1963, 'Ficción', 400, 'Rayuela', 'FISICO', 1, NULL, 'OCUPADO');

INSERT INTO libro (fecha, genero, paginas, titulo, tipo, ejemplares_disponibles, ruta_pdf, estado)
VALUES (1949, 'Ensayo', 250, 'El Aleph', 'FISICO', 1, NULL, 'DISPONIBLE');

-- 💾 Libros digitales (ruta_pdf vacía por precarga)
INSERT INTO libro (fecha, genero, paginas, titulo, tipo, ejemplares_disponibles, ruta_pdf, estado)
VALUES (1948, 'Novela', 200, 'El túnel', 'DIGITAL', NULL, '', 'DISPONIBLE');

INSERT INTO libro (fecha, genero, paginas, titulo, tipo, ejemplares_disponibles, ruta_pdf, estado)
VALUES (1967, 'Realismo mágico', 500, 'Cien años de soledad', 'DIGITAL', NULL, '', 'DISPONIBLE');

-- Relaciones libro_autor
INSERT INTO libro_autor (libro_id, autor_id) VALUES (1, 1);
INSERT INTO libro_autor (libro_id, autor_id) VALUES (2, 2);
INSERT INTO libro_autor (libro_id, autor_id) VALUES (3, 3);
INSERT INTO libro_autor (libro_id, autor_id) VALUES (4, 4);

-- Personas
INSERT INTO persona (nombre, apellido, dni, fk_domicilio) VALUES ('Ana', 'Pérez', 30123456, 1);
INSERT INTO persona (nombre, apellido, dni, fk_domicilio) VALUES ('Luis', 'Gómez', 29222444, 2);
INSERT INTO persona (nombre, apellido, dni, fk_domicilio) VALUES ('María', 'Fernández', 31111222, 3);

-- Relaciones persona_libro
INSERT INTO persona_libro (persona_id, libro_id) VALUES (1, 1);
--INSERT INTO persona_libro (persona_id, libro_id) VALUES (1, 2);
INSERT INTO persona_libro (persona_id, libro_id) VALUES (2, 3);
INSERT INTO persona_libro (persona_id, libro_id) VALUES (3, 4);

-- Reactivar las claves foráneas
SET FOREIGN_KEY_CHECKS = 1;
