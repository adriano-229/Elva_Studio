-- Tablas para rutinas, detalles diarios y ejercicios

create table if not exists empleado (
    persona_id bigint primary key,
    activo boolean not null,
    tipo varchar(20) not null,
    constraint fk_empleado_persona foreign key (persona_id) references persona(id)
);

create table if not exists rutina (
    id bigint not null auto_increment primary key,
    fecha_inicia date,
    fecha_finaliza date,
    objetivo varchar(255),
    activo boolean not null,
    estado_rutina varchar(20) not null,
    socio_id bigint not null,
    profesor_id bigint not null,
    constraint fk_rutina_socio foreign key (socio_id) references socio(persona_id),
    constraint fk_rutina_profesor foreign key (profesor_id) references empleado(persona_id)
);

create table if not exists detalle_diario (
    id bigint not null auto_increment primary key,
    numero_dia integer not null,
    activo boolean not null,
    rutina_id bigint not null,
    constraint fk_detalle_diario_rutina foreign key (rutina_id) references rutina(id),
    constraint uq_detalle_dia unique (rutina_id, numero_dia)
);

create table if not exists detalle_ejercicio (
    id bigint not null auto_increment primary key,
    actividad varchar(255) not null,
    series integer not null,
    repeticiones integer not null,
    activo boolean not null,
    grupo_muscular varchar(50) not null,
    detalle_diario_id bigint not null,
    constraint fk_detalle_ejercicio_diario foreign key (detalle_diario_id) references detalle_diario(id)
);

create index idx_detalle_diario_rutina on detalle_diario(rutina_id);
create index idx_detalle_ejercicio_diario on detalle_ejercicio(detalle_diario_id);
