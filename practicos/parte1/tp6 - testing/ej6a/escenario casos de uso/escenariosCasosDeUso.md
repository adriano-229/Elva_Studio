# Escenarios de casos de uso

## UC1 — Registrar socio

* **Actores**: Operador
* **Pre**: DNI no registrado.
* **Post**: Socio creado `estado=ACTIVO`; credencial emitida.
* **Flujo principal**

  1. Operador ingresa datos (DNI, nombre, contacto, fecha nacimiento…).
  2. Sistema valida unicidad de DNI y formato de campos.
  3. Sistema crea el socio ACTIVO.
  4. Sistema confirma la operacion realizada.
* **Alternativas / Excepciones**
  A1. **DNI duplicado** → se rechaza y se informa.
  A2. **Datos obligatorios faltantes** → se solicita completar.
---

## UC2 — Cargar / Actualizar foto de rostro

* **Actores**: Operador
* **Pre**: Socio existente.
* **Post**: `FotoRostro` asociada y validada.
* **Flujo**

  1. Operador selecciona archivo de imagen.
  2. Sistema valida tipo/tamaño y guarda la foto.
  3. Sistema confirma la operacion realizada.
* **Excepciones**
  E1. **Formato inválido** → rechazar.
  E2. **Tamaño excedido** → rechazar.

---

## UC3 — Crear grupo familiar

* **Actores**: Operador
* **Pre**: Socio ACTIVO; aún sin grupo o con política que admite varios.
* **Post**: Grupo creado; **titular designado**.
* **Flujo**

  1. Operador crea grupo y selecciona titular.
  2. Sistema crea `GrupoFamiliar`.
  3. Sistema **incluye al titular** como integrante (rol=TITULAR).
  4. Operador agrega integrantes al grupo familiar(si son nuevos, da de alta como en UC1)
  5. Sistema previsualiza todos los integrantes del grupo familiar
  6. Operador confirma.
  7. Sistema confirma la operacion realizada.

* **Excepciones**
  E1. **Socio titular no ACTIVO** → rechazar y notificar.
  E2. **Grupo ya existente** → rechazar y notificar.
  E3. **Socio con un grupo familiar** -> rechazar y notificar.
---

## UC4 — Agregar integrante al grupo

* **Actores**: Operador
* **Pre**: Grupo activo; Socio existente y ACTIVO.
* **Post**: Integración creada.
* **Flujo**

  1. Operador selecciona grupo y socio.
  2. Previsualiza el nuevo grupo familiar.
  3. Operador confirma.
  4. Sistema registra integración.
* **Excepciones**
  E1. **Socio ya pertenece a un grupo famliliar** → rechazar.
---

## UC5 — Reasignar titular

* **Actores**: Operador
* **Pre**: Grupo con titular actual; al menos un candidato ACTIVO.
* **Post**: Titular reasignado.
* **Flujo**

  1. Operador solicita reasignación.
  2. Sistema propone candidatos.
  3. Operador elige y confirma; sistema actualiza.
* **Excepciones**
  E1. **Sin candidatos** → ofrecer **cerrar/desactivar grupo**.

---

## UC6 — Dar de baja socio

* **Actores**: Operador
* **Pre**: Revisa que el socio no se encuentre como **Deudor**, ni como titular de un **Grupo Famliliar**.
* **Post**: `estado=BAJA` registrado.
* **Flujo**

  1. Sistema verifica precondiciones.
  2. Marca `estado=BAJA`.
  3. Sistema confirma el nuevo estado del socio
* **Excepciones**
  E1. **Es titular activo** → exige reasignar/cerrar grupo (UC5).

---

## UC7: Registrar Entrada

* **Actores:** Operador
* **Pre**: Socio Activo
* **Post** Registro de la entrada del socio al gym.

* **Flujo**:

    1. El Socio presenta credencial.
    2. El Sistema valida identidad y estado del socio.
    3. El Sistema verifica que no existe un registro abierto del socio.
    4. El Sistema crea el registro de entrada.
    5. El Sistema confirma el acceso.
* **Excepciones**
  E1. Si la autenticación falla → el sistema rechaza el acceso y notifica al operador.
  E2. Si ya existe un registro de entrada abierto (sin salida registrada) → el sistema alerta de posible error y no crea un nuevo registro.
  E3. Si el socio esta en estado **Baja** -> deniega acceso y notifica 
---

## UC8: Registrar Salida

* **Actores:** Operador
* **Pre:** Socio Activo
* **Post** Registro de la salida del socio al gym.

* **Flujo principal**:

    1. El socio presenta credencial al salir.
    2. El operador ingresa al sistema los datos de identificación del socio.
    3. El sistema busca el último registro de entrada abierto (sin salida).
    4. El sistema completa el campo *salida* con la hora actual.
    5. El sistema confirma que la salida fue registrada.

* **Excepciones**:
 E1. Si no hay un registro abierto → el sistema avisa al operador que no se puede registrar la salida.

---
## UC9: Cerrar Grupo Familiar
* **Actores:** Operador.
* **Pre:** grupo familiar activo, no debe regsitrar deudas.
* **Post:** grupo familiar inactivo con sus socios dados de baja.

* **Flujo principal:**
    1. El Operador selecciona el grupo a cerrar.
    2. El Sistema muestra integrantes actuales (incluido el titular) y alerta si hay más de un integrante.
    3. El Operador confirma el cierre.
    4. El Sistema marca el grupo como **inactivo** y da de **baja** todas las integraciones.
    5. El Sistema emite confirmación del cierre.

* **Excepciones**
 E1. **Deudas pendientes** → notificar y mostrar motivo.


## UC10 — Consultar accesos del mes

* **Actores**: Operador
* **Pre**: –
* **Post**: Lista de accesos por socio/mes; totales, primeras/últimas visitas.
* **Flujo**

  1. Operador ingresa filtros (socio, mes).
  2. Sistema devuelve accesos con métricas.

---

## UC11 — Listar integrantes del grupo

* **Actores**: Operador
* **Pre**: Grupo existente.
* **Post**: Lista actual de integrantes.
* **Flujo**

  1. Operador elige grupo.
  2. Sistema muestra titular y adherentes (nombre,apellido,telefono,etc).

---