-- Agregar soporte de direcciones para empleados

alter table empleado add column direccion_id varchar(36);

alter table empleado
    add constraint fk_empleado_direccion
        foreign key (direccion_id) references direccion(id);
