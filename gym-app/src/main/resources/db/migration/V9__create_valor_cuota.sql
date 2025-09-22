-- Crea tabla para valores de cuota mensuales vigentes

create table if not exists valor_cuota (
    id varchar(36) primary key,
    mes varchar(20) not null,
    anio integer not null,
    valor decimal(12,2) not null,
    creado_el timestamp not null,
    constraint uq_valor_cuota_mes_anio unique (anio, mes)
);
