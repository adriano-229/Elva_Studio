SET FOREIGN_KEY_CHECKS=0;

TRUNCATE TABLE contacto_telefonico;
TRUNCATE TABLE contacto_correo_electronico;
TRUNCATE TABLE contacto;
TRUNCATE TABLE empresa;
TRUNCATE TABLE persona;

SET FOREIGN_KEY_CHECKS=1;



-- =========================================
-- PERSONAS
-- =========================================
INSERT INTO persona (nombre, apellido) VALUES ('Juan', 'Pérez');      -- id 1
INSERT INTO persona (nombre, apellido) VALUES ('María', 'Gómez');     -- id 2
INSERT INTO persona (nombre, apellido) VALUES ('Carlos', 'Ramírez');  -- id 3
INSERT INTO persona (nombre, apellido) VALUES ('Ana', 'Torres');      -- id 4
INSERT INTO persona (nombre, apellido) VALUES ('Luis', 'Fernández');  -- id 5
INSERT INTO persona (nombre, apellido) VALUES ('Jose', 'Perez');  -- id 6

-- =========================================
-- USUARIOS
-- =========================================
INSERT INTO usuario (cuenta, clave, fk_persona, rol) VALUES ('admin', '{bcrypt}$2a$10$QVeTL8k2ElEEFHhHyZprlu8nh6NO21U5OnqkdL9Xu1vj5E3WwPEou', 6, 'ADMIN');      -- id 1
INSERT INTO usuario (cuenta, clave, fk_persona, rol) VALUES ('juan', '{bcrypt}$2a$10$GOfk.qNFA.k18GxBrxdzuuD8chgdOOgvCJCK1530K8v.qwbw1bglGmez', 1, 'USER');     -- id 2
INSERT INTO usuario (cuenta, clave, fk_persona, rol) VALUES ('maria', '{bcrypt}$2a$10$5x2q31Ty4s7UgmWxjYGb5.2F67U0PYT9bjt7Mj6298p/FjroP7.ly', 2, 'USER');  -- id 3
INSERT INTO usuario (cuenta, clave, fk_persona, rol) VALUES ('carlos', '{bcrypt}$2a$10$12iAugHWVFzKjxzype4DI.LOPZZK/87FbMTJFB7CR5A1/egQHMKu.', 3, 'USER');      -- id 4
INSERT INTO usuario (cuenta, clave, fk_persona, rol) VALUES ('ana', '{bcrypt}$2a$10$ykYPxyDk0eKS6MRzojGNperhPn6lnvY26vGnjM2ZOGwFvjk7HeCF2', 4, 'USER');  -- id 5
INSERT INTO usuario (cuenta, clave, fk_persona, rol) VALUES ('luis', '{bcrypt}$2a$10$0a8UQe7Ox8LvYQywlOCY.OPpGvzUkSYccyhKvDddLVBrgzN9Z9cFO', 5, 'USER');  -- id 6


-- =========================================
-- EMPRESAS
-- =========================================
INSERT INTO empresa (nombre) VALUES ('TechCorp');        -- id 1
INSERT INTO empresa (nombre) VALUES ('SoftSolutions');   -- id 2
INSERT INTO empresa (nombre) VALUES ('InnovaTech');      -- id 3

-- =========================================
-- CONTACTOS (tabla padre)
-- =========================================
-- Contactos de Juan
INSERT INTO contacto (tipo_contacto, tipo_subcontacto, observacion, fk_empresa, fk_persona)
VALUES ('LABORAL', 'TELEFONICO', 'Teléfono laboral Juan', 1, 1);  -- id 1
INSERT INTO contacto (tipo_contacto, tipo_subcontacto, observacion, fk_empresa, fk_persona)
VALUES ('PERSONAL', 'CORREO', 'Correo personal Juan', NULL, 1); -- id 2
INSERT INTO contacto (tipo_contacto, tipo_subcontacto, observacion, fk_empresa, fk_persona)
VALUES ('PERSONAL', 'TELEFONICO', 'Teléfono personal Juan', NULL, 1); -- id 3

-- Contactos de María
INSERT INTO contacto (tipo_contacto, tipo_subcontacto, observacion, fk_empresa, fk_persona)
VALUES ('LABORAL', 'TELEFONICO', 'Teléfono laboral María', 2, 2); -- id 4
INSERT INTO contacto (tipo_contacto, tipo_subcontacto, observacion, fk_empresa, fk_persona)
VALUES ('PERSONAL', 'CORREO', 'Correo personal María', NULL, 2); -- id 5
INSERT INTO contacto (tipo_contacto, tipo_subcontacto, observacion, fk_empresa, fk_persona)
VALUES ('PERSONAL', 'TELEFONICO', 'Teléfono personal María', NULL, 2); -- id 6

-- Contactos de Carlos
INSERT INTO contacto (tipo_contacto, tipo_subcontacto, observacion, fk_empresa, fk_persona)
VALUES ('LABORAL', 'TELEFONICO', 'Teléfono laboral Carlos', NULL, 3); -- id 7
INSERT INTO contacto (tipo_contacto, tipo_subcontacto, observacion, fk_empresa, fk_persona)
VALUES ('PERSONAL', 'CORREO', 'Correo personal Carlos', NULL, 3); -- id 8

-- Contactos de Ana
INSERT INTO contacto (tipo_contacto, tipo_subcontacto, observacion, fk_empresa, fk_persona)
VALUES ('LABORAL', 'TELEFONICO', 'Teléfono laboral Ana', 3, 4); -- id 9
INSERT INTO contacto (tipo_contacto, tipo_subcontacto, observacion, fk_empresa, fk_persona)
VALUES ('PERSONAL', 'CORREO', 'Correo personal Ana', NULL, 4); -- id 10

-- Contactos de Luis
INSERT INTO contacto (tipo_contacto, tipo_subcontacto, observacion, fk_empresa, fk_persona)
VALUES ('LABORAL', 'TELEFONICO', 'Teléfono laboral Luis', NULL, 5); -- id 11
INSERT INTO contacto (tipo_contacto, tipo_subcontacto, observacion, fk_empresa, fk_persona)
VALUES ('PERSONAL', 'CORREO', 'Correo personal Luis', NULL, 5); -- id 12

-- =========================================
-- CONTACTO TELEFONICO (hereda de contacto)
-- =========================================
INSERT INTO contacto_telefonico (id, telefono, tipo_telefono)
VALUES (1, '123456789', 'FIJO');      -- laboral Juan
INSERT INTO contacto_telefonico (id, telefono, tipo_telefono)
VALUES (3, '987654321', 'CELULAR');   -- personal Juan
INSERT INTO contacto_telefonico (id, telefono, tipo_telefono)
VALUES (4, '555111222', 'FIJO');      -- laboral María
INSERT INTO contacto_telefonico (id, telefono, tipo_telefono)
VALUES (6, '444555666', 'CELULAR');   -- personal María
INSERT INTO contacto_telefonico (id, telefono, tipo_telefono)
VALUES (7, '333222111', 'FIJO');      -- laboral Carlos
INSERT INTO contacto_telefonico (id, telefono, tipo_telefono)
VALUES (9, '777888999', 'CELULAR');   -- laboral Ana
INSERT INTO contacto_telefonico (id, telefono, tipo_telefono)
VALUES (11, '111222333', 'FIJO');     -- laboral Luis

-- =========================================
-- CONTACTO CORREO ELECTRONICO (hereda de contacto)
-- =========================================
INSERT INTO contacto_correo_electronico (id, email)
VALUES (2, 'juan.perez@gmail.com');         -- personal Juan
INSERT INTO contacto_correo_electronico (id, email)
VALUES (5, 'maria.gomez@gmail.com');        -- personal María
INSERT INTO contacto_correo_electronico (id, email)
VALUES (8, 'carlos.ramirez@gmail.com');     -- personal Carlos
INSERT INTO contacto_correo_electronico (id, email)
VALUES (10, 'ana.torres@gmail.com');        -- personal Ana
INSERT INTO contacto_correo_electronico (id, email)
VALUES (12, 'luis.fernandez@gmail.com');    -- personal Luis

-- Luego, actualizás la empresa con su contacto
--UPDATE empresa SET fk_contacto = 1 WHERE id = 1;
--UPDATE empresa SET fk_contacto = 4 WHERE id = 2;
--UPDATE empresa SET fk_contacto = 9 WHERE id = 3;
