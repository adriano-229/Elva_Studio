-- Vincular empleados con usuarios y corregir rol de profesor

alter table empleado add column if not exists usuario_id varchar(36);

alter table empleado
    add constraint if not exists fk_empleado_usuario
        foreign key (usuario_id) references usuario(id);

alter table empleado
    add constraint if not exists uq_empleado_usuario
        unique (usuario_id);

update usuario
set rol = 'PROFESOR'
where nombre_usuario = 'profesor1';

update empleado
set usuario_id = 'USR-PROF1'
where persona_id = 1001 and (usuario_id is null or usuario_id = '');

alter table empleado alter column usuario_id set not null;
