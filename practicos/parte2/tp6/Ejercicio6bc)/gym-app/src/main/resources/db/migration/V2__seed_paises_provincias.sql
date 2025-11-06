-- db/migration/V2__seed_paises_provincias.sql

-- PAISES
insert into pais (id, nombre, eliminado) values
('AR','Argentina',false);

-- PROVINCIAS
insert into provincia (id, nombre, eliminado, pais_id) values
('MZA','Mendoza',false,'AR'),
('BSAS','Buenos Aires',false,'AR');

-- DEPARTAMENTOS (Mendoza)
insert into departamento (id, nombre, eliminado, provincia_id) values
('GOD','Godoy Cruz',false,'MZA'),
('LUA','Luján de Cuyo',false,'MZA');

-- LOCALIDADES (Godoy Cruz)
insert into localidad (id, nombre, codigo_postal, eliminado, departamento_id) values
('DOR','Dorrego','5501',false,'GOD'),
('VHP','Villa Hipódromo','5501',false,'GOD');
