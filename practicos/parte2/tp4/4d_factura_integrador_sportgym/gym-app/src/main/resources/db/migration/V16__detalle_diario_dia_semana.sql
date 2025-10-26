-- Cambiar numero de día por día de la semana en detalle_diario

alter table detalle_diario add column if not exists dia_semana varchar(20);

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

alter table detalle_diario alter column dia_semana set not null;

alter table detalle_diario drop constraint if exists uq_detalle_dia;
alter table detalle_diario add constraint uq_detalle_dia_semana unique (rutina_id, dia_semana);

alter table detalle_diario drop column if exists numero_dia;
