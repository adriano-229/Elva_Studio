create table if not exists mensaje (
    id              varchar(36) primary key,
    titulo          varchar(120) not null,
    texto           text not null,
    tipo_mensaje    varchar(20) not null,
    eliminado       boolean not null default false,
    version         bigint,
    usuario_id      varchar(36) not null,
    constraint fk_mensaje_usuario foreign key (usuario_id) references usuario(id)
);

create table if not exists promocion (
    mensaje_id                 varchar(36) primary key,
    fecha_envio_promocion      date,
    cantidad_socios_enviados   bigint not null default 0,
    constraint fk_promocion_mensaje foreign key (mensaje_id) references mensaje(id) on delete cascade
);
