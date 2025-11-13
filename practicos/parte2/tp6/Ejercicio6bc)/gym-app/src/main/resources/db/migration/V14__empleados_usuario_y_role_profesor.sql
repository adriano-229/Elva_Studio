-- Vincular empleados con usuarios y corregir rol de profesor

alter table empleado add column usuario_id varchar(36);

alter table empleado
    add constraint fk_empleado_usuario
        foreign key (usuario_id) references usuario(id);

alter table empleado
    add constraint uq_empleado_usuario
        unique (usuario_id);

update usuario
set rol = 'PROFESOR'
where nombre_usuario = 'profesor1';

update empleado
set usuario_id = 'USR-PROF1'
where persona_id = 1001 and (usuario_id is null or usuario_id = '');

alter table empleado modify column usuario_id varchar(36) not null;
