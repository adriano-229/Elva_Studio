-- src/main/resources/db/migration/V6__seed_facturas.sql
-- Facturas de ejemplo y sus detalles

insert into factura (id, numero_factura, fecha_factura, total_pagado,
                    estado, eliminado, socio_id, forma_pago_id,
                    observacion_pago, observacion_anulacion, version) values
('FAC1',1,current_date, 8000.00,'PAGADA',false,1,'FP_EFEC','Pago en ventanilla',null,0),
('FAC2',2,current_date, 4000.00,'SIN_DEFINIR',false,2,null,null,null,0);

insert into detalle_factura (id, factura_id, cuota_mensual_id, importe, eliminado) values
('DET1','FAC1','CUO1',4000.00,false),
('DET2','FAC1','CUO2',4000.00,false),
('DET3','FAC2','CUO3',4000.00,false);
