-- db/migration/V2__seed_paises_provincias.sql  (compatible H2)

-- PAISES
merge into pais (id, nombre, eliminado) key(id)
values ('AR','Argentina',false);

-- PROVINCIAS
merge into provincia (id, nombre, eliminado, pais_id) key(id)
values ('MZA','Mendoza',false,'AR'),
       ('BSAS','Buenos Aires',false,'AR');

-- DEPARTAMENTOS (Mendoza)
merge into departamento (id, nombre, eliminado, provincia_id) key(id)
values ('GOD','Godoy Cruz',false,'MZA'),
       ('LUA','Luján de Cuyo',false,'MZA');

-- LOCALIDADES (Godoy Cruz)
merge into localidad (id, nombre, codigo_postal, eliminado, departamento_id) key(id)
values ('DOR','Dorrego','5501',false,'GOD'),
       ('VHP','Villa Hipódromo','5501',false,'GOD');
