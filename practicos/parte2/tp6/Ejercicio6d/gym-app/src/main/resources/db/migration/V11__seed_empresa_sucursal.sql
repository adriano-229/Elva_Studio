-- Datos iniciales para empresas y sucursales

insert into empresa (id, nombre, telefono, correo_electronico, eliminado) values
('EMP-001', 'Sport Corp', '+54 261 4000000', 'contacto@sportcorp.com', false);

insert into sucursal (id, nombre, eliminado, empresa_id) values
('SUC-001', 'Matriz Central', false, 'EMP-001'),
('SUC-002', 'Sucursal Oeste', false, 'EMP-001');

update persona set sucursal_id = 'SUC-001' where sucursal_id is null;

alter table persona modify column sucursal_id varchar(36) not null;
