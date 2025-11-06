-- Ajusta el generador de identidad de persona para evitar colisiones con datos seed

alter table persona alter column id restart with 1000;
