# Patrones aplicados y fundamentos

## Patrón de comportamiento: Template Method
- **Concepto**: define el esqueleto de un algoritmo en una operación, delegando a las subclases algunos pasos concretos. Garantiza que el flujo general se mantenga estable, mientras que las partes variables pueden personalizarse.
- **Implementación**: la clase `com.example.mecanico_app.service.reparacion.ProcesoReparacionTemplate` concentra los pasos comunes del proceso de reparación (`validarMecanico`, `validarVehiculo`, `diagnosticarProblema`, `ejecutarReparacion`, `accionesAdicionales`).  
  Las subclases `ReparacionMotorProceso`, `ReparacionFrenosProceso` y `ReparacionElectricaProceso` definen los diagnósticos y acciones específicas para cada tipo de trabajo, asegurando reutilización del flujo y consistencia en el registro del `HistorialArreglo`.

## Patrón creacional: Builder
- **Concepto**: separa la construcción compleja de un objeto de su representación final, permitiendo crear objetos paso a paso y validando que se cuente con toda la información necesaria antes de construirlo.
- **Implementación**: la clase `com.example.mecanico_app.domain.HistorialArreglo` incorpora un `Builder` interno que obliga a indicar detalle, tipo, vehículo y mecánico antes de invocar `build()`.  
  El Template Method crea siempre los registros a través de este Builder, lo que asegura consistencia de datos (fecha, asociaciones obligatorias) sin exponer constructores complejos ni permitir estados parciales.

## Patrón estructural: Facade
- **Concepto**: proporciona una interfaz simplificada que unifica operaciones complejas de varios subsistemas, ocultando la complejidad y reduciendo el acoplamiento con los clientes.
- **Implementación**: `com.example.mecanico_app.service.TallerMecanicoFacade` ofrece métodos de alto nivel (`listarVehiculosActivos`, `registrarVehiculo`, `registrarReparacion`, etc.) que orquestan repositorios y estrategias de reparación.  
  Los controladores MVC interactúan únicamente con la fachada, lo que simplifica el código del FrontEnd, protege las invariantes y permite incorporar validaciones en un punto central.

