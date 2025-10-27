# Planning Poker - Historial Clinico Paciente

Este documento muestra las **estimaciones de esfuerzo** realizadas con Planning Poker antes de empezar el proyecto, y la comparación con el **tiempo real**.

---

## 1. Estimaciones Planning Poker por módulo

| Tarea / Módulo | Estimación PP (puntos) | Tiempo real aproximado |
|----------------|-----------------------|----------------------|
| Creación de proyecto Maven + configuración Spring | 2 | 1 hora |
| Definición de entidades (`Paciente`, `HistoriaClinica`) | 3 | 1 hora |
| Implementación de `BaseControllerImp` y `BaseServiceImp` | 5 | 2 horas |
| Creación de `PacienteController` y endpoints CRUD | 5 | 2 horas |
| Creación de `HistoriaClinicaController` | 3 | 1 hora |
| Vistas Thymeleaf (`pacientes.html`, `paciente_form.html`) | 5 | 2,5 horas |
| Integración y prueba de CRUD completo | 5 | 3 horas |
| Refactorización y Template Method final | 5 | 2 horas |

**Total puntos estimados:** 44 puntos  
**Total horas reales:** 18 horas  

---

## 2. Comparación estimación vs. tiempo real

| Tarea | Estimación PP | Tiempo real | Diferencia |
|-------|---------------|------------|------------|
| Proyecto Maven | 2 | 1h | -1 |
| Entidades | 3 | 30' | 30' |
| BaseController / BaseService | 5 | 2h | -3 |
| PacienteController CRUD | 5 | 2h | -3 |
| HistoriaClinicaController | 3 | 1h | -2 |
| Vistas Thymeleaf | 5 | 2.5h | -2.5 |
| Integración CRUD | 5 | 3h | -2 |
| Testing / depuración | 8 | 4h | -4 |
| Refactor / Template Method | 5 | 2h | -3 |
| Documentación | 3 | 1h | -2 |

---

## 3. Análisis y conclusiones

- La mayoría de las tareas se completaron **más rápido** de lo estimado.  
- Las tareas de **testing y depuración** consumieron más tiempo del previsto, debido a los ajustes de Template Method.  

---

## 4. Notas adicionales

- Los puntos de Planning Poker se asignaron usando la secuencia de Fibonacci: 1, 2, 3, 5, 8, 13…  
- Las horas reales se calcularon de manera aproximada durante la realización del proyecto.
