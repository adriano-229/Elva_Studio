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
    empresa_id varchar(36) not null references empresa(id),
    direccion_id varchar(36) not null,
    constraint fk_sucursal_direccion foreign key (direccion_id) references direccion(id)
);

alter table persona add column if not exists sucursal_id varchar(36);

alter table persona add constraint if not exists fk_persona_sucursal foreign key (sucursal_id) references sucursal(id);
