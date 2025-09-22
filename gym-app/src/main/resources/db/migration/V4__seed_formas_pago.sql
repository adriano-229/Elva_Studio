-- src/main/resources/db/migration/V4__seed_formas_pago.sql
merge into forma_de_pago (id, tipo_pago, observacion, eliminado) key(id) values
  ('FP_EFEC','EFECTIVO',null,false),
  ('FP_TRANS','TRANSFERENCIA',null,false),
  ('FP_BILL','BILLETERA_VIRTUAL',null,false);
