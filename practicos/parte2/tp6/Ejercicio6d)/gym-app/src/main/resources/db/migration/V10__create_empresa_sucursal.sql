-- Estructura base para empresas y sucursales

create table if not exists empresa (
    id varchar(36) primary key,
    nombre varchar(150) not null,
    telefono varchar(30),
    correo_electronico varchar(120),
    eliminado boolean not null
);

create table if not exists sucursal (
    id varchar(36) primary key,
    nombre varchar(150) not null,
    eliminado boolean not null,
    empresa_id varchar(36) not null,
    constraint fk_sucursal_empresa foreign key (empresa_id) references empresa(id)
);

alter table persona add column sucursal_id varchar(36);

alter table persona add constraint fk_persona_sucursal foreign key (sucursal_id) references sucursal(id);
