# Estimación de Costos del Proyecto – Sistema de Gestión Escolar

## Descripción General

El presente documento detalla la estimación de costos del proyecto **“Sistema de Gestión Escolar”**, que permite registrar alumnos, profesores, materias y notas, conforme a las **Historias de Usuario** previamente elaboradas.  

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
- El presupuesto incluye el desarrollo del módulo funcional: **registro de alumnos, materias y notas**.  

---

## Sitios web del mismo rubro

- ColegioNet: Plataforma argentina de gestión escolar que permite administrar alumnos, notas, asistencias y comunicación con los padres. [https://www.colegionet.com.ar](https://www.colegionet.com.ar)
- ClassDojo: Sistema educativo internacional que conecta docentes, alumnos y familias para gestionar conducta, tareas y progreso. [https://www.classdojo.com/es](https://www.classdojo.com/es)
- Schoology: Plataforma LMS (Learning Management System) usada por escuelas para administrar clases, calificaciones y materiales. [https://www.schoology.com](https://www.schoology.com)

---

## Requisitos No Funcionales del Sistema

### Seguridad
- El sistema debe requerir autenticación mediante usuario y contraseña.
- Los datos personales de los alumnos y docentes deben almacenarse de manera segura.
- Solo el administrador podrá acceder a la gestión de usuarios y materias.

### Rendimiento
- El sistema debe responder en menos de 2 segundos al registrar notas o alumnos.
- Debe soportar al menos 100 usuarios concurrentes sin pérdida de rendimiento.

### Usabilidad
- La interfaz debe ser clara e intuitiva, accesible para usuarios no técnicos.
- Debe permitir navegar entre secciones sin necesidad de recargar completamente la página.

### Disponibilidad
- El sistema debe estar disponible el 99% del tiempo durante el ciclo lectivo.
- Las copias de seguridad deben realizarse semanalmente para evitar pérdida de información.

### Mantenibilidad
- El código fuente debe estar documentado y seguir un patrón arquitectónico MVC.
- La base de datos debe permitir la incorporación de nuevas entidades sin afectar las existentes.

### Compatibilidad
- El sistema debe funcionar correctamente en los principales navegadores web: Google Chrome, Microsoft Edge y Mozilla Firefox.
- Debe ser accesible desde computadoras, tablets y dispositivos móviles.

---


## Referencia

Colegio Profesional de Ciencias Informáticas de la Provincia de Córdoba (CPCIC)  
[https://cpcipc.org.ar/honorarios-recomendados/](https://cpcipc.org.ar/honorarios-recomendados/)  
**Documento:** Honorarios Profesionales Recomendados – Actualización IPIM

