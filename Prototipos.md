
# Socio uso del Sistema
## Visualizacion Cuotas 
Socio puede ver las cuotas y generar facturas
[SOCIO > CUOTAS]  GET /socios/{id}/cuotas?estado={TODAS|PENDIENTE|VENCIDA|PAGADA}
┌──────────────────────────────────────────────────────────────────────────────┐
│ Socio: #1234  |  Nombre: Juan Pérez   |  Plan: Mensual                       │
│ Filtro estado: [ PENDIENTE ▼ ]  [ Buscar ]                                   │
│                                                                              │
│ [ ] 2025-09 (VENCE: 2025-09-30)  |  $ 12.000  |  PENDIENTE                   │
│ [ ] 2025-08 (VENCE: 2025-08-31)  |  $ 12.000  |  VENCIDA                     │
│ [x] 2025-07 (VENCE: 2025z-07-31)  |  $ 11.000  |  PENDIENTE                  │
│                                                                              │
│                                   Subtotal seleccionado:        $ 11.000     │
│ [ Crear Factura ]  [ Ver Facturas ]                                          │
└──────────────────────────────────────────────────────────────────────────────┘

### Crear Factura 
Con los items seleccionados se genera la factura, mostrando un detalle de cada una y con los distintos medios de pago
en caso de pagar con MP no  

[FACTURA > DETALLE]  GET /facturas/{id}
┌──────────────────────────────────────────────────────────────────────────────┐
│ Factura #—(pendiente)  |  Fecha: 2025-09-15  |  Socio: Juan Pérez (#1234)    │
│ invoiceKey: 1234-20250915-2AD8…                                              │
│                                                                              │
│ Ítems (Detalle)                                                              │
│  • Cuota 2025-07 .................................................. $ 11.000 │
│ -------------------------------------------------------------- --------------│
│ Total ................................................................$11.000│
│                                                                              │
│ Forma de pago:                                                               │
│   (•) Efectivo    ( ) Transferencia    ( ) Billetera Virtual (Mercado Pago)  │
│                                                                              │
│ [ Crear Factura ]                                                            │
│                                                                              │
│ [ Anular Factura ]    [ Volver a Cuotas ]                                    │
└──────────────────────────────────────────────────────────────────────────────┘

[PAGO EN EFECTIVO]   GET /pagos/efectivo/instrucciones?facturaId=17
┌──────────────────────────────────────────────────────────────────────────────┐
│                ¡Factura creada con éxito!                                    │
│------------------------------------------------------------------------------│
│   Factura Nº: 17                                                             │
│   Socio: Juan Pérez (#1234)                                                  │
│   Total a abonar: $11.000                                                    │
│   Fecha de emisión: 2025-09-15                                               │
│------------------------------------------------------------------------------│
│  ➤ Diríjase a la VENTANILLA / PUERTA DE ENTRADA para realizar el pago        │
│    en EFECTIVO.                                                              │
│                                                                              │
│  • Presente su número de factura al personal de cobro.                       │
│                                                                              │
│  Estado actual: PENDIENTE de pago                                            │
│------------------------------------------------------------------------------│
│  [ Ver Factura Detallada ]                                                   │
└──────────────────────────────────────────────────────────────────────────────┘

[TRANSFERENCIA ]   GET /pagos/transferencia/instrucciones?facturaId=17
┌──────────────────────────────────────────────────────────────────────────────┐
│                ¡Factura creada con éxito!                                    │
│------------------------------------------------------------------------------│
│   Factura Nº: 17                                                             │
│   Socio: Juan Pérez (#1234)                                                  │
│   Total a abonar: $11.000                                                    │
│   Fecha de emisión: 2025-09-15                                               │
│------------------------------------------------------------------------------│
│  ➤ Realice una TRANSFERENCIA BANCARIA con los siguientes datos:              │
│     • Alias/CBU: GYM.ELVA.PAGOS                                              │
│     • Banco: Banco X                                                         │
│     • Monto exacto: $11.000                                                  │
│                                                                              │
│  ➤ Una vez hecha la transferencia:                                           │
│     • Adjunte el comprobante en esta misma página                            │
│     • Un operador validará el pago y confirmará la factura.                  │
│                                                                              │
│  Estado actual: PENDIENTE de pago                                            │
│------------------------------------------------------------------------------│
│  [ Subir Comprobante ]   [ Ver Factura Detallada ]                           │                                                              │
└──────────────────────────────────────────────────────────────────────────────┘

[PAGO MP]  POST /pagos/mp/preference { facturaId }
┌──────────────────────────────────────────────────────────────────────────────┐
│ Pago Online (Mercado Pago)                                                   │
│ Total: $11.000                                                               │
│ [ Ir a MP ]                                                                  │
│ *Se abrirá una ventana de Mercado Pago.                                      │
└──────────────────────────────────────────────────────────────────────────────┘

# Operador Gimansio
## Visualizar Facturas
[ADMIN > FACTURAS]  GET /facturas?estado={PENDIENTE|PAGADA|ANULADA}&q=
┌──────────────────────────────────────────────────────────────────────────────┐
│ Filtro: Estado [ PENDIENTE ▼ ]   Buscar [ Juan Pérez ]  [ Buscar ]           │
│                                                                              │
│ #ID   Fecha       Socio         Estado     Total    Pago        Acciones     │
│ 17    2025-09-15  Juan Pérez    PAGADA     $11.000  MP(999...)  [ Ver ]      │
│ 18    2025-09-15  Ana López     PENDIENTE  $24.000  -           [ Ver ]      │
└──────────────────────────────────────────────────────────────────────────────┘

## Confirmacion de Pagos Manual

[PAGO EFECTIVO]  POST /pagos/efectivo/confirmar/{facturaId}
┌──────────────────────────────────────────────────────────────────────────────┐
│ Confirmar cobro en efectivo                                                  │
│   Total: $11.000                                                             │
│   Recibido: [$11.000]  |  Vuelto: $0                                         │
│ [ Confirmar ]  [ Cancelar ]                                                  │
└──────────────────────────────────────────────────────────────────────────────┘

[TRANSFERENCIA - APROBAR]  POST /pagos/transferencia/aprobar/{facturaId}
┌──────────────────────────────────────────────────────────────────────────────┐
│ Aprobación manual                                                            │
│   Factura: #—  |  Esperado: $11.000  |  Comp. recibido: $11.000              │
│   Fecha comp.: 2025-09-15  |  Ref: TRX-ABC-999                               │
│ [ Aprobar y marcar PAGADA ]   [ Rechazar ]                                   │
└──────────────────────────────────────────────────────────────────────────────┘

## Crear Facturas
Crear factura a partir del administrador filtrando por socio y seleccionando las cuotas, tambien con la posibilidad ya de acentar el pago.
[OPERADOR > SOCIOS]   GET /admin/socios?q=
┌──────────────────────────────────────────────────────────────────────────────┐
│ Buscar socio: [ Juan Perez        ] [ Buscar ]   [ + Crear Socio ]           │
│------------------------------------------------------------------------------│
│ #ID   Apellido, Nombre    DNI        Estado   Plan        Acciones           │
│ 1234  Pérez, Juan         30.123.456 Activo   Mensual     [ Seleccionar ]    │
│ 1777  López, Ana          25.999.111 Activo   Mensual     [ Seleccionar ]    │
└──────────────────────────────────────────────────────────────────────────────┘

[OPERADOR > SOCIO > CUOTAS]   GET /admin/socios/{id}/cuotas?estado=PENDIENTE
┌──────────────────────────────────────────────────────────────────────────────┐
│ Socio: #1234  |  Nombre: Juan Pérez  |  Plan: Mensual                        │
│ Estado: [ PENDIENTE ▼ ]  [ Buscar ]         Sele. rápidas: [ Mes actual ]    │
│------------------------------------------------------------------------------│
│ [ ] 2025-09 (VENCE 2025-09-30)      |  $ 12.000  |  PENDIENTE                │
│ [ ] 2025-08 (VENCE 2025-08-31)      |  $ 12.000  |  VENCIDA                  │
│ [x] 2025-07 (VENCE 2025-07-31)      |  $ 11.000  |  PENDIENTE                │
│------------------------------------------------------------------------------│
│ Subtotal seleccionado:                              $ 11.000                 │
│                                                                              │
│ [ Crear Factura ]   [ Crear y Cobrar en Ventanilla ]                         │
│ [ Crear e Confirmar Transferencia ]                                          │
│                                                                              │
│ [ Volver a Búsqueda ]   [ Ver Facturas del Socio ]                           │
└──────────────────────────────────────────────────────────────────────────────┘



