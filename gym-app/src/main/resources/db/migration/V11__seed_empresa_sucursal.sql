-- Datos iniciales para empresas y sucursales

merge into empresa (id, nombre, telefono, correo_electronico, eliminado)
key(id) values
('EMP-001', 'Sport Corp', '+54 261 4000000', 'contacto@sportcorp.com', false);

merge into sucursal (id, nombre, eliminado, empresa_id)
key(id) values
('SUC-001', 'Matriz Central', false, 'EMP-001'),
('SUC-002', 'Sucursal Oeste', false, 'EMP-001');

update persona set sucursal_id = 'SUC-001' where sucursal_id is null;

alter table persona alter column sucursal_id set not null;
