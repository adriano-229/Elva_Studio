-- Ajusta el generador de identidad de persona para evitar colisiones con datos seed

insert into persona (id, nombre, apellido, eliminado)
values (1000, 'RESERVED', 'RESERVED', true);

delete from persona where id = 1000;
