# Estimación de Costos del Proyecto – Cadena de Restaurantes

## Descripción General

El presente documento detalla la estimación de costos del proyecto **Cadena de Restaurante**, que permite registrar movimientos, pedidos, comensales y calcular totales, conforme a las **Historias de Usuario** previamente elaboradas.  

## Metodología de Estimación

1. **Identificación de tareas**  
   Se consideraron las fases principales del ciclo de desarrollo: análisis, diseño, desarrollo, pruebas y documentación.

2. **Estimación de esfuerzo**  
   Se calcularon 52 horas totales de trabajo, a partir de la suma de las estimaciones de cada historia de usuario.

3. **Aplicación de tarifa por hora**  
   Según los valores de referencia del CPCIC, se asume un costo promedio de **$76.711,16 ARS/hora** .


---

## Estimación por Fases

| Fase                         | Horas estimadas | Tarifa/hora (ARS) | Subtotal (ARS) |
|------------------------------|-----------------|-------------------|----------------|
| Análisis de requerimientos   | 10 h            | $76.711,16           | $767.111,6       |
| Diseño (UML y prototipado)   | 8 h             | $76.711,16            | $613.689,28         |
| Desarrollo                   | 28 h            | $76.711,16            | $2.147.912,48       |
| Pruebas y documentación      | 6 h             | $76.711,16            | $460.266,96      |
| **Subtotal**                 | —               | —                 | **$3.988.980,32 ARS**   |
| **Total estimado del proyecto** | —            | —                 | **$3.988.980,32 ARS** |

---

## Observaciones

- Los valores son **estimativos** y deben actualizarse según los honorarios vigentes del CPCIC al momento de la contratación.  
- El presupuesto incluye el desarrollo del módulo funcional: **registro comensales, pedidos y pagos**.  

---

## Sitios web del mismo rubro

- RestobarApp: Software POS para bares y restaurantes, incluye control de caja, ingresos/egresos, inventario, multi-sucursal, etc.
- POSPanda: Plataforma de gestión para restaurantes con POS, facturación, inventario, panel de sucursales, kioscos de auto-pedido, etc.
- Techni‑Web POS: Software TPV (terminal punto de venta) especializado en restaurantes, permite “cash control” (control de caja), múltiples establecimientos, stock, personal.
---

## Requisitos No Funcionales del Sistema

## Seguridad
- El sistema debe requerir autenticación mediante usuario y contraseña para acceder.  
- Los usuarios tendrán distintos niveles de permisos según su rol (mozo, cajero, gerente, administrador).  
- Los datos de los movimientos de caja y los pedidos deben almacenarse de manera cifrada en la base de datos.  
- El sistema debe cerrar automáticamente la sesión después de 10 minutos de inactividad.  

---

## Rendimiento
- El sistema debe permitir registrar un pedido o movimiento en menos de **3 segundos**.  
- La consulta del saldo de caja no debe demorar más de **2 segundos**.  
- El sistema debe poder manejar al menos **50 operaciones simultáneas** sin degradación del rendimiento.  

---

## Fiabilidad y Disponibilidad
- El sistema debe garantizar la disponibilidad del **99% del tiempo operativo**.  
- Debe realizarse un respaldo automático de los datos cada 24 horas.  
- En caso de error o pérdida de conexión, el sistema debe permitir la recuperación de las últimas operaciones no guardadas.  

---

##  Usabilidad
- La interfaz debe ser clara, intuitiva y accesible para personal sin conocimientos técnicos.  
- Los botones de acción deben estar claramente identificados y con colores que indiquen su función (por ejemplo: verde para confirmar, rojo para cancelar).  
- El sistema debe ofrecer mensajes de error comprensibles para el usuario.  
- Debe ser posible usar el sistema tanto en PC como en tablets.  

---

## Portabilidad y Escalabilidad  
- Debe poder escalar para agregar nuevas sucursales sin modificar el núcleo del sistema.   

---

## Mantenibilidad
- RNF18. El código debe estar documentado y con buenas prácticas.  
- El sistema debe permitir actualizaciones sin afectar las operaciones en curso.  

---


## Referencia

Colegio Profesional de Ciencias Informáticas de la Provincia de Córdoba (CPCIC)  
[https://cpcipc.org.ar/honorarios-recomendados/](https://cpcipc.org.ar/honorarios-recomendados/)  
**Documento:** Honorarios Profesionales Recomendados – Actualización IPIM
