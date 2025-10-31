-- Cambiar numero de día por día de la semana en detalle_diario

alter table detalle_diario add column dia_semana varchar(20);

update detalle_diario
set dia_semana = case numero_dia
    when 1 then 'LUNES'
    when 2 then 'MARTES'
    when 3 then 'MIERCOLES'
    when 4 then 'JUEVES'
    when 5 then 'VIERNES'
    when 6 then 'SABADO'
    when 7 then 'DOMINGO'
    else 'LUNES'
end
where dia_semana is null;

alter table detalle_diario modify column dia_semana varchar(20) not null;

alter table detalle_diario drop index uq_detalle_dia;
alter table detalle_diario add constraint uq_detalle_dia_semana unique (rutina_id, dia_semana);

alter table detalle_diario drop column numero_dia;
