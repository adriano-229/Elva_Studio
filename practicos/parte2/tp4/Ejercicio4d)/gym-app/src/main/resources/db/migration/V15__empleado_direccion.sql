-- Agregar soporte de direcciones para empleados

alter table empleado add column if not exists direccion_id varchar(36);

alter table empleado
    add constraint if not exists fk_empleado_direccion
        foreign key (direccion_id) references direccion(id);
